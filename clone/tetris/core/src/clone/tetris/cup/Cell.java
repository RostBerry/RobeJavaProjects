package clone.tetris.cup;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Cell {
    public final float x;
    public final float y;
    private final float size;
    private final Color color;
    private final float lineWidth;
    private final float lineWidthHalf;


    public Cell(float x, float y, float size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        lineWidth = 5;
        lineWidthHalf = lineWidth / 2;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);

        shapeRenderer.rect(x, y, size, size);

        shapeRenderer.end();
    }
}
