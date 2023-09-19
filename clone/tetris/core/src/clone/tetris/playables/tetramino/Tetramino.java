package clone.tetris.playables.tetramino;


import clone.tetris.cup.Cup;
import clone.tetris.playables.Block;
import clone.tetris.playables.TetraminoColors;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Tetramino {
    protected enum Type {
        Stick,
        Square,
        Z,
        ZReverse,
        Triangle,
        G,
        GReverse
    }

    protected enum Rotation {
        Upside,
        Clockwise,
        Downside,
        CounterClockwise
    }

    protected static final int[][][] rotatingOffsets = {
            {
                    {12, 1, -10, -21},
                    {-19, -10, -1, 8},
            },
            {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            {
                    {2, -9, 0, -11},
                    {-20, -11, 0, 9}
            },
            {
                    {-20, -9, 0, 11},
                    {-2, -11, 0, -9}
            },
            {
                    {},
                    {}
            },
            {
                    {},
                    {}
            },
            {
                    {},
                    {}
            }
    };

    protected final Block[] allBlocks;
    protected final Type type;
    protected Rotation rotation;
    protected Color color;

    public Tetramino(Type type, Color color) {
        this.type = type;
        allBlocks = new Block[4];
        this.color = color;
        rotation = Rotation.Upside;
        CreateBlocks();
    }

    protected abstract void CreateBlocks();

    public void Rotate(boolean clockwise) {
        int rotationOffsetIndex;
        int multiply = 1;

        switch (rotation) {

            case Upside:
                rotationOffsetIndex = clockwise? 0: 1;
                rotation = clockwise? Rotation.Clockwise: Rotation.CounterClockwise;
                break;

            case Clockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                rotation = clockwise? Rotation.Downside: Rotation.Upside;
                break;

            case Downside:
                rotationOffsetIndex = clockwise? 0: 1;
                multiply = -1;
                rotation = clockwise? Rotation.CounterClockwise: Rotation.Clockwise;
                break;

            case CounterClockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                multiply = -1;
                rotation = clockwise? Rotation.Upside: Rotation.Downside;
                break;

            default:
                System.out.println("You messed up with rotation");
                rotationOffsetIndex = -1;
        }

        for(int index = 0; index < 4; index++) {
            allBlocks[index].id += multiply * rotatingOffsets[type.ordinal()][rotationOffsetIndex][index];
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (Block block: allBlocks) {
            block.draw(shapeRenderer, Cup.IdToPos(block.id));
        }
    }
}
