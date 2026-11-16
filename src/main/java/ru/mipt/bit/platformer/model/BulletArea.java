package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/** Domain output port for bullet lifecycle. */
public interface BulletArea {

    void addBullet(Bullet bullet);

    boolean tryMoveBullet(Bullet bullet, GridPoint2 destinationCoordinates);

    void removeBullet(Bullet bullet);
}
