package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.level.GameLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class BulletTest {

    @Test
    public void shouldMoveInItsDirection() {
        GameLevel level = new GameLevel(5, 5);
        Bullet bullet = new Bullet(
                new GridPoint2(1, 1), Direction.RIGHT, 0.1f, 1, level
        );
        level.addBullet(bullet);

        level.update(0.1f);

        assertEquals(new GridPoint2(2, 1), bullet.getDestinationCoordinates());
        assertEquals(0f, bullet.getMovementProgress(), 0f);

        level.update(0.1f);

        assertEquals(new GridPoint2(2, 1), bullet.getCoordinates());
    }

    @Test
    public void shouldDisappearOutsideLevel() {
        GameLevel level = new GameLevel(2, 2);
        Bullet bullet = new Bullet(
                new GridPoint2(2, 1), Direction.RIGHT, 0.1f, 1, level
        );

        level.addBullet(bullet);

        assertFalse(level.getObjects().contains(bullet));
    }
}
