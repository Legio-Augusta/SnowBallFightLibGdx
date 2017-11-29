package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 11/28/2017.
 */

public class LogoScreen extends DefaultScreen {
    // TODO do we need use InputProcessor or just texture ?
    TextureRegion logo;
    SpriteBatch batch;
    float time = 0;

    public LogoScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        logo = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/logo.png")), 0, 0, 1080, 1122);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1080, 1122);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(logo, 0, 0);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                game.setScreen(new SamsungFunclubScreen(game));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        logo.getTexture().dispose();
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }
}
