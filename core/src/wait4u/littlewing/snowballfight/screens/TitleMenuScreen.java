package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 11/28/2017.
 */

public class TitleMenuScreen extends DefaultScreen {
    // TODO handle touch event in button like position
    Texture imgMM;
    Texture title;
    Texture imgSl;
    Texture imgBk;

    SpriteBatch batch;
    float time = 0;

    public TitleMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        imgMM = new Texture(Gdx.files.internal("data/samsung-white/mm.png"));
        title = new Texture(Gdx.files.internal("data/samsung-white/title.png"));
        imgSl = new Texture(Gdx.files.internal("data/samsung-white/sl.png"));
        imgBk = new Texture(Gdx.files.internal("data/samsung-white/bk.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(imgMM, 0, 480);
        batch.draw(title, 120, 515);
        batch.draw(imgSl, 10, 320);
        batch.draw(imgBk, 560, 320);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                game.setScreen(new GameScreen(game));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        imgMM.dispose();
        title.dispose();
        imgSl.dispose();
        imgBk.dispose();
    }
}
