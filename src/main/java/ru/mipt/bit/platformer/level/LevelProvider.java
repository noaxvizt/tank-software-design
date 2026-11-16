package ru.mipt.bit.platformer.level;

/** Application output port for loading a level. */
public interface LevelProvider {

    Level createLevel(int width, int height);
}
