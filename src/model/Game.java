package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


public class Game {
    private final int width;
    private final int height;
    private final int bombs;

    private final String[][] board;
    private final boolean[][] mineGrid;

    public static final String UNREVEALED_LABEL = "?";
    public static final String MINE_LABEL = "*";
    public static final String NO_NEIGHBORS_LABEL = " ";

    public Game(int width, int height, int bombs) {
        this.width = width;
        this.height = height;
        this.bombs = bombs;

        this.board = new String[this.height][this.width];
        this.mineGrid = new boolean[this.height][this.width];
        for (int row = 0; row < this.height; row++) {
            this.board[row] = new String[this.width];
            Arrays.fill(this.board[row], UNREVEALED_LABEL);

            this.mineGrid[row] = new boolean[this.width];
            Arrays.fill(this.mineGrid[row], false);
        }
    }
    public static class Coordinate {
        public final int x;
        public final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void initializeBombs(Coordinate c, int bombs) {
        while (bombs > 0) {
            int x = (int) (Math.random() * this.height);
            int y = (int) (Math.random() * this.width);

            if (mineGrid[x][y] || (Math.abs(x - c.x) <= 1 && Math.abs(y - c.y) <= 1)) {
                continue;
            }

            mineGrid[x][y] = true;
            bombs--;
        }
    }

    public void revealNeighbors(Coordinate start) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            Coordinate c = queue.poll();
            int count = 0;
            int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

            for (int[] direction : directions) {
                int newX = c.x + direction[0];
                int newY = c.y + direction[1];

                if (newX >= 0 && newX < this.height && newY >= 0 && newY < this.width) {
                    if (mineGrid[newX][newY]) {
                        count++;
                    }
                }
            }

            if (count == 0) {
                board[c.x][c.y] = NO_NEIGHBORS_LABEL;
                for (int[] direction : directions) {
                    int newX = c.x + direction[0];
                    int newY = c.y + direction[1];

                    if (newX >= 0 && newX < this.height && newY >= 0 && newY < this.width) {
                        if (board[newX][newY].equals(UNREVEALED_LABEL)) {
                            queue.add(new Coordinate(newX, newY));
                        }
                    }
                }
            } else {
                board[c.x][c.y] = String.valueOf(count);
            }
        }
    }

    public boolean isMine(Coordinate c) {
        return mineGrid[c.x][c.y];
    }

    public String getCellLabel(Coordinate c) {
        return board[c.x][c.y];
    }

    public void setCellLabel(Coordinate c, String label) {
        board[c.x][c.y] = label;
    }

    public boolean isTwoBoxesLeft() {
        int revealedCount = 0;
        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                if (!mineGrid[row][column] && !board[row][column].equals(UNREVEALED_LABEL)) {
                    revealedCount++;
                }
            }
        }
        return (this.height * this.width) - revealedCount - 8 == 2;
    }

    public boolean isWinCondition() {
        int revealedCount = 0;
        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                if (!mineGrid[row][column] && !board[row][column].equals(UNREVEALED_LABEL)) {
                    revealedCount++;
                }
            }
        }
        return revealedCount == (this.height * this.width) - 8;
    }

    public String[][] getBoard() {
        return board;
    }
}
