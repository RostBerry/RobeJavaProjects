package clone.tetris;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import clone.tetris.config.Config;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Config.Update(555, 960);

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode((int)Config.ScreenWidth, (int)Config.ScreenHeight);
		config.setForegroundFPS(90);
		config.setTitle("Tetris Clone");
		config.setResizable(false);
		new Lwjgl3Application(new TetrisClone(), config);
	}
}
