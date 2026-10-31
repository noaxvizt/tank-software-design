package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TankControllerTest {

    @Test
    public void shouldMoveTankUsingStrategy() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level);
        CommandQueue commandQueue = new CommandQueue();
        TankController controller = new TankController(
                tank, () -> Direction.UP, commandQueue, key -> false, 1
        );

        controller.handle();
        commandQueue.executeAll();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void shouldDoNothingWithoutDirection() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level);
        CommandQueue commandQueue = new CommandQueue();
        TankController controller = new TankController(
                tank, () -> null, commandQueue, key -> false, 1
        );

        controller.handle();

        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
    }

    @Test
    public void shouldShootByConfiguredKey() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        level.addObject(tank);
        CommandQueue commandQueue = new CommandQueue();
        TankController controller = new TankController(
                tank, () -> null, commandQueue, key -> true, 1
        );

        controller.handle();
        commandQueue.executeAll();

        assertEquals(2, level.getObjects().size());
    }

    @Test
    public void shouldRandomlyShootForComputerTank() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        level.addObject(tank);
        CommandQueue commandQueue = new CommandQueue();
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        TankController controller = new TankController(
                tank, () -> Direction.UP, commandQueue, random
        );

        controller.handle();
        commandQueue.executeAll();

        assertEquals(2, level.getObjects().size());
    }

    @Test
    public void shouldMoveComputerTankWhenItDoesNotShoot() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        CommandQueue commandQueue = new CommandQueue();
        Random random = new Random() {
            @Override
            public int nextInt(int bound) {
                return 1;
            }
        };
        TankController controller = new TankController(
                tank, () -> Direction.RIGHT, commandQueue, random
        );

        controller.handle();
        commandQueue.executeAll();

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
    }
}
