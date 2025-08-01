package GUI.renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class SelectedRenderer extends DefaultTableCellRenderer {

    private final boolean isPlayer;

    public SelectedRenderer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        CommonRenderer.applyCommonStyles(table, this, row, column);

        if (isSelected && ((row > 0 && column > 0) || (row == 0 && column == 0 && isPlayer))) {
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK),
                    BorderFactory.createLineBorder(Color.BLUE, 4)));
        }

        return this;
    }
}
