package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.Updatable;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.BulletArea;
import ru.mipt.bit.platformer.model.GameField;
import ru.mipt.bit.platformer.model.LevelObject;
import ru.mipt.bit.platformer.model.MovementArea;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLevel implements Updatable, MovementArea, BulletArea {

    private final GameField field;
    private final List<LevelObject> objects = new ArrayList<>();
    private final List<GameLevelListener> listeners = new ArrayList<>();

    public GameLevel(int width, int height) {
        field = new GameField(width, height);
    }

    public void addListener(GameLevelListener listener) {
        listeners.add(listener);
    }

    public void addObject(LevelObject object) {
        if (object instanceof Tree) {
            field.addObstacle(((Tree) object).getCoordinates());
        }
        objects.add(object);
        for (GameLevelListener listener : listeners) {
            listener.objectAdded(object);
        }
    }

    public void removeObject(LevelObject object) {
        if (!objects.remove(object)) {
            return;
        }

        if (object instanceof Tank) {
            Tank tank = (Tank) object;
            field.removeTank(tank.getCoordinates());
            field.removeTank(tank.getDestinationCoordinates());
        }
        if (object instanceof Tree) {
            field.removeObstacle(((Tree) object).getCoordinates());
        }

        for (GameLevelListener listener : listeners) {
            listener.objectRemoved(object);
        }
    }

    public List<LevelObject> getObjects() {
        return Collections.unmodifiableList(new ArrayList<>(objects));
    }

    @Override
    public void update(float deltaTime) {
        List<LevelObject> objectsToUpdate = new ArrayList<>(objects);
        for (LevelObject object : objectsToUpdate) {
            if (objects.contains(object)) {
                object.update(deltaTime);
            }
        }
        checkBulletCollisions();
    }

    @Override
    public void addBullet(Bullet bullet) {
        addObject(bullet);
        if (!field.isInside(bullet.getCoordinates())) {
            removeBullet(bullet);
            return;
        }
        resolveBulletCollision(bullet, bullet.getCoordinates());
    }

    @Override
    public boolean tryMoveBullet(Bullet bullet, GridPoint2 destinationCoordinates) {
        if (!field.isInside(destinationCoordinates)) {
            removeBullet(bullet);
            return false;
        }
        if (resolveBulletCollision(bullet, destinationCoordinates)) {
            return false;
        }
        bullet.startMoving(destinationCoordinates);
        return true;
    }

    @Override
    public void removeBullet(Bullet bullet) {
        removeObject(bullet);
    }

    private boolean resolveBulletCollision(Bullet bullet, GridPoint2 coordinates) {
        for (LevelObject object : new ArrayList<>(objects)) {
            if (object == bullet || !occupies(object, coordinates)) {
                continue;
            }

            if (object instanceof Tank) {
                Tank tank = (Tank) object;
                tank.takeDamage(bullet.getDamage());
                if (!tank.isAlive()) {
                    removeObject(tank);
                }
            } else if (object instanceof Bullet) {
                removeObject(object);
            }
            removeBullet(bullet);
            return true;
        }
        return false;
    }

    private void checkBulletCollisions() {
        for (LevelObject object : new ArrayList<>(objects)) {
            if (!(object instanceof Bullet) || !objects.contains(object)) {
                continue;
            }

            Bullet bullet = (Bullet) object;
            if (!resolveBulletCollision(bullet, bullet.getCoordinates())
                    && objects.contains(bullet)) {
                resolveBulletCollision(bullet, bullet.getDestinationCoordinates());
            }
        }
    }

    private boolean occupies(LevelObject object, GridPoint2 coordinates) {
        if (object instanceof Tank) {
            return ((Tank) object).occupies(coordinates);
        }
        if (object instanceof Tree) {
            return ((Tree) object).getCoordinates().equals(coordinates);
        }
        if (object instanceof Bullet) {
            return ((Bullet) object).occupies(coordinates);
        }
        return false;
    }

    @Override
    public boolean canMoveTo(GridPoint2 coordinates) {
        return field.canMoveTo(coordinates);
    }

    @Override
    public void addTank(GridPoint2 coordinates) {
        field.addTank(coordinates);
    }

    @Override
    public boolean tryStartMovement(GridPoint2 fromCoordinates, GridPoint2 toCoordinates) {
        return field.tryStartMovement(fromCoordinates, toCoordinates);
    }

    @Override
    public void finishMovement(GridPoint2 fromCoordinates) {
        field.finishMovement(fromCoordinates);
    }

    @Override
    public void removeTank(GridPoint2 coordinates) {
        field.removeTank(coordinates);
    }
}
