package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface MovementArea {

    boolean canMoveTo(GridPoint2 coordinates);
}
