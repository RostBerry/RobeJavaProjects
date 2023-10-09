package clone.tetris.input;

import clone.tetris.TetrisClone;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;

public class GameInputManager implements InputProcessor, ControllerListener {

    private final TetrisClone game;
    public GameInputManager(TetrisClone game) {
        this.game = game;
    }

    //Keyboard

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                game.pressMoveButton(true);
                return true;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                game.pressMoveButton(false);
                return true;
            case Input.Keys.UP:
                game.rotateTetramino(true);
                return true;
            case Input.Keys.Q:
                game.rotateTetramino(false);
                return true;
            case Input.Keys.SPACE:
                game.hardDropTetramino();
                return true;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                game.isSoftDropHold = true;
                return true;
            case Input.Keys.C:
                game.holdTetramino();
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
            case Input.Keys.A:
                game.unPressMoveButton(false);
                return true;
            case Input.Keys.D:
                game.unPressMoveButton(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //Touch screen

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

    //Mouse

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    //Controller

    public void connected(Controller controller) {
        System.out.println("Controller Connected");
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("Controller Disconnected");
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println(buttonCode);
        switch (buttonCode) {
            case 0:
                game.rotateTetramino(false);
                return true;
            case 1:
                game.rotateTetramino(true);
                return true;
            case 13:
                game.pressMoveButton(false);
                return true;
            case 14:
                game.pressMoveButton(true);
                return true;
            case 12:
                game.isSoftDropHold = true;
                return true;
            case 11:
                game.hardDropTetramino();
                return true;
            case 9:
            case 10:
                game.holdTetramino();
                return true;
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        switch (buttonCode) {
            case 12:
                game.isSoftDropHold = false;
                return true;
            case 13:
                game.unPressMoveButton(false);
                return true;
            case 14:
                game.unPressMoveButton(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }
}
