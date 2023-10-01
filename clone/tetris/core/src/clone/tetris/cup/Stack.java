package clone.tetris.cup;

import clone.tetris.game.Stats;
import clone.tetris.playables.Block;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Stack {
    public static Block.StackBlock[][] allBlocks = fillAllBlocks();

    public static Stats.LineRemoveType removeType = Stats.LineRemoveType.None;

    private static Block.StackBlock[][] fillAllBlocks() {
        Block.StackBlock[][] blocks = new Block.StackBlock[40][10];
        for(int y = 39; y >= 0; y--) {
            for(int x = 0; x < 10; x++) {
                blocks[y][x] = null;
            }
        }
        return blocks;
    }

    public static void addBlock(Block newBlock) {
        allBlocks[newBlock.yId][newBlock.xId] = new Block.StackBlock(new Color(newBlock.color));
    }

    public static void removeBlock(int y, int x) {
        allBlocks[y][x] = null;
    }

    public static void draw(ShapeRenderer shapeRenderer) {
        for(int y = 0; y < 20; y++) {
            for(int x = 0; x < 10; x++) {
                if (allBlocks[y][x] != null) {
                    Cell cell = Cup.allCells[y][x];
                    allBlocks[y][x].draw(shapeRenderer, cell.x, cell.y);
                }
            }
        }
    }

    private static void moveDown(int startY) {
        for(int y = startY; y < 40; y++) {
            for(int x = 0; x < 10; x++) {
                if (allBlocks[y][x] != null) {
                    allBlocks[y - 1][x] = allBlocks[y][x];
                    allBlocks[y][x] = null;
                }
            }
        }
    }

    private static void removeLine(int Y) {
        for(int x = 0; x < 10; x++) {
            if(allBlocks[Y][x] != null) {
                removeBlock(Y, x);
            }
        }
    }

    public static void updateLines() {
        int removedLinesCount = 0;
        for(int y = 39; y >= 0; y--) {
            boolean isLineFull = true;
            for (int x = 0; x < 10; x++) {
                if (allBlocks[y][x] == null) {
                    isLineFull = false;
                    break;
                }
            }
            if(isLineFull) {
                removeLine(y);
                moveDown(y + 1);
                removedLinesCount++;
            }
        }
        removeType = Stats.allRemoveTypes[removedLinesCount];
    }
}
