package wait4u.littlewing.snowballfight.screens;

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
                game.setScreen(new GameScreen(game, -1));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        victory.getTexture().dispose();
        /*        int k;
        if (this.ani_step < 30)
        {
            paramGraphics.setColor(0);
            for (k = 0; k < 11; k++) {
                paramGraphics.fillRect(0, k * 10, this.ani_step * 4 + 12, 5);
            }
        }
        else if (this.ani_step < 65)
        {
            paramGraphics.setColor(0);
            for (k = 0; k < 11; k++) {
                paramGraphics.fillRect(0, k * 10 + 5, (this.ani_step - 30) * 7 - k * 10, 5);
            }
        }
        else if ((this.ani_step >= 65) && (this.ani_step <= 100))
        {
            if (this.ani_step > 90) {
                paramGraphics.drawImage(this.imgLose, 60, 60, 0x10 | 0x1);
            }
            paramGraphics.drawImage(this.imgHero_l, this.h_x * 5, 87, 0x10 | 0x1);
        }*/
    }
}
