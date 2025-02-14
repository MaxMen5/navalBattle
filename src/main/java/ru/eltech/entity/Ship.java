package ru.eltech.entity;

import ru.eltech.enums.ShipLayout;
import ru.eltech.enums.ShipLength;

public class Ship {

    public ShipLength shipLength;
    public ShipLayout shipLayout;
    private boolean[] status;
    public boolean isAlive;
    public int index;

    Ship(ShipLength shipLength, ShipLayout shipLayout, int index) {
        this.shipLength = shipLength;
        this.shipLayout = shipLayout;
        this.index = index;
        status = new boolean[shipLength.getSize()];
        for (int i = 0; i < shipLength.getSize(); i++) status[i] = true;
        isAlive = true;
    }

    private void Injury(Integer index) {
        status[index] = false;
        for (int i = 0; i < shipLength.getSize(); i++) if (status[i]) return;
        isAlive = false;
    }
}