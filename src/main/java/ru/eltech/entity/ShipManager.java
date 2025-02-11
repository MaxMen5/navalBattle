package ru.eltech.entity;

import ru.eltech.enums.Layout;
import ru.eltech.enums.Length;

public class ShipManager {

    Ship[] ship = new Ship[10];

    public ShipManager() {
        for (int i = 0; i < ship.length; i++) {
            ship[i] = null;
        }
    }

    public void addShip(Length length, Layout layout) {
        for (int i = 0; i < ship.length; i++) {
            if (ship[i] == null) ship[i] = new Ship(length, layout, i);
        }
    }
}
