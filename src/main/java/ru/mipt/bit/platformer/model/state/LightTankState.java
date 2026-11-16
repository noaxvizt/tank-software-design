package ru.mipt.bit.platformer.model.state;

/** State for a tank with more than 70 percent health. */
public class LightTankState implements TankState {

    @Override
    public float getMovementDuration(float basicDuration) {
        return basicDuration;
    }

    @Override
    public float getShootingDelay(float basicDelay) {
        return basicDelay;
    }

    @Override
    public boolean canShoot() {
        return true;
    }
}
