package de.swagner.sbf2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by nickfarow on 18/10/2016.
 */
public abstract class DefaultScreen implements Screen {
    protected Game game;

    public DefaultScreen(Game game) {
        this.game = game;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
//		dispose();
    }

    @Override
    public void resume() {
//		Resources.getInstance().reInit();
    }

    @Override
    public void dispose() {
//		Resources.getInstance().dispose();
    }
}
