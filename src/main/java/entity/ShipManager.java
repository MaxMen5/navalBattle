package entity;

public class ShipManager {
    // Вспомогательный подкласс
    public static class Ship {
        int row = -1;
        int col = -1;
        int ship = -1;
    }

    public Ship[] matrix = new Ship[20];

    // Конструктор
    public ShipManager() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Ship();
        }
    }

    // Методы

    public void addPoint(int row, int col, int ship) {
        for (int i = 0; i < 20; i++) {
            if (matrix[i].col == -1) {
                matrix[i].col = col;
                matrix[i].row = row;
                matrix[i].ship = ship;
                break;
            }
        }
    }

    public int shot(int row, int col) {
        for (int i = 0; i < 20; i++) {
            if (matrix[i].col == col && matrix[i].row == row) {
                int ans = matrix[i].ship;
                matrix[i].ship = -1;
                return ans;
            }
        }
        return -1;
    }

    public boolean isSink(int ship) {
        for (int i = 0; i < 20; i++) {
            if (matrix[i].ship == ship) return false;
        }
        return true;
    }

}
