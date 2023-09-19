package clone.tetris.tetramino;


import clone.tetris.cup.Cup;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tetramino {
    public enum Type {
        Stick,
        Square,
        Z,
        ZReverse,
        Triangle,
        G,
        GReversed
    }

    private enum Rotation {
        Upside,
        Clockwise,
        Downside,
        CounterClockwise
    }

    private static final int[][][] rotatingOffsets = {
            {
                    {12, 1, -10, -21},
                    {-19, -10, -1, 8},
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

    private final Block[] allBlocks;
    private final Type type;
    private Rotation rotation;
    private final Color color;

    public Tetramino(Type type) {
        this.type = type;
        allBlocks = new Block[4];
        switch(this.type) {
            case Stick:
                color = TetraminoColors.Cyan;
                break;
            case Square:
                color = TetraminoColors.Yellow;
                break;
            case Z:
                color = TetraminoColors.Red;
                break;
            case ZReverse:
                color = TetraminoColors.Green;
                break;
            case Triangle:
                color = TetraminoColors.Pink;
                break;
            case G:
                color = TetraminoColors.Blue;
                break;
            case GReversed:
                color = TetraminoColors.Orange;
                break;
            default:
                System.out.println("You messed up with tetramino type");
        }
        rotation = Rotation.Upside;
        CreateBlocks();
    }

    private void CreateBlocks() {
        if (type == Type.Stick) {
            allBlocks[0] = new Block(183, color);
            allBlocks[1] = new Block(184, color);
            allBlocks[2] = new Block(185, color);
            allBlocks[3] = new Block(186, color);
        }
    }

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
