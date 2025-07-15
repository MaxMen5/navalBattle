package GUI.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CommonRenderer {
    public static void applyCommonStyles(JTable table, DefaultTableCellRenderer renderer, int row, int column) {

        renderer.setFont(new Font("Arial", Font.BOLD, 24));
        renderer.setHorizontalAlignment(JLabel.CENTER);

        if (column == 10) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Внутренние отступы
            ));
        }
        if (row == 10) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Внутренние отступы
            ));
        }
        if (row == 10 && column == 10) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (column == 0) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 0, 4, 0, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0 && column == 0) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0 && column == 10) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 0, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 10 && column == 0) {
            renderer.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
    }
}
