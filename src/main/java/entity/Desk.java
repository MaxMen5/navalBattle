package entity;

import java.util.Random;

public class Desk {

    public boolean[][] matrix = new boolean[10][10];
    int[] normalArr = {1, 1, 1, 1, 2, 2, 2, 3, 3, 4};

    Random rand = new Random();
    ShipManager shipManager = new ShipManager();

    public Desk() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrix[i][j] = false;
            }
        }
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

    private boolean isContain(ShipManager.Ship[] array, int row, int col) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].ship == -1) return false;
            else if (array[i].row == row && array[i].col == col) return true;
        }
        return false;
    }

    private boolean diagonal(int i, int j) {
        if (i - 1 >= 0 && j - 1 >= 0 && matrix[i-1][j-1]) {return true;}
        else if (i - 1 >= 0 && j + 1 < 10 && matrix[i-1][j+1]) {return true;}
        else if (i + 1 < 10 && j - 1 >= 0 && matrix[i+1][j-1]) {return true;}
        else if (i + 1 < 10 && j + 1 < 10 && matrix[i+1][j+1]) {return true;}
        else return false;
    }

    public boolean checkMatrix() {
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j]) {
                    count++;
                    if (diagonal(i, j)) return false;
                }
            }
        }
        if (count != 20) return false;

        int[] list = new int[30]; //
        int ship = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {ship++;}
                else if (ship > 0) {
                    for (int k = 0; k < 30; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
                if (j == matrix[i].length - 1 && ship > 0) {
                    for (int k = 0; k < 30; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i]) {ship++;}
                else if (ship > 0) {
                    for (int k = 0; k < 30; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
                if (j == matrix[i].length - 1 && ship > 0) {
                    for (int k = 0; k < 30; k++) {
                        if (list[k] == 0) {
                            list[k] = ship;
                            break;
                        }
                    }
                    ship = 0;
                }
            }
        }

        for (int i = 0; i < 30; i++) {
            for (int j = i + 1; j < 30; j++) {
                if (list[i] > list[j]) {
                    int temp = list[i];
                    list[i] = list[j];
                    list[j] = temp;
                }
            }
        }
        for (int i = 0; i < 10; i++) if (list[i+20] != normalArr[i]) return false;

        setShipManager();
        return true;
    }

    private void setShipManager() {
        int ship = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j] && !isContain(shipManager.matrix, i, j)) {
                    shipManager.addPoint(i, j, ship);

                    if (i < 9 && matrix[i+1][j]) {
                        shipManager.addPoint(i+1, j, ship);
                        for (int k = 2; k < 4; k++) { // вертикальное расположение
                            if (i < 10 - k && matrix[i+k][j]) shipManager.addPoint(i+k, j, ship);
                            else break;
                        }
                    }
                    else if (j < 9 && matrix[i][j+1]) {
                        shipManager.addPoint(i, j+1, ship);
                        for (int k = 2; k < 4; k++) { // горизонтальное расположение
                            if (j < 10 - k && matrix[i][j+k]) shipManager.addPoint(i, j+k, ship);
                            else break;
                        }
                    }
                    ship++;
                }
            }
        }
    }

    public void createAuto() {

        for (int j = 9; j >= 0; j--) {
            int length = normalArr[j];
            while (true) {
                int x = rand.nextInt(10);
                int y = rand.nextInt(10);
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
            if (x + length - 1 > 9) return false;
            by = y + 1;
            dx = x + length;
        } else { // горизонтально
            if (y + length - 1 > 9) return false;
            by = y + length;
            dx = x + 1;
        }
        if (ax < 0) ax = 0;
        if (ay < 0) ay = 0;
        if (by > 9) by = 9;
        if (dx > 9) dx = 9;
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

