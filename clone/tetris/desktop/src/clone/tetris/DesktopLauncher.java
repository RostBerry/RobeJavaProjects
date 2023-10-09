package clone.tetris;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import clone.tetris.game.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Config.Update(1280, 960, Config.GameFormat.Tetris99, 1, 60);

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode((int) (Config.ScreenWidth / Config.MultiplierFactor), (int) (Config.ScreenHeight / Config.MultiplierFactor));
		config.setForegroundFPS(Config.FPS);
		config.setTitle("Tetris Clone");
		config.setResizable(false);
		new Lwjgl3Application(new TetrisClone(), config);
	}
}
