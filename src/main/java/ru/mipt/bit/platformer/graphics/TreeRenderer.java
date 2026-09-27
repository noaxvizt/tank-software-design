package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tree;

public class TreeRenderer extends GameObjectRenderer {

    private final Tree tree;
    private final GameFieldRenderer fieldRenderer;

    public TreeRenderer(String textureFile, Tree tree, GameFieldRenderer fieldRenderer) {
        super(textureFile);
        this.tree = tree;
        this.fieldRenderer = fieldRenderer;
    }

    public void render(Batch batch) {
        fieldRenderer.moveAtTileCenter(rectangle, tree.getCoordinates());
        draw(batch, 0f);
    }
}
