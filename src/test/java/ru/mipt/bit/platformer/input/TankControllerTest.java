package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.Tank;

import static org.junit.Assert.assertEquals;

public class TankControllerTest {

    @Test
    public void shouldMoveTankUsingStrategy() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);
        TankController controller = new TankController(tank, () -> Direction.UP);

        controller.handle();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void shouldDoNothingWithoutDirection() {
        GameField field = new GameField(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 0.4f, field);
        TankController controller = new TankController(tank, () -> null);

        controller.handle();

        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
    }
}
