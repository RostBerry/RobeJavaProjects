package clone.tetris.game.config;

public class GameConfig {
    public enum GameMode {
        Marathon,
        Ultra,
        Sprint
    }
    public static int StartDifficulty;
    public static GameMode CurrentGameMode;

    public static void update(int difficulty, GameMode gameMode) {
        StartDifficulty = difficulty;
        CurrentGameMode = gameMode;
    }
}
