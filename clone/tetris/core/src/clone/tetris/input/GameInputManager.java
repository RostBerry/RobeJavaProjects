package clone.tetris.input;

import clone.tetris.TetrisClone;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInputManager implements InputProcessor {

    private final TetrisClone game;
    public GameInputManager(TetrisClone game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                if (game.currentState == TetrisClone.GameState.Running) {
                    game.tetramino.moveToSide(true);
                    game.resetLockPause();
                }
                return true;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                if (game.currentState == TetrisClone.GameState.Running) {
                    game.tetramino.moveToSide(false);
                    game.resetLockPause();
                }
                return true;
            case Input.Keys.UP:
                if (game.currentState == TetrisClone.GameState.Running) {
                    game.tetramino.Rotate(true);
                    game.resetLockPause();
                }
                return true;
            case Input.Keys.Q:
                if (game.currentState == TetrisClone.GameState.Running) {
                    game.tetramino.Rotate(false);
                    game.resetLockPause();
                }
                return true;
            case Input.Keys.SPACE:
                if (game.currentState == TetrisClone.GameState.Running) {
                    game.tetramino.hardDrop();
                }
                return true;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                game.isSoftDropHold = true;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
            case Input.Keys.S:
                game.isSoftDropHold = false;
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
