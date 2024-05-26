import controller.GameController;
import model.Game;
import view.GameView;

public class Main {
    public static void main(String[] args) {
    	System.out.println("💣💥💣 Welcome to Minesweeper Game 💣💥💣 \n");
    	Game game = new Game(9, 9, 8);
        GameView view = new GameView();
        GameController controller = new GameController(game, view);
        controller.play();
    }
}
