package ru.eltech.GUI;

import ru.eltech.GUI.renderers.ColorRenderer;
import ru.eltech.GUI.renderers.FontRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlayTable extends JPanel {
    public JTable table;
    private DefaultTableModel model;
    private static final int CELL_SIZE = 45;
    private static final int BORDER_THICKNESS = 4;

    public PlayTable() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(11, 11) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        table = new JTable(model);
        table.setTableHeader(null);
        table.setRowHeight(CELL_SIZE);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(table, BorderLayout.CENTER);

        int totalSize = 11 * CELL_SIZE + 2 * BORDER_THICKNESS;
        setPreferredSize(new Dimension(totalSize, totalSize));

        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setSelectionBackground(new Color(0, 0, 0, 0));
        table.setSelectionForeground(table.getForeground());
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        table.setIntercellSpacing(new Dimension(0, 0));

        FontRenderer renderer = new FontRenderer();
        table.setDefaultRenderer(Object.class, renderer);


        String[] headers = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};

        for (int i = 0; i < table.getRowCount(); i++) {
            model.setValueAt(i, i, 0);
            model.setValueAt(headers[i], 0, i);

        }
    }
}