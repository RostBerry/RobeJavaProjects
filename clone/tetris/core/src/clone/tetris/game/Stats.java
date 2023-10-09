package clone.tetris.game;

public class Stats {
    private static final int[] lineBonuses = {0, 100, 300, 500, 800};

    private static final int hardDropBonus = 2;

    private static final int softDropBonus = 1;
    public static final int softDropIncrease = 10;

    public static final int movingDelayOnPressed = 12;
    public static final int movingDelayOnHold = 3;

    public static int score;

    public static int lineCount;
    public static int possibleLineCount;
    private static int tetrisCount;

    public static int difficulty;

    public static final int[] NESFallingIntervals = {
            48, //1
            43, //2
            38, //3
            33, //4
            28, //5
            23, //6
            18, //7
            13, //8
            8, //9
            6, //10
            5, 5, 5, //11-13
            4, 4, 4, //14-16
            3, 3, 3, //17-19
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, //20 - 29
            1 //30
    };

    public static final int lockPause = 30;

    public enum LineRemoveType {
        None,
        Single,
        Double,
        Triple,
        Tetris
    }
    public static final LineRemoveType[] allRemoveTypes = LineRemoveType.values();

    public static void refresh() {
        score = 0;
        lineCount = 0;
        possibleLineCount = 0;
        tetrisCount = 0;
        difficulty = Config.StartDifficulty;
    }

    public static void addLineBonus(LineRemoveType type) {
        score += difficulty * lineBonuses[type.ordinal()];
        lineCount += type.ordinal();
        possibleLineCount += type == LineRemoveType.None? 0: 4;
        switch (type) {
            case None:
                break;
            case Tetris:
                tetrisCount++;
            default:
                refreshDifficulty();
        }
    }

    public static int getActualDifficulty() {
        return Math.min(difficulty, Config.CurrentLayout == Config.GameFormat.NES ? 30: 15);
    }

    public static int calculateGuidelineDifficulty() {
        return (int) ((float) Math.round(Math.pow(0.8f - (((float)difficulty - 1) * 0.007f), difficulty - 1) * Config.FPS));
    }

    private static void refreshDifficulty() {
        int linesToChangeDifficulty = difficulty * 10 - Config.StartDifficulty * 10 + 10;
        if (lineCount >= linesToChangeDifficulty) {
            switch (Config.CurrentLayout) {
                case NES:
                    difficulty++;
                    break;
                case Tetris99:
                    difficulty = Math.min(15, difficulty + 1);
            }
        }
    }

    public static float getTetrisRate() {
        if (possibleLineCount == 0) {
            return 1;
        }
        return (float) tetrisCount * 4 / possibleLineCount;
    }

    public static void addHardDropBonus(int linesDelta) {
        score += (linesDelta - 1) * hardDropBonus;
    }

    public static void addSoftDropBonus() {score += softDropBonus;}
}
