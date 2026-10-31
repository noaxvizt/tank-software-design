package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tank implements LevelObject {

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private final float movementDuration;
    private final MovementArea movementArea;
    private final BulletArea bulletArea;

    private float movementProgress = 1f;
    private float shootingDelay;
    private Direction direction = Direction.RIGHT;
    private int health = 3;

    public Tank(GridPoint2 initialCoordinates, float movementDuration, MovementArea movementArea) {
        this(initialCoordinates, movementDuration, movementArea, null);
    }

    public Tank(GridPoint2 initialCoordinates, float movementDuration, MovementArea movementArea,
                BulletArea bulletArea) {
        coordinates = new GridPoint2(initialCoordinates);
        destinationCoordinates = new GridPoint2(initialCoordinates);
        this.movementDuration = movementDuration;
        this.movementArea = movementArea;
        this.bulletArea = bulletArea;
        movementArea.addTank(initialCoordinates);
    }

    public void tryMove(Direction direction) {
        if (!isIdle() || !isAlive()) {
            return;
        }

        GridPoint2 newDestination = direction.nextTileFrom(coordinates);
        if (movementArea.tryStartMovement(coordinates, newDestination)) {
            destinationCoordinates.set(newDestination);
            movementProgress = 0f;
        }

        this.direction = direction;
    }

    public void shoot() {
        if (!isIdle() || !isAlive() || bulletArea == null || shootingDelay > 0f) {
            return;
        }

        Bullet bullet = new Bullet(
                direction.nextTileFrom(coordinates), direction, 0.1f, 1, bulletArea
        );
        bulletArea.addBullet(bullet);
        shootingDelay = 0.5f;
    }

    @Override
    public void update(float deltaTime) {
        shootingDelay = Math.max(shootingDelay - deltaTime, 0f);
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
        return direction.getRotation();
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean occupies(GridPoint2 tileCoordinates) {
        return coordinates.equals(tileCoordinates)
                || destinationCoordinates.equals(tileCoordinates);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public boolean isIdle() {
        return movementProgress == 1f;
    }
}
