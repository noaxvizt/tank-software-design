package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.GameField;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

public class RandomTankGeneratorTest {

    @Test
    public void shouldGenerateTanksOnFreeTiles() {
        GameField field = new GameField(3, 3);
        GridPoint2 obstacle = new GridPoint2(1, 1);
        GridPoint2 player = new GridPoint2(0, 0);
        field.addObstacle(obstacle);
        field.addTank(player);
        RandomTankGenerator generator = new RandomTankGenerator(4, new Random(1));

        List<GridPoint2> coordinates = generator.generate(field, 3, 3);

        assertEquals(4, coordinates.size());
        assertEquals(4, new HashSet<>(coordinates).size());
        assertFalse(coordinates.contains(obstacle));
        assertFalse(coordinates.contains(player));
    }

    @Test
    public void shouldRejectTooManyTanks() {
        GameField field = new GameField(2, 2);
        RandomTankGenerator generator = new RandomTankGenerator(5, new Random(1));

        assertThrows(IllegalArgumentException.class, () -> generator.generate(field, 2, 2));
    }
}
