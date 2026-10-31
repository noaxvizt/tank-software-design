package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class GameFieldTest {

    @Test
    public void shouldAllowMovementInsideField() {
        GameField field = new GameField(3, 4);

        assertTrue(field.canMoveTo(new GridPoint2(0, 0)));
        assertTrue(field.canMoveTo(new GridPoint2(2, 3)));
    }

    @Test
    public void shouldNotAllowMovementOutsideField() {
        GameField field = new GameField(3, 4);

        assertFalse(field.canMoveTo(new GridPoint2(-1, 0)));
        assertFalse(field.canMoveTo(new GridPoint2(0, -1)));
        assertFalse(field.canMoveTo(new GridPoint2(3, 0)));
        assertFalse(field.canMoveTo(new GridPoint2(0, 4)));
    }

    @Test
    public void shouldNotAllowMovementToObstacle() {
        GameField field = new GameField(3, 4);
        field.addObstacle(new GridPoint2(1, 2));

        assertFalse(field.canMoveTo(new GridPoint2(1, 2)));
    }

    @Test
    public void shouldReserveBothTilesWhileTankIsMoving() {
        GameField field = new GameField(3, 4);
        GridPoint2 from = new GridPoint2(1, 1);
        GridPoint2 to = new GridPoint2(2, 1);
        field.addTank(from);

        assertTrue(field.tryStartMovement(from, to));
        assertFalse(field.canMoveTo(from));
        assertFalse(field.canMoveTo(to));

        field.finishMovement(from);

        assertTrue(field.canMoveTo(from));
        assertFalse(field.canMoveTo(to));
    }

    @Test
    public void shouldNotStartMovementOutsideField() {
        GameField field = new GameField(3, 4);
        GridPoint2 from = new GridPoint2(0, 0);
        field.addTank(from);

        assertFalse(field.tryStartMovement(from, new GridPoint2(-1, 0)));
    }

    @Test
    public void shouldNotAddTwoTanksToOneTile() {
        GameField field = new GameField(3, 4);
        GridPoint2 coordinates = new GridPoint2(1, 1);
        field.addTank(coordinates);

        assertThrows(IllegalArgumentException.class, () -> field.addTank(coordinates));
    }
}
