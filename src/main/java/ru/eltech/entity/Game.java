package ru.eltech.entity;

public class Game {

    public Desk desk;
    public Desk computerDesk;

    public boolean isReadyToStart = false;
    public boolean isComputerMove = false;

    public Game() {
        desk = new Desk();
        computerDesk = new Desk();
    }

    public void newGame() {
        desk = new Desk();
        computerDesk = new Desk();
    }

    public void Move(int x, int y) {
        if (isComputerMove) {
            boolean result = desk.attack(x, y);
            if (!result) isComputerMove = false;
        }
        else {
            boolean result = computerDesk.attack(x, y);
            if (!result) isComputerMove = true;
        }
    }
}
