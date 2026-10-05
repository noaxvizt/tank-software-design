package ru.mipt.bit.platformer;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.configuration.GdxGameFactory;
import ru.mipt.bit.platformer.model.Direction;

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
                new GridPoint2(1, 1),
                new GridPoint2(1, 3),
                0.4f,
                createControls()
        );

        new Lwjgl3Application(new TankGame(gameFactory), config);
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
