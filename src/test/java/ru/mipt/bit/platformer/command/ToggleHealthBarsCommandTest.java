package ru.mipt.bit.platformer.command;

import org.junit.Test;
import ru.mipt.bit.platformer.graphics.HealthBarDisplay;

import static org.junit.Assert.assertEquals;

public class ToggleHealthBarsCommandTest {

    @Test
    public void shouldToggleHealthBars() {
        TestHealthBarDisplay display = new TestHealthBarDisplay();
        Command command = new ToggleHealthBarsCommand(display);

        command.execute();

        assertEquals(1, display.toggleCalls);
    }

    private static class TestHealthBarDisplay implements HealthBarDisplay {

        private int toggleCalls;

        @Override
        public void toggleHealthBars() {
            toggleCalls++;
        }
    }
}
