package ru.mipt.bit.platformer.model.state;

/** State for a tank with less than 15 percent health. */
public class HeavyTankState implements TankState {

    @Override
    public float getMovementDuration(float basicDuration) {
        return basicDuration * 3f;
    }

    @Override
    public float getShootingDelay(float basicDelay) {
        return basicDelay;
    }

    @Override
    public boolean canShoot() {
        return false;
    }
}
