package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Admin on 12/21/2017.
 */

public class AllClearScreen extends DefaultScreen {
    Texture imgAllClear;

    SpriteBatch batch;
    float time = 0;
    public Music music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));

    // TODO draw last state of game, need many more shared data between runningGame and Victory screen.
    // This require more complex constructor and Screen class data.
    public AllClearScreen(Game game) {
        super(game);
        music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));
    }

    @Override
    public void show() {
        imgAllClear = new Texture("data/samsung-white/allClear2.jpg");

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int VIEW_PORT_HEIGHT = (int)SCREEN_HEIGHT*3/4;
        int BOTTOM_SPACE = (int)SCREEN_HEIGHT/8;

        if(music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/victory.mp3"));
        }
        music.setVolume(0.5f);
        if(!music.isPlaying()) {
            music.play();
            music.setLooping(false);
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgAllClear, 0, 110);
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
        imgAllClear.dispose();
    }

}
