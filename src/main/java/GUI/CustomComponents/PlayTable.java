package GUI.CustomComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlayTable extends JPanel {
    public JTable table;
    public static String[] headers = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П"};
    public PlayTable(int size) {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(size + 1, size + 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setTableHeader(null);


        table.setIntercellSpacing(new Dimension(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(table, BorderLayout.CENTER);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setSelectionBackground(new Color(0, 0, 0, 0));
        table.setGridColor(new Color(200, 200, 200));

        for (int i = 0; i < table.getRowCount(); i++) {
            model.setValueAt(i, i, 0);
            model.setValueAt(headers[i], 0, i);
        }
        fixTableSizes();

    }

    private void fixTableSizes() {
        int cellSize = 45;
        table.setRowHeight(cellSize);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setMinWidth(cellSize);
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
            table.getColumnModel().getColumn(i).setMaxWidth(cellSize);
        }
        table.setPreferredScrollableViewportSize(
                new Dimension(table.getColumnCount() * cellSize,
                        table.getRowCount() * cellSize));
    }
}