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

public class SamsungFunclubScreen extends DefaultScreen {
    TextureRegion present;
    Texture sam_logo;
    Texture http1;
    Texture http2;

    SpriteBatch batch;
    float time = 0;

    public SamsungFunclubScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        present = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/present.png")), 0, 0, 747, 99);
        sam_logo = new Texture(Gdx.files.internal("data/samsung-white/sam_logo.png"));
        http1 = new Texture(Gdx.files.internal("data/samsung-white/http1.png"));
        http2 = new Texture(Gdx.files.internal("data/samsung-white/http2.png"));
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1080, 1122);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(present, 0, 0);
        batch.draw(sam_logo, 0, 99);
        batch.draw(http1, 0, 400);
        batch.draw(http2, 0, 580);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                game.setScreen(new TitleMenuScreen(game));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        present.getTexture().dispose();
        sam_logo.dispose();
        http1.dispose();
        http2.dispose();
    }
}
