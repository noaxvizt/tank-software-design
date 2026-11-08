package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void shouldStoreVectorAndRotation() {
        assertEquals(new GridPoint2(0, 1), Direction.UP.getVector());
        assertEquals(new GridPoint2(-1, 0), Direction.LEFT.getVector());
        assertEquals(new GridPoint2(0, -1), Direction.DOWN.getVector());
        assertEquals(new GridPoint2(1, 0), Direction.RIGHT.getVector());

        assertEquals(90f, Direction.UP.getRotation(), 0f);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0f);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0f);
    }

    @Test
    public void shouldCalculateNextTile() {
        GridPoint2 coordinates = new GridPoint2(2, 3);

        GridPoint2 result = Direction.RIGHT.nextTileFrom(coordinates);

        assertEquals(new GridPoint2(3, 3), result);
        assertEquals(new GridPoint2(2, 3), coordinates);
    }
}
