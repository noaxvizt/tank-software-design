package ru.mipt.bit.platformer.input;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InputHandlerTest {

    @Test
    public void shouldCallAllButtonHandlers() {
        InputHandler inputHandler = new InputHandler();
        int[] calls = new int[1];
        inputHandler.addButtonHandler(() -> calls[0]++);
        inputHandler.addButtonHandler(() -> calls[0]++);

        inputHandler.handle();

        assertEquals(2, calls[0]);
    }
}
