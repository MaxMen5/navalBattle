package GUI;

import GUI.CustomComponents.DirectionButton;
import GUI.CustomComponents.ExitButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends JFrame {

    private static final int MAX = 12;
    private static final int MIN = 9;

    private JButton moreButton;
    private JButton lessButton;
    private JLabel sizeLabel;
    private JButton startButton;
    private JButton backButton;
    private int size = 10;

    private String[] labels = {"Четырехпалубников: ", "Трехпалубников: ", "Двухпалубников: ", "Однопалубников: "};
    private int[] size10 = {1, 2, 3, 4};

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
        });

        lessButton.addActionListener(e -> {
            if (size > MIN) size--;
            lessButton.setEnabled(size != MIN);
            moreButton.setEnabled(size != MAX);
            sizeLabel.setText(String.valueOf(size));
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new GameFrame(menu, Integer.parseInt(sizeLabel.getText()));
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

        for (int row = 0; row < 4; row++) {
            JLabel entryLabel = new JLabel();
            entryLabel.setText(labels[row] + (size10[row]));
            entryLabel.setFont(new Font("Arial", Font.BOLD, 24));
            entryLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
            entryLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            entriesPanel.add(entryLabel);
            entriesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
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
