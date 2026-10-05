package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {

    @Test
    public void shouldHandleInputUpdateTankAndRender() {
        Tank tank = new Tank(new GridPoint2(1, 1), 1f);
        GameInput inputHandler = () -> tank.tryMove(Direction.RIGHT, coordinates -> true);
        TestRenderer renderer = new TestRenderer();
        GameController game = new GameController(
                Collections.singletonList(tank), inputHandler, renderer
        );

        game.update(0.5f);

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
        assertEquals(0.5f, tank.getMovementProgress(), 0f);
        assertEquals(1, renderer.renderCalls);
    }

    @Test
    public void shouldDisposeRenderer() {
        Tank tank = new Tank(new GridPoint2(1, 1), 1f);
        TestRenderer renderer = new TestRenderer();
        GameController game = new GameController(
                Collections.singletonList(tank), () -> { }, renderer
        );

        game.dispose();

        assertEquals(1, renderer.disposeCalls);
    }

    private static class TestRenderer implements GameRenderer {

        private int renderCalls;
        private int disposeCalls;

        @Override
        public void render() {
            renderCalls++;
        }

        @Override
        public void dispose() {
            disposeCalls++;
        }
    }
}
