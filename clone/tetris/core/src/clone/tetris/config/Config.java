package clone.tetris.config;

public class Config {
    public static float ScreenWidth;
    public static float ScreenHeight;
    public static float CupHeight;
    public static float CellSize;
    public static float CupWidth;
    public static float CupX;
    public static float CupY;
    public static float CupTopY;

    public static void Update(float screenWidth, float screenHeight) {
        ScreenWidth = screenWidth;
        ScreenHeight = screenHeight;
        CupHeight = ScreenHeight * 0.6f;
        CellSize = CupHeight / 20;
        CupWidth = CellSize * 10;
        CupX = (ScreenWidth - CupWidth) / 2;
        CupY = (ScreenHeight - CupHeight) / 2;
        CupTopY = CupY - CupHeight;
    }
}
