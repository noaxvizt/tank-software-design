package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashSet;
import java.util.Set;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GameField implements Disposable {

    private final TiledMap level;
    private final TiledMapTileLayer groundLayer;
    private final MapRenderer renderer;
    private final TileMovement movement;
    private final Set<GridPoint2> occupiedTiles = new HashSet<>();

    public GameField(String levelFile, Batch batch, Interpolation interpolation) {
        level = new TmxMapLoader().load(levelFile);
        renderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        movement = new TileMovement(groundLayer, interpolation);
    }

    public void addObstacle(GridPoint2 coordinates) {
        occupiedTiles.add(new GridPoint2(coordinates));
    }

    public boolean canMoveTo(GridPoint2 coordinates) {
        return isInside(coordinates) && !occupiedTiles.contains(coordinates);
    }

    public Rectangle moveAtTileCenter(Rectangle rectangle, GridPoint2 coordinates) {
        return moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public Rectangle moveBetweenTileCenters(Rectangle rectangle, GridPoint2 fromCoordinates, GridPoint2 toCoordinates, float progress) {
        return movement.moveRectangleBetweenTileCenters(rectangle, fromCoordinates, toCoordinates, progress);
    }

    public void render() {
        renderer.render();
    }

    private boolean isInside(GridPoint2 coordinates) {
        return coordinates.x >= 0
                && coordinates.x < groundLayer.getWidth()
                && coordinates.y >= 0
                && coordinates.y < groundLayer.getHeight();
    }

    @Override
    public void dispose() {
        level.dispose();
    }
}
