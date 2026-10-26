package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.GameInput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InputHandler implements GameInput {

    private final List<GameInput> inputHandlers;

    public InputHandler(Collection<GameInput> inputHandlers) {
        this.inputHandlers = new ArrayList<>(inputHandlers);
    }

    @Override
    public void handle() {
        for (GameInput inputHandler : inputHandlers) {
            inputHandler.handle();
        }
    }
}
