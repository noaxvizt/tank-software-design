package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Tank implements Disposable {

    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private final float movementSpeed;

    private float movementProgress = 1f;
    private float rotation = 0f;

    public Tank(String textureFile, GridPoint2 initialCoordinates, float movementSpeed, GameField field) {
        texture = new Texture(textureFile);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        coordinates = new GridPoint2(initialCoordinates);
        destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementSpeed = movementSpeed;

        field.moveAtTileCenter(rectangle, coordinates);
    }

    public void tryMove(Direction direction, GameField field) {
        if (!isIdle()) {
            return;
        }

        GridPoint2 newDestination = direction.nextTileFrom(coordinates);
        if (field.canMoveTo(newDestination)) {
            destinationCoordinates.set(newDestination);
            movementProgress = 0f;
        }

        rotation = direction.getRotation();
    }

    public void update(GameField field, float deltaTime) {
        field.moveBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);

        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isIdle()) {
            coordinates.set(destinationCoordinates);
        }
    }

    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    private boolean isIdle() {
        return isEqual(movementProgress, 1f);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
