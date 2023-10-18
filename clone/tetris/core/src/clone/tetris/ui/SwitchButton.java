package clone.tetris.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SwitchButton extends Button {
    private final boolean side;

    public SwitchButton(int id, boolean side, float x, float y, float width, float height) {
        super(id, x, y, width, height);
        this.side = side;
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        super.draw(shapeRenderer, batch);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < 8; i++) {
            float xStart;
            float xEnd;
            if (side) {
                xStart = x + width / 8 + i;
                xEnd = x + width - width / 8 + i;
            } else {
                xEnd = x + width / 8 + i;
                xStart = x + width - width / 8 + i;
            }

            shapeRenderer.line(xStart, y + height / 8, xEnd, y + height / 2);
            shapeRenderer.line(xEnd, y + height / 2, xStart, y + height - height / 8);
        }
        shapeRenderer.end();
    }
}
