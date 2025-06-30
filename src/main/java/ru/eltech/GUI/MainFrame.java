package ru.eltech.GUI;


import ru.eltech.GUI.renderers.ColorRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JDialog {

    private PlayTable playerTable;
    private PlayTable computerTable;
    private JTextPane logPane;

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
        logPane = new JTextPane();

        JPanel playerPanel = createTablePanel("Ваше поле", playerTable);
        JPanel computerPanel = createTablePanel("Поле противника", computerTable);

        int tablePanelWidth = playerTable.getPreferredSize().width;
        int tablePanelHeight = playerTable.getPreferredSize().height;

        playerPanel.setPreferredSize(new Dimension(tablePanelWidth, tablePanelHeight + 24));
        computerPanel.setPreferredSize(new Dimension(tablePanelWidth, tablePanelHeight + 24));

        JTextPane logPane = new JTextPane();
        logPane.setEditable(false);
        logPane.setPreferredSize(new Dimension(tablePanelWidth * 2, 100)); // Ширина двух таблиц
        JScrollPane logScrollPane = new JScrollPane(logPane);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 50, 10, 50);

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

        logPane.setText("Расставьте корабли на своем поле. Нужно расставить:\n1 четырехпалубник\n" +
                "2 трехпалубника\n3 двухпалубника\n4 однопалубника\nПосле завершения нажмите кнопку \"#\" на своем поле.");

        ColorRenderer renderer = new ColorRenderer();
        playerTable.table.setDefaultRenderer(Object.class, renderer);


        // Добавляем обработчик кликов
        playerTable.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = playerTable.table.rowAtPoint(e.getPoint());
                int col = playerTable.table.columnAtPoint(e.getPoint());

                if (row >= 1 && col >= 1) {

                    renderer.toggleCellColor(row, col);
                    playerTable.table.repaint();
                }
            }
        });
    }

    private JPanel createTablePanel(String title, JComponent table) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(table);

        return panel;
    }
}