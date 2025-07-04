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

        setFont(new Font("Arial", Font.BOLD, 24));
        setHorizontalAlignment(JLabel.CENTER);

        if (coloredCells.contains(new Point(row, column))) {
            setBackground(Color.RED);
        } else {
            setBackground(table.getBackground());
        }

        if (column == 10) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Внутренние отступы
            ));
        }
        if (row == 10) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 4, 0, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0) // Внутренние отступы
            ));
        }
        if (row == 10 && column == 10) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (column == 0) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 0, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 0, 4, 0, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0 && column == 0) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 0 && column == 10) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(4, 0, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (row == 10 && column == 0) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 4, 4, 4, Color.BLACK),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5) // Внутренние отступы
            ));
        }
        if (isSelected && ((row > 0 && column > 0) || (row == 0 && column == 0))) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK),
                    BorderFactory.createLineBorder(Color.RED, 4)));
        }

        return this;
    }
}