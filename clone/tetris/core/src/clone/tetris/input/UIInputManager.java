package clone.tetris.input;

import clone.tetris.game.TetrisClone;
import clone.tetris.game.config.Config;
import com.badlogic.gdx.InputProcessor;

public class UIInputManager implements InputProcessor {
    private final TetrisClone tetrisClone;

    public UIInputManager(TetrisClone tetris) {
        tetrisClone = tetris;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        screenY = (int)Config.ScreenHeight - screenY;
        tetrisClone.mouseReleased(screenX, screenY);
        return true;
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
        screenY = (int)Config.ScreenHeight - screenY;
        tetrisClone.mouseMoved(screenX, screenY);
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
