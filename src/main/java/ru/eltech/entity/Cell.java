package ru.eltech.entity;

import ru.eltech.enums.CellStatus;

public class Cell {

    public CellStatus cellStatus;
    public boolean isShip;

    public Cell() {
        cellStatus = CellStatus.UNKNOWN;
        isShip = false;
    }

    public void addShip() {
        isShip = true;
    }

    public void openCell() {
        if (isShip) cellStatus = CellStatus.HIT;
        else cellStatus = CellStatus.MISS;
    }
}
