package controller;

import model.Game;
import view.GameView;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class GameController {
    private final Game game;
    private final GameView view;
    private boolean firstMove;

    public GameController(Game game, GameView view) {
        this.game = game;
        this.view = view;
        this.firstMove = true;
    }

    public void play() {
        while (true) {
            view.render(game);
            Game.Coordinate c = getMoveInput();

            if (firstMove) {
                game.initializeBombs(c, 8);
                firstMove = false;
            }

            if (game.isMine(c)) {
                game.setCellLabel(c, Game.MINE_LABEL);
                view.render(game);
                view.showMessage("You hit a mine! Game over.");
                break;
            }

            game.revealNeighbors(c);

            if (game.isTwoBoxesLeft()) {
                if (!askToContinue()) {
                    view.render(game);
                    view.showMessage("Congratulations! You win!");
                    break;
                }
            }

            if (game.isWinCondition()) {
                view.render(game);
                view.showMessage("Congratulations! You win!");
                break;
            }
        }
    }

    private Game.Coordinate getMoveInput() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String text;
        try {
            text = in.readLine().trim();
            if (text.length() < 2) throw new IllegalArgumentException();

            char col = text.charAt(0);
            int row = Integer.parseInt(text.substring(1));

            if (col < 'a' || col >= 'a' + game.getBoard()[0].length || row < 1 || row > game.getBoard().length) {
                throw new IllegalArgumentException();
            }
            return new Game.Coordinate(row - 1, col - 'a');
        } catch (Exception e) {
            view.showMessage("Invalid input. Please enter a valid move (e.g., a3).");
            return getMoveInput();
        }
    }

    private boolean askToContinue() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        view.showMessage("Only two boxes left to reveal. Do you want to continue? (y/n)");
        try {
            String response = in.readLine().trim().toLowerCase();
            return response.equals("y");
        } catch (Exception e) {
            view.showMessage("Invalid input. Please enter 'y' or 'n'.");
            return askToContinue();
        }
    }
}
