package clone.tetris.scenes;

import clone.tetris.game.config.Config;
import clone.tetris.cup.Stack;
import clone.tetris.game.Stats;
import clone.tetris.game.TetraminoStatsManager;
import clone.tetris.game.config.GameConfig;
import clone.tetris.game.config.UIConfig;
import clone.tetris.input.GameInputManager;
import clone.tetris.playables.Tetramino;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import clone.tetris.cup.Cup;

public class GameSession implements Screen {
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

	private final Sound rotatingSound;
	private final Sound fallingSound;
	private final Sound moveSound;
	private final Sound lineRemovingSound;
	private final Sound lockingSound;
	private final Sound wallTouchingSound;
	private final Sound gameOverSound;
	private final float soundVolume;

	private final Music music;
	private final float musicVolume;

	private final OrthographicCamera camera;
	private final SpriteBatch batch;
	private final ShapeRenderer shapeRenderer;

	public GameSession () {
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

		rotatingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rotate.mp3"));
		fallingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/falling.mp3"));
		moveSound = Gdx.audio.newSound(Gdx.files.internal("sounds/move.mp3"));
		lineRemovingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/line.mp3"));
		lockingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/locking.mp3"));
		wallTouchingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/touching_wall.mp3"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.mp3"));

		soundVolume = 0.4f;

		music = Gdx.audio.newMusic(Gdx.files.internal("music/tetris_theme.mp3"));

		musicVolume = 0.5f;

		music.play();
		music.setLooping(true);
		music.setVolume(musicVolume);
	}

	private void gameOver() {
		music.stop();
		currentState = GameState.TerminatedByOverflow;
		gameOverSound.play(soundVolume);
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
			gameOver();
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
		int interval = Config.CurrentSpeedCurveFormat == Config.SpeedCurveFormat.NES? Stats.NESFallingIntervals[Stats.getActualDifficulty() - 1] - 1 : Stats.calculateGuidelineDifficulty();
		if (isSoftDropHold) {
			interval /= (int) Math.log(Math.pow(Stats.softDropIncrease, Stats.difficulty)) + Stats.softDropIncrease;
		}
		if (canBeMovedDown && frameCounter >= interval) {
			fallingSound.play(soundVolume);
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
				gameOver();
				return;
			}
			Stack.updateLines();
			Stats.addLineBonus(Stack.removeType);
			if (Stack.removeType != Stats.LineRemoveType.None) {
				lineRemovingSound.play(soundVolume);
			}
			createTetramino();
			createGhostTetramino();
			isHeld = false;
			isMovingRightPressed = false;
			isMovingLeftPressed = false;
		}
	}

	public void moveTetramino(boolean side) {
		if (currentState == GameState.Running) {
			moveSound.play(soundVolume);
			tetramino.moveToSide(side);
			createGhostTetramino();
			resetLockPause();
		}
	}

	public void rotateTetramino(boolean clockwise) {
		if (currentState == GameState.Running) {
			rotatingSound.play(soundVolume);
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
	public void show() {

	}

	@Override
	public void render(float delta) {
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

		if (GameConfig.DoShowTetraminoStats) {
			TetraminoStatsManager.drawPreviews(batch);
		}

		batch.begin();
		UIConfig.GameFont.draw(batch, "LINES: " + Stats.lineCount, Config.LinesCountX, Config.LinesCountY);
		UIConfig.GameFont.draw(batch, "SCORE: \n" + Stats.score, Config.CurrentScoreX, Config.CurrentScoreY);
		UIConfig.GameFont.draw(batch, "LEVEL: " + Stats.difficulty, Config.DifficultyX, Config.DifficultyY);
		UIConfig.GameFont.draw(batch, "NEXT:", Config.PreviewTextX, Config.PreviewTextY);
		UIConfig.GameFont.draw(batch, "HOLD:", Config.HoldTextX, Config.HoldTextY);
		if (GameConfig.DoShowTetraminoStats) {
			UIConfig.GameFont.draw(batch, "STATISTICS:", Config.StatsTextX, Config.StatsTextY);
			TetraminoStatsManager.drawTexts(batch, UIConfig.GameFont);
		}
		UIConfig.GameFont.draw(batch, "TETRIS RATE: " + (int)(Stats.getTetrisRate() * 100) + "%", Config.TetrisRateX, Config.TetrisRateY);
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
	public void dispose () {
		rotatingSound.dispose();
		fallingSound.dispose();
		moveSound.dispose();
		lineRemovingSound.dispose();
		lockingSound.dispose();
		wallTouchingSound.dispose();
		gameOverSound.dispose();

		music.dispose();

		batch.dispose();
		shapeRenderer.dispose();
	}
}
