package ru.eltech.entity;

import ru.eltech.enums.ShipLayout;
import ru.eltech.enums.ShipLength;

public class ShipManager {

    Ship[] ship = new Ship[10];

    public ShipManager() {
        for (int i = 0; i < ship.length; i++) {
            ship[i] = null;
        }
    }

    public void addShip(ShipLength shipLength, ShipLayout shipLayout) {
        for (int i = 0; i < ship.length; i++) {
            if (ship[i] == null) ship[i] = new Ship(shipLength, shipLayout, i);
        }
    }
}
