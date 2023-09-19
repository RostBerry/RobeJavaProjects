package clone.tetris.playables.tetramino;

import clone.tetris.playables.Block;
import clone.tetris.playables.TetraminoColors;

public class ZReverse extends Tetramino{
    public ZReverse() {
        super(Type.ZReverse, TetraminoColors.ZReverseColor);
    }

    protected void CreateBlocks() {
        allBlocks[0] = new Block(195, color);
        allBlocks[1] = new Block(194, color);
        allBlocks[2] = new Block(184, color);
        allBlocks[3] = new Block(183, color);
    }
}

