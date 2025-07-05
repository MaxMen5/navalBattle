package ru.eltech.GUI.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static ru.eltech.GUI.renderers.CommonRenderer.applyCommonStyles;

public class PlayerRenderer extends DefaultTableCellRenderer {
    private final Set<Point> coloredCells = new HashSet<>();

    public void toggleCellColor(int row, int col) {
        Point cell = new Point(row, col);
        if (coloredCells.contains(cell)) {
            coloredCells.remove(cell);
        } else if (coloredCells.size() == 20) {
            return;
        }
        else {
            coloredCells.add(cell);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (coloredCells.contains(new Point(row, column))) {
            setBackground(Color.RED);
        } else {
            setBackground(table.getBackground());
        }

        applyCommonStyles(table, this, row, column);

        if (isSelected && ((row > 0 && column > 0) || (row == 0 && column == 0))) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK),
                    BorderFactory.createLineBorder(Color.BLUE, 4)));
        }

        return this;
    }
}