package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

public interface TilePositioner {

    void moveAtTileCenter(Rectangle rectangle, GridPoint2 coordinates);

    void moveBetweenTileCenters(Rectangle rectangle, GridPoint2 fromCoordinates,
                                GridPoint2 toCoordinates, float progress);
}
