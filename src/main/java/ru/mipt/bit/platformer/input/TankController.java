package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.strategy.TankMovementStrategy;

public class TankController implements GameInput {

    private final Tank tank;
    private final TankMovementStrategy movementStrategy;

    public TankController(Tank tank, TankMovementStrategy movementStrategy) {
        this.tank = tank;
        this.movementStrategy = movementStrategy;
    }

    @Override
    public void handle() {
        Direction direction = movementStrategy.getDirection();
        if (direction != null) {
            Command command = new MoveTankCommand(tank, direction);
            command.execute();
        }
    }
}
