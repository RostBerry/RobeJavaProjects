package clone.tetris.ui;

import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
    public final int id;
    public final float x;
    public final float y;
    public final float width;
    public final float height;
    public Color strokeColor;

    public Button(int id, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        strokeColor = UIConfig.ButtonStrokeColor;
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(UIConfig.ButtonBackgroundColor);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(strokeColor);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
}
