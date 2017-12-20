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
 * Created by Admin on 11/29/2017.
 */

public class VillageScreen extends DefaultScreen {
    private SpriteBatch batch;
    Texture imgVill;
    Texture imgSchool;
    Texture samsung_blue;
    Texture heroIcon;
    Rectangle easternBoy;
    Rectangle southernBoy;
    Rectangle westernBoy;
    Rectangle northernBoy;

    BitmapFont font;
    Vector3 touchPoint;
    int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    int SCREEN_WIDTH = Gdx.graphics.getWidth();
    int CELL = (int)(SCREEN_WIDTH/120);

    public VillageScreen(Game game) {
        super(game);
        touchPoint = new Vector3();
    }
    @Override
    public void show() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);

        imgVill = new Texture("data/samsung-white/village.png");
        imgSchool = new Texture("data/samsung-white/school.png");
        samsung_blue = new Texture("data/samsung-white/samsung_blue.png");
        heroIcon = new Texture("data/samsung-white/hero_icon.png");
        batch = new SpriteBatch();
    }
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        int villHeight = imgVill.getHeight();
        easternBoy = new Rectangle(100*CELL, 480+villHeight/2-180, 200, 240);
        southernBoy = new Rectangle(20+SCREEN_WIDTH/2, 480+200, 200, 240);
        westernBoy = new Rectangle(SCREEN_WIDTH/2-20-200, 480+200, 200, 240);
        northernBoy = new Rectangle(20, 480+villHeight/2-180, 200, 240);

        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT);
        font.draw(batch, "Chose school", 110, SCREEN_HEIGHT-360);
        batch.draw(imgVill, 0, 480);
        batch.draw(heroIcon, SCREEN_WIDTH/2 - heroIcon.getWidth()/2, imgVill.getHeight()/2+480);

        batch.end();

        if (Gdx.input.justTouched()) {
            touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ northernBoy.toString());

            if(OverlapTester.pointInRectangle(easternBoy, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new GameScreen(game, 6, 1));
            }
            if(OverlapTester.pointInRectangle(southernBoy, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new GameScreen(game, 6, 2));
            }
            if(OverlapTester.pointInRectangle(westernBoy, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new GameScreen(game, 6, 3));
            }
            if(OverlapTester.pointInRectangle(northernBoy, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new GameScreen(game, 6, 4));
            }


            Rectangle selectButtonBound = new Rectangle(10, 480, SCREEN_WIDTH/2-32, 100);
            Rectangle optionsButtonBound = new Rectangle(SCREEN_WIDTH/2+12, 480, SCREEN_WIDTH/2-12, 100);
            if(OverlapTester.pointInRectangle(selectButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                game.setScreen(new GameScreen(game, 6, 1));
            } else if(OverlapTester.pointInRectangle(optionsButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                Gdx.input.vibrate(5);
                game.setScreen(new GameScreen(game, 6, 1));
            }
        }
    }

    @Override
    public void hide() {

    }

}
