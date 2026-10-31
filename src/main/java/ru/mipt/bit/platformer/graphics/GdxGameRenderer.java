package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.GameRenderer;
import ru.mipt.bit.platformer.level.GameLevelListener;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GdxGameRenderer implements GameRenderer, GameLevelListener {

    private final Batch batch;
    private final GameFieldRenderer fieldRenderer;
    private final Map<LevelObject, ObjectRenderer> objectRenderers = new LinkedHashMap<>();
    private final String tankTexture;
    private final String treeTexture;
    private final float red;
    private final float green;
    private final float blue;
    private final float alpha;

    public GdxGameRenderer(Batch batch, GameFieldRenderer fieldRenderer,
                           String tankTexture, String treeTexture,
                           float red, float green, float blue, float alpha) {
        this.batch = batch;
        this.fieldRenderer = fieldRenderer;
        this.tankTexture = tankTexture;
        this.treeTexture = treeTexture;
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
        for (ObjectRenderer objectRenderer : objectRenderers.values()) {
            objectRenderer.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (ObjectRenderer objectRenderer : objectRenderers.values()) {
            objectRenderer.dispose();
        }
        fieldRenderer.dispose();
        batch.dispose();
    }

    @Override
    public void objectAdded(LevelObject object) {
        ObjectRenderer objectRenderer = null;
        if (object instanceof Tank) {
            objectRenderer = new TankRenderer(tankTexture, (Tank) object, fieldRenderer);
        } else if (object instanceof Tree) {
            objectRenderer = new TreeRenderer(treeTexture, (Tree) object, fieldRenderer);
        } else if (object instanceof Bullet) {
            objectRenderer = new BulletRenderer((Bullet) object, fieldRenderer);
        }

        if (objectRenderer != null) {
            objectRenderers.put(object, objectRenderer);
        }
    }

    @Override
    public void objectRemoved(LevelObject object) {
        ObjectRenderer objectRenderer = objectRenderers.remove(object);
        if (objectRenderer != null) {
            objectRenderer.dispose();
        }
    }
}
