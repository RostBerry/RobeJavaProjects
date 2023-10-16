package clone.tetris.game.config;

public class GameStartConfig {
    public static float PlayButtonX;
    public static float PlayButtonY;
    public static float PlayButtonWidth;
    public static float PlayButtonHeight;

    public static void update() {
        PlayButtonWidth = Config.ScreenWidth * 0.3f;
        PlayButtonHeight = Config.ScreenHeight * 0.1f;
        PlayButtonX = (Config.ScreenWidth - PlayButtonWidth) / 2;
        PlayButtonY = Config.ScreenHeight * 0.2f;
    }
}
