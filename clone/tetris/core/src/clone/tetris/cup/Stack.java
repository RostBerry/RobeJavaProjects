package clone.tetris.cup;

import clone.tetris.game.Stats;
import clone.tetris.playables.Block;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;

public class Stack {
    public static ArrayList<Block> allBlocks = new ArrayList<>();

    public static Stats.LineRemoveType removeType = Stats.LineRemoveType.None;

    public static void draw(ShapeRenderer shapeRenderer) {
        for (Block block: allBlocks) {
            block.draw(shapeRenderer, Cup.IdToPos(block.id));
        }
    }

    private static void moveDown(int startY) {
        for(Block block: allBlocks) {
            if (Block.getYFromId(block.id) >= startY) {
                block.id -= 10;
            }
        }
    }

    private static void removeLine(int Y) {
        Iterator<Block> iterator = allBlocks.iterator();
        while(iterator.hasNext()) {
            Block block = iterator.next();
            if (Block.getYFromId(block.id) == Y) {
                iterator.remove();
            }
        }
    }

    public static void updateLines() {
        int removedLinesCount = 0;
        for (int y = 19; y >= 0; y--) {
            int count = 0;
            for (int x = 0; x < 10; x++) {
                int id = x + y * 10;
                for (Block block: allBlocks) {
                    if (block.id == id) {
                        count++;
                        break;
                    }
                }
                if (count < x + 1) {
                    break;
                }
            }
            if (count == 10) {
                removeLine(y);
                moveDown(y + 1);
                removedLinesCount++;
            }
        }
        removeType = Stats.allRemoveTypes[removedLinesCount];
    }
}
