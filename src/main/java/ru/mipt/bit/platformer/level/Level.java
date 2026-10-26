package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Level {

    private final GridPoint2 tankCoordinates;
    private final List<GridPoint2> treeCoordinates;

    public Level(GridPoint2 tankCoordinates, Collection<GridPoint2> treeCoordinates) {
        this.tankCoordinates = new GridPoint2(tankCoordinates);
        this.treeCoordinates = copyCoordinates(treeCoordinates);
    }

    public GridPoint2 getTankCoordinates() {
        return new GridPoint2(tankCoordinates);
    }

    public List<GridPoint2> getTreeCoordinates() {
        return copyCoordinates(treeCoordinates);
    }

    private List<GridPoint2> copyCoordinates(Collection<GridPoint2> coordinates) {
        List<GridPoint2> result = new ArrayList<>();
        for (GridPoint2 coordinate : coordinates) {
            result.add(new GridPoint2(coordinate));
        }
        return result;
    }
}
