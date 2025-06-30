package ru.eltech.GUI.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ColorRenderer extends DefaultTableCellRenderer {
    private final Set<Point> coloredCells = new HashSet<>();

    public void toggleCellColor(int row, int col) {
        Point cell = new Point(row, col);
        if (coloredCells.contains(cell)) {
            coloredCells.remove(cell);
        } else {
            coloredCells.add(cell);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setFont(new Font("Arial", Font.BOLD, 24));
        setHorizontalAlignment(JLabel.CENTER);

        if (coloredCells.contains(new Point(row, column))) {
            setBackground(Color.RED);
        } else {
            setBackground(table.getBackground());
        }

        return this;
    }
}