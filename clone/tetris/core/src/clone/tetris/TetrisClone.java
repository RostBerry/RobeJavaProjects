package clone.tetris;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;

import clone.tetris.cup.Cup;
import clone.tetris.playables.tetramino.*;

public class TetrisClone extends ApplicationAdapter {

	private final Color whiteColor = new Color(1, 1, 1, 1);
	private final Color redColor = new Color(1, 0, 0, 1);

	private Cup cup;
	private Tetramino tetramino;

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

		tetramino = new Triangle();
		tetramino.Rotate(true);

		timerTask = new Timer.Task() {
			@Override
			public void run() {
				tetramino.moveDown();
			}
		};

		Timer.schedule(timerTask, 2f, 0.1f);
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
