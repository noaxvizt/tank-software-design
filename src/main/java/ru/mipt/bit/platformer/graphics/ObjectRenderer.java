package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

/** Infrastructure contract for rendering one level object. */
public interface ObjectRenderer extends Disposable {

    void render(Batch batch);
}
