package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public interface BulletArea {

    void addBullet(Bullet bullet);

    boolean tryMoveBullet(Bullet bullet, GridPoint2 destinationCoordinates);

    void removeBullet(Bullet bullet);
}
