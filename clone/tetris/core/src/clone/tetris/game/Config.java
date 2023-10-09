package clone.tetris.game;

public class Config {
    public static float MultiplierFactor;
    public static int FPS;

    public static float ScreenWidth;
    public static float ScreenHeight;
    public enum GameFormat {
        NES,
        Tetris99
    }
    public static GameFormat CurrentLayout;
    public static int StartDifficulty;
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

    public static float TetrisRateX;
    public static float TetrisRateY;

    public static float HoldTextX;
    public static float HoldTextY;

    public static float HoldX;
    public static float HoldY;

    public static float StatsTextX;
    public static float StatsTextY;

    public static float StatsX;
    public static float StatsY;

    public static void Update(float screenWidth, float screenHeight, GameFormat gameFormat, int startDifficulty, int fps) {
        MultiplierFactor = 1f;
        FPS = fps;
        CurrentLayout = gameFormat;
        ScreenWidth = screenWidth * MultiplierFactor;
        ScreenHeight = screenHeight * MultiplierFactor;
        StartDifficulty = startDifficulty;
        GameFontSize = (int) (ScreenHeight * 0.03f);
        CupHeight = ScreenHeight * 0.6f;
        CellSize = CupHeight / 20;
        CupWidth = CellSize * 10;
        CupX = (ScreenWidth - CupWidth) / 2;
        CupY = (ScreenHeight - CupHeight) / 2;
        CupTopY = CupY - CupHeight;

        LinesCountX = CupX;
        LinesCountY = CupY + CupHeight + (ScreenHeight - CupHeight - CupY) / 4;

        TopScoreX = CupX + CupWidth + 10 * MultiplierFactor;
        TopScoreY = LinesCountY;

        CurrentScoreX = TopScoreX;
        CurrentScoreY = TopScoreY - GameFontSize * 2;

        PreviewTextX = TopScoreX;
        PreviewTextY = CurrentScoreY - GameFontSize * 3;

        PreviewX = TopScoreX;
        PreviewY = PreviewTextY - GameFontSize * 2 - CellSize * 4;

        DifficultyX = TopScoreX;
        DifficultyY = PreviewY;

        TetrisRateX = DifficultyX;
        TetrisRateY = DifficultyY - GameFontSize;

        HoldTextX = CupX - CellSize * 5;
        HoldTextY = CupY + CupHeight;

        HoldX = HoldTextX;
        HoldY = HoldTextY - CellSize * 4 - GameFontSize * 2;

        StatsX = CupX * 0.3f;
        StatsY = CupY - CellSize * 2;

        StatsTextX = StatsX;
        StatsTextY = StatsY + CellSize * 3 * 4 + CellSize * 2.5f;
    }
}
