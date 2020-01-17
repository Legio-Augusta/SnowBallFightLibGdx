package com.littlewing.sbf.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import com.littlewing.sbf.app.OverlapTester;

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
    int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    int SCREEN_WIDTH = Gdx.graphics.getWidth();
    float hd_ratio = (float)SCREEN_WIDTH/(float)1080;

    public NewGameMenuScreen(Game game) {
        super(game);
        touchPoint = new Vector3();
    }

    @Override
    public void show() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6*hd_ratio);

        imgMM = new Texture("data/sprites/mm.png");
        title = new Texture("data/sprites/title.png");
        imgSl = new Texture("data/sprites/sl.png");
        imgBk = new Texture("data/sprites/bk.png");
        imgCh = new Texture("data/sprites/check.png");
        samsung_blue = new Texture("data/sprites/samsung_blue.png");
        batch = new SpriteBatch();
    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT);
//        batch.draw(imgMM, 0, 480);
//        font.draw(batch, "1. New Game", 110, 1300); // orig: 13 27 20-anchor
//        font.draw(batch, "2. Saved Game", 110, 1160); // orig: 13, 44, 20
//        batch.draw(imgSl, SCREEN_WIDTH-imgSl.getWidth(), 480);
//        batch.draw(imgBk, 15, 480);
//        batch.draw(imgCh, 36, 1230); // this.m_mode * 17 + 14

        drawFitScreen(imgMM, 0, 480*hd_ratio, hd_ratio);
        font.draw(batch, "1. New Game", 110*hd_ratio, 1300*hd_ratio);
        font.draw(batch, "2. Saved Game", 110*hd_ratio, 1160*hd_ratio);
        drawFitScreen(imgSl, SCREEN_WIDTH-(float)imgSl.getWidth()*hd_ratio, 480*hd_ratio, hd_ratio);
        drawFitScreen(imgBk, 15*hd_ratio, 480*hd_ratio, hd_ratio);
        drawFitScreen(imgCh, 36*hd_ratio, 1230*hd_ratio, hd_ratio);

        batch.end();

        if (Gdx.input.justTouched()) {
            // Can not use font getBound(), so hard code position of text Bitmap.
            touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            Rectangle newGameTextBound = new Rectangle(110*hd_ratio, 1140*hd_ratio, SCREEN_WIDTH-200*hd_ratio, 160*hd_ratio);
            Rectangle savedGameTextBound = new Rectangle(110*hd_ratio, 1020*hd_ratio, SCREEN_WIDTH-200*hd_ratio, 140*hd_ratio);
            Rectangle selectButtonBound = new Rectangle(SCREEN_WIDTH-imgSl.getWidth()*hd_ratio, 480*hd_ratio, imgSl.getWidth()*hd_ratio, imgSl.getHeight()*hd_ratio);
            Rectangle backButtonBound = new Rectangle(15*hd_ratio, 480*hd_ratio, imgBk.getWidth()*hd_ratio, imgBk.getHeight()*hd_ratio);

            //Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ newGameTextBound.toString() + " saved "+ savedGameTextBound.toString());
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
    public void drawFitScreen(Texture texture, float x, float y, float hd_ratio) {
        batch.draw(texture, x, y, 0, 0, texture.getWidth(), texture.getHeight(), hd_ratio, hd_ratio, 0, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

}
