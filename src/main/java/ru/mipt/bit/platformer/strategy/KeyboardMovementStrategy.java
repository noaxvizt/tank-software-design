package ru.mipt.bit.platformer.strategy;

import ru.mipt.bit.platformer.input.Keyboard;
import ru.mipt.bit.platformer.model.Direction;

import java.util.LinkedHashMap;
import java.util.Map;

public class KeyboardMovementStrategy implements TankMovementStrategy {

    private final Keyboard keyboard;
    private final Map<Integer, Direction> controls;

    public KeyboardMovementStrategy(Keyboard keyboard, Map<Integer, Direction> controls) {
        this.keyboard = keyboard;
        this.controls = new LinkedHashMap<>(controls);
    }

    @Override
    public Direction getDirection() {
        for (Map.Entry<Integer, Direction> control : controls.entrySet()) {
            if (keyboard.isKeyPressed(control.getKey())) {
                return control.getValue();
            }
        }
        return null;
    }
}
