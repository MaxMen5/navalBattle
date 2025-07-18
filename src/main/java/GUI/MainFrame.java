package GUI;

import GUI.renderers.SelectedRenderer;
import GUI.renderers.ShipAndBlockRenderer;
import entity.Desk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Random;

import static GUI.PlayTable.headers;
import static utils.ComputerMoves.*;

public class MainFrame extends JDialog {

    public int size;

    private PlayTable playerTable;
    private PlayTable computerTable;
    private Desk playerDesk;
    private Desk computerDesk;

    private MouseAdapter playerTableMouseAdapter;

    private int yourShip = 20;
    private int compShip = 20;

    private boolean isPlayerTurn = true;
    private boolean computerProcess = false;
    private boolean alterComputerMove = false;

    private final LogPane logPane = new LogPane();
    private final SelectedRenderer playerRenderer = new SelectedRenderer(true);
    private final SelectedRenderer computerRenderer = new SelectedRenderer(false);
    private final ShipAndBlockRenderer blockedRenderer = new ShipAndBlockRenderer();
    private ShipAndBlockRenderer shipRenderer;

    public MainFrame(int size) {
        this.size = size;
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
        // Получаем цвет фона основного окна
        Color bgColor = getBackground();

        playerTable = new PlayTable(size);
        computerTable = new PlayTable(size);

        // Создаем панели таблиц с заголовками
        JPanel playerPanel = createTablePanel("Ваше поле", playerTable, bgColor);
        JPanel computerPanel = createTablePanel("Поле противника", computerTable, bgColor);

        // Настраиваем панель логов
        logPane.setPreferredSize(new Dimension(0, 100));
        logPane.setMinimumSize(new Dimension(0, 50));
        logPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        JScrollPane logScrollPane = new JScrollPane(logPane);
        logScrollPane.setBackground(bgColor);

        // Создаем контейнер для панели логов с ограниченной шириной
        JPanel logContainer = new JPanel(new BorderLayout());
        logContainer.setBackground(bgColor);
        logContainer.add(logScrollPane, BorderLayout.CENTER);
        int tablesWidth = playerTable.getPreferredSize().width * 2 + 30; // Ширина двух таблиц + отступ
        logContainer.setPreferredSize(new Dimension(tablesWidth, 100));
        logContainer.setMaximumSize(new Dimension(tablesWidth, 150));

        // Основной контейнер с GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // 1. Верхняя панель с таблицами
        JPanel tablesPanel = new JPanel(new BorderLayout());
        tablesPanel.setBackground(bgColor);

        JPanel tablesContainer = new JPanel(new GridLayout(1, 2, 30, 0));
        tablesContainer.setBackground(bgColor);
        tablesContainer.add(computerPanel);
        tablesContainer.add(playerPanel);

        tablesPanel.add(tablesContainer, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        add(tablesPanel, gbc);

        // 2. Панель логов (теперь в центрирующем контейнере)
        JPanel logWrapper = new JPanel(new GridBagLayout());
        logWrapper.setBackground(bgColor);
        logWrapper.add(logContainer);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(logWrapper, gbc);

        shipLayout();
    }

    private JPanel createTablePanel(String title, JComponent table, Color bgColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgColor);

        // Заголовок с отступом
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        label.setBackground(bgColor);
        label.setOpaque(true);
        panel.add(label, BorderLayout.NORTH);

        // Таблица в центрирующей панели
        JPanel tableContainer = new JPanel(new GridBagLayout());
        tableContainer.setBackground(bgColor);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(bgColor);

        tableContainer.add(scrollPane);
        panel.add(tableContainer, BorderLayout.CENTER);

        return panel;
    }

    private void shipLayout() {
        playerTable.table.setDefaultRenderer(Object.class, playerRenderer);
        computerTable.table.setDefaultRenderer(Object.class, blockedRenderer);

        playerDesk = new Desk(size);
        computerDesk = new Desk(size);

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
                    boolean[][] matrix = new boolean[size][size];
                    for (int i = 0; i < size; i++) {
                        for (int j = 0; j < size; j++) {
                            matrix[i][j] = playerTable.table.getModel().getValueAt(i + 1, j + 1) != null;
                        }
                    }
                    playerDesk.playerLayout(matrix);    
                    if (playerDesk.checkMatrix()) {
                        logPane.start();
                        logPane.playerTurn();
                        playerTable.table.removeMouseListener(playerTableMouseAdapter);
                        shipRenderer = new ShipAndBlockRenderer(playerDesk);
                        playerTable.table.setDefaultRenderer(Object.class, shipRenderer);
                        computerTable.table.setDefaultRenderer(Object.class, computerRenderer);

                        for (int i = 1; i <= size; i++) {
                            for (int j = 1; j <= size; j++) {
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
        Timer timer = new Timer(1000, ev -> {
            ((Timer) ev.getSource()).stop();
            int row, col;
            Random rand = new Random();
            if (!alterComputerMove) {
                do {
                    row = rand.nextInt(size) + 1;
                    col = rand.nextInt(size) + 1;
                } while (playerTable.table.getModel().getValueAt(row, col) != null);
            }
            else {
                do {
                    int[] point = alterMove();
                    row = ++point[0];
                    col = ++point[1];
                } while (playerTable.table.getModel().getValueAt(row, col) != null);
            }
            int shot = playerDesk.shot(col, row);
            if (shot == 0) { // мимо
                playerTable.table.getModel().setValueAt('*', row, col);
                logPane.computerMiss(row, headers[col]);
                isPlayerTurn = true;
                logPane.playerTurn();
            }
            if (shot == 1) { // ранение
                alterComputerMove = true;
                addPoints(col - 1, row - 1);
                playerTable.table.getModel().setValueAt("X", row, col);
                logPane.computerHit(row, headers[col]);
                yourShip--;
                logPane.computerTurn();
                computerMove();
            }
            if (shot == 2) { // потопление
                alterComputerMove = false;
                cleanPoints();
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
        if (col < size && row < size && table.table.getModel().getValueAt(row + 1, col + 1) == null) {
            table.table.getModel().setValueAt('*', row + 1, col + 1);
        }
        if (col < size && row > 1 && table.table.getModel().getValueAt(row - 1, col + 1) == null) {
            table.table.getModel().setValueAt('*', row - 1, col + 1);
        }
        if (col > 1 && row < size && table.table.getModel().getValueAt(row + 1, col - 1) == null) {
            table.table.getModel().setValueAt('*', row + 1, col - 1);
        }
        if (col > 1 && row > 1 && table.table.getModel().getValueAt(row - 1, col - 1) == null) {
            table.table.getModel().setValueAt('*', row - 1, col - 1);
        }
        if (col < size) {
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
        if (row < size) {
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