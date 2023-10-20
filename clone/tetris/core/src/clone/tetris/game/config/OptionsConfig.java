package clone.tetris.game.config;

public class OptionsConfig {
    public static float BackButtonX;
    public static float BackButtonY;
    public static float BackButtonWidth;
    public static float BackButtonHeight;

    public static float StatsSwitcherX;
    public static float StatsSwitcherY;

    public static float StatsTextX;
    public static float StatsTextY;

    public static void update() {
        BackButtonWidth = Config.ScreenWidth * 0.2f;
        BackButtonHeight = BackButtonWidth * 0.5f;
        BackButtonX = BackButtonWidth * 0.25f;
        BackButtonY = Config.ScreenHeight - BackButtonHeight - BackButtonWidth * 0.25f;

        StatsSwitcherX = (Config.ScreenWidth - UIConfig.SwitcherWidth) / 2;
        StatsSwitcherY = Config.ScreenHeight * 0.5f - UIConfig.SwitcherButtonSize;

        StatsTextX = StatsSwitcherX;
        StatsTextY = StatsSwitcherY + UIConfig.SwitcherButtonSize + Config.MenuHeaderFontSize;
    }
}
