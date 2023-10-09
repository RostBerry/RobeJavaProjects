package clone.tetris.cup;


import clone.tetris.game.Config;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cell {
    public final float x;
    public final float y;

    public Cell(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Cup.color);

        shapeRenderer.rect(x, y, Config.CellSize, Config.CellSize);

        shapeRenderer.end();
    }
}
