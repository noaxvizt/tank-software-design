package ru.mipt.bit.platformer.configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.Game;
import ru.mipt.bit.platformer.GameController;
import ru.mipt.bit.platformer.GameFactory;
import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.GameRenderer;
import ru.mipt.bit.platformer.Updatable;
import ru.mipt.bit.platformer.graphics.GameFieldRenderer;
import ru.mipt.bit.platformer.graphics.GdxGameRenderer;
import ru.mipt.bit.platformer.graphics.ObjectRenderer;
import ru.mipt.bit.platformer.graphics.TankRenderer;
import ru.mipt.bit.platformer.graphics.TreeRenderer;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.Keyboard;
import ru.mipt.bit.platformer.input.TankController;
import ru.mipt.bit.platformer.input.keyboard.GdxKeyboard;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelProvider;
import ru.mipt.bit.platformer.level.RandomTankGenerator;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;
import ru.mipt.bit.platformer.strategy.KeyboardMovementStrategy;
import ru.mipt.bit.platformer.strategy.RandomMovementStrategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

        GameField field = new GameField(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        Level level = levelProvider.createLevel(fieldRenderer.getWidth(), fieldRenderer.getHeight());

        List<Tree> trees = new ArrayList<>();
        for (GridPoint2 coordinates : level.getTreeCoordinates()) {
            Tree tree = new Tree(coordinates);
            trees.add(tree);
            field.addObstacle(tree.getCoordinates());
        }

        List<Tank> tanks = new ArrayList<>();
        Tank playerTank = new Tank(level.getTankCoordinates(), movementDuration, field);
        tanks.add(playerTank);

        RandomTankGenerator tankGenerator = new RandomTankGenerator(enemyTankCount);
        List<GridPoint2> enemyCoordinates = tankGenerator.generate(
                field, fieldRenderer.getWidth(), fieldRenderer.getHeight()
        );
        for (GridPoint2 coordinates : enemyCoordinates) {
            tanks.add(new Tank(coordinates, movementDuration, field));
        }

        List<ObjectRenderer> objectRenderers = new ArrayList<>();
        for (Tank tank : tanks) {
            objectRenderers.add(new TankRenderer(tankTexture, tank, fieldRenderer));
        }
        for (Tree tree : trees) {
            objectRenderers.add(new TreeRenderer(treeTexture, tree, fieldRenderer));
        }

        GameRenderer gameRenderer = new GdxGameRenderer(
                batch,
                fieldRenderer,
                objectRenderers,
                0f, 0f, 0.2f, 1f
        );

        Keyboard keyboard = new GdxKeyboard(Gdx.input);
        List<GameInput> tankControllers = new ArrayList<>();
        tankControllers.add(
                new TankController(playerTank, new KeyboardMovementStrategy(keyboard, controls))
        );
        for (int index = 1; index < tanks.size(); index++) {
            tankControllers.add(new TankController(tanks.get(index), new RandomMovementStrategy()));
        }
        GameInput inputHandler = new InputHandler(tankControllers);

        List<Updatable> updatableObjects = new ArrayList<>();
        updatableObjects.addAll(tanks);

        return new GameController(
                updatableObjects,
                inputHandler,
                gameRenderer
        );
    }
}
