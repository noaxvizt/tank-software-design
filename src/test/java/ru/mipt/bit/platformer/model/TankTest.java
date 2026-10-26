package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankTest {

    @Test
    public void shouldMoveToNextTile() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);

        tank.tryMove(Direction.RIGHT);

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
        assertEquals(0f, tank.getMovementProgress(), 0f);
        assertFalse(tank.isIdle());

        tank.update(0.2f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(0.5f, tank.getMovementProgress(), 0.001f);

        tank.update(0.2f);
        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertTrue(tank.isIdle());
    }

    @Test
    public void shouldNotMoveToObstacle() {
        GameField field = new GameField(5, 5);
        field.addObstacle(new GridPoint2(1, 2));
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);

        tank.tryMove(Direction.UP);

        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
        assertEquals(90f, tank.getRotation(), 0f);
        assertTrue(tank.isIdle());
    }

    @Test
    public void shouldNotChangeDirectionWhileMoving() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);

        tank.tryMove(Direction.RIGHT);
        tank.tryMove(Direction.UP);

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
        assertEquals(0f, tank.getRotation(), 0f);
    }

    @Test
    public void shouldNotMoveOutsideField() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(0, 0), 0.4f, field);

        tank.tryMove(Direction.LEFT);

        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
        assertTrue(tank.isIdle());
    }

    @Test
    public void shouldNotMoveToAnotherTank() {
        GameField field = new GameField(5, 5);
        Tank firstTank = new Tank(new GridPoint2(1, 1), 0.4f, field);
        Tank secondTank = new Tank(new GridPoint2(3, 1), 0.4f, field);

        firstTank.tryMove(Direction.RIGHT);
        secondTank.tryMove(Direction.LEFT);

        assertEquals(new GridPoint2(2, 1), firstTank.getDestinationCoordinates());
        assertEquals(new GridPoint2(3, 1), secondTank.getDestinationCoordinates());
    }
}
