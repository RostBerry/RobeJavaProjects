package clone.tetris.playables.tetramino;

import clone.tetris.playables.Block;
import clone.tetris.playables.TetraminoColors;

public class G extends Tetramino{
    public G() {
        super(Type.G, TetraminoColors.GColor);
    }

    protected void CreateBlocks() {
        allBlocks[0] = new Block(193, color);
        allBlocks[1] = new Block(183, color);
        allBlocks[2] = new Block(184, color);
        allBlocks[3] = new Block(185, color);
    }
}
