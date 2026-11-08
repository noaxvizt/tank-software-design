package ru.mipt.bit.platformer.strategy;

import org.junit.Test;
import ru.mipt.bit.platformer.input.Keyboard;
import ru.mipt.bit.platformer.model.Direction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class KeyboardMovementStrategyTest {

    @Test
    public void shouldReturnDirectionForPressedKey() {
        TestKeyboard keyboard = new TestKeyboard();
        keyboard.press(1);
        KeyboardMovementStrategy strategy = new KeyboardMovementStrategy(
                keyboard, Collections.singletonMap(1, Direction.RIGHT)
        );

        assertEquals(Direction.RIGHT, strategy.getDirection());
    }

    @Test
    public void shouldReturnNullForUnknownKey() {
        TestKeyboard keyboard = new TestKeyboard();
        keyboard.press(2);
        KeyboardMovementStrategy strategy = new KeyboardMovementStrategy(
                keyboard, Collections.singletonMap(1, Direction.RIGHT)
        );

        assertNull(strategy.getDirection());
    }

    private static class TestKeyboard implements Keyboard {

        private final Set<Integer> pressedKeys = new HashSet<>();

        public void press(int key) {
            pressedKeys.add(key);
        }

        @Override
        public boolean isKeyPressed(int key) {
            return pressedKeys.contains(key);
        }
    }
}
