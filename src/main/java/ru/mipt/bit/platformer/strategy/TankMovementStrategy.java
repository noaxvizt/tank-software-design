package ru.mipt.bit.platformer.strategy;

import ru.mipt.bit.platformer.model.Direction;

/** Application policy for choosing movement direction. */
public interface TankMovementStrategy {

    Direction getDirection();
}
