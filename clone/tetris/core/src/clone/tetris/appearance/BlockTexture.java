package clone.tetris.appearance;

import clone.tetris.game.config.Config;
import clone.tetris.playables.Tetramino;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BlockTexture {

    public static final Sprite[][] spritePacks = createTexturePacks(false);
    public static final Sprite[][] ghostSpritePacks = createTexturePacks(true);

    private static Sprite[][] createTexturePacks(boolean isGhost) {
        int arraySize = Tetramino.Type.values().length;
        Sprite[][] textures = new Sprite[1][arraySize];
        for (int packIndex = 0; packIndex < 1; packIndex++) {
            for (int tetraminoIndex = 0; tetraminoIndex < arraySize; tetraminoIndex++) {
                Color textureColor = TetraminoColors.colorPacks[packIndex][tetraminoIndex];
                textures[packIndex][tetraminoIndex] = new Sprite(createTexture(textureColor, isGhost));
                textures[packIndex][tetraminoIndex].setSize(Config.CellSize, Config.CellSize);
            }
        }
        return textures;
    }

    private static Texture createTexture(Color mainColor, boolean isGhost) {
        int size = 8;

        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                pixmap.setColor(mainColor);
                if (isGhost) {
                    if (x == 0 || y == 0 || x == size - 1 || y == size - 1) {
                        pixmap.drawPixel(x, y);
                    }
                } else {
                    pixmap.drawPixel(x, y);
                    if (x == 0 && y == 0 || x == 1 && y == 1 || x == 2 && y == 1 || x == 1 && y == 2) {
                        pixmap.setColor(new Color(1, 1, 1, mainColor.a));
                        pixmap.drawPixel(x, y);
                    }
                    if (x == size - 1 || y == size - 1) {
                        pixmap.setColor(Color.BLACK);
                        pixmap.drawPixel(x, y);
                    }
                }
            }
        }

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}