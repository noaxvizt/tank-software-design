package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.LevelObject;

public interface GameLevelListener {

    void objectAdded(LevelObject object);

    void objectRemoved(LevelObject object);
}
