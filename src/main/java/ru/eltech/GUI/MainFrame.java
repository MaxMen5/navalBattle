package ru.eltech.GUI;

import ru.eltech.entity.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JDialog {

    private PlayTable table;
    private PlayTable computerTable;
    private Game game = new Game();

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
        table = new PlayTable();
        computerTable = new PlayTable();
        JLabel label1 = new JLabel("Ваше поле");
        add(label1, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);

        computerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!game.isComputerMove) {
                    // int row = computerTable.rowAtPoint(e.getPoint());
                    // int col = computerTable.columnAtPoint(e.getPoint());
                    // boolean result = game.computerDesk.attack(row - 1, col - 1);
                }
            }
        });
    }
}
