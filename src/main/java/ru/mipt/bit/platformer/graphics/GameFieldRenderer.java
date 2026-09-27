package ru.mipt.bit.platformer.graphics;

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

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class GameFieldRenderer implements Disposable {

    private final TiledMap level;
    private final TiledMapTileLayer groundLayer;
    private final MapRenderer renderer;
    private final TileMovement movement;

    public GameFieldRenderer(String levelFile, Batch batch, Interpolation interpolation) {
        level = new TmxMapLoader().load(levelFile);
        renderer = createSingleLayerMapRenderer(level, batch);
        groundLayer = getSingleLayer(level);
        movement = new TileMovement(groundLayer, interpolation);
    }

    public int getWidth() {
        return groundLayer.getWidth();
    }

    public int getHeight() {
        return groundLayer.getHeight();
    }

    public void moveAtTileCenter(Rectangle rectangle, GridPoint2 coordinates) {
        moveRectangleAtTileCenter(groundLayer, rectangle, coordinates);
    }

    public void moveBetweenTileCenters(Rectangle rectangle, GridPoint2 fromCoordinates,
                                       GridPoint2 toCoordinates, float progress) {
        movement.moveRectangleBetweenTileCenters(rectangle, fromCoordinates, toCoordinates, progress);
    }

    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        level.dispose();
    }
}
