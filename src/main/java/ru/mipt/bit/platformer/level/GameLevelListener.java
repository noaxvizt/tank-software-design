package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.LevelObject;

/** Domain output port for level changes. */
public interface GameLevelListener {

    void objectAdded(LevelObject object);

    void objectRemoved(LevelObject object);
}
