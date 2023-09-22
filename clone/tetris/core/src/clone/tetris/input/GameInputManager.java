package clone.tetris.input;

import clone.tetris.playables.tetramino.Tetramino;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class GameInputManager implements InputProcessor {

    private Tetramino currentTetramino;
    public GameInputManager(Tetramino tetramino) {
        currentTetramino = tetramino;
    }

    public void setCurrentTetramino(Tetramino newTetramino) {
        currentTetramino = newTetramino;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                currentTetramino.moveToSide(true);
                return true;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                currentTetramino.moveToSide(false);
                return true;
            case Input.Keys.E:
                currentTetramino.Rotate(true);
                return true;
            case Input.Keys.Q:
                currentTetramino.Rotate(false);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
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
