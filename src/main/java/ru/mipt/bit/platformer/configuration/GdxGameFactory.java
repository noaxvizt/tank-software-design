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
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GdxGameFactory implements GameFactory {

    private final String levelFile;
    private final String tankTexture;
    private final String treeTexture;
    private final GridPoint2 tankCoordinates;
    private final GridPoint2 treeCoordinates;
    private final float movementDuration;
    private final Map<Integer, Direction> controls;

    public GdxGameFactory(String levelFile, String tankTexture, String treeTexture,
                          GridPoint2 tankCoordinates, GridPoint2 treeCoordinates,
                          float movementDuration, Map<Integer, Direction> controls) {
        this.levelFile = levelFile;
        this.tankTexture = tankTexture;
        this.treeTexture = treeTexture;
        this.tankCoordinates = new GridPoint2(tankCoordinates);
        this.treeCoordinates = new GridPoint2(treeCoordinates);
        this.movementDuration = movementDuration;
        this.controls = new LinkedHashMap<>(controls);
    }

    @Override
    public Game create() {
        Batch batch = new SpriteBatch();
        GameFieldRenderer fieldRenderer = new GameFieldRenderer(levelFile, batch, Interpolation.smooth);

        GameField field = new GameField(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        Tank tank = new Tank(tankCoordinates, movementDuration);
        Tree tree = new Tree(treeCoordinates);
        field.addObstacle(tree.getCoordinates());

        ObjectRenderer tankRenderer = new TankRenderer(tankTexture, tank, fieldRenderer);
        ObjectRenderer treeRenderer = new TreeRenderer(treeTexture, tree, fieldRenderer);
        GameRenderer gameRenderer = new GdxGameRenderer(
                batch,
                fieldRenderer,
                Arrays.asList(tankRenderer, treeRenderer),
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
