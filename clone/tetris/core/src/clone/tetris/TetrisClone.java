package clone.tetris;

import clone.tetris.game.Config;
import clone.tetris.cup.Stack;
import clone.tetris.game.Stats;
import clone.tetris.game.TetraminoStatsManager;
import clone.tetris.input.GameInputManager;
import clone.tetris.playables.Tetramino;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import clone.tetris.cup.Cup;

public class TetrisClone extends ApplicationAdapter {
	private Tetramino tetramino;
	private Tetramino.Preview heldTetramino;
	private Tetramino.Preview previewTetramino;
	private Tetramino ghostTetramino;

	public enum GameState {
		Running,
		TerminatedByOverflow,
		TerminatedByTimeout
	}

	private GameState currentState;
	private GameInputManager gameInputManager;

	public boolean isSoftDropHold;
	private boolean isMovingRightPressed;
	private boolean isMovingLeftPressed;
	private boolean isMovingRightHold;
	private boolean isMovingLeftHold;
	private boolean movingSide;
	private boolean isHeld;

	private int frameCounter;
	private int movingFrameCounter;

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

		Tetramino.createBag();
		TetraminoStatsManager.refresh();
		createTetramino();
		createGhostTetramino();

		currentState = GameState.Running;
		Stats.refresh();
		resetCounter();
		movingFrameCounter = 0;
		isSoftDropHold = false;
		isMovingRightPressed = false;
		isMovingLeftPressed = false;
		isMovingRightHold = false;
		isMovingLeftHold = false;
		movingSide = false;
		isHeld = false;

		gameInputManager = new GameInputManager(this);
		InputMultiplexer multiplexer = new InputMultiplexer(gameInputManager);
		Gdx.input.setInputProcessor(multiplexer);

