package clone.tetris.game.config;

public class MenuConfig {
    public static float PlayButtonX;
    public static float PlayButtonY;
    public static float PlayButtonWidth;
    public static float PlayButtonHeight;

    public static float SettingsButtonX;
    public static float SettingsButtonY;
    public static float SettingsButtonWidth;
    public static float SettingsButtonHeight;

    public static float QuitButtonX;
    public static float QuitButtonY;
    public static float QuitButtonWidth;
    public static float QuitButtonHeight;

    public static void update() {
        PlayButtonWidth = Config.ScreenWidth * 0.33f;
        PlayButtonHeight = Config.ScreenHeight * 0.1f;
        PlayButtonX = (Config.ScreenWidth - PlayButtonWidth) / 2;
        PlayButtonY = Config.ScreenHeight * 0.5f;

        SettingsButtonWidth = PlayButtonWidth;
        SettingsButtonHeight = PlayButtonHeight;
        SettingsButtonX = PlayButtonX;
        SettingsButtonY = PlayButtonY - SettingsButtonHeight * 1.25f;

        QuitButtonWidth = PlayButtonWidth;
        QuitButtonHeight = PlayButtonHeight;
        QuitButtonX = PlayButtonX;
        QuitButtonY = SettingsButtonY - QuitButtonHeight * 1.25f;
    }
}
