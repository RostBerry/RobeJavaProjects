package clone.tetris.game;

import clone.tetris.game.config.Config;
import clone.tetris.game.config.GameStartConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.input.GameInputManager;
import clone.tetris.input.UIInputManager;
import clone.tetris.scenes.GameSession;
import clone.tetris.scenes.GameStart;
import clone.tetris.scenes.MainMenu;
import clone.tetris.scenes.Options;
import clone.tetris.ui.Button;
import clone.tetris.ui.Switcher;
import com.badlogic.gdx.*;

import java.util.List;

public class TetrisClone extends Game {
    private MainMenu menu;
    private Options options;
    private GameStart gameStart;
    private GameSession game;
    private InputMultiplexer inputMultiplexer;
    private UIInputManager uiInputManager;
    private GameInputManager gameInputManager;

    public static boolean isColliding(Button button, int x, int y) {
        return x >= button.x && x <= button.x + button.width && y >= button.y && y <= button.y + button.height;
    }

    public static boolean isColliding(Switcher switcher, int x, int y) {
        for (Button button: switcher.allSwitchButtons) {
            if (isColliding(button, x, y)) {
                return true;
            }
        }
        return false;
    }

    public static void highlightButtons(int x, int y, List<Button> allButtons) {
        for (Button button: allButtons) {
            if (TetrisClone.isColliding(button, x, y)) {
                button.strokeColor = UIConfig.ButtonSelectedStrokeColor;
            } else {
                button.strokeColor = UIConfig.ButtonStrokeColor;
            }
        }
    }

    public static void highlightSwitchers(int x, int y, List<Switcher> allSwitchers) {
        for (Switcher switcher: allSwitchers) {
            highlightButtons(x, y, switcher.allSwitchButtons);
        }
    }

    @Override
    public void create() {
        UIConfig.update();

        uiInputManager = new UIInputManager(this);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(uiInputManager);
        Gdx.input.setInputProcessor(inputMultiplexer);

        menu = new MainMenu();
        options = new Options();
        gameStart = new GameStart();
        setScreen(menu);
    }

    public void mouseMoved(int x, int y) {
        if (getScreen() == menu) {
            highlightButtons(x, y, menu.allButtons);
        } else if (getScreen() == gameStart) {
            highlightButtons(x, y, gameStart.allButtons);
            highlightSwitchers(x, y, gameStart.allSwitchers);
        } else if (getScreen() == options) {
            highlightButtons(x, y, options.allButtons);
            highlightSwitchers(x, y, options.allSwitchers);
        }
    }

    public void mouseReleased(int x, int y) {
        if (getScreen() == menu) {
            menu.clickOnButton(x, y);
        } else if (getScreen() == gameStart) {
            gameStart.clickOnButton(x, y);
        } else if (getScreen() == options) {
            options.clickOnButton(x, y);
        }
        updateEvents();
    }

    private void updateEvents() {
        if (getScreen() == menu) {
            switch (menu.eventId) {
                case 0:
                    return;
                case 1:
                    setScreen(gameStart);
                    return;
                case 2:
                    setScreen(options);
                    return;
                case 3:
                    dispose();
                    return;
            }
        } else if (getScreen() == gameStart) {
            switch (gameStart.eventId) {
                case 0:
                    return;
                case 1:
                    game = new GameSession();
                    setScreen(game);
                    return;
            }
        } else if (getScreen() == options) {
            switch (options.eventId) {
                case 0:
                    return;
                case 1:
                    setScreen(menu);
                    return;
            }
        }
    }

    @Override
    public void render() {
        screen.render(Math.min(Gdx.graphics.getDeltaTime(), 1 / (float) Config.FPS));
    }

    @Override
    public void dispose() {
        screen.hide();
        UIConfig.dispose();
        menu.dispose();
        gameStart.dispose();
    }
}
