package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import wait4u.littlewing.snowballfight.OverlapTester;

/**
 * Created by nickfarow on 05/10/2016.
 */
public class NewGameMenuScreen extends  DefaultScreen {
    // TODO add key up/down for select m_mode. Use touch position instead of up/down touch.
    private SpriteBatch batch;
    Texture imgMM;
    Texture title;
    Texture imgSl;
    Texture imgBk;
    Texture imgCh;
    Texture samsung_blue;
    BitmapFont font;
    Vector3 touchPoint;

    public NewGameMenuScreen(Game game) {
        super(game);
        touchPoint = new Vector3();
    }

    @Override
    public void show() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);

        imgMM = new Texture("data/samsung-white/mm.png");
        title = new Texture("data/samsung-white/title.png");
        imgSl = new Texture("data/samsung-white/sl.png");
        imgBk = new Texture("data/samsung-white/bk.png");
        imgCh = new Texture("data/samsung-white/check.png");
        samsung_blue = new Texture("data/samsung-white/samsung_blue.png");
        batch = new SpriteBatch();
    }

    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT);
        batch.draw(imgMM, 0, 480);
        font.draw(batch, "1. New Game", 110, 1300); // orig: 13 27 20-anchor
        font.draw(batch, "2. Saved Game", 110, 1160); // orig: 13, 44, 20
        batch.draw(imgSl, SCREEN_WIDTH-imgSl.getWidth(), 480);
        batch.draw(imgBk, 15, 480);
        batch.draw(imgCh, 36, 1230); // this.m_mode * 17 + 14
        batch.end();

        if (Gdx.input.justTouched()) {
            // Can not use font getBound(), so hard code position of text Bitmap.
            touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            Rectangle newGameTextBound = new Rectangle(110, 1300-160, SCREEN_WIDTH-200, 160);
            Rectangle savedGameTextBound = new Rectangle(110, 1160-140, SCREEN_WIDTH-200, 140);
            Rectangle selectButtonBound = new Rectangle(SCREEN_WIDTH-imgSl.getWidth(), 480, imgSl.getWidth(), imgSl.getHeight());
            Rectangle backButtonBound = new Rectangle(15, 480, imgBk.getWidth(), imgBk.getHeight());

            Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ newGameTextBound.toString() + " saved "+ savedGameTextBound.toString());
            if(OverlapTester.pointInRectangle(newGameTextBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
//                game.setScreen(new VillageScreen(game));
            } else if(OverlapTester.pointInRectangle(savedGameTextBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                // Saved game
                Gdx.gl.glClearColor( 1, 0, 0, 1 );
                Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
                batch.begin();
                batch.draw(imgCh, 36, 1230-140);
                batch.end();
//                game.setScreen(new VillageScreen(game));
            } else if(OverlapTester.pointInRectangle(selectButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new VillageScreen(game));
            } else if(OverlapTester.pointInRectangle(backButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                // game.setScreen(new VillageScreen(game)); // Configurations screen
            }
        }
    }

    @Override
    public void hide() {
        imgMM.dispose();
        title.dispose();
        imgSl.dispose();
        imgBk.dispose();
        imgCh.dispose();
        samsung_blue.dispose();
    }

}
