package clone.tetris.game.config;

import java.util.ArrayList;
import java.util.List;

public class GameStartConfig {
    public static float StartButtonX;
    public static float StartButtonY;
    public static float StartButtonWidth;
    public static float StartButtonHeight;

    public static float GameModeSwitcherX;
    public static float GameModeSwitcherY;
    public static List<String> AllGameModes;

    public static void update() {
        StartButtonWidth = Config.ScreenWidth * 0.3f;
        StartButtonHeight = Config.ScreenHeight * 0.1f;
        StartButtonX = (Config.ScreenWidth - StartButtonWidth) / 2;
        StartButtonY = Config.ScreenHeight * 0.2f;

        GameModeSwitcherX = StartButtonX;
        GameModeSwitcherY = StartButtonY + StartButtonHeight;
        AllGameModes = new ArrayList<>();
        AllGameModes.add("MARATHON");
        AllGameModes.add("ULTRA");
        AllGameModes.add("SPRINT");
    }
}
