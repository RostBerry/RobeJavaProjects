package clone.tetris.scenes;

import clone.tetris.game.TetrisClone;
import clone.tetris.game.config.Config;
import clone.tetris.game.config.GameConfig;
import clone.tetris.game.config.GameStartConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.ui.Button;
import clone.tetris.ui.SwitchButton;
import clone.tetris.ui.Switcher;
import clone.tetris.ui.TextButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameStart implements Screen {

    public List<Button> allButtons;
    public List<Switcher> allSwitchers;

    public boolean isPlayPressed;

    private GameConfig.GameMode chosenGameMode;
    private int startDifficulty;

    private final OrthographicCamera camera;

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    public GameStart() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.ScreenWidth, Config.ScreenHeight);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        allButtons = new ArrayList<>();
        // Start button
        allButtons.add(new TextButton(0, GameStartConfig.StartButtonX, GameStartConfig.StartButtonY,
                GameStartConfig.StartButtonWidth, GameStartConfig.StartButtonHeight,
                "START"));
        // Switcher

        allSwitchers = new ArrayList<>();
        // Game Mode Switcher
        allSwitchers.add(new Switcher(0, GameStartConfig.GameModeSwitcherX, GameStartConfig.GameModeSwitcherY, GameStartConfig.AllGameModes));

        isPlayPressed = false;

        chosenGameMode = GameConfig.GameMode.Marathon;
        startDifficulty = 1;
    }

    public void clickOnButton(int x, int y) {
        for (Button button: allButtons) {
            if (TetrisClone.isColliding(button, x, y)) {
                switch (button.id) {
                    case 0: // Start
                        isPlayPressed = true;
                        GameConfig.update(startDifficulty, chosenGameMode);
                        return;
                }
            }
        }
        for (Switcher switcher: allSwitchers) {
            // Left
            if (TetrisClone.isColliding(switcher.allSwitchButtons.get(0), x, y)) {
                switcher.changeOption(false);
                return;
            }
            // Right
            if (TetrisClone.isColliding(switcher.allSwitchButtons.get(1), x, y)) {
                switcher.changeOption(true);
                return;
            }
        }
    }

    @Override
    public void show() {
        isPlayPressed = false;
        for (Button button: allButtons) {
            button.strokeColor = UIConfig.ButtonStrokeColor;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT  );
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        for (Button button: allButtons) {
            button.draw(shapeRenderer, batch);
        }
        for (Switcher switcher: allSwitchers) {
            switcher.draw(shapeRenderer, batch);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
