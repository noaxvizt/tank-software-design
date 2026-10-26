package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Updatable;

public class Tank implements Updatable {

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private final float movementDuration;
    private final MovementArea movementArea;

    private float movementProgress = 1f;
    private float rotation = 0f;

    public Tank(GridPoint2 initialCoordinates, float movementDuration, MovementArea movementArea) {
        coordinates = new GridPoint2(initialCoordinates);
        destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementDuration = movementDuration;
        this.movementArea = movementArea;
        movementArea.addTank(initialCoordinates);
    }

    public void tryMove(Direction direction) {
        if (!isIdle()) {
            return;
        }

        GridPoint2 newDestination = direction.nextTileFrom(coordinates);
        if (movementArea.tryStartMovement(coordinates, newDestination)) {
            destinationCoordinates.set(newDestination);
            movementProgress = 0f;
        }

        rotation = direction.getRotation();
    }

    @Override
    public void update(float deltaTime) {
        movementProgress = Math.min(movementProgress + deltaTime / movementDuration, 1f);
        if (isIdle() && !coordinates.equals(destinationCoordinates)) {
            GridPoint2 previousCoordinates = new GridPoint2(coordinates);
            coordinates.set(destinationCoordinates);
            movementArea.finishMovement(previousCoordinates);
        }
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isIdle() {
        return movementProgress == 1f;
    }
}
