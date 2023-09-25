package clone.tetris.playables;

import clone.tetris.config.Config;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Block implements Cloneable{
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

    public static int getXFromId(int id) {
        return id % 10;
    }

    public static int getYFromId(int id) {
        return id / 10;
    }

    @Override
    public Block clone() {
        try {
            Block clone = (Block) super.clone();
            clone.color = new Color(this.color);
            clone.id = id;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
