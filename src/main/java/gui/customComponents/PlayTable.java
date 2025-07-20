package gui.customComponents;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayTable extends JPanel {
    private JTable table;
    public final static String[] HEADERS = {"#", "А", "Б", "В", "Г", "Д", "Е", "Ж", "З", "И", "К", "Л", "М", "Н", "О", "П"};
    private static final int CELL_SIZE = 45;
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
            model.setValueAt(HEADERS[i], 0, i);
        }
        fixTableSizes();

    }

    private void fixTableSizes() {
        table.setRowHeight(CELL_SIZE);
        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            col.setMinWidth(CELL_SIZE);
            col.setPreferredWidth(CELL_SIZE);
            col.setMaxWidth(CELL_SIZE);
        }
        table.setPreferredScrollableViewportSize(
                new Dimension(table.getColumnCount() * CELL_SIZE,
                        table.getRowCount() * CELL_SIZE));
    }

    public Object getValue(int row, int col) {
        return table.getModel().getValueAt(row, col);
    }
    public void setRenderer(DefaultTableCellRenderer renderer) {
        table.setDefaultRenderer(Object.class, renderer);
    }
    public int getRow(Point p) {
        return table.rowAtPoint(p);
    }
    public int getCol(Point p) {
        return table.columnAtPoint(p);
    }
    public void hit(int row, int col) {
        table.getModel().setValueAt("X", row, col);
        table.repaint();
    }
    public void specialHit(int row, int col) { // Русская Х стоит
        table.getModel().setValueAt("Х", row, col);
    }
    public void miss(int row, int col) {
        table.getModel().setValueAt("*", row, col);
    }
    public void setNull(int row, int col) {
        table.getModel().setValueAt(null, row, col);
    }
    public void addMouse(MouseAdapter adapter) {
        table.addMouseListener(adapter);
    }
    public void removeMouse(MouseAdapter adapter) {
        table.removeMouseListener(adapter);
    }

}