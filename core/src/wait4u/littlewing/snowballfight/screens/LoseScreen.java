package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
        imgLose = new Texture("data/samsung-white/lose.png");
        imgHero_l = new Texture("data/samsung-white/hero-lose.png");
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

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgLose, SCREEN_WIDTH/2-(int)(imgLose.getWidth()/2), VIEW_PORT_HEIGHT/2+2*BOTTOM_SPACE);
        batch.draw(imgHero_l, SCREEN_WIDTH/2-(int)(imgHero_l.getWidth()/2), VIEW_PORT_HEIGHT/2+BOTTOM_SPACE);
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
}
