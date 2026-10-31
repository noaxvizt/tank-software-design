package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.HashSet;
import java.util.Set;

public class GameField implements MovementArea {

    private final int width;
    private final int height;
    private final Set<GridPoint2> occupiedTiles = new HashSet<>();
    private final Set<GridPoint2> tankTiles = new HashSet<>();

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addObstacle(GridPoint2 coordinates) {
        occupiedTiles.add(new GridPoint2(coordinates));
    }

    public void removeObstacle(GridPoint2 coordinates) {
        occupiedTiles.remove(coordinates);
    }

    @Override
    public boolean canMoveTo(GridPoint2 coordinates) {
        return isInside(coordinates)
                && !occupiedTiles.contains(coordinates)
                && !tankTiles.contains(coordinates);
    }

    @Override
    public void addTank(GridPoint2 coordinates) {
        if (!canMoveTo(coordinates)) {
            throw new IllegalArgumentException("Tank position is occupied");
        }
        tankTiles.add(new GridPoint2(coordinates));
    }

    @Override
    public boolean tryStartMovement(GridPoint2 fromCoordinates, GridPoint2 toCoordinates) {
        if (!tankTiles.contains(fromCoordinates) || !canMoveTo(toCoordinates)) {
            return false;
        }
        tankTiles.add(new GridPoint2(toCoordinates));
        return true;
    }

    @Override
    public void finishMovement(GridPoint2 fromCoordinates) {
        tankTiles.remove(fromCoordinates);
    }

    @Override
    public void removeTank(GridPoint2 coordinates) {
        tankTiles.remove(coordinates);
    }

    public boolean isInside(GridPoint2 coordinates) {
        return coordinates.x >= 0
                && coordinates.x < width
                && coordinates.y >= 0
                && coordinates.y < height;
    }
}
