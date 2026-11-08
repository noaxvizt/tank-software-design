package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Tank;

public class HealthBarRendererDecorator implements ObjectRenderer {

    private final ObjectRenderer objectRenderer;
    private final Tank tank;
    private final TilePositioner tilePositioner;
    private final Texture texture;
    private final Rectangle rectangle = new Rectangle(0f, 0f, 80f, 8f);

    private boolean visible;

    public HealthBarRendererDecorator(ObjectRenderer objectRenderer, Tank tank,
                                      TilePositioner tilePositioner) {
        this.objectRenderer = objectRenderer;
        this.tank = tank;
        this.tilePositioner = tilePositioner;

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render(Batch batch) {
        objectRenderer.render(batch);
        if (!visible) {
            return;
        }

        tilePositioner.moveBetweenTileCenters(
                rectangle,
                tank.getCoordinates(),
                tank.getDestinationCoordinates(),
                tank.getMovementProgress()
        );
        rectangle.y += 50f;

        Color color = batch.getColor();
        float red = color.r;
        float green = color.g;
        float blue = color.b;
        float alpha = color.a;

        batch.setColor(0.35f, 0.05f, 0.05f, 1f);
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        float healthPart = Math.max(tank.getHealth(), 0) / (float) tank.getMaxHealth();
        batch.setColor(0.1f, 0.8f, 0.2f, 1f);
        batch.draw(
                texture,
                rectangle.x,
                rectangle.y,
                rectangle.width * healthPart,
                rectangle.height
        );
        batch.setColor(red, green, blue, alpha);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void dispose() {
        objectRenderer.dispose();
        texture.dispose();
    }
}
