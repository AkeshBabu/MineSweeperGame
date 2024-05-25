import controller.GameController;
import model.Game;
import view.GameView;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(9, 9, 8); // Customize width, height, and number of bombs as needed
        GameView view = new GameView();
        GameController controller = new GameController(game, view);
        controller.play();
    }
}
