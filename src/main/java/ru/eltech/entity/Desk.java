package ru.eltech.entity;

import ru.eltech.enums.CheckerFill;
import ru.eltech.enums.Layout;
import ru.eltech.exceptions.IncorrectLocationEx;

public class Desk {

    public Checker[][] matrix;
    private ShipManager shipManager;


    public Desk() {
        matrix = new Checker[10][10];
        shipManager = new ShipManager();
    }

    public boolean attack(int x, int y) {
        if (matrix[x][y].isShip) {
            matrix[x][y].checkerFill = CheckerFill.HIT;
            return true;
        }
        else {
            matrix[x][y].checkerFill = CheckerFill.MISS;
            return false;
        }
    }

    public void addShip(int index, int x, int y) throws IncorrectLocationEx {
        if (!possibleShip(x, y, shipManager.ship[index])) {
            throw new IncorrectLocationEx("Некорректное расположение корабля!");
        }
        for (int i = 0; i < shipManager.ship[index].length.getSize(); i++) {
            matrix[x][y].addShip();
            if (shipManager.ship[index].layout == Layout.HORIZONTAL) x++;
            else y++;
        }
    }

    private boolean possibleShip(int x, int y, Ship ship) {
        if (matrix[x][y].isShip) return false;
        for (int i = 0; i < ship.length.getSize(); i++) {
            if (x > 10 || y > 10) return false;
            if (checkerCheck(x, y)) return false;
            if (ship.layout == Layout.HORIZONTAL) x++;
            else y++;
        }
        return true;
    }

    private boolean checkerCheck(int x, int y) {
        return ((y != 10 && matrix[x][y + 1].isShip) ||
                (y != 0 && matrix[x][y - 1].isShip) ||
                (x != 10 && matrix[x + 1][y].isShip) ||
                (x != 0 && matrix[x - 1][y].isShip) ||
                (x != 10 && y != 10 && matrix[x + 1][y + 1].isShip) ||
                (x != 10 && y != 0 && matrix[x + 1][y - 1].isShip) ||
                (x != 0 && y != 10 && matrix[x - 1][y + 1].isShip) ||
                (x != 0 && y != 0 && matrix[x - 1][y - 1].isShip));
    }
}
