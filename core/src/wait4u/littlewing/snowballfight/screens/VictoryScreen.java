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

public class VictoryScreen extends DefaultScreen {
    TextureRegion victory;
    SpriteBatch batch;
    float time = 0;

    public VictoryScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        victory = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/king_victory.png")), 0, 0, 432, 360);
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
                game.setScreen(new GameScreen(game, 6));
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        victory.getTexture().dispose();
        /*if ((this.ani_step >= 13) && (this.ani_step < 27))
        {
            paramGraphics.setColor(16777215);
            paramGraphics.fillRect(0, 60, 128, 47);
            paramGraphics.drawImage(this.imgHero_v, this.h_x * 5, 83, 0x10 | 0x1);
        }
        else if ((this.ani_step >= 28) && (this.ani_step < 50))
        {
            paramGraphics.drawImage(this.imgV, this.h_x * 5 + 8, 87, 0x10 | 0x1);
            if (this.ani_step > 41) {
                paramGraphics.drawImage(this.imgVictory, 60, 60, 0x10 | 0x1);
            }
        }
        else if (this.ani_step == 50)
        {
            this.ani_step = -1;
        }*/
    }

}
