package GUI.CustomComponents;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ExitButton extends JButton {

    public ExitButton() {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        Border thickBorder = BorderFactory.createLineBorder(Color.BLACK, 4);
        setBorder(thickBorder);
        setRolloverEnabled(false);
        setPressedIcon(null);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/exit.jpg"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
    }
}
