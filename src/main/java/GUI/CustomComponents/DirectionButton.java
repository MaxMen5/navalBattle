package GUI.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class DirectionButton extends JButton {

    public DirectionButton(boolean isRight) {
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setRolloverEnabled(false);
        setPressedIcon(null);
        ImageIcon originalIcon;
        if (isRight) originalIcon = new ImageIcon(getClass().getResource("/images/right.png"));
        else originalIcon = new ImageIcon(getClass().getResource("/images/left.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
    }
}
