package clone.tetris;

import clone.tetris.config.Config;
import clone.tetris.cup.Stack;
import clone.tetris.game.Stats;
import clone.tetris.input.GameInputManager;
import clone.tetris.playables.Tetramino;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import clone.tetris.cup.Cup;
import com.badlogic.gdx.utils.Timer;

public class TetrisClone extends ApplicationAdapter {

	private Cup cup;
	public Tetramino tetramino;

	public enum GameState {
		Running,
		TerminatedByOverflow,
		TerminatedByTimeout
	}

	public GameState currentState;
	private GameInputManager gameInputManager;

	private Timer.Task fallingTask;

	private boolean isOnVergeOfPlacing;

	private BitmapFont gameFont;
	private FreeTypeFontGenerator generator;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.ScreenWidth, Config.ScreenHeight);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(camera.combined);

		cup = new Cup();

		createTetramino();

		currentState = GameState.Running;
		Stats.refresh();
		Stats.setDifficulty(25);

		gameInputManager = new GameInputManager(this);
		InputMultiplexer multiplexer = new InputMultiplexer(gameInputManager);
		Gdx.input.setInputProcessor(multiplexer);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial_black.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = Config.GameFontSize;
		parameter.color = Color.WHITE;
		parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:";
		gameFont = generator.generateFont(parameter);

		fallingTask = new Timer.Task() {
			@Override
			public void run() {
				tetramino.updateCanBePlaced();
				tetramino.moveDown();
			}
		};

		updateTimer(0);
		isOnVergeOfPlacing = false;
	}


	private void createTetramino() {
		tetramino = Tetramino.create();
		tetramino.updateCanBePlaced();
	}

	private void updateTimer(float delay) {
		fallingTask.cancel();
		if (delay == 0) {
			delay = Stats.fallIntervals[Stats.difficulty - 1];
		}
		if (currentState == GameState.Running) {
			Timer.schedule(fallingTask, delay, Stats.fallIntervals[Stats.difficulty - 1]);
		}
	}

	private void updateCup() {
		if(tetramino.isPlaced) {
			Stack.updateLines();
			Stats.addLineBonus(Stack.removeType);
			createTetramino();
			isOnVergeOfPlacing = false;
			if(tetramino.isObstructed()) {
				currentState = GameState.TerminatedByOverflow;
			}
			updateTimer(0);
			return;
		}
		if(tetramino.canBePlaced && !isOnVergeOfPlacing) {
			isOnVergeOfPlacing = true;
			updateTimer(Stats.lockPause);
		}
	}

	@Override
	public void render () {
		updateCup();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT  );
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		camera.update();

		batch.begin();

		cup.draw(shapeRenderer);

		Stack.draw(shapeRenderer);

		tetramino.draw(shapeRenderer);

		batch.end();

		batch.begin();
		gameFont.draw(batch, "LINES: " + Stats.lineCount, Config.LinesCountX, Config.LinesCountY);
		gameFont.draw(batch, "SCORE: \n" + Stats.score, Config.CurrentScoreX, Config.CurrentScoreY);
		gameFont.draw(batch, "LEVEL: " + Stats.difficulty, Config.DifficultyX, Config.DifficultyY);
		batch.end();
	}
	
	@Override
	public void dispose () {

		batch.dispose();
		shapeRenderer.dispose();
		gameFont.dispose();
		generator.dispose();
	}
}
