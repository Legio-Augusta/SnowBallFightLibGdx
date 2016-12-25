package de.swagner.sbf2.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 12/21/2016.
 */

public class LoseScreen extends DefaultScreen {
    public LoseScreen(Game game) {
        super(game);
    }

    TextureRegion victory;
    SpriteBatch batch;
    float time = 0;

    @Override
    public void show() {
        victory = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/hero_lose3.png")), 0, 0, 432, 360);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 432, 360);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(victory, 0, 0);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                game.setScreen(new Screen1(game));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        victory.getTexture().dispose();
    }
}
