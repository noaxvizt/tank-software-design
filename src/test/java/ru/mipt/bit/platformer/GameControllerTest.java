package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.command.CommandQueue;
import ru.mipt.bit.platformer.command.MoveTankCommand;
import ru.mipt.bit.platformer.level.GameLevel;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

import static org.junit.Assert.assertEquals;

public class GameControllerTest {

    @Test
    public void shouldHandleInputUpdateTankAndRender() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(1, 1), 1f, level);
        level.addObject(tank);
        CommandQueue commandQueue = new CommandQueue();
        GameInput inputHandler = () -> commandQueue.add(
                new MoveTankCommand(tank, Direction.RIGHT)
        );
        TestRenderer renderer = new TestRenderer();
        GameController game = new GameController(level, inputHandler, commandQueue, renderer);

        game.update(0.5f);

        assertEquals(new GridPoint2(2, 1), tank.getDestinationCoordinates());
        assertEquals(0.5f, tank.getMovementProgress(), 0f);
        assertEquals(1, renderer.renderCalls);
    }

    @Test
    public void shouldDisposeRenderer() {
        GameLevel level = new GameLevel(5, 5);
        CommandQueue commandQueue = new CommandQueue();
        TestRenderer renderer = new TestRenderer();
        GameController game = new GameController(level, () -> { }, commandQueue, renderer);

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
