package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TreeTest {

    @Test
    public void shouldStoreCoordinates() {
        GridPoint2 coordinates = new GridPoint2(1, 3);
        Tree tree = new Tree(coordinates);

        coordinates.set(4, 4);

        assertEquals(new GridPoint2(1, 3), tree.getCoordinates());
    }
}
