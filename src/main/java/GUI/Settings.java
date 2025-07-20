package GUI;

import GUI.CustomComponents.DirectionButton;
import GUI.CustomComponents.ExitButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends JFrame {

    private static final int MAX = 12;
    private static final int MIN = 7;

    private JButton moreButton;
    private JButton lessButton;
    private JLabel sizeLabel;
    private final JLabel[] shipLabels = new JLabel[4];
    private JButton startButton;
    private JButton backButton;
    private int size = 10;
    private final String[] labels = {"Четырехпалубников: ", "Трехпалубников: ", "Двухпалубников: ", "Однопалубников: "};
    private final int[][] sizeArr = {{0, 1, 2, 3}, {1, 1, 1, 4}, {1, 1, 2, 5}, {1, 2, 3, 4}, {1, 3, 3, 5}, {2, 3, 3, 6}};
    // 9 - 16, 8 - 13, 7 - 10, 11 - 24, 12 - 29

    public Settings(Menu menu) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setUndecorated(true);
        setLocationRelativeTo(null);

        createGUI();

        moreButton.addActionListener(e -> {
            if (size < MAX) size++;
            lessButton.setEnabled(size != MIN);
            moreButton.setEnabled(size != MAX);
            sizeLabel.setText(String.valueOf(size));
            for (int i = 0; i < 4; i++) shipLabels[i].setText(labels[i] + sizeArr[size - 7][i]);
        });

        lessButton.addActionListener(e -> {
            if (size > MIN) size--;
            lessButton.setEnabled(size != MIN);
            moreButton.setEnabled(size != MAX);
            sizeLabel.setText(String.valueOf(size));
            for (int i = 0; i < 4; i++) shipLabels[i].setText(labels[i] + sizeArr[size - 7][i]);
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new GameFrame(menu, size, sizeArr[size - 7]);
                dispose();
            }
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                menu.setVisible(true);
                dispose();
            }
        });

        setVisible(true);
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Надпись сверху
        JLabel titleLabel = new JLabel("Выберите размер игрового поля", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Панель с цифрой и кнопками
        JPanel counterPanel = new JPanel();
        counterPanel.setLayout(new BoxLayout(counterPanel, BoxLayout.X_AXIS));
        counterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sizeLabel = new JLabel(String.valueOf(size), SwingConstants.CENTER);
        sizeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sizeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Кнопки
        lessButton = new DirectionButton(false);
        moreButton = new DirectionButton(true);

        counterPanel.add(lessButton);
        counterPanel.add(sizeLabel);
        counterPanel.add(moreButton);
        mainPanel.add(counterPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Четыре запии друг под другом
        JPanel entriesPanel = new JPanel();
        entriesPanel.setLayout(new BoxLayout(entriesPanel, BoxLayout.Y_AXIS));
        entriesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < 4; i++) {
            JLabel entryLabel = new JLabel();
            entryLabel.setText(labels[i] + (sizeArr[3][i]));
            entryLabel.setFont(new Font("Arial", Font.BOLD, 24));
            entryLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            entryLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            entriesPanel.add(entryLabel);
            entriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            shipLabels[i] = entryLabel;
        }

        mainPanel.add(entriesPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 4. Панель с кнопками "Назад" и "Начать игру"
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new ExitButton();
        buttonsPanel.add(backButton);

        buttonsPanel.add(Box.createHorizontalGlue());

        startButton = new JButton("Начать игру");
        startButton.setFont(new Font("Arial", Font.BOLD, 18));
        startButton.setFocusPainted(false);
        buttonsPanel.add(startButton);

        mainPanel.add(buttonsPanel);

        add(mainPanel);
    }
}
