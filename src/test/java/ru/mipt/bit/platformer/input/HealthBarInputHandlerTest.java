package ru.mipt.bit.platformer.input;

import org.junit.Test;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.graphics.HealthBarDisplay;

import static org.junit.Assert.assertEquals;

public class HealthBarInputHandlerTest {

    @Test
    public void shouldToggleHealthBarsOnEachKeyPress() {
        TestKeyboard keyboard = new TestKeyboard();
        TestHealthBarDisplay display = new TestHealthBarDisplay();
        CommandQueue commandQueue = new CommandQueue();
        HealthBarInputHandler inputHandler = new HealthBarInputHandler(
                keyboard, 1, commandQueue, display
        );

        keyboard.keyPressed = true;
        inputHandler.handle();
        inputHandler.handle();
        commandQueue.executeAll();

        assertEquals(1, display.toggleCalls);

        keyboard.keyPressed = false;
        inputHandler.handle();
        keyboard.keyPressed = true;
        inputHandler.handle();
        commandQueue.executeAll();

        assertEquals(2, display.toggleCalls);
    }

    private static class TestKeyboard implements Keyboard {

        private boolean keyPressed;

        @Override
        public boolean isKeyPressed(int key) {
            return keyPressed;
        }
    }

    private static class TestHealthBarDisplay implements HealthBarDisplay {

        private int toggleCalls;

        @Override
        public void toggleHealthBars() {
            toggleCalls++;
        }
    }
}
