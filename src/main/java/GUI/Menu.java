package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Меню");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton classic = new JButton("Классическая игра");
        classic.setFocusPainted(false);
        JButton mod = new JButton("Настраиваемая игра");
        mod.setFocusPainted(false);

        classic.setAlignmentX(Component.CENTER_ALIGNMENT);
        mod.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(classic);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Фиксированный отступ
        mainPanel.add(mod);
        mainPanel.add(Box.createVerticalGlue());

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);

        classic.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int[] normalArr = {1, 2, 3, 4};
                new GameFrame(Menu.this, 10, normalArr);
                setVisible(false);
            }
        });

        mod.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                new Settings(Menu.this);
                setVisible(false);
            }
        });
    }
}
