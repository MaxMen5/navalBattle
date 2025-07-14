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


//        if (table.getValueAt(row, column) == "X") {
//            if (isOnce(table, row, column)) {
//                setBorder(BorderFactory.createCompoundBorder(
//                    BorderFactory.createMatteBorder(4, 4, 4, 4, Color.RED),
//                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
//                ));
//            }

//            else if (isCenter(table, row, column) == 1) {
//                setBorder(BorderFactory.createCompoundBorder(
//                        BorderFactory.createMatteBorder(0, 4, 0, 4, Color.RED),
//                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
//                ));
//            }
//            else if (isCenter(table, row, column) == 2) {
//                setBorder(BorderFactory.createCompoundBorder(
//                        BorderFactory.createMatteBorder(4, 0, 4, 0, Color.RED),
//                        BorderFactory.createEmptyBorder(0, 0, 0, 0)
//                ));
//            }


        //}

        return this;
    }

//    private boolean isOnce(JTable table, int row, int column) {
//        if (table.getValueAt(row - 1, column) == "X") return false;
//        if (table.getValueAt(row , column - 1) == "X") return false;
//        if (row != table.getRowCount() - 1) {
//            if (table.getValueAt(row + 1, column) == "X") return false;
//        }
//        if (column != table.getColumnCount() - 1) {
//            if (table.getValueAt(row, column + 1) == "X") return false;
//        }
//        return true;
//    }

//    private int isCenter(JTable table, int row, int column) {
//        if (row == 9 || column == 9) return 0;
//        else if (table.getValueAt(row - 1, column) == "X" &&
//                table.getValueAt(row + 1, column) == "X") return 1;
//        else if (table.getValueAt(row, column - 1) == "X" &&
//                table.getValueAt(row, column + 1) == "X") return 2;
//        else return 0;
//    }
}