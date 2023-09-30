package clone.tetris.playables;


import clone.tetris.cup.Cup;
import clone.tetris.cup.Stack;
import clone.tetris.game.Stats;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Tetramino {
    public enum Type {
        I,
        O,
        Z,
        S,
        T,
        J,
        L
    }

    private enum Rotation {
        Upside,
        Clockwise,
        Downside,
        CounterClockwise
    }

    private static final int[][][] rotatingOffsets = {
            { //I
                    {12, 1, -10, -21},
                    {-19, -10, -1, 8},
            },
            { //O
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            },
            { //Z
                    {2, -9, 0, -11},
                    {-20, -11, 0, 9}
            },
            { //S
                    {-20, -9, 0, 11},
                    {-2, -11, 0, -9}
            },
            { //T
                    {-9, 11, 0, -11},
                    {-11, -9, 0, 9}
            },
            { //J
                    {2, 11, 0, -11},
                    {-20, -9, 0, 9}
            },
            { //L
                    {-20, 11, 0, -11},
                    {-2, -9, 0, 9}
            }
    };

    private final int[][] wallKicks = {
            {-1, 9, -20, -21},
            {1, -9, 20, 21},
            {1, 11, -20, -19},
            {-1, -11, 20, 19},
    };

    private final int[][] wallKicksStick = {
            {-2, 1, -12, 21},
            {-1, 2, -21, 12},
            {2, -1, 12, -21},
            {1, -2, 21, -12}
    };
    private final Block[] allBlocks;
    private final Type type;
    private Rotation rotation;
    private Color color;

    public boolean isPlaced;
    public boolean canBePlaced;

    public Tetramino(Type type) {
        this.type = type;
        switch (this.type) {
            case I:
                color = TetraminoColors.IColor;
                break;
            case O:
                color = TetraminoColors.OColor;
                break;
            case Z:
                color = TetraminoColors.ZColor;
                break;
            case S:
                color = TetraminoColors.SColor;
                break;
            case T:
                color = TetraminoColors.TColor;
                break;
            case J:
                color = TetraminoColors.JColor;
                break;
            case L:
                color = TetraminoColors.LColor;
        }
        allBlocks = new Block[4];
        rotation = Rotation.Upside;
        isPlaced = false;
        canBePlaced = false;
        CreateBlocks();
    }

    public static Tetramino create() {
        Random r = new Random();
        Tetramino.Type[] allTypes = Tetramino.Type.values();
        return new Tetramino(allTypes[r.nextInt(allTypes.length)]);
    }

    private void CreateBlocks() {
        switch(type) {
            case I:
                allBlocks[0] = new Block(183, color);
                allBlocks[1] = new Block(184, color);
                allBlocks[2] = new Block(185, color);
                allBlocks[3] = new Block(186, color);
                return;
            case O:
                allBlocks[0] = new Block(194, color);
                allBlocks[1] = new Block(195, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(185, color);
                return;
            case Z:
                allBlocks[0] = new Block(193, color);
                allBlocks[1] = new Block(194, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(185, color);
                return;
            case S:
                allBlocks[0] = new Block(195, color);
                allBlocks[1] = new Block(194, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(183, color);
                return;
            case T:
                allBlocks[0] = new Block(194, color);
                allBlocks[1] = new Block(183, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(185, color);
                return;
            case J:
                allBlocks[0] = new Block(193, color);
                allBlocks[1] = new Block(183, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(185, color);
                return;
            case L:
                allBlocks[0] = new Block(195, color);
                allBlocks[1] = new Block(183, color);
                allBlocks[2] = new Block(184, color);
                allBlocks[3] = new Block(185, color);
                return;
            default:
                System.out.println("You messed up with type");
        }
    }

    public void Rotate(boolean clockwise) {
        rotate(clockwise);
        if(isObstructed()) {
            int perspective = clockwise? 1: -1;
            for(int wallKickOffset: type == Type.I ?
                    wallKicksStick[rotation.ordinal()]:
                    wallKicks[rotation.ordinal()]) {
                move(perspective * wallKickOffset);
                if(!isObstructed()) {
                    updateCanBePlaced();
                    return;
                }
                move(-perspective * wallKickOffset);
            }
            rotate(!clockwise);
        }
        updateCanBePlaced();
    }

    private void rotate(boolean clockwise) {
        int rotationOffsetIndex;
        int perspective;

        switch (rotation) {

            case Upside:
                rotationOffsetIndex = clockwise? 0: 1;
                perspective = 1;
                rotation = clockwise? Rotation.Clockwise: Rotation.CounterClockwise;
                break;

            case Clockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                perspective = clockwise? 1: -1;
                rotation = clockwise? Rotation.Downside: Rotation.Upside;
                break;

            case Downside:
                rotationOffsetIndex = clockwise? 0: 1;
                perspective = -1;
                rotation = clockwise? Rotation.CounterClockwise: Rotation.Clockwise;
                break;

            case CounterClockwise:
                rotationOffsetIndex = clockwise? 1: 0;
                perspective = clockwise? -1: 1;
                rotation = clockwise? Rotation.Upside: Rotation.Downside;
                break;

            default:
                System.out.println("You messed up with rotation");
                perspective = 1000;
                rotationOffsetIndex = -1;
        }

        for(int index = 0; index < 4; index++) {
            allBlocks[index].id += perspective * rotatingOffsets[type.ordinal()][rotationOffsetIndex][index];
        }
    }

    public void updateCanBePlaced() {
        canBePlaced = false;
        if(isOnBottom()) {
            canBePlaced = true;
        }
        move(-10);
        if(isColliding()) {
            canBePlaced = true;
        }
        move(10);
    }

    public void moveDown() {
        if(canBePlaced) {
            place();
            return;
        }
        move(-10);
        updateCanBePlaced();
    }

    public void moveToSide(boolean side) {
        int offset = side ? 1: -1;
        if(isOnEdge(side)) {
            return;
        }
        move(offset);
        if(isObstructed()) {
            move(-offset);
        }
        updateCanBePlaced();
    }

    private void move(int offset) {
        for(Block block: allBlocks) {
            block.id += offset;
        }
    }

    public void place() {
        for(Block block: allBlocks) {
            Stack.allBlocks.add(block.clone());
        }
        isPlaced = true;
    }

    public void hardDrop() {
        int linesCount = 0;
        do {
            moveDown();
            linesCount ++;
        } while (!isPlaced);
        Stats.addHardDropBonus(linesCount);
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

    private boolean isOnBottom() {
        for(Block block: allBlocks) {
            if (block.id < 10) {
                return true;
            }
        }
        return false;
    }

    public boolean isObstructed() {
        return isDistorted() || isColliding() || isOutsideCup();
    }

    private boolean isOutsideCup() {
        for(Block block: allBlocks) {
            if (block.id < 0 || block.id > 199) {
                return true;
            }
        }
        return false;
    }

    private boolean isDistorted() {
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

    private boolean isColliding() {
        for(Block stackBlock: Stack.allBlocks) {
            for(Block block: allBlocks) {
                if (block.id == stackBlock.id) {
                    return true;
                }
            }
        }
        return false;
    }
}
