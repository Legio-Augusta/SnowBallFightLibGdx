package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by nickfarow on 05/10/2016.
 */
public class MainMenuScreen {
    private SpriteBatch batch;
    private Texture ttrSplash;

    public MainMenuScreen() {
        super();
        batch = new SpriteBatch();
        ttrSplash = new Texture("data/samsung-white/main_menu_bg.png");
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

    }

}
