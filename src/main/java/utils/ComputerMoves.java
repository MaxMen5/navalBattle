package utils;

import java.util.Random;

public class ComputerMoves {

    private final static Random rand = new Random();
    private final static int[][] points = {{-1, -1}, {-1, -1}, {-1, -1}};
    private int size;

    public ComputerMoves(int size) {
        this.size = size;
    }

    public int[] alterMove() {
        int[] point = {-1, -1};
            if (points[1][0] == -1) { // Если ранение только одно
            int row = points[0][0];
            int col = points[0][1];
            while (true) {
                int shot = rand.nextInt(4);
                if (shot == 0 && row > 0) { // верх
                    point[0] = row - 1;
                    point[1] = col;
                    break;
                }
                else if (shot == 1 && col < size - 1) { // право
                    point[0] = row;
                    point[1] = col + 1;
                    break;
                }
                else if (shot == 2 && row < size - 1) { // низ
                    point[0] = row + 1;
                    point[1] = col;
                    break;
                }
                else if (shot == 3 && col > 0) { // лево
                    point[0] = row;
                    point[1] = col - 1;
                    break;
                }
            }
        }
        else { // Если ранений несколько
            if (points[0][0] == points[1][0]) { // горизонтальное расположение
                int row = points[0][0];
                int left = points[0][1];
                int right = points[0][1];
                if (points[1][1] > right) right = points[1][1];
                else left = points[1][1];

                if (points[2][1] != -1) {
                    if (points[2][1] > right) right = points[2][1];
                    if (points[2][1] < left) left = points[2][1];
                }
                while (true) {
                    int shot = rand.nextInt(2);
                    point[0] = row;
                    if (shot == 0 && right < size - 1) { // бьем правее
                        point[1] = right + 1;
                        break;
                    }
                    if (shot == 1 && left > 0) { // бьем левее
                        point[1] = left - 1;
                        break;
                    }
                }

            }
            else { // вертикальное расположение
                int col = points[0][1];
                int up = points[0][0];
                int down = points[0][0];
                if (points[1][0] > down) down = points[1][0];
                else up = points[1][0];

                if (points[2][0] != -1) {
                    if (points[2][0] > down) down = points[2][0];
                    if (points[2][0] < up) up = points[2][0];
                }
                while (true) {
                    int shot = rand.nextInt(2);
                    point[1] = col;
                    if (shot == 0 && down < size - 1) { // бьем ниже
                        point[0] = down + 1;
                        break;
                    }
                    if (shot == 1 && up > 0) { // бьем выше
                        point[0] = up - 1;
                        break;
                    }
                }
            }
        }
        return point;
    }

    public static void cleanPoints() {
        for (int i = 0; i < 3; i++) {
            points[i][0] = -1;
            points[i][1] = -1;
        }
    }

    public static void addPoints(int col, int row) {
        for (int i = 0; i < 3; i++) {
            if (points[i][0] == -1) {
                points[i][0] = row;
                points[i][1] = col;
                break;
            }
        }
    }
}
