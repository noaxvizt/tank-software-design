package ru.mipt.bit.platformer.strategy;

import ru.mipt.bit.platformer.model.Direction;

import java.util.Random;

public class RandomMovementStrategy implements TankMovementStrategy {

    private final Random random;

    public RandomMovementStrategy() {
        this(new Random());
    }

    public RandomMovementStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Direction getDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}
