package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import wait4u.littlewing.snowballfight.OverlapTester;

/**
 * Created by Admin on 11/28/2017.
 */

public class TitleMenuScreen extends DefaultScreen {
    // TODO handle touch event in button like position
    Texture imgMM;
    Texture title;
    Texture imgPl;
    Texture imgBk;
    Texture samsung_blue;
    Vector3 touchPoint;

    SpriteBatch batch;
    float time = 0;

    public TitleMenuScreen(Game game) {
        super(game);
        touchPoint = new Vector3();
    }

    @Override
    public void show() {
        // TODO handle screen ratio
        imgMM = new Texture("data/samsung-white/mm.png");
        title = new Texture("data/samsung-white/title.png");
        imgPl = new Texture("data/samsung-white/play.png");
        imgBk = new Texture("data/samsung-white/bk.png");
        samsung_blue = new Texture("data/samsung-white/samsung_blue.png");
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();

        batch.begin();
        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT);
        batch.draw(imgMM, 0, 480);
        batch.draw(title, 90, 300+480);
        batch.draw(imgPl, SCREEN_WIDTH-imgPl.getWidth(), 480);
        batch.draw(imgBk, 0, 480);
        batch.end();

        // time += delta;
        // if (time > 1) { } // Delay a bit for music ie.
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            // TODO use global var ie. screen or just pass value in constructor
            Gdx.app.log("INFO", "Touched at " + Gdx.input.getX());
            touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            Rectangle selectButtonBound = new Rectangle(SCREEN_WIDTH-imgPl.getWidth(), 480, imgPl.getWidth(), imgPl.getHeight());
            Rectangle backButtonBound = new Rectangle(0, 480, imgBk.getWidth(), imgBk.getHeight());

            Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ selectButtonBound.toString());
            // Adjust coordinate and geometry vector touch follow GDX anchor.
            if(OverlapTester.pointInRectangle(selectButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                game.setScreen(new NewGameMenuScreen(game));
            } else if(OverlapTester.pointInRectangle(backButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                // options
//                    game.setScreen(new VillageScreen(game)); // Configurations screen
            }
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        imgMM.dispose();
        title.dispose();
        imgPl.dispose();
        imgBk.dispose();

        /*        paramGraphics.drawImage(this.imgMM, 0, 0, 20);
        try
        {
            paramGraphics.drawImage(Image.createImage("/title.png"), 64, 35, 0x10 | 0x1);
        }
        catch (Exception localException6) {}
        paramGraphics.drawImage(this.imgPl, 68, 115, 20);
        paramGraphics.drawImage(this.imgBk, 2, 115, 20);
        System.gc();*/
    }
}
