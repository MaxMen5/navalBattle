package ru.eltech.GUI;


import ru.eltech.GUI.renderers.BlockedRenderer;
import ru.eltech.GUI.renderers.ComputerRenderer;
import ru.eltech.GUI.renderers.PlayerRenderer;
import ru.eltech.GUI.renderers.ShipRenderer;
import ru.eltech.entity.Desk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JDialog {

    private PlayTable playerTable;
    private PlayTable computerTable;

    private Desk playerDesk;
    private Desk computerDesk;

    private MouseAdapter playerTableMouseAdapter;
    private MouseAdapter computerTableMouseAdapter;

    private final LogPane logPane = new LogPane();
    private final PlayerRenderer playerRenderer = new PlayerRenderer();
    private final ComputerRenderer computerRenderer = new ComputerRenderer();
    private final BlockedRenderer blockedRenderer = new BlockedRenderer();
    private ShipRenderer shipRenderer;

    public MainFrame() {
        setTitle("Морской бой");
        createGUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(
                        MainFrame.this,
                        "Прогресс будет потерян! Вы действительно хотите выйти?",
                        "Выход",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGUI() {
        playerTable = new PlayTable();
        computerTable = new PlayTable();

        JPanel playerPanel = createTablePanel("Ваше поле", playerTable);
        JPanel computerPanel = createTablePanel("Поле противника", computerTable);

        int tablePanelWidth = playerTable.getPreferredSize().width;
        int tablePanelHeight = playerTable.getPreferredSize().height;

        playerPanel.setPreferredSize(new Dimension(tablePanelWidth, tablePanelHeight + 25));
        computerPanel.setPreferredSize(new Dimension(tablePanelWidth, tablePanelHeight + 25));

        logPane.setPreferredSize(new Dimension(tablePanelWidth * 2, 100)); // Ширина двух таблиц
        JScrollPane logScrollPane = new JScrollPane(logPane);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 120, 10, 120);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(computerPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(playerPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        add(logScrollPane, gbc);

        shipLayout();
    }

    private JPanel createTablePanel(String title, JComponent table) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 24));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(table);

        return panel;
    }

    private void shipLayout() {
        playerTable.table.setDefaultRenderer(Object.class, playerRenderer);
        computerTable.table.setDefaultRenderer(Object.class, blockedRenderer);

        computerDesk = new Desk();
        playerDesk = new Desk();

        computerDesk.createAuto();

        playerTableMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int row = playerTable.table.rowAtPoint(p);
                int col = playerTable.table.columnAtPoint(p);

                if (row >= 1 && col >= 1) {
                    if (playerTable.table.getModel().getValueAt(row, col) == null) {
                        playerTable.table.getModel().setValueAt("X", row, col);
                    } else playerTable.table.getModel().setValueAt(null, row, col);
                    playerDesk.changeMatrix(row, col);
                }
                if (row == 0 && col == 0) {
                    boolean[][] matrix = new boolean[10][10];
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (playerTable.table.getModel().getValueAt(i + 1, j + 1) == null)
                                matrix[i][j] = false;
                            else matrix[i][j] = true;
                        }
                    }
                    playerDesk.playerLayout(matrix);
                    if (playerDesk.checkMatrix()) {
                        logPane.start();
                        playerTable.table.removeMouseListener(playerTableMouseAdapter);
                        shipRenderer = new ShipRenderer();
                        playerTable.table.setDefaultRenderer(Object.class, shipRenderer);
                        computerTable.table.setDefaultRenderer(Object.class, computerRenderer);
                        startGame();
                    }
                    else logPane.errorShipLayout();
                }
            }
        };

        playerTable.table.addMouseListener(playerTableMouseAdapter);
    }

    private void startGame() {

        String[] headers = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};
        int yourShip = 20;
        int compShip = 20;

        computerTableMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int row = playerTable.table.rowAtPoint(p);
                int col = playerTable.table.columnAtPoint(p);

                if (computerTable.table.getModel().getValueAt(row, col) == null) {
                    int shot = computerDesk.shot(col, row);
                    if (shot == 0) { // мимо
                        computerTable.table.getModel().setValueAt('*', row, col);
                        logPane.playerMiss(row, headers[col]);
                    }
                    if (shot == 1) { // ранение
                        computerTable.table.getModel().setValueAt('X', row, col);
                        logPane.playerHit(row, headers[col]);
                    }
                    if (shot == 2) { // потопление
                        computerTable.table.getModel().setValueAt('X', row, col);
                        logPane.playerSink(row, headers[col]);
                        // TODO РАССТАВИТЬ *
                    }
                }
            }
        };

        computerTable.table.addMouseListener(computerTableMouseAdapter);
    }
}