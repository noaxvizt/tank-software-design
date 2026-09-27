package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class GameObjectRenderer implements Disposable {

    private final Texture texture;
    protected final TextureRegion graphics;
    protected final Rectangle rectangle;

    protected GameObjectRenderer(String textureFile) {
        texture = new Texture(textureFile);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
    }

    protected void draw(Batch batch, float rotation) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
