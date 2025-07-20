package entity;

import java.util.Random;

public class Desk {

    public boolean[][] matrix;
    int[] normalArr;
    int size;
    int countShip = 0;
    int segments;
    int magicValue = 0;

    Random rand = new Random();
    ShipManager shipManager;

    public Desk(int size, int[] shipArr, int segments) {
        this.size = size;
        this.matrix = new boolean[size][size];
        this.segments = segments;
        shipManager = new ShipManager(segments);
        for (int value : shipArr) countShip += value;
        normalArr = new int[countShip];
        for (int i = shipArr.length - 1, len = 1,  k = 0; i >= 0; i--, len++) {
            for (int j = 0; j < shipArr[i]; j++, k++) normalArr[k] = len;
        }
        for (int i = 0, k = 3; i < shipArr.length - 1; i++, k--) magicValue += shipArr[i] * k;
    }

    public void playerLayout(boolean[][] matrix) {
        this.matrix = matrix;
    }

    public void changeMatrix(int row, int col) {
        matrix[row-1][col-1] = !matrix[row - 1][col - 1];
    }

    public int shot(int col, int row) {
        if (!matrix[row-1][col-1]) return 0;
        else {
            int ship = shipManager.shot(row - 1, col - 1);
            if (shipManager.isSink(ship)) return 2;
            else return 1;
        }
    }

    private boolean diagonal(int i, int j) {
        return (i - 1 >= 0 && j - 1 >= 0 && matrix[i - 1][j - 1]) ||
                (i - 1 >= 0 && j + 1 < size && matrix[i - 1][j + 1]) ||
                (i + 1 < size && j - 1 >= 0 && matrix[i + 1][j - 1]) ||
                (i + 1 < size && j + 1 < size && matrix[i + 1][j + 1]);
    }

    public boolean checkMatrix() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(matrix[i][j]) {
                    count++;
                    if (diagonal(i, j)) return false;
                }
            }
        }
        if (count != segments) return false;

        int length = segments * 2 - magicValue;
        int[] list = new int[length];
        int ship = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j]) ship++;
                else if (ship > 0) {
                    for (int k = 0; k < length; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
                if (j == size - 1 && ship > 0) {
                    for (int k = 0; k < length; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[j][i]) ship++;
                else if (ship > 0) {
                    for (int k = 0; k < length; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
                if (j == size - 1 && ship > 0) {
                    for (int k = 0; k < length; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
            }
        }

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (list[i] > list[j]) {
                    int temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }

        for (int i = 0; i < normalArr.length; i++) if (list[i + length - normalArr.length] != normalArr[i]) return false;

        setShipManager();
        return true;
    }

    private void setShipManager() {
        int ship = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] && !shipManager.isContain(i, j)) {
                    shipManager.addPoint(i, j, ship);

                    if (i < size - 1 && matrix[i+1][j]) {
                        shipManager.addPoint(i+1, j, ship);
                        for (int k = 2; k < 4; k++) { // вертикальное расположение
                            if (i < size - k && matrix[i+k][j]) shipManager.addPoint(i+k, j, ship);
                            else break;
                        }
                    }
                    else if (j < size - 1 && matrix[i][j+1]) {
                        shipManager.addPoint(i, j+1, ship);
                        for (int k = 2; k < 4; k++) { // горизонтальное расположение
                            if (j < size - k && matrix[i][j+k]) shipManager.addPoint(i, j+k, ship);
                            else break;
                        }
                    }
                    ship++;
                }
            }
        }
    }

    public void createAuto() {

        for (int j = normalArr.length - 1; j >= 0; j--) {
            int length = normalArr[j];
            while (true) {
                int x = rand.nextInt(size);
                int y = rand.nextInt(size);
                int z = rand.nextInt(2);
                if (isFree(x, y, z, length)) {
                    if (z == 0) {
                        for (int i = x; i < x + length; i++) {
                            matrix[i][y] = true;
                            shipManager.addPoint(i, y, j);
                        }
                    }
                    if (z == 1) {
                        for (int i = y; i < y + length; i++) {
                            matrix[x][i] = true;
                            shipManager.addPoint(x, i, j);
                        }
                    }
                    break;
                }
            }
        }
    }

    private boolean isFree(int x, int y, int z, int length) {
        int ax = x - 1, ay = y - 1, by, dx;
        if (z == 0) { // вертикально
            if (x + length - 1 > size - 1) return false;
            by = y + 1;
            dx = x + length;
        } else { // горизонтально
            if (y + length - 1 > size - 1) return false;
            by = y + length;
            dx = x + 1;
        }
        if (ax < 0) ax = 0;
        if (ay < 0) ay = 0;
        if (by > size - 1) by = size - 1;
        if (dx > size - 1) dx = size - 1;
        for (int i = ax; i <= dx; i++) {
            for (int j = ay; j <= by; j++) {
                if (matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

