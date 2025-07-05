package ru.eltech.GUI;


import ru.eltech.GUI.renderers.BlockedRenderer;
import ru.eltech.GUI.renderers.ComputerRenderer;
import ru.eltech.GUI.renderers.PlayerRenderer;
import ru.eltech.entity.Desk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.locks.ReentrantLock;

public class MainFrame extends JDialog {

    private PlayTable playerTable;
    private PlayTable computerTable;
    private LogPane logPane = new LogPane();

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
        PlayerRenderer playerRenderer = new PlayerRenderer();
        ComputerRenderer computerRenderer = new ComputerRenderer();
        BlockedRenderer blockedRenderer = new BlockedRenderer();
        playerTable.table.setDefaultRenderer(Object.class, playerRenderer);
        computerTable.table.setDefaultRenderer(Object.class, blockedRenderer);

        Desk playerDesk = new Desk();
        Desk computerDesk = new Desk();
        computerDesk.createAuto();

        playerTable.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int row = playerTable.table.rowAtPoint(p);
                int col = playerTable.table.columnAtPoint(p);

                if (row >= 1 && col >= 1) {
                    if (playerTable.table.getModel().getValueAt(row, col) == null) {
                        playerTable.table.getModel().setValueAt("Х", row, col);
                    }
                    else playerTable.table.getModel().setValueAt(null, row, col);
                    playerDesk.changeMatrix(row, col);
                }
                if (row == 0 && col == 0) {
                    boolean[][] matrix = new boolean[10][10];
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            if (playerTable.table.getModel().getValueAt(i + 1, j + 1) == null) matrix[i][j] = false;
                            else matrix[i][j] = true;
                        }
                    }
                    playerDesk.playerLayout(matrix);
                    if (playerDesk.checkMatrix()) startGame();
                    else logPane.errorShipLayout();
                }
            }
        });
    }

    private void startGame() {
        logPane.setText("\nGOOD");
    }
}