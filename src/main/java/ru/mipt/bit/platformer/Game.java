package ru.mipt.bit.platformer;

/** Application input port for the game loop. */
public interface Game {

    void update(float deltaTime);

    void dispose();
}
