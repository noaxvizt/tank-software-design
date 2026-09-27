package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TankMovementInputHandlerTest {

    @Test
    public void shouldMoveTankByArrowKey() {
        TestKeyboard keyboard = new TestKeyboard();
        keyboard.press(Input.Keys.RIGHT);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f);
        GameField field = new GameField(5, 5);
        TankMovementInputHandler handler = new TankMovementInputHandler(keyboard, tank, field);

        handler.handle();

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
    }

    @Test
    public void shouldMoveTankByWasdKey() {
        TestKeyboard keyboard = new TestKeyboard();
        keyboard.press(Input.Keys.W);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f);
        GameField field = new GameField(5, 5);
        TankMovementInputHandler handler = new TankMovementInputHandler(keyboard, tank, field);

        handler.handle();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
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
