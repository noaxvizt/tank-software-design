package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.GameInput;
import ru.mipt.bit.platformer.command.Command;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.command.ShootTankCommand;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.strategy.TankMovementStrategy;

import java.util.Random;

public class TankController implements GameInput {

    private final Tank tank;
    private final TankMovementStrategy movementStrategy;
    private final CommandQueue commandQueue;
    private final Keyboard keyboard;
    private final int shootKey;
    private final Random random;

    public TankController(Tank tank, TankMovementStrategy movementStrategy,
                          CommandQueue commandQueue, Keyboard keyboard, int shootKey) {
        this.tank = tank;
        this.movementStrategy = movementStrategy;
        this.commandQueue = commandQueue;
        this.keyboard = keyboard;
        this.shootKey = shootKey;
        random = null;
    }

    public TankController(Tank tank, TankMovementStrategy movementStrategy,
                          CommandQueue commandQueue, Random random) {
        this.tank = tank;
        this.movementStrategy = movementStrategy;
        this.commandQueue = commandQueue;
        this.random = random;
        keyboard = null;
        shootKey = 0;
    }

    @Override
    public void handle() {
        if (keyboard != null && keyboard.isKeyPressed(shootKey)) {
            commandQueue.add(new ShootTankCommand(tank));
            return;
        }
        if (random != null && random.nextInt(4) == 0) {
            commandQueue.add(new ShootTankCommand(tank));
            return;
        }

        Direction direction = movementStrategy.getDirection();
        if (direction != null) {
            Command command = new MoveTankCommand(tank, direction);
            commandQueue.add(command);
        }
    }
}
