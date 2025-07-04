package ru.eltech.GUI;

import javax.swing.*;
import java.awt.*;

public class LogPane extends JTextPane {
    public LogPane() {
        setFont(new Font("Arial", Font.BOLD, 24));
        setText("Расставьте корабли на своем поле. Нужно расставить: 1 четырехпалубник, " +
                "2 трехпалубника, 3 двухпалубника, 4 однопалубника, После завершения нажмите кнопку \"#\" на своем поле.");
    }

    public void errorShipLayout() {
        setText(getText() + "\nКорабли расставлены неверно, попробуйте еще раз!");
    }

    public void playerMiss() {
        setText(getText() + "\nВы промахнулись!");
    }

    public void computerMiss() {
        setText(getText() + "\nКомпьютер промахнулся!");
    }

    public void playerHit() {
        setText(getText() + "\nВы попали в корабль!");
    }

    public void computerHit() {
        setText(getText() + "\nКомпьютер попал в корабль!");
    }

    public void playerSink() {
        setText(getText() + "\nВы потопили корабль!");
    }

    public void computerSink() {
        setText(getText() + "\nКомпьютер потопил корабль!");
    }

    public void playerWin() {
        setText(getText() + "\nВы победили!");
    }

    public void computerWin() {
        setText(getText() + "\nКомпьютер победил!");
    }
}
