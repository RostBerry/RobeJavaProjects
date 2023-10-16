package clone.tetris.playables;


import clone.tetris.game.config.Config;
import clone.tetris.cup.Cell;
import clone.tetris.cup.Cup;
import clone.tetris.cup.Stack;
import clone.tetris.game.Stats;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.*;

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

    private static final int[][][][] rotatingOffsets = {
            { //I
                    {{2, 1}, {1, 0}, {0, -1}, {-1, -2}},
                    {{1, -2}, {0, -1}, {-1, 0}, {-2, 1}},
            },
            { //O
                    {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                    {{0, 0}, {0, 0}, {0, 0}, {0, 0}}
            },
            { //Z
                    {{2, 0}, {1, -1}, {0, 0}, {-1, -1}},
                    {{0, -2}, {-1, -1}, {0, 0}, {-1, 1}}
            },
            { //S
                    {{0, -2}, {1, -1}, {0, 0}, {1, 1}},
                    {{-2, 0}, {-1, -1}, {0, 0}, {1, -1}}
            },
            { //T
                    {{1, -1}, {1, 1}, {0, 0}, {-1, -1}},
                    {{-1, -1}, {1, -1}, {0, 0}, {-1, 1}}
            },
            { //J
                    {{2, 0}, {1, 1}, {0, 0}, {-1, -1}},
                    {{0, -2}, {1, -1}, {0, 0}, {-1, 1}}
            },
            { //L
                    {{0, -2}, {1, 1}, {0, 0}, {-1, -1}},
                    {{-2, 0}, {1, -1}, {0, 0}, {-1, 1}}
            }
    };

    private static final int[][][] wallKicks = {
            {{-1, 0}, {-1, 1}, {0, -2}, {-1, -2}},
            {{1, 0}, {1, -1}, {0, 2}, {1, 2}},
            {{1, 0}, {1, 1}, {0, -2}, {1, -2}},
            {{-1, 0}, {-1, -1}, {0, 2}, {-1, 2}},
    };

    private static final int[][][] wallKicksStick = {
            {{-2, 0}, {1, 0}, {-2, -1}, {1, 2}},
            {{-1, 0}, {2, 0}, {-1, 2}, {2, -1}},
            {{2, 0}, {-1, 0}, {2, 1}, {-1, -2}},
            {{1, 0}, {-2, 0}, {1, -2}, {-2, 1}}
    };
    private final Block[] allBlocks;
    private final Type type;

    private static final Tetramino.Type[] allTypes = Tetramino.Type.values();
    public static ArrayList<Tetramino.Type> preview;

    public static class Preview {
        private final Block.StackBlock[] allBlocks;
        private static final int[][][] allBlocksPos = {
                {{0, 2}, {1, 2}, {2, 2}, {3, 2}},
                {{1, 3}, {2, 3}, {1, 2}, {2, 2}},
                {{0, 3}, {1, 3}, {1, 2}, {2, 2}},
                {{1, 3}, {2, 3}, {0, 2}, {1, 2}},
                {{1, 3}, {0, 2}, {1, 2}, {2, 2}},
                {{0, 3}, {0, 2}, {1, 2}, {2, 2}},
                {{2, 3}, {0, 2}, {1, 2}, {2, 2}}
        };
        private final Type type;

        public Preview(Type type) {
            this.type = type;
            allBlocks = new Block.StackBlock[4];
            for(int i = 0; i < 4; i++) {
                allBlocks[i] = new Block.StackBlock(type);
            }
        }

        public Type getType() {
            return type;
        }

        public void draw(SpriteBatch batch, float x, float y) {
            batch.begin();
            for (int i = 0; i < 4; i++) {
                int[] blockPos = allBlocksPos[type.ordinal()][i];
                allBlocks[i].draw(batch,
                        x + Config.CellSize * blockPos[0],
                        y + Config.CellSize * blockPos[1]);
            }
            batch.end();
        }
    }
    private Rotation rotation;

    public boolean isPlaced;
    public boolean canBePlaced;

    public Tetramino(Type type) {
        this.type = type;
        allBlocks = new Block[4];
        rotation = Rotation.Upside;
        isPlaced = false;
        canBePlaced = false;
        switch(type) {
            case I:
                allBlocks[0] = new Block(3, 19, type);
                allBlocks[1] = new Block(4, 19, type);
                allBlocks[2] = new Block(5, 19, type);
                allBlocks[3] = new Block(6, 19, type);
                break;
            case O:
                allBlocks[0] = new Block(4, 20, type);
                allBlocks[1] = new Block(5, 20, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(5, 19, type);
                break;
            case Z:
                allBlocks[0] = new Block(3, 20, type);
                allBlocks[1] = new Block(4, 20, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(5, 19, type);
                break;
            case S:
                allBlocks[0] = new Block(5, 20, type);
                allBlocks[1] = new Block(4, 20, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(3, 19, type);
                break;
            case T:
                allBlocks[0] = new Block(4, 20, type);
                allBlocks[1] = new Block(3, 19, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(5, 19, type);
                break;
            case J:
                allBlocks[0] = new Block(3, 20, type);
                allBlocks[1] = new Block(3, 19, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(5, 19, type);
                break;
            case L:
                allBlocks[0] = new Block(5, 20, type);
                allBlocks[1] = new Block(3, 19, type);
                allBlocks[2] = new Block(4, 19, type);
                allBlocks[3] = new Block(5, 19, type);
                break;
            default:
                System.out.println("You messed up with type");
        }
        updateCanBePlaced();
    }

    public Type getType() {
        return type;
    }

    public static void createBag() {
        preview = new ArrayList<>(Arrays.asList(allTypes));
        Collections.shuffle(preview);

    }

    public static Tetramino create() {
        Tetramino createdTetramino = new Tetramino(preview.get(0));
        preview.remove(0);
        if(preview.isEmpty()) {
            createBag();
        }
        return createdTetramino;
    }

    public static Tetramino createGhost(Tetramino tetramino) {
        Tetramino ghost = new Tetramino(tetramino.type);
        for (int blockId = 0; blockId < 4; blockId++) {
            ghost.allBlocks[blockId] = new Block(tetramino.allBlocks[blockId].xId, tetramino.allBlocks[blockId].yId, tetramino.type);
            ghost.allBlocks[blockId].isGhost = true;
        }
        return ghost;
    }

    public static Preview createPreviewFromBag() {
        return new Preview(preview.get(0));
    }

    public static Tetramino fromPreview(Preview previewMino) {return new Tetramino(previewMino.type);}

    public static Tetramino.Preview toPreview(Tetramino tetramino) {return new Preview(tetramino.type);}

    public void Rotate(boolean clockwise) {
        Rotation initialRotation = rotation;
        rotate(clockwise);
        if(isObstructed()) {
            int perspective = clockwise? 1: -1;
            int rotationIndex = clockwise? initialRotation.ordinal(): rotation.ordinal();
            for(int[] wallKickOffset: type == Type.I ?
                    wallKicksStick[rotationIndex]:
                    wallKicks[rotationIndex]) {
                int offsetX = perspective * wallKickOffset[0];
                int offsetY = perspective * wallKickOffset[1];
                move(offsetX, offsetY);
                if(!isObstructed()) {
                    updateCanBePlaced();
                    return;
                }
                move(-offsetX, -offsetY);
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
            int[] offset = rotatingOffsets[type.ordinal()][rotationOffsetIndex][index];
            allBlocks[index].xId += perspective * offset[0];
            allBlocks[index].yId += perspective * offset[1];
        }
    }

    public void updateCanBePlaced() {
        canBePlaced = false;
        move(0, -1);
        if(isObstructed()) {
            canBePlaced = true;
        }
        move(0, 1);
    }

    public void moveDown() {
        if(canBePlaced) {
            place();
            return;
        }
        move(0, -1);
        updateCanBePlaced();
    }

    public void moveToSide(boolean side) {
        int offsetX = side ? 1: -1;
        move(offsetX, 0);
        if(isObstructed()) {
            move(-offsetX, 0);
        }
        updateCanBePlaced();
    }

    private void move(int offsetX, int offsetY) {
        for(Block block: allBlocks) {
            block.xId += offsetX;
            block.yId += offsetY;
        }
    }

    public void place() {
        for(Block block: allBlocks) {
            Stack.addBlock(block);
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

    public void draw(SpriteBatch batch) {
        batch.begin();
        for (Block block : allBlocks) {
            if (block.yId < 20) {
                Cell cell = Cup.allCells[block.yId][block.xId];
                block.draw(batch, cell.x, cell.y);
            }
        }
        batch.end();
    }

    public boolean isOnBottom() {
        for(Block block: allBlocks) {
            if (block.yId < 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isObstructed() {
        return isOutsideCup() || isColliding() ;
    }

    private boolean isOutsideCup() {
        for(Block block: allBlocks) {
            if (block.xId < 0 || block.yId < 0 || block.xId > 9 || block.yId > 39) {
                return true;
            }
        }
        return false;
    }

    public boolean isOutsideVisibleCup() {
        for(Block block: allBlocks) {
            if (block.yId < 20) {
                return false;
            }
        }
        return true;
    }

    private boolean isColliding() {
        for(Block block: allBlocks) {
            if(Stack.allBlocks[block.yId][block.xId] != null) {
                return true;
            }
        }
        return false;
    }
}
