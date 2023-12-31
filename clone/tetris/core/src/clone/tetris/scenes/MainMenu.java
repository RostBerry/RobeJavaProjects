package clone.tetris.scenes;

import clone.tetris.game.TetrisClone;
import clone.tetris.game.config.Config;
import clone.tetris.game.config.MenuConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.ui.Button;
import clone.tetris.ui.TextButton;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

    public List<Button> allButtons;

    public int eventId;

    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    public MainMenu() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.ScreenWidth, Config.ScreenHeight);

        allButtons = new ArrayList<>();
        // Play button
        allButtons.add(new TextButton(0, MenuConfig.PlayButtonX, MenuConfig.PlayButtonY,
                MenuConfig.PlayButtonWidth, MenuConfig.PlayButtonHeight,
                "PLAY"));
        // Options button
        allButtons.add(new TextButton(1, MenuConfig.SettingsButtonX, MenuConfig.SettingsButtonY,
                MenuConfig.SettingsButtonWidth, MenuConfig.SettingsButtonHeight,
                "OPTIONS"));
        //Quit button
        allButtons.add(new TextButton(2, MenuConfig.QuitButtonX, MenuConfig.QuitButtonY,
                MenuConfig.QuitButtonWidth, MenuConfig.QuitButtonHeight,
                "QUIT"));

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void clickOnButton(int x, int y) {
        for (Button button: allButtons) {
            if (TetrisClone.isColliding(button, x, y)) {
                switch (button.id) {
                    case 0: // Play
                        eventId = 1;
                        return;
                    case 1:
                        eventId = 2;
                        return;
                    case 2:
                        eventId = 3;
                }
            }
        }
    }

    @Override
    public void show() {
        eventId = 0;
        for (Button button: allButtons) {
            button.strokeColor = UIConfig.ButtonStrokeColor;
        }
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
        shapeRenderer.dispose();
        batch.dispose();
    }
}