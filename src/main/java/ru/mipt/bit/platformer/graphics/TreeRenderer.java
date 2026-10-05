package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.model.Tree;

public class TreeRenderer extends GameObjectRenderer {

    private final Tree tree;
    private final TilePositioner tilePositioner;

    public TreeRenderer(String textureFile, Tree tree, TilePositioner tilePositioner) {
        super(textureFile);
        this.tree = tree;
        this.tilePositioner = tilePositioner;
    }

    @Override
    public void render(Batch batch) {
        tilePositioner.moveAtTileCenter(rectangle, tree.getCoordinates());
        draw(batch, 0f);
    }
}
