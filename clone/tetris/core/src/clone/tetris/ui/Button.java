package clone.tetris.ui;

import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
    public final float x;
    public final float y;
    public final float width;
    public final float height;
    public final String text;
    private final float textWidth;
    private final float textHeight;
    public Color strokeColor;

    public Button(float x, float y, float width, float height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(UIConfig.MenuFont, text);
        textWidth = layout.width;
        textHeight = layout.height;
        strokeColor = UIConfig.ButtonStrokeColor;
    }
    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(UIConfig.ButtonBackgroundColor);
        shapeRenderer.rect(x + 1, y - 1, width - 1, height - 1);
        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(strokeColor);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
        batch.begin();
        UIConfig.MenuFont.draw(batch, text, x + (width - textWidth) / 2, y + height / 2 + textHeight / 3);
        batch.end();
    }
}
