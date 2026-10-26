package ru.mipt.bit.platformer.command;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;

import static org.junit.Assert.assertEquals;

public class MoveTankCommandTest {

    @Test
    public void shouldMoveTank() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);
        Command command = new MoveTankCommand(tank, Direction.RIGHT);

        command.execute();

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
    }
}
