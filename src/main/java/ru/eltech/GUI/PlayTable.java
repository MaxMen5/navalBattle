package ru.eltech.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlayTable extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public PlayTable() {
        setLayout(new BorderLayout());
        String[] columnHeaders = {" ", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};

        model = new DefaultTableModel();
        model.setRowCount(11);
        model.setColumnCount(11);
        for (int i = 1; i < 11; i++) {
            model.setValueAt(i, i, 0);
            model.setValueAt(columnHeaders[i], 0, i);
        }

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}