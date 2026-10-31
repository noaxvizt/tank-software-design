package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameLevelTest {

    @Test
    public void shouldNotifyListenerAboutAddedAndRemovedObjects() {
        GameLevel level = new GameLevel(5, 5);
        TestListener listener = new TestListener();
        level.addListener(listener);
        Tree tree = new Tree(new GridPoint2(1, 1));

        level.addObject(tree);
        level.removeObject(tree);

        assertEquals(1, listener.addedObjects);
        assertEquals(1, listener.removedObjects);
    }

    @Test
    public void shouldRemoveBulletAfterTreeCollision() {
        GameLevel level = new GameLevel(5, 5);
        Tree tree = new Tree(new GridPoint2(2, 1));
        level.addObject(tree);
        Bullet bullet = new Bullet(
                new GridPoint2(1, 1), Direction.RIGHT, 0.1f, 1, level
        );
        level.addBullet(bullet);

        level.update(0.1f);

        assertFalse(level.getObjects().contains(bullet));
        assertTrue(level.getObjects().contains(tree));
    }

    @Test
    public void shouldDamageAdjacentTankImmediately() {
        GameLevel level = new GameLevel(5, 5);
        Tank shooter = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        Tank target = new Tank(new GridPoint2(2, 1), 0.4f, level, level);
        level.addObject(shooter);
        level.addObject(target);

        shooter.shoot();

        assertEquals(2, target.getHealth());
        assertEquals(2, level.getObjects().size());
    }

    @Test
    public void shouldRemoveTankWithoutHealth() {
        GameLevel level = new GameLevel(5, 5);
        Tank shooter = new Tank(new GridPoint2(1, 1), 0.4f, level, level);
        Tank target = new Tank(new GridPoint2(2, 1), 0.4f, level, level);
        level.addObject(shooter);
        level.addObject(target);

        for (int shot = 0; shot < 3; shot++) {
            shooter.shoot();
            shooter.update(0.5f);
        }

        assertFalse(level.getObjects().contains(target));
        assertTrue(level.canMoveTo(new GridPoint2(2, 1)));
    }

    @Test
    public void shouldRemoveBothBulletsAfterCollision() {
        GameLevel level = new GameLevel(5, 5);
        Bullet firstBullet = new Bullet(
                new GridPoint2(1, 1), Direction.RIGHT, 0.1f, 1, level
        );
        Bullet secondBullet = new Bullet(
                new GridPoint2(2, 1), Direction.LEFT, 0.1f, 1, level
        );
        level.addBullet(firstBullet);
        level.addBullet(secondBullet);

        level.update(0.1f);

        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void shouldDamageTankThatMovesIntoBullet() {
        GameLevel level = new GameLevel(5, 5);
        Tank tank = new Tank(new GridPoint2(0, 1), 0.4f, level, level);
        level.addObject(tank);
        Bullet bullet = new Bullet(
                new GridPoint2(1, 1), Direction.RIGHT, 0.1f, 1, level
        );
        level.addBullet(bullet);

        tank.tryMove(Direction.RIGHT);
        level.update(0f);

        assertEquals(2, tank.getHealth());
        assertFalse(level.getObjects().contains(bullet));
    }

    private static class TestListener implements GameLevelListener {

        private int addedObjects;
        private int removedObjects;

        @Override
        public void objectAdded(LevelObject object) {
            addedObjects++;
        }

        @Override
        public void objectRemoved(LevelObject object) {
            removedObjects++;
        }
    }
}
