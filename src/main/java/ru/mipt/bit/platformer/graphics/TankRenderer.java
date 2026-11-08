package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tank;

public class TankRenderer extends GameObjectRenderer {

    private final Tank tank;
    private final TilePositioner tilePositioner;

    public TankRenderer(String textureFile, Tank tank, TilePositioner tilePositioner) {
        super(textureFile);
        this.tank = tank;
        this.tilePositioner = tilePositioner;
    }

    @Override
    public void render(Batch batch) {
        tilePositioner.moveBetweenTileCenters(
                rectangle,
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress()
        );
        draw(batch, tank.getRotation());
    }
}
