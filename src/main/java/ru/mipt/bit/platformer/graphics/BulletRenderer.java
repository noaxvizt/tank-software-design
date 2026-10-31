package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Bullet;

public class BulletRenderer implements ObjectRenderer {

    private final Bullet bullet;
    private final TilePositioner tilePositioner;
    private final Texture texture;
    private final Rectangle rectangle = new Rectangle(0f, 0f, 14f, 14f);

    public BulletRenderer(Bullet bullet, TilePositioner tilePositioner) {
        this.bullet = bullet;
        this.tilePositioner = tilePositioner;

        Pixmap pixmap = new Pixmap(14, 14, Pixmap.Format.RGBA8888);
        pixmap.setColor(1f, 0.75f, 0f, 1f);
        pixmap.fillCircle(7, 7, 6);
        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void render(Batch batch) {
        tilePositioner.moveBetweenTileCenters(
                rectangle,
                bullet.getCoordinates(),
                bullet.getDestinationCoordinates(),
                bullet.getMovementProgress()
        );
        batch.draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
