package com.littlewing.sbf.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Admin on 12/21/2016.
 */

public class LoseScreen extends DefaultScreen {
    public LoseScreen(Game game) {
        super(game);
        music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/lose.mp3"));
    }

    Texture imgLose;
    Texture imgHero_l;
    SpriteBatch batch;
    public Music music;

    @Override
    public void show() {
        imgLose = new Texture("data/sprites/lose.png");
        imgHero_l = new Texture("data/sprites/hero-lose.png");
        batch = new SpriteBatch();
        if(music != null) {
            if(!music.isPlaying()) {
                music.play();
                music.setLooping(false);
            }
        }
    }

    @Override
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int VIEW_PORT_HEIGHT = (int)SCREEN_HEIGHT*3/4;
        int BOTTOM_SPACE = (int)SCREEN_HEIGHT/8;
        float hd_ratio = (float)SCREEN_WIDTH/(float)1080;

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //batch.draw(imgLose, SCREEN_WIDTH/2-(int)(imgLose.getWidth()/2*hd_ratio), VIEW_PORT_HEIGHT/2+2*BOTTOM_SPACE);
        drawFitScreen(imgLose, SCREEN_WIDTH/2-(int)(imgLose.getWidth()/2*hd_ratio), VIEW_PORT_HEIGHT/2+2*BOTTOM_SPACE, hd_ratio);
        //batch.draw(imgHero_l, SCREEN_WIDTH/2-(int)(imgHero_l.getWidth()/2*hd_ratio), VIEW_PORT_HEIGHT/2+BOTTOM_SPACE);
        drawFitScreen(imgHero_l, SCREEN_WIDTH/2-(int)(imgHero_l.getWidth()/2*hd_ratio), VIEW_PORT_HEIGHT/2+BOTTOM_SPACE, hd_ratio);
        batch.end();
        if(music != null) {
            if(!music.isPlaying()) {
                music.play();
                music.setLooping(false);
            }
        }

        if (Gdx.input.justTouched()) {
            try {
                Thread.sleep(1000);
            }
            catch (Exception localException1) {}
            game.setScreen(new VillageScreen(game));
        }
    }

    @Override
    public void hide() {
        batch.dispose();
        imgLose.dispose();
        imgHero_l.dispose();
        music.dispose();
    }
    public void drawFitScreen(Texture texture, float x, float y, float hd_ratio) {
        batch.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight(), hd_ratio, hd_ratio, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
