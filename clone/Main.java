package clone;

import javax.swing.SwingUtilities;
import clone.tetris.game.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("This is a tetris clone");
        SwingUtilities.invokeLater(() -> {
            new Game();
        });
    }
}
