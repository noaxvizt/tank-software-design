package ru.mipt.bit.platformer;

/** Domain contract for objects that live in the game loop. */
public interface Updatable {

    void update(float deltaTime);
}
