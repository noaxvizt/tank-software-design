package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.command.ToggleHealthBarsCommand;
import ru.mipt.bit.platformer.graphics.HealthBarDisplay;

public class HealthBarInputHandler implements GameInput {

    private final Keyboard keyboard;
    private final int key;
    private final CommandQueue commandQueue;
    private final HealthBarDisplay healthBarDisplay;

    private boolean keyWasPressed;

    public HealthBarInputHandler(Keyboard keyboard, int key, CommandQueue commandQueue,
                                 HealthBarDisplay healthBarDisplay) {
        this.keyboard = keyboard;
        this.key = key;
        this.commandQueue = commandQueue;
        this.healthBarDisplay = healthBarDisplay;
    }

    @Override
    public void handle() {
        boolean keyPressed = keyboard.isKeyPressed(key);
        if (keyPressed && !keyWasPressed) {
            commandQueue.add(new ToggleHealthBarsCommand(healthBarDisplay));
        }
        keyWasPressed = keyPressed;
    }
}
