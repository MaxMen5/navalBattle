package ru.eltech.entity;

import ru.eltech.enums.CheckerFill;

public class Checker {

    public CheckerFill checkerFill;
    public boolean isShip;

    public Checker() {
        checkerFill = CheckerFill.UNKNOWN;
        isShip = false;
    }

    public void addShip() {
        isShip = true;
    }

    public void choose() {
        if (isShip) checkerFill = CheckerFill.HIT;
        else checkerFill = CheckerFill.MISS;
    }
}
