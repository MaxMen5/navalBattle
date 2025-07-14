package ru.eltech.GUI.renderers;

import ru.eltech.entity.Desk;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

import static ru.eltech.GUI.renderers.CommonRenderer.applyCommonStyles;

public class ShipRenderer extends DefaultTableCellRenderer {

    Desk desk;
    public ShipRenderer(Desk desk) {this.desk = desk;}

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, false, false, row, column);
        applyCommonStyles(table, this, row, column);

        if (row > 0 && column > 0) setForeground(Color.RED);
        else setForeground(table.getForeground());

        if (row > 0 && column > 0 && desk.matrix[row-1][column-1]) setBackground(Color.BLACK);
        else setBackground(table.getBackground());

        return this;
    }
}