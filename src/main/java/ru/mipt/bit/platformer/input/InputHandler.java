package ru.mipt.bit.platformer.input;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    private final List<ButtonHandler> buttonHandlers = new ArrayList<>();

    public void addButtonHandler(ButtonHandler buttonHandler) {
        buttonHandlers.add(buttonHandler);
    }

    public void handle() {
        for (ButtonHandler buttonHandler : buttonHandlers) {
            buttonHandler.handle();
        }
    }
}
