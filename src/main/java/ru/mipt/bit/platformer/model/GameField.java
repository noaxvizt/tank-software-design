package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

public class GameField implements MovementArea {

    private final int width;
    private final int height;
    private final Set<GridPoint2> occupiedTiles = new HashSet<>();

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addObstacle(GridPoint2 coordinates) {
        occupiedTiles.add(new GridPoint2(coordinates));
    }

    @Override
    public boolean canMoveTo(GridPoint2 coordinates) {
        return isInside(coordinates) && !occupiedTiles.contains(coordinates);
    }

    private boolean isInside(GridPoint2 coordinates) {
        return coordinates.x >= 0
                && coordinates.x < width
                && coordinates.y >= 0
                && coordinates.y < height;
    }
}
