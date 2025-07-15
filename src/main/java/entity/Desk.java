package entity;

import GUI.PlayTable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
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
        if (matrix[row-1][col-1]) matrix[row-1][col-1] = false;
        else matrix[row-1][col-1] = true;
    }

    public boolean checkMatrix(PlayTable table) {
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

        List<Integer> list = new ArrayList<>();
        Integer ship = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {ship++;}
                else if (ship > 0) {
                    list.add(ship);
                    ship = 0;
                }
                if (j == matrix[i].length - 1 && ship > 0) {
                    list.add(ship);
                    ship = 0;
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[j][i]) {ship++;}
                else if (ship > 0) {
                    list.add(ship);
                    ship = 0;
                }
                if (j == matrix[i].length - 1 && ship > 0) {
                    list.add(ship);
                    ship = 0;
                }
            }
        }
        Collections.sort(list);
        int[] playerArr = new int[10];
        for (int i = 20; i < list.size(); i++) playerArr[i-20] = list.get(i);
        if (Arrays.equals(normalArr, playerArr)) {
            setShipManager();
            return true;
        }
        return false;
    }

    private void setShipManager() {
        int ship = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (matrix[i][j] && !isContain(shipManager.matrix, i, j)) {
                    shipManager.AddPoint(i, j, ship);

                    if (i < 9 && matrix[i+1][j]) {
                        shipManager.AddPoint(i+1, j, ship);
                        // вертикальное расположение
                        for (int k = 2; k < 4; k++) {
                            if (i < 10 - k && matrix[i+k][j]) shipManager.AddPoint(i+k, j, ship);
                        }
                    }
                    else if (j < 9 && matrix[i][j+1]) {
                        shipManager.AddPoint(i, j+1, ship);
                        // горизонтальное расположение
                        for (int k = 2; k < 4; k++) {
                            if (j < 10 - k && matrix[i][j+k]) shipManager.AddPoint(i, j+k, ship);
                        }
                    }
                    ship++;
                }
            }
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
                            shipManager.AddPoint(i, y, j);
                        }
                    }
                    if (z == 1) {
                        for (int i = y; i < y + length; i++) {
                            matrix[x][i] = true;
                            shipManager.AddPoint(x, i, j);
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

    public int shot(int col, int row) {
        if (!matrix[row-1][col-1]) return 0;
        else {
            int ship = shipManager.Shot(row - 1, col - 1);
            if (shipManager.isSink(ship)) return 2;
            else return 1;
        }
    }
}

