package clone.tetris.game.config;

import java.util.ArrayList;
import java.util.List;

public class GameStartConfig {
    public static float StartButtonX;
    public static float StartButtonY;
    public static float StartButtonWidth;
    public static float StartButtonHeight;

    public static float GameModeTextX;
    public static float GameModeTextY;

    public static float GameModeSwitcherX;
    public static float GameModeSwitcherY;

    public static float StartDifficultyTextX;
    public static float StartDifficultyTextY;

    public static float StartDifficultySwitcherX;
    public static float StartDifficultySwitcherY;

    public static List<String> AllGameModes;
    public static List<String> AllStartDifficulties;

    public static void update() {
        StartButtonWidth = Config.ScreenWidth * 0.3f;
        StartButtonHeight = Config.ScreenHeight * 0.1f;
        StartButtonX = (Config.ScreenWidth - StartButtonWidth) / 2;
        StartButtonY = Config.ScreenHeight * 0.2f;

        StartDifficultySwitcherX = StartButtonX;
        StartDifficultySwitcherY = StartButtonY + StartButtonHeight * 2;
        StartDifficultyTextX = StartDifficultySwitcherX;
        StartDifficultyTextY = StartDifficultySwitcherY + UIConfig.SwitcherButtonSize + Config.MenuHeaderFontSize;

        GameModeSwitcherX = StartButtonX;
        GameModeSwitcherY = StartDifficultySwitcherY + UIConfig.SwitcherButtonSize * 2;
        GameModeTextX = GameModeSwitcherX;
        GameModeTextY = GameModeSwitcherY + UIConfig.SwitcherButtonSize + Config.MenuHeaderFontSize;

        AllStartDifficulties = new ArrayList<>();
        AllStartDifficulties.add("1");
        AllStartDifficulties.add("5");
        AllStartDifficulties.add("10");
        AllStartDifficulties.add("15");

        AllGameModes = new ArrayList<>();
        AllGameModes.add("MARATHON");
        AllGameModes.add("ULTRA");
        AllGameModes.add("SPRINT");
    }
}
