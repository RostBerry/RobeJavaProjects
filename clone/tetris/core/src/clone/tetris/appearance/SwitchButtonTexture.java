package clone.tetris.appearance;

import clone.tetris.game.config.UIConfig;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SwitchButtonTexture {
    public static final Sprite[] switchButtonRight = createButtonTexture(true);
    public static final Sprite[] switchButtonLeft = createButtonTexture(false);

    private static Sprite[] createButtonTexture(boolean side) {
        int size = 8;

        Sprite[] sprites = new Sprite[2];

        Pixmap pixmapDefault = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmapDefault.setColor(UIConfig.ButtonStrokeColor);
        Pixmap pixmapSelected = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pixmapSelected.setColor(UIConfig.ButtonSelectedStrokeColor);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (side) {
                    if (x >= 1 && x <3 && (y == 1 || y == 6) || x >= 3 && x < 5 && (y == 2 || y == 5) || x >= 5 && x < 7 && (y == 3 || y == 4)) {
                        pixmapDefault.drawPixel(x, y);
                        pixmapSelected.drawPixel(x, y);
                    }
                } else {
                    if (x >= 5 && x < 7  && (y == 1 || y == 6) || x >= 3 && x < 5 && (y == 2 || y == 5) || x >= 1 && x <3 && (y == 3 || y == 4)) {
                        pixmapDefault.drawPixel(x, y);
                        pixmapSelected.drawPixel(x, y);
                    }
                }
            }
        }

        sprites[0] = new Sprite(new Texture(pixmapDefault));
        pixmapDefault.dispose();
        sprites[1] = new Sprite(new Texture(pixmapSelected));
        pixmapSelected.dispose();

        return sprites;
    }
}
