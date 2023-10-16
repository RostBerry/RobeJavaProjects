package clone.tetris.scenes;

import clone.tetris.game.TetrisClone;
import clone.tetris.game.config.Config;
import clone.tetris.game.config.MenuConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.ui.Button;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Screen {

    public List<Button> allButtons;

    public boolean isPlayPressed;

    private final OrthographicCamera camera;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    public MainMenu() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.ScreenWidth, Config.ScreenHeight);

        allButtons = new ArrayList<>();
        allButtons.add(new Button(MenuConfig.PlayButtonX, MenuConfig.PlayButtonY, MenuConfig.PlayButtonWidth, MenuConfig.PlayButtonHeight, "PLAY"));

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
    }

    public void clickOnButton(int x, int y) {
        for (Button button: allButtons) {
            if (TetrisClone.isColliding(button, x, y)) {
                switch (button.text) {
                    case "PLAY":
                        isPlayPressed = true;
                        return;
                }
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