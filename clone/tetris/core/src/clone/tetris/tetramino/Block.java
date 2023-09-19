package clone.tetris.tetramino;

import clone.tetris.config.Config;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block {
    public int id;
    public Color color;
    private final float size;

    public Block(int id, Color color) {
        this.id = id;
        size = Config.CellSize;
        this.color = color;
    }

    public void draw(ShapeRenderer shapeRenderer, float[] pos) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(pos[0], pos[1], size, size);
        shapeRenderer.end();
    }
}
