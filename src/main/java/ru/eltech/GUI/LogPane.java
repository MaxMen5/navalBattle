package ru.eltech.GUI;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class LogPane extends JTextPane {

    StyledDocument doc = getStyledDocument();

    public LogPane() {
        setFont(new Font("Arial", Font.BOLD, 24));
        setText("Расставьте корабли на своем поле. Нужно расставить: 1 четырехпалубник, " +
                "2 трехпалубника, 3 двухпалубника, 4 однопалубника, После завершения нажмите кнопку \"#\" на своем поле.");
    }

    public void appendColoredText(String text, Color color) {
        try {
            Style style = doc.addStyle("ColorStyle", null);
            StyleConstants.setForeground(style, color);
            doc.insertString(doc.getLength(), text, style);
            setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            System.err.println("Ошибка добавления текста: " + e.getMessage());
        }
    }

    public void errorShipLayout() {
        appendColoredText("\nКорабли расставлены неверно, попробуйте еще раз!", Color.RED);
    }

    public void start() {
        appendColoredText("\nИгра началась! Вы ходите первым.", Color.BLACK);
    }

    public void playerTurn() {
        appendColoredText("\nВаш ход", Color.GRAY);
        appendColoredText(": ", Color.BLACK);
    }

    public void computerTurn() {
        appendColoredText("\nХод компьютера", Color.GRAY);
        appendColoredText(": ", Color.BLACK);
    }

    public void playerMiss(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Промах", Color.RED);
        appendColoredText("!", Color.BLACK);
    }

    public void computerMiss(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Промах", Color.GREEN);
        appendColoredText("!", Color.BLACK);
    }

    public void playerHit(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Ранение", Color.GREEN);
        appendColoredText("!", Color.BLACK);
    }

    public void computerHit(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Ранение", Color.RED);
        appendColoredText("!", Color.BLACK);
    }

    public void playerSink(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Потопление", Color.GREEN);
        appendColoredText("!", Color.BLACK);

    }

    public void computerSink(int x, String ch) {
        appendColoredText(ch + x + ". ", Color.BLACK);
        appendColoredText("Потопление", Color.RED);
        appendColoredText("!", Color.BLACK);
    }

    public void playerWin() {
        appendColoredText("\nВы ", Color.GRAY);
        appendColoredText("победили", Color.GREEN);
        appendColoredText("!", Color.BLACK);
    }

    public void computerWin() {
        appendColoredText("\nКомпьютер ", Color.GRAY);
        appendColoredText("победил", Color.RED);
        appendColoredText("!", Color.BLACK);
    }
}
