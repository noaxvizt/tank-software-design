package ru.mipt.bit.platformer.model.state;

/** State for a tank with 15 to 70 percent health. */
public class MediumTankState implements TankState {

    @Override
    public float getMovementDuration(float basicDuration) {
        return basicDuration * 2f;
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
