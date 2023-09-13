package clone;

import clone.minecraft.config.Config;
import clone.chess.game.ChessTest;

public class Main {
    public static void main(String[] args) {
        System.out.println("This is a minecraft clone");
        System.out.println(Config.IsHere);
        ChessTest chessTest = new ChessTest();
        chessTest.main(args);
    }
}
