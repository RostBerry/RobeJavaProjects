package clone.tetris.playables.tetramino;

import clone.tetris.playables.Block;
import clone.tetris.playables.TetraminoColors;

public class Stick extends Tetramino{
    public Stick() {
        super(Type.Stick, TetraminoColors.StickColor);
    }

    protected void CreateBlocks() {
        allBlocks[0] = new Block(183, color);
        allBlocks[1] = new Block(184, color);
        allBlocks[2] = new Block(185, color);
        allBlocks[3] = new Block(186, color);
    }
}
