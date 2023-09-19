package clone.tetris.cup;

import clone.tetris.config.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import sun.jvm.hotspot.oops.CellTypeState;

import java.util.ArrayList;

public class Cup {
    private final float X;
    private final float Y;
    private final float width;
    private final float height;
    private final float cellSize;

    private final Color color;

    private final Cell[] allCells;

    public Cup(){
        height = Config.CupHeight;
        cellSize = Config.CellSize;
        width = Config.CupWidth;

        X = Config.CupX;
        Y = Config.CupY;


        color = new Color(1f, 1f, 1f, 0.25f);

        allCells = CreateAllCells();

    }

    public static float[] IdToPos(int id) {
        float[] pos = new float[2];
        pos[0] = Config.CupX + Config.CellSize * (id % 10);
        pos[1] = Config.CupY + Config.CellSize * (id / 10);
        return pos;
        }

    private Cell[] CreateAllCells() {
        Cell[] cells = new Cell[10 * 20];
        for (int y = 19; y >= 0; y--) {
            for (int x = 0; x < 10; x++) {
                int index = x + y * 10;
                cells[index] = new Cell(X + cellSize * x, Y + cellSize * y, cellSize, color);
            }
        }
        return cells;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (Cell cell : allCells) {
            cell.draw(shapeRenderer);
        }
    }
}
