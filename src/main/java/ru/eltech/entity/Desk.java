package ru.eltech.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Desk {

    public boolean[][] matrix = new boolean[10][10];
    int[] normalArr = {1, 1, 1, 1, 2, 2, 2, 3, 3, 4};
    Random rand = new Random();

    public Desk() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matrix[i][j] = false;
            }
        }
    }

    private void showMatrix() { // ВСПОМОГАТЕЛЬНАЯ ФУНКЦИЯ
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j]) {System.out.print(1 + "  ");}
                else {System.out.print(0 + "  ");}
            } System.out.println();
        } System.out.println();
    }

    public void changeMatrix(int row, int col) {
        if (matrix[row-1][col-1]) matrix[row-1][col-1] = false;
        else matrix[row-1][col-1] = true;
    }

    public boolean checkMatrix() {
        showMatrix();
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
                if (i == matrix[j].length - 1 && ship > 0) {
                    list.add(ship);
                    ship = 0;
                }
            }
        }
        Collections.sort(list);
        int[] playerArr = new int[10];
        for (int i = 20; i < list.size(); i++) {
            playerArr[i-20] = list.get(i);
            System.out.print(list.get(i) + " ");
        }
        return Arrays.equals(normalArr, playerArr);
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
                        }
                    }
                    if (z == 1) {
                        for (int i = y; i < y + length; i++) {
                            matrix[x][i] = true;
                        }
                    }
                    break;
                }
            }
        }
        showMatrix();
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

