package ru.eltech.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PlayTable extends JTable {
    private final String[] str = {"", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};
    public PlayTable() {
        DefaultTableModel model = new DefaultTableModel(11, 11);
        setModel(model);

        for (int column = 0; column < getColumnCount(); column++) {
            getColumnModel().getColumn(column).setPreferredWidth(30);
        }
        setRowHeight(30);

        for (int i = 1; i < getRowCount(); i++) setValueAt(i, i, 0);
        for (int i = 1; i < getColumnCount(); i++) setValueAt(str[i], 0, i);
    }
}
