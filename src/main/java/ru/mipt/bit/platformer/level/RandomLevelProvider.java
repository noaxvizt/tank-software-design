package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLevelProvider implements LevelProvider {

    private final int treeCount;
    private final Random random;

    public RandomLevelProvider(int treeCount) {
        this(treeCount, new Random());
    }

    public RandomLevelProvider(int treeCount, Random random) {
        this.treeCount = treeCount;
        this.random = random;
    }

    @Override
    public Level createLevel(int width, int height) {
        int cellCount = width * height;
        if (treeCount < 0 || treeCount >= cellCount) {
            throw new IllegalArgumentException("Wrong tree count");
        }

        List<GridPoint2> cells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new GridPoint2(x, y));
            }
        }
        Collections.shuffle(cells, random);

        GridPoint2 tankCoordinates = cells.get(0);
        List<GridPoint2> treeCoordinates = cells.subList(1, treeCount + 1);
        return new Level(tankCoordinates, treeCoordinates);
    }
}
