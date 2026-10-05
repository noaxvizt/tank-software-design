package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.MovementArea;
import ru.mipt.bit.platformer.model.Tank;

import java.util.LinkedHashMap;
import java.util.Map;

public class TankMovementInputHandler implements GameInput {

    private final Keyboard keyboard;
    private final Tank tank;
    private final MovementArea movementArea;
    private final Map<Integer, Direction> controls;

    public TankMovementInputHandler(Keyboard keyboard, Tank tank,
                                    MovementArea movementArea, Map<Integer, Direction> controls) {
        this.keyboard = keyboard;
        this.tank = tank;
        this.movementArea = movementArea;
        this.controls = new LinkedHashMap<>(controls);
    }

    @Override
    public void handle() {
        for (Map.Entry<Integer, Direction> control : controls.entrySet()) {
            if (keyboard.isKeyPressed(control.getKey())) {
                tank.tryMove(control.getValue(), movementArea);
            }
        }
    }
}
