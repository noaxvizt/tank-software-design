package ru.mipt.bit.platformer.strategy;

import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class RandomMovementStrategyTest {

    @Test
    public void shouldReturnRandomDirection() {
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 2;
            }
        };
        RandomMovementStrategy strategy = new RandomMovementStrategy(random);

        assertEquals(Direction.DOWN, strategy.getDirection());
    }
}
