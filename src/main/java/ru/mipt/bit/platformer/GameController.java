package ru.mipt.bit.platformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameController implements Game {

    private final List<Updatable> updatableObjects;
    private final GameInput inputHandler;
    private final GameRenderer renderer;

    public GameController(Collection<Updatable> updatableObjects, GameInput inputHandler,
                          GameRenderer renderer) {
        this.updatableObjects = new ArrayList<>(updatableObjects);
        this.inputHandler = inputHandler;
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        inputHandler.handle();
        for (Updatable updatableObject : updatableObjects) {
            updatableObject.update(deltaTime);
        }
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
