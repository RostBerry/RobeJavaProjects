package clone.tetris.scenes;

import clone.tetris.game.TetrisClone;
import clone.tetris.game.config.Config;
import clone.tetris.game.config.GameConfig;
import clone.tetris.game.config.OptionsConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.ui.Button;
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
import java.util.Objects;

public class Options implements Screen {
    public List<Button> allButtons;
    public List<Switcher> allSwitchers;

    public int eventId;

    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    public Options() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.ScreenWidth, Config.ScreenHeight);

        allButtons = new ArrayList<>();
        // Back button
        allButtons.add(new TextButton(0, OptionsConfig.BackButtonX, OptionsConfig.BackButtonY,
                OptionsConfig.BackButtonWidth, OptionsConfig.BackButtonHeight,
                "BACK"));

        allSwitchers = new ArrayList<>();
        // Tetramino stats
        allSwitchers.add(new Switcher(1, OptionsConfig.StatsSwitcherX, OptionsConfig.StatsSwitcherY,
                UIConfig.onOff, true));

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void clickOnButton(int x, int y) {
        for (Button button: allButtons) {
            if (TetrisClone.isColliding(button, x, y)) {
                switch (button.id) {
                    // Back button
                    case 0:
                        eventId = 1;
                        return;
                }
            }
        }
        for (Switcher switcher: allSwitchers) {
            boolean isClicked = false;
            // Left Button
            if (TetrisClone.isColliding(switcher.allSwitchButtons.get(0), x, y)) {
                switcher.changeOption(false);
                isClicked = true;
            }
            // Right Button
            if (TetrisClone.isColliding(switcher.allSwitchButtons.get(1), x, y)) {
                switcher.changeOption(true);
                isClicked = true;
            }
            if (isClicked) {
                switch(switcher.id) {
                    case 1:
                        GameConfig.DoShowTetraminoStats = Objects.equals(switcher.getOption(), "ON");
                }
                return;
            }
        }
    }

    @Override
    public void show() {
        eventId = 0;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT  );
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        for (Button button: allButtons) {
            button.draw(shapeRenderer, batch);
        }
        for (Switcher switcher: allSwitchers) {
            switcher.draw(shapeRenderer, batch);
        }

        batch.begin();
        UIConfig.MenuHeaderFont.draw(batch, "TETRAMINO STATS", OptionsConfig.StatsTextX, OptionsConfig.StatsTextY);
        batch.end();
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