		Controllers.addListener(gameInputManager);

		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = Config.GameFontSize;
		parameter.color = Color.WHITE;
		parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:%.";
		gameFont = generator.generateFont(parameter);
	}


	private void createTetramino() {
		tetramino = Tetramino.create();
		previewTetramino = Tetramino.createPreviewFromBag();
		resetCounter();
		specifySpawnPos();
		TetraminoStatsManager.addToCount(tetramino);
	}

	private void createGhostTetramino() {
		ghostTetramino = Tetramino.createGhost(tetramino);
		if (!tetramino.canBePlaced) {
			while (!ghostTetramino.canBePlaced) {
				ghostTetramino.moveDown();
			}
		}
	}

	private void specifySpawnPos() {
		if (tetramino.isObstructed()) {
			currentState = GameState.TerminatedByOverflow;
			return;
		}
		if (tetramino.canBePlaced) {
			return;
		}
		tetramino.moveDown();
	}

	private void resetCounter() {
		frameCounter = 0;
	}

	public void resetLockPause() {
		if (tetramino.canBePlaced) {
			resetCounter();
		}
	}

	public void pressMoveButton(boolean side) {
		moveTetramino(side);
		movingFrameCounter = 0;
		if (side) {
			isMovingRightPressed = true;
		} else {
			isMovingLeftPressed = true;
		}
		movingSide = side;
	}

	public void unPressMoveButton(boolean side) {
		movingSide = !side;
		if(side) {
			isMovingRightHold = false;
			isMovingRightPressed = false;
		} else {
			isMovingLeftHold = false;
			isMovingLeftPressed = false;
		}
	}

	private void updateTetramino() {
		frameCounter++;
		movingFrameCounter++;
		boolean canBeMovedDown = true;
		if(isMovingRightPressed || isMovingLeftPressed) {
			if (movingFrameCounter >= Stats.movingDelayOnPressed) {
				moveTetramino(movingSide);
				if (movingSide) {
					isMovingRightHold = true;
				} else {
					isMovingLeftHold = true;
				}
				if (movingSide) {
					isMovingRightPressed = false;
				} else {
					isMovingLeftPressed = false;
				}
				movingFrameCounter = 0;
			}
		} else if (isMovingRightHold || isMovingLeftHold) {
			if (movingFrameCounter >= Stats.movingDelayOnHold) {
				moveTetramino(movingSide);
				movingFrameCounter = 0;
			}
		}
		if (tetramino.canBePlaced) {
			if (frameCounter >= Stats.lockPause) {
				tetramino.moveDown();
				resetCounter();
				return;
			}
			canBeMovedDown = false;
		}
		int interval = Config.CurrentLayout == Config.GameFormat.NES? Stats.NESFallingIntervals[Stats.getActualDifficulty() - 1] - 1 : Stats.calculateGuidelineDifficulty();
		if (isSoftDropHold) {
			interval /= (int) Math.log(Math.pow(Stats.softDropIncrease, Stats.difficulty)) + Stats.softDropIncrease;
		}
		if (canBeMovedDown && frameCounter >= interval) {
			tetramino.moveDown();
			if (isSoftDropHold) {
				Stats.addSoftDropBonus();
			}
			resetCounter();
		}
	}

	private void updateCup() {
		if(tetramino.isPlaced) {
			if (tetramino.isOutsideVisibleCup()) {
				currentState = GameState.TerminatedByOverflow;
				return;
			}
			Stack.updateLines();
			Stats.addLineBonus(Stack.removeType);
			createTetramino();
			createGhostTetramino();
			isHeld = false;
			isMovingRightPressed = false;
			isMovingLeftPressed = false;
		}
	}

	public void moveTetramino(boolean side) {
		if (currentState == GameState.Running) {
			tetramino.moveToSide(side);
			createGhostTetramino();
			resetLockPause();
		}
	}

	public void rotateTetramino(boolean clockwise) {
		if (currentState == GameState.Running) {
			tetramino.Rotate(clockwise);
			createGhostTetramino();
			resetLockPause();
		}
	}

	public void holdTetramino() {
		if (currentState != GameState.Running || isHeld) {
			return;
		}
		isHeld = true;
		if (heldTetramino != null) {
			Tetramino.Type heldTetraminoType = heldTetramino.getType();
			heldTetramino = new Tetramino.Preview(tetramino.getType());
			tetramino = new Tetramino(heldTetraminoType);
			createGhostTetramino();
			resetCounter();
			specifySpawnPos();
			return;
		}
		heldTetramino = Tetramino.toPreview(tetramino);
		createTetramino();
		createGhostTetramino();
	}

	public void hardDropTetramino() {
		if(currentState == GameState.Running) {
			tetramino.hardDrop();
		}
	}

	@Override
	public void render () {
		if(currentState == GameState.Running) {
			updateTetramino();
			updateCup();
		}
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT  );
		Gdx.gl.glEnable(GL30.GL_BLEND);
		Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		camera.update();

		Cup.draw(shapeRenderer);

		Stack.draw(batch);

		ghostTetramino.draw(batch);

		tetramino.draw(batch);

		previewTetramino.draw(batch, Config.PreviewX, Config.PreviewY);

		if(heldTetramino != null) {
			heldTetramino.draw(batch, Config.HoldX, Config.HoldY);
		}

		TetraminoStatsManager.drawPreviews(batch);

		batch.begin();
		gameFont.draw(batch, "LINES: " + Stats.lineCount, Config.LinesCountX, Config.LinesCountY);
		gameFont.draw(batch, "SCORE: \n" + Stats.score, Config.CurrentScoreX, Config.CurrentScoreY);
		gameFont.draw(batch, "LEVEL: " + Stats.difficulty, Config.DifficultyX, Config.DifficultyY);
		gameFont.draw(batch, "NEXT:", Config.PreviewTextX, Config.PreviewTextY);
		gameFont.draw(batch, "HOLD:", Config.HoldTextX, Config.HoldTextY);
		gameFont.draw(batch, "STATISTICS:", Config.StatsTextX, Config.StatsTextY);
		TetraminoStatsManager.drawTexts(batch, gameFont);
		gameFont.draw(batch, "TETRIS RATE: " + (int)(Stats.getTetrisRate() * 100) + "%", Config.TetrisRateX, Config.TetrisRateY);
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
