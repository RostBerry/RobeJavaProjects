package clone.tetris.ui;

import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TextButton extends Button{
    public final String text;
    private final float textWidth;
    private final float textHeight;


    public TextButton(int id, float x, float y, float width, float height, String text) {
        super(id, x, y, width, height);
        this.text = text;
        GlyphLayout layout = new GlyphLayout();
        layout.setText(UIConfig.MenuMainFont, text);
        textWidth = layout.width;
        textHeight = layout.height;
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        super.draw(shapeRenderer, batch);
        batch.begin();
        UIConfig.MenuMainFont.draw(batch, text, x + (width - textWidth) / 2, y + height / 2 + textHeight / 3);
        batch.end();
    }
}
