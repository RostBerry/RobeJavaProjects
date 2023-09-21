package clone.tetris;

import clone.tetris.input.GameInputManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

import clone.tetris.cup.Cup;
import clone.tetris.playables.tetramino.*;

public class TetrisClone extends ApplicationAdapter {

	private Cup cup;
	private Tetramino tetramino;

	private GameInputManager gameInputManager;

	private Timer.Task timerTask;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 555, 960);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		cup = new Cup();

		tetramino = new Stick();

		gameInputManager = new GameInputManager(tetramino);
		InputMultiplexer multiplexer = new InputMultiplexer(gameInputManager);
		Gdx.input.setInputProcessor(multiplexer);

		timerTask = new Timer.Task() {
			@Override
			public void run() {
				tetramino.moveDown();
			}
		};
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT  );
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		camera.update();
		batch.begin();

		cup.draw(shapeRenderer);

		tetramino.draw(shapeRenderer);

		batch.end();
	}
	
	@Override
	public void dispose () {

		batch.dispose();
		shapeRenderer.dispose();
	}
}
