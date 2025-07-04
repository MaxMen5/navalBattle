package ru.eltech.GUI;


import ru.eltech.GUI.renderers.ColorRenderer;
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
        ColorRenderer renderer = new ColorRenderer();
        playerTable.table.setDefaultRenderer(Object.class, renderer);
        Desk playerDesk = new Desk();
        Desk computerDesk = new Desk();
        computerDesk.createAuto();

        final ReentrantLock clickLock = new ReentrantLock();

        playerTable.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!clickLock.tryLock()) return; // Игнорируем новые клики во время обработки

                try {
                    Point p = e.getPoint();
                    int row = playerTable.table.rowAtPoint(p);
                    int col = playerTable.table.columnAtPoint(p);

                    SwingUtilities.invokeLater(() -> {
                        if (row >= 1 && col >= 1) {
                            renderer.toggleCellColor(row, col);
                            playerTable.table.repaint(row, row, col, col);
                            playerDesk.changeMatrix(row, col);
                        }
                        if (row == 0 && col == 0) {
                            if (playerDesk.checkMatrix()) startGame();
                            else logPane.errorShipLayout();
                        }
                    });
                } finally {
                    clickLock.unlock();
                }
            }
        });
    }

    private void startGame() {
        logPane.setText("\nGOOD");
    }
}