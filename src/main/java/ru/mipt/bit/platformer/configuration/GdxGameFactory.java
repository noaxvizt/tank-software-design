package ru.mipt.bit.platformer.configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.Game;
import ru.mipt.bit.platformer.GameController;
import ru.mipt.bit.platformer.GameFactory;
import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.graphics.GameFieldRenderer;
import ru.mipt.bit.platformer.graphics.GdxGameRenderer;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.Keyboard;
import ru.mipt.bit.platformer.input.TankController;
import ru.mipt.bit.platformer.input.keyboard.GdxKeyboard;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.level.LevelProvider;
import ru.mipt.bit.platformer.level.RandomTankGenerator;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.strategy.KeyboardMovementStrategy;
import ru.mipt.bit.platformer.strategy.RandomMovementStrategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GdxGameFactory implements GameFactory {

    private final String levelFile;
    private final String tankTexture;
    private final String treeTexture;
    private final LevelProvider levelProvider;
    private final int enemyTankCount;
    private final float movementDuration;
    private final Map<Integer, Direction> controls;

    public GdxGameFactory(String levelFile, String tankTexture, String treeTexture,
                          LevelProvider levelProvider,
                          int enemyTankCount,
                          float movementDuration, Map<Integer, Direction> controls) {
        this.levelFile = levelFile;
        this.tankTexture = tankTexture;
        this.treeTexture = treeTexture;
        this.levelProvider = levelProvider;
        this.enemyTankCount = enemyTankCount;
        this.movementDuration = movementDuration;
        this.controls = new LinkedHashMap<>(controls);
    }

    @Override
    public Game create() {
        Batch batch = new SpriteBatch();
        GameFieldRenderer fieldRenderer = new GameFieldRenderer(levelFile, batch, Interpolation.smooth);

        GameLevel gameLevel = new GameLevel(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        Level level = levelProvider.createLevel(fieldRenderer.getWidth(), fieldRenderer.getHeight());

        GdxGameRenderer gameRenderer = new GdxGameRenderer(
                batch,
                fieldRenderer,
                tankTexture,
                treeTexture,
                0f, 0f, 0.2f, 1f
        );
        gameLevel.addListener(gameRenderer);

        for (GridPoint2 coordinates : level.getTreeCoordinates()) {
            Tree tree = new Tree(coordinates);
            gameLevel.addObject(tree);
        }

        List<Tank> tanks = new ArrayList<>();
        Tank playerTank = new Tank(
                level.getTankCoordinates(), movementDuration, gameLevel, gameLevel
        );
        tanks.add(playerTank);
        gameLevel.addObject(playerTank);

        RandomTankGenerator tankGenerator = new RandomTankGenerator(enemyTankCount);
        List<GridPoint2> enemyCoordinates = tankGenerator.generate(
                gameLevel, fieldRenderer.getWidth(), fieldRenderer.getHeight()
        );
        for (GridPoint2 coordinates : enemyCoordinates) {
            Tank enemyTank = new Tank(coordinates, movementDuration, gameLevel, gameLevel);
            tanks.add(enemyTank);
            gameLevel.addObject(enemyTank);
        }

        Keyboard keyboard = new GdxKeyboard(Gdx.input);
        CommandQueue commandQueue = new CommandQueue();
        List<GameInput> tankControllers = new ArrayList<>();
        tankControllers.add(
                new TankController(
                        playerTank,
                        new KeyboardMovementStrategy(keyboard, controls),
                        commandQueue,
                        keyboard,
                        Input.Keys.SPACE
                )
        );
        for (int index = 1; index < tanks.size(); index++) {
            tankControllers.add(
                    new TankController(
                            tanks.get(index),
                            new RandomMovementStrategy(),
                            commandQueue,
                            new Random()
                    )
            );
        }
        GameInput inputHandler = new InputHandler(tankControllers);

        return new GameController(
                gameLevel,
                inputHandler,
                commandQueue,
                gameRenderer
        );
    }
}
