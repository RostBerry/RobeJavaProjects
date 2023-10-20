package clone.tetris.ui;

import clone.tetris.appearance.SwitchButtonTexture;
import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SwitchButton extends Button {
    private final Sprite spriteDefault;
    private final Sprite spriteSelected;

    public SwitchButton(int id, boolean side, float x, float y, float size) {
        super(id, x, y, size, size);
        spriteDefault = new Sprite(side? SwitchButtonTexture.switchButtonRight[0]: SwitchButtonTexture.switchButtonLeft[0]);
        spriteSelected = new Sprite(side? SwitchButtonTexture.switchButtonRight[1]: SwitchButtonTexture.switchButtonLeft[1]);
        spriteDefault.setBounds(x, y, size, size);
        spriteSelected.setBounds(x, y, size, size);
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        super.draw(shapeRenderer, batch);
        batch.begin();
        if (strokeColor == UIConfig.ButtonStrokeColor) {
            spriteDefault.draw(batch);
        } else {
            spriteSelected.draw(batch);
        }
        batch.end();
    }
}
