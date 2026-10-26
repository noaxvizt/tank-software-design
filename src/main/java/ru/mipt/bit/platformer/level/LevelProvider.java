package ru.mipt.bit.platformer.level;

public interface LevelProvider {

    Level createLevel(int width, int height);
}
