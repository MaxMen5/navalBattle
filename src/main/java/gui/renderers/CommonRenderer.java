package gui.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CommonRenderer {

    public static void applyCommonStyles(JTable table, DefaultTableCellRenderer renderer, int row, int column) {
        renderer.setFont(new Font("Arial", Font.BOLD, 24));
        renderer.setHorizontalAlignment(JLabel.CENTER);
        int size = table.getRowCount() - 1;

        if (column == size) setBorder(0, 0, 0, 4, renderer);
        if (row == size) setBorder(0, 0, 4, 0, renderer);
        if (row == size && column == size) setBorder(0, 0, 4, 4, renderer);
        if (column == 0) setBorder(0, 4, 0, 4, renderer);
        if (row == 0) setBorder(4, 0, 4, 0, renderer);
        if (row == 0 && column == 0) setBorder(4, 4, 4, 4, renderer);
        if (row == 0 && column == size) setBorder(4, 0, 4, 4, renderer);
        if (row == size && column == 0) setBorder(0, 4, 4, 4, renderer);
    }

    private static void setBorder(int top, int left, int bottom, int right, DefaultTableCellRenderer renderer) {
        renderer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK),
                BorderFactory.createEmptyBorder(0, 5, 0, 5)
        ));
    }

}
