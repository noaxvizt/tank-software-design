package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class TankGame implements ApplicationListener {

    private final GameFactory gameFactory;
    private Game game;

    public TankGame(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @Override
    public void create() {
        game = gameFactory.create();
    }

    @Override
    public void render() {
        game.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
