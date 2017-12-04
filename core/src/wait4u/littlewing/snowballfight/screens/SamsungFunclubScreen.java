package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Admin on 11/28/2017.
 */

public class SamsungFunclubScreen extends DefaultScreen {
    Texture present;
    Texture sam_logo;
    Texture http1;
    Texture http2;
    Texture samsung_blue;

    SpriteBatch batch;
    float time = 0;

    public SamsungFunclubScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        samsung_blue = new Texture(Gdx.files.internal("data/samsung-white/samsung_blue.png"));
        present = new Texture(Gdx.files.internal("data/samsung-white/present.png")); // 150
        sam_logo = new Texture(Gdx.files.internal("data/samsung-white/sam_logo.png")); // 370
        http1 = new Texture(Gdx.files.internal("data/samsung-white/http1.png")); // 208
        http2 = new Texture(Gdx.files.internal("data/samsung-white/http2.png"));
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT); // TODO handle ratio, scale, if ratio not match texture it may be not rendered.
        batch.draw(present, SCREEN_WIDTH/8, (1440) );
        batch.draw(sam_logo, 0, SCREEN_HEIGHT/2 - (int)(sam_logo.getHeight()/2)+160 );
        batch.draw(http1, SCREEN_WIDTH/16, 700 );
        batch.draw(http2, SCREEN_WIDTH/16, 500 );
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            game.setScreen(new TitleMenuScreen(game));
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        present.dispose();
        sam_logo.dispose();
        http1.dispose();
        http2.dispose();

        /*  paramGraphics.setColor(16777215);
        paramGraphics.fillRect(0, 0, 128, 135);
        paramGraphics.setColor(25054);
        paramGraphics.fillRect(0, 0, 128, 22);
        paramGraphics.fillRect(0, 71, 128, 84);
        try
        {
            paramGraphics.drawImage(Image.createImage("/present.png"), 64, 5, 0x10 | 0x1);
            paramGraphics.drawImage(Image.createImage("/sam_logo.png"), 64, 28, 0x10 | 0x1);
            paramGraphics.drawImage(Image.createImage("/http1.png"), 7, 77, 20);
            paramGraphics.drawImage(Image.createImage("/http2.png"), 7, 103, 20);
        }
        catch (Exception localException3) {}
        System.gc();*/
    }
}
