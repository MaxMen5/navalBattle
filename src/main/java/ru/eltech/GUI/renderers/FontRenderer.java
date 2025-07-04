package ru.eltech.GUI.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class FontRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setFont(new Font("Arial", Font.BOLD, 24));
        setHorizontalAlignment(JLabel.CENTER);

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
        if (isSelected && row > 0 && column > 0) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK),
                    BorderFactory.createLineBorder(Color.RED, 4)));
        }
        return this;
    }
}