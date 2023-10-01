package clone.tetris.playables;

import clone.tetris.config.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block{
    public static class StackBlock {
        public Color color;
        public StackBlock(Color color) {
            this.color = color;
        }
        public void draw(ShapeRenderer shapeRenderer, float x, float y) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, Config.CellSize, Config.CellSize);
            shapeRenderer.end();
        }
    }
    public int xId;
    public int yId;
    public Color color;

    public Block(int x, int y, Color color) {
        xId = x;
        yId = y;
        this.color = color;
    }

    public void draw(ShapeRenderer shapeRenderer, float x, float y) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, Config.CellSize, Config.CellSize);
        shapeRenderer.end();
    }
}
