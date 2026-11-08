package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.model.MovementArea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomTankGenerator {

    private final int tankCount;
    private final Random random;

    public RandomTankGenerator(int tankCount) {
        this(tankCount, new Random());
    }

    public RandomTankGenerator(int tankCount, Random random) {
        this.tankCount = tankCount;
        this.random = random;
    }

    public List<GridPoint2> generate(MovementArea movementArea, int width, int height) {
        List<GridPoint2> freeCells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GridPoint2 coordinates = new GridPoint2(x, y);
                if (movementArea.canMoveTo(coordinates)) {
                    freeCells.add(coordinates);
                }
            }
        }

        if (tankCount < 0 || tankCount > freeCells.size()) {
            throw new IllegalArgumentException("Not enough free cells for tanks");
        }

        Collections.shuffle(freeCells, random);
        return new ArrayList<>(freeCells.subList(0, tankCount));
    }
}
