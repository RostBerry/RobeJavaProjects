package clone.tetris.game;

public class Stats {
    private static final int[] lineBonuses = {0, 100, 300, 500, 800};

    private static final int hardDropBonus = 2;

    private static final int softDropBonus = 1;

    public static int score;

    public static int lineCount;
    public static int possibleLineCount;
    private static int valueLineCount;

    //line values for variable goal for difficulty change
    private static final int[] lineValues = {0, 1, 3, 5, 8};

    public static int difficulty;

    public static int[] fallIntervals = {
            800, //1
            716, //2
            633, //3
            550, //4
            466, //5
            383, //6
            216, //7
            133, //8
            100, //9
            83, 83, 83, //10-12
            66, 66, 66, //13-15
            50, 50, 50, //16-18
            33, 33, 33, 33, 33, 33, 33, 33, 33, 33, //19-28
            16 //29
    };

    public static final int lockPause = 500;

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
        valueLineCount = 0;
        difficulty = 1;
    }

    public static void addLineBonus(LineRemoveType type) {
        score += difficulty * lineBonuses[type.ordinal()];
        lineCount += type.ordinal();
        possibleLineCount += type == LineRemoveType.None? 0: 4;
        valueLineCount += lineValues[type.ordinal()];
        refreshDifficulty();
    }

    public static void setDifficulty(int n) {
        if (n > 29) {
            difficulty = 29;
            return;
        }
        difficulty = n;
    }

    private static void refreshDifficulty() {
        if (difficulty < 29 && valueLineCount >= difficulty * 5) {
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
        score += linesDelta * hardDropBonus;
    }
}
