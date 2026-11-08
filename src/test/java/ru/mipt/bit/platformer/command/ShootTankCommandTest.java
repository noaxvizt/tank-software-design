package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.Tank;

import static org.junit.Assert.assertEquals;

public class ShootTankCommandTest {

    @Test
    public void shouldAskTankToShoot() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        level.addObject(tank);
        Command command = new ShootTankCommand(tank);

        command.execute();

        assertEquals(2, level.getObjects().size());
    }
}
