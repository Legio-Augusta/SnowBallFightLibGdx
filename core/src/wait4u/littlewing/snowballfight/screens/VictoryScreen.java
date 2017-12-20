package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 12/21/2016.
 */

public class VictoryScreen extends DefaultScreen {
    Texture imgVictory;
    Texture imgV;
    Texture imgHero_v;

    Texture snowWhiteBg;
    Texture imgBack;
    Texture ui;

    SpriteBatch batch;
    float time = 0;
    public Music music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));

    // TODO draw last state of game, need many more shared data between runningGame and Victory screen.
    // This require more complex constructor and Screen class data.
    public VictoryScreen(Game game) {
        super(game);
        music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));
    }

    @Override
    public void show() {
        imgVictory = new Texture("data/samsung-white/victory.png");
        imgV = new Texture("data/samsung-white/v.png");
        imgHero_v = new Texture("data/samsung-white/hero-vic.png");

        imgBack = new Texture("data/samsung-white/back1.png");
        snowWhiteBg = new Texture("data/samsung-white/white_bg.png");
        ui = new Texture("data/samsung-white/ui.png");

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int VIEW_PORT_HEIGHT = (int)SCREEN_HEIGHT*3/4;
        int BOTTOM_SPACE = (int)SCREEN_HEIGHT/8;
        int snowBoardHeight = VIEW_PORT_HEIGHT - ui.getHeight();

        if(music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));
        }
        if(!music.isPlaying()) {
            music.play();
            music.setLooping(false);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(snowWhiteBg, 0, BOTTOM_SPACE+ui.getHeight(), snowBoardHeight, snowBoardHeight);
        batch.draw(imgBack, 0, VIEW_PORT_HEIGHT+BOTTOM_SPACE/2);
        batch.draw(ui, 0, BOTTOM_SPACE);

        batch.draw(imgVictory, SCREEN_WIDTH/6, VIEW_PORT_HEIGHT/2+BOTTOM_SPACE);
        batch.draw(imgHero_v, SCREEN_WIDTH/2 - imgHero_v.getWidth()/2, VIEW_PORT_HEIGHT/2);
        batch.draw(imgV, SCREEN_WIDTH/2+16, VIEW_PORT_HEIGHT/2+imgHero_v.getHeight()/4);
        batch.end();

        delta = 0.05f;
        time += delta;
        if (time > 1) {
             if (Gdx.input.justTouched()) {
                 game.setScreen(new VillageScreen(game));
             }
        }
    }

    @Override
    public void hide() {
        batch.dispose();
        imgVictory.dispose();
        imgV.dispose();
        imgHero_v.dispose();
        imgBack.dispose();
        snowWhiteBg.dispose();
        ui.dispose();
        music.dispose();
    }

}
