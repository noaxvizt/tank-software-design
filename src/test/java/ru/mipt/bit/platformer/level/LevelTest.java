package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    @Test
    public void shouldCopyCoordinates() {
        GridPoint2 tankCoordinates = new GridPoint2(1, 2);
        List<GridPoint2> treeCoordinates = new ArrayList<>(
                Collections.singletonList(new GridPoint2(3, 4))
        );
        Level level = new Level(tankCoordinates, treeCoordinates);

        tankCoordinates.set(5, 5);
        treeCoordinates.get(0).set(6, 6);

        assertEquals(new GridPoint2(1, 2), level.getTankCoordinates());
        assertEquals(
                Collections.singletonList(new GridPoint2(3, 4)),
                level.getTreeCoordinates()
        );
    }
}
