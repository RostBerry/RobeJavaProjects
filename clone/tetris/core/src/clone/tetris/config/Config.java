package clone.tetris.config;

public class Config {
    public static float ScreenWidth;
    public static float ScreenHeight;
    public enum LayoutType {
        NES,
        Tetris99
    }
    public static LayoutType CurrentLayout;
    public static float CupHeight;
    public static float CellSize;
    public static float CupWidth;
    public static float CupX;
    public static float CupY;
    public static float CupTopY;

    public static int GameFontSize;

    public static float LinesCountX;
    public static float LinesCountY;

    public static float TopScoreX;
    public static float TopScoreY;

    public static float CurrentScoreX;
    public static float CurrentScoreY;

    public static float PreviewTextX;
    public static float PreviewTextY;

    public static float PreviewX;
    public static float PreviewY;

    public static float DifficultyX;
    public static float DifficultyY;

    public static float StatsX;
    public static float StatsY;

    public static void Update(float screenWidth, float screenHeight, LayoutType layoutType) {
        CurrentLayout = layoutType;
        ScreenWidth = screenWidth;
        ScreenHeight = screenHeight;
        GameFontSize = (int) (ScreenHeight * 0.04f);
        CupHeight = ScreenHeight * 0.6f;
        CellSize = CupHeight / 20;
        CupWidth = CellSize * 10;
        CupX = (ScreenWidth - CupWidth) / 2;
        CupY = (ScreenHeight - CupHeight) / 2;
        CupTopY = CupY - CupHeight;

        LinesCountX = CupX;
        LinesCountY = CupY + CupHeight + (ScreenHeight - CupHeight - CupY) / 2;

        TopScoreX = CupX + CupWidth + 10;
        TopScoreY = LinesCountY;

        CurrentScoreX = TopScoreX;
        CurrentScoreY = TopScoreY - GameFontSize * 2;

        PreviewTextX = TopScoreX;
        PreviewTextY = CurrentScoreY - GameFontSize * 3;

        PreviewX = TopScoreX;
        PreviewY = PreviewTextY - GameFontSize * 2 - CellSize * 4;

        DifficultyX = TopScoreX;
        DifficultyY = PreviewY - 100;

        StatsX = CupX * 0.2f;
        StatsY = LinesCountY;
    }
}
