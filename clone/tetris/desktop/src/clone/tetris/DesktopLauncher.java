package clone.tetris;

import clone.tetris.game.TetrisClone;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import clone.tetris.game.config.Config;

public class DesktopLauncher {
	private static boolean isTetrisLaunched = false;
	private static DialogueFrame frame;

	public static void main(String[] arg) {
		frame = new DialogueFrame();
	}

	public static void launchTetris() {
		if (!isTetrisLaunched) {
			isTetrisLaunched = true;
			frame.dispose();

			Config.Update(1280, 960, Config.SpeedCurveFormat.Modern, 60);

			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setWindowedMode((int) (Config.ScreenWidth / Config.MultiplierFactor), (int) (Config.ScreenHeight / Config.MultiplierFactor));
			config.setForegroundFPS(Config.FPS);
			config.setTitle("Tetris Clone");
			config.setResizable(false);
			new Lwjgl3Application(new TetrisClone(), config);
		}
	}
}
