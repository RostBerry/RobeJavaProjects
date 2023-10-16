package clone.tetris.scenes;

import clone.tetris.game.config.Config;
import clone.tetris.game.config.GameStartConfig;
import clone.tetris.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameStart implements Screen {

    public List<Button> allButtons;

    public boolean isPlayPressed;

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
        allButtons.add(new Button(GameStartConfig.PlayButtonX, GameStartConfig.PlayButtonY, GameStartConfig.PlayButtonWidth, GameStartConfig.PlayButtonHeight, "PLAY"));
        isPlayPressed = false;
    }

    @Override
    public void show() {
        isPlayPressed = false;
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
