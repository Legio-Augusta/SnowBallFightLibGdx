package de.swagner.sbf2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {
    OrthographicCamera camera;
    SpriteBatch batch;
    private Stage stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;

    public void create () {
        /*
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/gui/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/gui/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("data/gui/block.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);

        blockSpeed = 5;
        */

        setScreen(new de.swagner.sbf2.screens.Screen1(new SnowBallFight()));
//        setScreen(new de.swagner.sbf2.mainmenu.MainMenu(this));
    }
/*
    public void render() {
        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //Move blockSprite with TouchPad
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*blockSpeed);

        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
*/
}