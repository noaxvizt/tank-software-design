package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.state.HeavyTankState;
import ru.mipt.bit.platformer.model.state.LightTankState;
import ru.mipt.bit.platformer.model.state.MediumTankState;

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

    @Test
    public void shouldCreateBulletInCurrentDirection() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        level.addObject(tank);

        tank.shoot();

        Bullet bullet = (Bullet) level.getObjects().get(1);
        assertEquals(new GridPoint2(2, 1), bullet.getCoordinates());
    }

    @Test
    public void shouldLoseHealth() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);

        assertEquals(100, tank.getHealth());

        tank.takeDamage(2);

        assertEquals(98, tank.getHealth());
        assertEquals(100, tank.getMaxHealth());
        assertTrue(tank.isAlive());

        tank.takeDamage(98);

        assertFalse(tank.isAlive());
    }

    @Test
    public void shouldChangeStateDependingOnHealth() {
        GameField field = new GameField(10, 10);
        Tank lightTank = new Tank(new GridPoint2(1, 1), 0.6f, field);
        Tank mediumTank = new Tank(new GridPoint2(3, 1), 0.6f, field);
        Tank mediumBoundaryTank = new Tank(new GridPoint2(5, 1), 0.6f, field);
        Tank heavyTank = new Tank(new GridPoint2(7, 1), 0.6f, field);

        lightTank.takeDamage(29);
        mediumTank.takeDamage(30);
        mediumBoundaryTank.takeDamage(85);
        heavyTank.takeDamage(86);

        assertTrue(lightTank.getState() instanceof LightTankState);
        assertTrue(mediumTank.getState() instanceof MediumTankState);
        assertTrue(mediumBoundaryTank.getState() instanceof MediumTankState);
        assertTrue(heavyTank.getState() instanceof HeavyTankState);
    }

    @Test
    public void shouldMoveSlowerAfterDamage() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.6f, field);
        tank.takeDamage(30);

        tank.tryMove(Direction.RIGHT);
        tank.update(0.3f);

        assertEquals(0.25f, tank.getMovementProgress(), 0.001f);
    }

    @Test
    public void shouldMoveThreeTimesSlowerInHeavyState() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.6f, field);
        tank.takeDamage(86);

        tank.tryMove(Direction.RIGHT);
        tank.update(0.3f);

        assertEquals(1f / 6f, tank.getMovementProgress(), 0.001f);
    }

    @Test
    public void shouldNotShootInHeavyState() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        level.addObject(tank);
        tank.takeDamage(86);

        tank.shoot();

        assertEquals(1, level.getObjects().size());
    }
}
