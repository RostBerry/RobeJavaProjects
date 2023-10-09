package clone.tetris.playables;

import clone.tetris.appearance.BlockTexture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block{
    public static class StackBlock {
        private final Tetramino.Type type;
        public StackBlock(Tetramino.Type type) {
            this.type = type;
        }
        public void draw(SpriteBatch batch, float x, float y) {
            Sprite sprite = BlockTexture.spritePacks[0][type.ordinal()];
            sprite.setPosition(x, y);
            sprite.draw(batch);
        }
    }
    public int xId;
    public int yId;

    public final Tetramino.Type blockType;

    public boolean isGhost;

    public Block(int x, int y, Tetramino.Type type) {
        xId = x;
        yId = y;
        blockType = type;
        isGhost = false;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        Sprite sprite = isGhost ? BlockTexture.ghostSpritePacks[0][blockType.ordinal()]: BlockTexture.spritePacks[0][blockType.ordinal()];
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }
}
