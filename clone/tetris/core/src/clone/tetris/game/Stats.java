package clone.tetris.game;

public class Stats {
    private static final int[] lineBonuses = {0, 100, 300, 500, 800};

    private static final int hardDropBonus = 2;

    private static final int softDropBonus = 1;
    public static final int softDropIncrease = 10;

    public static int score;

    public static int lineCount;
    public static int possibleLineCount;

    public static int difficulty;

    public static int[] fallIntervals = {
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
        difficulty = 1;
    }

    public static void addLineBonus(LineRemoveType type) {
        score += difficulty * lineBonuses[type.ordinal()];
        lineCount += type.ordinal();
        possibleLineCount += type == LineRemoveType.None? 0: 4;
        refreshDifficulty();
    }

    public static int getDifficulty() {
        return Math.min(difficulty, 30);
    }

    private static void refreshDifficulty() {
        if (lineCount >= difficulty * 10) {
            difficulty++;
        }
    }

    public static int getTetrisRate() {
        if (possibleLineCount == 0) {
            return 100;
        }
        return score / possibleLineCount;
    }

    public static void addHardDropBonus(int linesDelta) {
        score += (linesDelta - 1) * hardDropBonus;
    }

    public static void addSoftDropBonus() {score += softDropBonus;}
}
