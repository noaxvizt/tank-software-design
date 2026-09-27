package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tank;

public class TankRenderer extends GameObjectRenderer {

    private final Tank tank;
    private final GameFieldRenderer fieldRenderer;

    public TankRenderer(String textureFile, Tank tank, GameFieldRenderer fieldRenderer) {
        super(textureFile);
        this.tank = tank;
        this.fieldRenderer = fieldRenderer;
    }

    public void render(Batch batch) {
        fieldRenderer.moveBetweenTileCenters(
                rectangle,
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress()
        );
        draw(batch, tank.getRotation());
    }
}
