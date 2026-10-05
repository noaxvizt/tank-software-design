package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.GameRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GdxGameRenderer implements GameRenderer {

    private final Batch batch;
    private final GameFieldRenderer fieldRenderer;
    private final List<ObjectRenderer> objectRenderers;
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public GdxGameRenderer(Batch batch, GameFieldRenderer fieldRenderer,
                           Collection<ObjectRenderer> objectRenderers,
                           float red, float green, float blue, float alpha) {
        this.batch = batch;
        this.fieldRenderer = fieldRenderer;
        this.objectRenderers = new ArrayList<>(objectRenderers);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(red, green, blue, alpha);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        fieldRenderer.render();
        batch.begin();
        for (ObjectRenderer objectRenderer : objectRenderers) {
            objectRenderer.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (ObjectRenderer objectRenderer : objectRenderers) {
            objectRenderer.dispose();
        }
        fieldRenderer.dispose();
        batch.dispose();
    }
}
