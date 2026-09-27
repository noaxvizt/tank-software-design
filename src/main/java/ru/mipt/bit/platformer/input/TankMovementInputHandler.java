package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;

import java.util.LinkedHashMap;
import java.util.Map;

public class TankMovementInputHandler implements ButtonHandler {

    private final Keyboard keyboard;
    private final Tank tank;
    private final GameField field;
    private final Map<Integer, Direction> controls = new LinkedHashMap<>();

    public TankMovementInputHandler(Keyboard keyboard, Tank tank, GameField field) {
        this.keyboard = keyboard;
        this.tank = tank;
        this.field = field;

        controls.put(Input.Keys.UP, Direction.UP);
        controls.put(Input.Keys.W, Direction.UP);
        controls.put(Input.Keys.LEFT, Direction.LEFT);
        controls.put(Input.Keys.A, Direction.LEFT);
        controls.put(Input.Keys.DOWN, Direction.DOWN);
        controls.put(Input.Keys.S, Direction.DOWN);
        controls.put(Input.Keys.RIGHT, Direction.RIGHT);
        controls.put(Input.Keys.D, Direction.RIGHT);
    }

    @Override
    public void handle() {
        for (Map.Entry<Integer, Direction> control : controls.entrySet()) {
            if (keyboard.isKeyPressed(control.getKey())) {
                tank.tryMove(control.getValue(), field);
            }
        }
    }
}
