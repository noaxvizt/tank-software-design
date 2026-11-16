package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

public class RandomLevelProviderTest {

    @Test
    public void shouldCreateRandomLevelWithoutIntersections() {
        RandomLevelProvider provider = new RandomLevelProvider(5, new Random(1));

        Level level = provider.createLevel(4, 3);

        List<GridPoint2> trees = level.getTreeCoordinates();
        Set<GridPoint2> uniqueTrees = new HashSet<>(trees);
        assertEquals(5, trees.size());
        assertEquals(5, uniqueTrees.size());
        assertFalse(uniqueTrees.contains(level.getTankCoordinates()));

        assertInside(level.getTankCoordinates(), 4, 3);
        for (GridPoint2 tree : trees) {
            assertInside(tree, 4, 3);
        }
    }

    @Test
    public void shouldRejectTooManyTrees() {
        RandomLevelProvider provider = new RandomLevelProvider(4, new Random(1));

        assertThrows(IllegalArgumentException.class, () -> provider.createLevel(2, 2));
    }

    private void assertInside(GridPoint2 coordinates, int width, int height) {
        assertTrue(coordinates.x >= 0 && coordinates.x < width);
        assertTrue(coordinates.y >= 0 && coordinates.y < height);
    }
}
