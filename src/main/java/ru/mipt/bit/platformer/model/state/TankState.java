package ru.mipt.bit.platformer.model.state;

/** Domain state for tank behavior. */
public interface TankState {

    float getMovementDuration(float basicDuration);

    float getShootingDelay(float basicDelay);

    boolean canShoot();
}
