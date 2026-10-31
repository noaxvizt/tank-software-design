package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Bullet implements LevelObject {

    private final GridPoint2 coordinates;
    private final GridPoint2 destinationCoordinates;
    private final Direction direction;
    private final float movementDuration;
    private final int damage;
    private final BulletArea bulletArea;

    private float movementProgress = 1f;

    public Bullet(GridPoint2 coordinates, Direction direction, float movementDuration,
                  int damage, BulletArea bulletArea) {
        this.coordinates = new GridPoint2(coordinates);
        destinationCoordinates = new GridPoint2(coordinates);
        this.direction = direction;
        this.movementDuration = movementDuration;
        this.damage = damage;
        this.bulletArea = bulletArea;
    }

    @Override
    public void update(float deltaTime) {
        if (movementProgress < 1f) {
            movementProgress = Math.min(movementProgress + deltaTime / movementDuration, 1f);
            if (movementProgress == 1f) {
                coordinates.set(destinationCoordinates);
            }
        }

        if (movementProgress == 1f) {
            bulletArea.tryMoveBullet(this, direction.nextTileFrom(coordinates));
        }
    }

    public void startMoving(GridPoint2 destinationCoordinates) {
        this.destinationCoordinates.set(destinationCoordinates);
        movementProgress = 0f;
    }

    public boolean occupies(GridPoint2 tileCoordinates) {
        return coordinates.equals(tileCoordinates)
                || destinationCoordinates.equals(tileCoordinates);
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

    public int getDamage() {
        return damage;
    }
}
