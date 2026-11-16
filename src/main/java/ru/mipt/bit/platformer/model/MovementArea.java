package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/** Domain output port for movement and occupied tiles. */
public interface MovementArea {

    boolean canMoveTo(GridPoint2 coordinates);

    void addTank(GridPoint2 coordinates);

    boolean tryStartMovement(GridPoint2 fromCoordinates, GridPoint2 toCoordinates);

    void finishMovement(GridPoint2 fromCoordinates);

    void removeTank(GridPoint2 coordinates);
}
