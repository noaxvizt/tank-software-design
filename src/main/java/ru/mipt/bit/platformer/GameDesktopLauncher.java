package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.configuration.GdxGameFactory;
import ru.mipt.bit.platformer.level.FileLevelProvider;
import ru.mipt.bit.platformer.level.LevelProvider;
import ru.mipt.bit.platformer.level.RandomLevelProvider;
import ru.mipt.bit.platformer.model.Direction;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameDesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);

        GdxGameFactory gameFactory = new GdxGameFactory(
                "level.tmx",
                "images/tank_blue.png",
                "images/greenTree.png",
                createLevelProvider(args),
                0.4f,
                createControls()
        );

        new Lwjgl3Application(new TankGame(gameFactory), config);
    }

    private static LevelProvider createLevelProvider(String[] args) {
        if (args.length > 0 && args[0].equals("random")) {
            return new RandomLevelProvider(12);
        }
        return new FileLevelProvider(Paths.get("src/main/resources/game-level.txt"));
    }

    private static Map<Integer, Direction> createControls() {
        Map<Integer, Direction> controls = new LinkedHashMap<>();
        controls.put(Input.Keys.UP, Direction.UP);
        controls.put(Input.Keys.W, Direction.UP);
        controls.put(Input.Keys.LEFT, Direction.LEFT);
        controls.put(Input.Keys.A, Direction.LEFT);
        controls.put(Input.Keys.DOWN, Direction.DOWN);
        controls.put(Input.Keys.S, Direction.DOWN);
        controls.put(Input.Keys.RIGHT, Direction.RIGHT);
        controls.put(Input.Keys.D, Direction.RIGHT);
        return controls;
    }
}
