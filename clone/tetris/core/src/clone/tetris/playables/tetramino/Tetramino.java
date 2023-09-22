package clone.tetris.playables.tetramino;


import clone.tetris.cup.Cup;
import clone.tetris.playables.Block;
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
            { //Stick
                    {12, 1, -10, -21},
                    {-19, -10, -1, 8},
            },
            { //Square
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            { //Z
                    {2, -9, 0, -11},
                    {-20, -11, 0, 9}
            },
            { //ZReverse
                    {-20, -9, 0, 11},
                    {-2, -11, 0, -9}
            },
            { //Triangle
                    {-9, 11, 0, -11},
                    {-11, -9, 0, 9}
            },
            { //G
                    {2, 11, 0, -11},
                    {-20, -9, 0, 9}
            },
            { //GReverse
                    {-20, 11, 0, -11},
                    {-2, -9, 0, 9}
            }
    };

    protected final int[][] wallKicks = {
            {-1, 9, -20, -21},
            {1, -9, 20, 21},
            {1, 11, -20, 19},

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
        int[] blocksIdBefore = new int[4];
        Rotation rotationBefore = rotation;
        for(int i = 0; i < 4; i++) {
            blocksIdBefore[i] = allBlocks[i].id;
        }
        rotate(clockwise);
        if(isObstructed()) {
            for(int i = 0; i < 4; i++) {
                allBlocks[i].id = blocksIdBefore[i];
            }
            rotation = rotationBefore;
        }
    }

    private void rotate(boolean clockwise) {
        int rotationOffsetIndex;
        int multiply;

        switch (rotation) {

            case Upside:
                rotationOffsetIndex = clockwise? 0: 1;
                multiply = 1;
                rotation = clockwise? Rotation.Clockwise: Rotation.CounterClockwise;
                break;

            case Clockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                multiply = clockwise? 1: -1;
                rotation = clockwise? Rotation.Downside: Rotation.Upside;
                break;

            case Downside:
                rotationOffsetIndex = clockwise? 0: 1;
                multiply = -1;
                rotation = clockwise? Rotation.CounterClockwise: Rotation.Clockwise;
                break;

            case CounterClockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                multiply = clockwise? -1: 1;
                rotation = clockwise? Rotation.Upside: Rotation.Downside;
                break;

            default:
                System.out.println("You messed up with rotation");
                multiply = 1000;
                rotationOffsetIndex = -1;
        }

        for(int index = 0; index < 4; index++) {
            allBlocks[index].id += multiply * rotatingOffsets[type.ordinal()][rotationOffsetIndex][index];
        }
    }

    public void moveDown() {
        for(Block block: allBlocks) {
            block.id -= 10;
        }
    }

    public void moveToSide(boolean side) {
        int offset = side ? 1: -1;
        if (!isOnEdge(side)) {
            for(Block block: allBlocks) {
                block.id += offset;
            }
        }
    }

    public void draw(ShapeRenderer shapeRenderer) {
        for (Block block: allBlocks) {
            block.draw(shapeRenderer, Cup.IdToPos(block.id));
        }
    }

    private boolean isOnEdge(boolean side ) { //true = right, false = left
        int edgeX = side ? 9: 0;
        for(Block block: allBlocks) {
            if (Block.getXFromId(block.id) == edgeX) {
                return true;
            }
        }
        return false;
    }

    private boolean isObstructed() {
        return isObstructedByEdge();
    }

    private boolean isObstructedByEdge() {
        int min = Block.getXFromId(allBlocks[0].id);
        int maxDifference = Math.abs(Block.getXFromId(allBlocks[1].id) - min);

        for(Block block: allBlocks) {
            int current = Block.getXFromId(block.id);
            int currentDifference = Math.abs(current - min);
            maxDifference = Math.max(maxDifference, currentDifference);
            min = Math.min(min, current);
        }

        return maxDifference > 3;
    }
}
