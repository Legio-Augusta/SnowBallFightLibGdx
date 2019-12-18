package com.littlewing.sbf.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 11/28/2017.
 */

public class LogoScreen extends DefaultScreen {
    // TODO do we need use InputProcessor or just texture ?
    TextureRegion logo2;
    Texture logo;
    SpriteBatch batch;
    float time = 0;
    public Music music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/9.mid"));
    // public Music music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/night_opening.wav"));

    public LogoScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        // logo = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/logo.png")), 0, 0, 1080, 1122);
        batch = new SpriteBatch();
        // batch.getProjectionMatrix().setToOrtho2D(0, 0, 1080, 1122);
        logo = new Texture(Gdx.files.internal("data/samsung-white/logo.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();

        if(music != null) {
            if(!music.isPlaying()) {
                music.play();
                music.setLooping(false);
            }
        }
        batch.begin();
        //batch.draw(logo, 0, 400);
        float hd_ratio = (float)SCREEN_WIDTH/(float)logo.getWidth();
        batch.draw(logo, 0, hd_ratio*400, 0, 0, logo.getWidth(), logo.getHeight(), hd_ratio, hd_ratio, 0, 0, 0, logo.getWidth(), logo.getHeight(), false, false);
        batch.end();

        if (Gdx.input.justTouched()) {
            game.setScreen(new SamsungFunclubScreen(game));
        }
    }

    @Override
    public void hide() {
        batch.dispose();
        logo.dispose();
        music.dispose();
    }
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

}
