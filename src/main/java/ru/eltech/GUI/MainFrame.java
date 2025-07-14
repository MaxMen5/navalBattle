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
import java.util.Random;

public class MainFrame extends JDialog {

    private PlayTable playerTable;
    private PlayTable computerTable;

    private Desk playerDesk;
    private Desk computerDesk;

    private MouseAdapter playerTableMouseAdapter;

    private int yourShip = 20;
    private int compShip = 20;
    Random rand = new Random();
    String[] headers = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};
    private boolean isPlayerTurn = true;
    private boolean computerProcess = false;

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
                            matrix[i][j] = playerTable.table.getModel().getValueAt(i + 1, j + 1) != null;
                        }
                    }
                    playerDesk.playerLayout(matrix);
                    if (playerDesk.checkMatrix(playerTable)) {
                        logPane.start();
                        logPane.playerTurn();
                        playerTable.table.removeMouseListener(playerTableMouseAdapter);
                        shipRenderer = new ShipRenderer(playerDesk);
                        playerTable.table.setDefaultRenderer(Object.class, shipRenderer);
                        computerTable.table.setDefaultRenderer(Object.class, computerRenderer);

                        for (int i = 1; i < 11; i++) {
                            for (int j = 1; j < 11; j++) {
                                if (playerTable.table.getModel().getValueAt(i, j) != null) {
                                    playerTable.table.getModel().setValueAt(null, i, j);
                                }
                            }
                        }
                        startGame();
                    }
                    else logPane.errorShipLayout();
                }
            }
        };

        playerTable.table.addMouseListener(playerTableMouseAdapter);
    }

    private void startGame() {
        MouseAdapter computerTableMouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = e.getPoint();
                int row = playerTable.table.rowAtPoint(p);
                int col = playerTable.table.columnAtPoint(p);

                if (isPlayerTurn) {
                    computerProcess = false;
                    if (computerTable.table.getModel().getValueAt(row, col) == null) {
                        int shot = computerDesk.shot(col, row);
                        if (shot == 0) { // мимо
                            computerTable.table.getModel().setValueAt('*', row, col);
                            logPane.playerMiss(row, headers[col]);
                            isPlayerTurn = false;
                            logPane.computerTurn();
                        }
                        if (shot == 1) { // ранение
                            computerTable.table.getModel().setValueAt("X", row, col);
                            logPane.playerHit(row, headers[col]);
                            compShip--;
                            logPane.playerTurn();
                        }
                        if (shot == 2) { // потопление
                            computerTable.table.getModel().setValueAt("X", row, col);
                            logPane.playerSink(row, headers[col]);
                            stars(row, col, computerTable);
                            compShip--;
                            if (compShip == 0) {
                                logPane.playerWin();
                                endGame();
                            }
                            logPane.playerTurn();
                        }
                    }
                }
                if (!isPlayerTurn && !computerProcess) {
                    computerProcess = true;
                    computerMove();
                }
            }
        };
        computerTable.table.addMouseListener(computerTableMouseAdapter);
    }

    private void computerMove() {
        //
        Timer timer = new Timer(1000, ev -> {
            ((Timer) ev.getSource()).stop();
            int row, col;
            do {
                row = rand.nextInt(10);
                col = rand.nextInt(10);
                row++;
                col++;
            } while (playerTable.table.getModel().getValueAt(row, col) != null);

            int shot = playerDesk.shot(col, row);
            if (shot == 0) { // мимо
                playerTable.table.getModel().setValueAt('*', row, col);
                logPane.computerMiss(row, headers[col]);
                isPlayerTurn = true;
                logPane.playerTurn();
            }
            if (shot == 1) { // ранение
                playerTable.table.getModel().setValueAt("X", row, col);
                logPane.computerHit(row, headers[col]);
                yourShip--;
                logPane.computerTurn();
                computerMove();
            }
            if (shot == 2) { // потопление
                playerTable.table.getModel().setValueAt("X", row, col);
                logPane.computerSink(row, headers[col]);
                stars(row, col, playerTable);
                yourShip--;
                if (yourShip == 0) {
                    logPane.computerWin();
                    endGame();
                }
                logPane.computerTurn();
                computerMove();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void stars(int row, int col, PlayTable table) {
        table.table.getModel().setValueAt("Х", row, col); // русская Х стоит, чтобы избежать лишней рекурсии
        if (col < 10 && row < 10 && table.table.getModel().getValueAt(row + 1, col + 1) == null) {
            table.table.getModel().setValueAt('*', row + 1, col + 1);
        }
        if (col < 10 && row > 1 && table.table.getModel().getValueAt(row - 1, col + 1) == null) {
            table.table.getModel().setValueAt('*', row - 1, col + 1);
        }
        if (col > 1 && row < 10 && table.table.getModel().getValueAt(row + 1, col - 1) == null) {
            table.table.getModel().setValueAt('*', row + 1, col - 1);
        }
        if (col > 1 && row > 1 && table.table.getModel().getValueAt(row - 1, col - 1) == null) {
            table.table.getModel().setValueAt('*', row - 1, col - 1);
        }
        if (col < 10) {
            if (table.table.getModel().getValueAt(row, col + 1) == null) {
                table.table.getModel().setValueAt('*', row, col + 1);
            }
            if (table.table.getModel().getValueAt(row, col + 1) == "X") {
                stars(row, col + 1, table);
            }
        }
        if (col > 1) {
            if (table.table.getModel().getValueAt(row, col - 1) == null) {
                table.table.getModel().setValueAt('*', row, col - 1);
            }
            if (table.table.getModel().getValueAt(row, col - 1) == "X") {
                stars(row, col - 1, table);
            }
        }
        if (row < 10) {
            if (table.table.getModel().getValueAt(row + 1, col) == null) {
                table.table.getModel().setValueAt('*', row + 1, col);
            }
            if (table.table.getModel().getValueAt(row + 1, col) == "X") {
                stars(row + 1, col, table);
            }
        }
        if (row > 1) {
            if (table.table.getModel().getValueAt(row - 1, col) == null) {
                table.table.getModel().setValueAt('*', row - 1, col);
            }
            if (table.table.getModel().getValueAt(row - 1, col) == "X") {
                stars(row - 1, col, table);
            }
        }
    }

    private void endGame() {
        JOptionPane.showConfirmDialog(
                MainFrame.this,
                "Конец игры!",
                "Поздравляем",
                JOptionPane.DEFAULT_OPTION);
        dispose();
    }

}