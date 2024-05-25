package view;

import model.Game;


public class GameView {
    public void render(Game game) {
        String[][] board = game.getBoard();
        int height = board.length;
        int width = board[0].length;

        System.out.print(" ");
        for (int col = 'a'; col < width + 'a'; col++) {
            System.out.printf(" %c", col);
        }
        System.out.println();
        for (int row = 0; row < height; row++) {
            System.out.printf("%d", row + 1);
            for (int column = 0; column < width; column++) {
                System.out.printf(" %s", board[row][column]);
            }
            System.out.println();
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
