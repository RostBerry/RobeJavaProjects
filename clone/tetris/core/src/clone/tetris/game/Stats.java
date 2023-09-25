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

    public static float[] fallIntervals = {
            0.8f, //1
            0.7166f, //2
            0.6333f, //3
            0.55f, //4
            0.4666f, //5
            0.3833f, //6
            0.2166f, //7
            0.1333f, //8
            0.1f, //9
            0.0833f, 0.0833f, 0.0833f, //10-12
            0.0666f, 0.0666f, 0.0666f, //13-15
            0.05f, 0.05f, 0.05f, //16-18
            0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, 0.0333f, //19-28
            0.0166f //29
    };

    public static final float lockPause = 0.5f;

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
