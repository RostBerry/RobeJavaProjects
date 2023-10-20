package clone.tetris.game.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.List;

public class UIConfig {
    public static Color ButtonBackgroundColor = Color.LIGHT_GRAY;
    public static Color ButtonStrokeColor = Color.BLUE;
    public static Color ButtonSelectedStrokeColor = Color.RED;
    public static Color ButtonSelectionColor = Color.BLUE;

    public static BitmapFont GameFont;
    public static BitmapFont MenuMainFont;
    public static BitmapFont MenuHeaderFont;

    public static float SwitcherButtonSize;
    public static float SwitcherWidth;

    public static List<String> onOff;

    public static void update() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Config.GameFontSize;
        parameter.color = Color.WHITE;
        parameter.characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:%.";
        GameFont = generator.generateFont(parameter);
        parameter.size = Config.MenuMainFontSize;
        MenuMainFont = generator.generateFont(parameter);
        parameter.size = Config.MenuHeaderFontSize;
        MenuHeaderFont = generator.generateFont(parameter);
        generator.dispose();

        SwitcherButtonSize = Config.MenuHeaderFontSize * 2f;
        SwitcherWidth = Config.ScreenWidth * 0.3f;

        onOff = new ArrayList<>();
        onOff.add("ON");
        onOff.add("OFF");

        GameStartConfig.update();
        MenuConfig.update();
        OptionsConfig.update();
    }

    public static void dispose() {
        GameFont.dispose();
        MenuMainFont.dispose();
        MenuHeaderFont.dispose();
    }
}
