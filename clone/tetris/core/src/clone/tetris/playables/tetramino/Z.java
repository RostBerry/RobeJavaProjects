package clone.tetris.playables.tetramino;

import clone.tetris.playables.Block;
import clone.tetris.playables.TetraminoColors;

public class Z extends Tetramino{
    public Z() {
        super(Type.Z, TetraminoColors.ZColor);
    }

    protected void CreateBlocks() {
        allBlocks[0] = new Block(193, color);
        allBlocks[1] = new Block(194, color);
        allBlocks[2] = new Block(184, color);
        allBlocks[3] = new Block(185, color);
    }
}

