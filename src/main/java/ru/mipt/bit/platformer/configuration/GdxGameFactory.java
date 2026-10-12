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
import ru.mipt.bit.platformer.graphics.GameFieldRenderer;
import ru.mipt.bit.platformer.graphics.GdxGameRenderer;
import ru.mipt.bit.platformer.graphics.ObjectRenderer;
import ru.mipt.bit.platformer.graphics.TankRenderer;
import ru.mipt.bit.platformer.graphics.TreeRenderer;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.TankMovementInputHandler;
import ru.mipt.bit.platformer.input.keyboard.GdxKeyboard;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.level.LevelProvider;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GdxGameFactory implements GameFactory {

    private final String levelFile;
    private final String tankTexture;
    private final String treeTexture;
    private final LevelProvider levelProvider;
    private final float movementDuration;
    private final Map<Integer, Direction> controls;

    public GdxGameFactory(String levelFile, String tankTexture, String treeTexture,
                          LevelProvider levelProvider,
                          float movementDuration, Map<Integer, Direction> controls) {
        this.levelFile = levelFile;
        this.tankTexture = tankTexture;
        this.treeTexture = treeTexture;
        this.levelProvider = levelProvider;
        this.movementDuration = movementDuration;
        this.controls = new LinkedHashMap<>(controls);
    }

    @Override
    public Game create() {
        Batch batch = new SpriteBatch();
        GameFieldRenderer fieldRenderer = new GameFieldRenderer(levelFile, batch, Interpolation.smooth);

        GameField field = new GameField(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        Level level = levelProvider.createLevel(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        Tank tank = new Tank(level.getTankCoordinates(), movementDuration);

        List<ObjectRenderer> objectRenderers = new ArrayList<>();
        ObjectRenderer tankRenderer = new TankRenderer(tankTexture, tank, fieldRenderer);
        objectRenderers.add(tankRenderer);

        for (GridPoint2 coordinates : level.getTreeCoordinates()) {
            Tree tree = new Tree(coordinates);
            field.addObstacle(tree.getCoordinates());
            objectRenderers.add(new TreeRenderer(treeTexture, tree, fieldRenderer));
        }

        GameRenderer gameRenderer = new GdxGameRenderer(
                batch,
                fieldRenderer,
                objectRenderers,
                0f, 0f, 0.2f, 1f
        );

        GameInput movementHandler = new TankMovementInputHandler(
                new GdxKeyboard(Gdx.input), tank, field, controls
        );
        GameInput inputHandler = new InputHandler(Collections.singletonList(movementHandler));

        return new GameController(
                Collections.singletonList(tank),
                inputHandler,
                gameRenderer
        );
    }
}
