package ru.mipt.bit.platformer.model.state;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankStateTest {

    @Test
    public void shouldKeepBasicBehaviorInLightState() {
        TankState state = new LightTankState();

        assertEquals(0.4f, state.getMovementDuration(0.4f), 0f);
        assertEquals(0.5f, state.getShootingDelay(0.5f), 0f);
        assertTrue(state.canShoot());
    }

    @Test
    public void shouldDoubleMovementDurationInMediumState() {
        TankState state = new MediumTankState();

        assertEquals(0.8f, state.getMovementDuration(0.4f), 0f);
        assertTrue(state.canShoot());
    }

    @Test
    public void shouldTripleMovementDurationAndForbidShootingInHeavyState() {
        TankState state = new HeavyTankState();

        assertEquals(1.2f, state.getMovementDuration(0.4f), 0.001f);
        assertFalse(state.canShoot());
    }
}
