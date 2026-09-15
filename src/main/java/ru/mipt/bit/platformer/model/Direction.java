package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;

public enum Direction {

    UP(0, 1, 90f, Input.Keys.UP, Input.Keys.W),
    LEFT(-1, 0, -180f, Input.Keys.LEFT, Input.Keys.A),
    DOWN(0, -1, -90f, Input.Keys.DOWN, Input.Keys.S),
    RIGHT(1, 0, 0f, Input.Keys.RIGHT, Input.Keys.D);

    private final int deltaX;
    private final int deltaY;
    private final float rotation;
    private final int[] keys;

    Direction(int deltaX, int deltaY, float rotation, int... keys) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.rotation = rotation;
        this.keys = keys;
    }

    public boolean isPressed(Input input) {
        for (int key : keys) {
            if (input.isKeyPressed(key)) {
                return true;
            }
        }

        return false;
    }

    public GridPoint2 nextTileFrom(GridPoint2 coordinates) {
        return new GridPoint2(coordinates).add(deltaX, deltaY);
    }

    public float getRotation() {
        return rotation;
    }
}
