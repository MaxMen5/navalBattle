package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static GUI.MainFrame.size;

public class PlayTable extends JPanel {
    public JTable table;
    private static final int TABLE_SIZE = ++size;
    private static final int CELL_SIZE = 45;
    private static final int BORDER_THICKNESS = 4;
    public static String[] headers = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К"};

    public PlayTable() {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(TABLE_SIZE, TABLE_SIZE) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setTableHeader(null);
        table.setRowHeight(CELL_SIZE);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        add(table, BorderLayout.CENTER);

        int totalSize = TABLE_SIZE * CELL_SIZE + 2 * BORDER_THICKNESS;
        setPreferredSize(new Dimension(totalSize, totalSize));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setSelectionBackground(new Color(0, 0, 0, 0));
        table.setGridColor(new Color(200, 200, 200));

        for (int i = 0; i < table.getRowCount(); i++) {
            model.setValueAt(i, i, 0);
            model.setValueAt(headers[i], 0, i);
        }

    }
}