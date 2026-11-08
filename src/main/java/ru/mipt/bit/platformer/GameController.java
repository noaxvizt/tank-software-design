package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.command.CommandQueue;

public class GameController implements Game {

    private final Updatable logicalLevel;
    private final GameInput inputHandler;
    private final CommandQueue commandQueue;
    private final GameRenderer renderer;

    public GameController(Updatable logicalLevel, GameInput inputHandler,
                          CommandQueue commandQueue, GameRenderer renderer) {
        this.logicalLevel = logicalLevel;
        this.inputHandler = inputHandler;
        this.commandQueue = commandQueue;
        this.renderer = renderer;
    }

    @Override
    public void update(float deltaTime) {
        inputHandler.handle();
        commandQueue.executeAll();
        logicalLevel.update(deltaTime);
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
