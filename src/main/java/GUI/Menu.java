package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

    public Menu() {
        setTitle("Кнопки по центру");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton classic = new JButton("Классическая игра");
        JButton mod = new JButton("В разработке...");

        classic.setAlignmentX(Component.CENTER_ALIGNMENT);
        mod.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(classic);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Фиксированный отступ
        mainPanel.add(mod);
        mainPanel.add(Box.createVerticalGlue());

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JTextField sizeL = new JTextField("12");
        add(sizeL, BorderLayout.NORTH);

        setVisible(true);

        classic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(10);
            }
        });

        mod.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(Integer.parseInt(sizeL.getText()));
                // max 13
                // min 8? or 5
            }
        });
    }
}
