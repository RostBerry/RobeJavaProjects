package clone.tetris.cup;

import clone.tetris.game.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cup {
    public static Color color = new Color(1f, 1f, 1f, 0.25f);

    public static final Cell[][] allCells = CreateAllCells();

    private static Cell[][] CreateAllCells() {
        Cell[][] cells = new Cell[40][10];
        for (int y = 39; y >= 0; y--) {
            for (int x = 0; x < 10; x++) {
                cells[y][x] = new Cell(Config.CupX + Config.CellSize * x, Config.CupY + Config.CellSize * y);
            }
        }
        return cells;
    }

    public static void draw(ShapeRenderer shapeRenderer) {
        for (int y = 0; y < 20; y++) {
            for (Cell cell: allCells[y]) {
                cell.draw(shapeRenderer);
            }
        }
    }
}
