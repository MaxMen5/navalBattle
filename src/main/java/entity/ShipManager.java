package entity;

public class ShipManager {

    private static class Ship {
        int row = -1;
        int col = -1;
        int ship = -1;
    }

    private final Ship[] matrix;

    public ShipManager(int segments) {
        matrix = new Ship[segments];
        for (int i = 0; i < matrix.length; i++) matrix[i] = new Ship();
    }

    public void addPoint(int row, int col, int ship) {
        for (Ship value : matrix) {
            if (value.col == -1) {
                value.col = col;
                value.row = row;
                value.ship = ship;
                break;
            }
        }
    }

    public int shot(int row, int col) {
        for (Ship ship : matrix) {
            if (ship.col == col && ship.row == row) {
                int ans = ship.ship;
                ship.ship = -1;
                return ans;
            }
        }
        return -1;
    }

    public boolean isSink(int ship) {
        for (Ship value : matrix) {
            if (value.ship == ship) return false;
        }
        return true;
    }

    public boolean isContain(int row, int col) {
        for (Ship ship : matrix) {
            if (ship.ship == -1) return false;
            else if (ship.row == row && ship.col == col) return true;
        }
        return false;
    }

}
