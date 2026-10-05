package ru.mipt.bit.platformer.input;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InputHandlerTest {

    @Test
    public void shouldCallAllButtonHandlers() {
        int[] calls = new int[1];
        InputHandler inputHandler = new InputHandler(Arrays.asList(
                () -> calls[0]++,
                () -> calls[0]++
        ));

        inputHandler.handle();

        assertEquals(2, calls[0]);
    }
}
