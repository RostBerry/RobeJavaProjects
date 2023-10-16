package clone.tetris.game;

import clone.tetris.game.config.Config;
import clone.tetris.playables.Tetramino;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;

public class TetraminoStatsManager {
    private static final int[] allCounts = new int[7];
    private static final Tetramino.Preview[] allTypes = createTypes();

    public static void refresh() {
        Arrays.fill(allCounts, 0);
    }

    private static Tetramino.Preview[] createTypes() {
        Tetramino.Preview[] allTypes = new Tetramino.Preview[7];
        for (int i = 0; i < 7; i++) {
            allTypes[i] = new Tetramino.Preview(Tetramino.Type.values()[i]);
        }

        return allTypes;
    }

    public static void addToCount(Tetramino tetramino) {
        allCounts[tetramino.getType().ordinal()] ++;
    }

    public static void drawPreviews(SpriteBatch batch) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 4; y++) {
                int position = x * 4 + y;
                if (position < 7) {
                    allTypes[position].draw(batch, Config.StatsX + Config.CellSize * 6 * x, Config.StatsY + Config.CellSize * 3 * y);
                }
            }
        }
    }

    public static void drawTexts(SpriteBatch batch, BitmapFont font) {
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 4; y++) {
                int position = x * 4 + y;
                if (position < 7) {
                    font.draw(batch, Integer.toString(allCounts[position]), Config.StatsX + Config.CellSize * 4 * (x + 1) + Config.CellSize * 2 * x, Config.StatsY + Config.CellSize * 3 * y + Config.GameFontSize * 3);
                }
            }
        }
    }
}
