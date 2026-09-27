package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.GameFieldRenderer;
import ru.mipt.bit.platformer.graphics.TankRenderer;
import ru.mipt.bit.platformer.graphics.TreeRenderer;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.TankMovementInputHandler;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class TankGame implements ApplicationListener {

    private static final float MOVEMENT_DURATION = 0.4f;
    private static final String LEVEL_FILE = "level.tmx";
    private static final String TANK_TEXTURE = "images/tank_blue.png";
    private static final String TREE_TEXTURE = "images/greenTree.png";

    private Batch batch;
    private GameFieldRenderer fieldRenderer;
    private TankRenderer tankRenderer;
    private TreeRenderer treeRenderer;
    private Tank tank;
    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fieldRenderer = new GameFieldRenderer(LEVEL_FILE, batch, Interpolation.smooth);

        GameField field = new GameField(fieldRenderer.getWidth(), fieldRenderer.getHeight());
        tank = new Tank(new GridPoint2(1, 1), MOVEMENT_DURATION);
        Tree tree = new Tree(new GridPoint2(1, 3));
        field.addObstacle(tree.getCoordinates());

        tankRenderer = new TankRenderer(TANK_TEXTURE, tank, fieldRenderer);
        treeRenderer = new TreeRenderer(TREE_TEXTURE, tree, fieldRenderer);

        inputHandler = new InputHandler();
        inputHandler.addButtonHandler(
                new TankMovementInputHandler(Gdx.input::isKeyPressed, tank, field)
        );
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        inputHandler.handle();
        tank.update(Gdx.graphics.getDeltaTime());

        fieldRenderer.render();
        batch.begin();
        tankRenderer.render(batch);
        treeRenderer.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        treeRenderer.dispose();
        tankRenderer.dispose();
        fieldRenderer.dispose();
        batch.dispose();
    }
}
