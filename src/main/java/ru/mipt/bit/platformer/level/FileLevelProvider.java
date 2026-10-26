package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileLevelProvider implements LevelProvider {

    private final Path file;

    public FileLevelProvider(Path file) {
        this.file = file;
    }

    @Override
    public Level createLevel(int width, int height) {
        List<String> lines = readLines();
        if (lines.size() != height) {
            throw new IllegalArgumentException("Wrong level height");
        }

        GridPoint2 tankCoordinates = null;
        List<GridPoint2> treeCoordinates = new ArrayList<>();

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            if (line.length() != width) {
                throw new IllegalArgumentException("Wrong level width");
            }

            int y = height - row - 1;
            for (int x = 0; x < line.length(); x++) {
                char cell = line.charAt(x);
                if (cell == 'T') {
                    treeCoordinates.add(new GridPoint2(x, y));
                } else if (cell == 'X') {
                    if (tankCoordinates != null) {
                        throw new IllegalArgumentException("More than one tank position");
                    }
                    tankCoordinates = new GridPoint2(x, y);
                } else if (cell != '_') {
                    throw new IllegalArgumentException("Unknown level symbol");
                }
            }
        }

        if (tankCoordinates == null) {
            throw new IllegalArgumentException("Tank position is missing");
        }

        return new Level(tankCoordinates, treeCoordinates);
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
