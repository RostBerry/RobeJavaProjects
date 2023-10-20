package clone.tetris.ui;

import clone.tetris.game.config.Config;
import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class Switcher {
    public final int id;
    public final float x;
    public final float y;

    public final float width;
    public final float height;

    private static final float gapWidth = Config.MenuHeaderFontSize;
    private final float buttonSize;

    private final List<String> allOptions;
    public final List<Button> allSwitchButtons;

    private int currentOptionId;

    public Switcher(int id, float x, float y, List<String> options, boolean isWidthFixed) {
        this.id = id;
        this.x = x;
        this.y = y;
        currentOptionId = 0;
        allOptions = options;

        float textWidth;

        buttonSize = UIConfig.SwitcherButtonSize;

        float rightButtonX;
        if (isWidthFixed) {
            width = UIConfig.SwitcherWidth;
            rightButtonX = x + width - buttonSize;
        } else {
            float maxTextWidth = 0;
            GlyphLayout layout = new GlyphLayout();
            for (String option: allOptions) {
                layout.setText(UIConfig.MenuHeaderFont, option);
                if (layout.width > maxTextWidth) {
                    maxTextWidth = layout.width;
                }
            }
            textWidth = maxTextWidth;
            rightButtonX = x + buttonSize + textWidth + gapWidth * 2;
            width = buttonSize * 2 + textWidth + gapWidth * 2;
        }

        allSwitchButtons = new ArrayList<>();

        allSwitchButtons.add(new SwitchButton(0, false, x, y, buttonSize));
        allSwitchButtons.add(new SwitchButton(1, true, rightButtonX, y, buttonSize));

        height = buttonSize;
    }

    public void changeOption(boolean side) {
        if (side) {
            if (currentOptionId < allOptions.size() - 1) {
                currentOptionId++;
            }
            return;
        }
        if (currentOptionId > 0) {
            currentOptionId--;
        }
    }

    public int getOptionId() {
        return currentOptionId;
    }

    public String getOption() {
        return allOptions.get(currentOptionId);
    }

    public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch) {
        for (Button button: allSwitchButtons) {
            button.draw(shapeRenderer, batch);
        }
        batch.begin();
        UIConfig.MenuHeaderFont.draw(batch, allOptions.get(currentOptionId), x + buttonSize + gapWidth, y + buttonSize * 0.75f);
        batch.end();
    }
}
