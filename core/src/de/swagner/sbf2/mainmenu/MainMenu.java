package de.swagner.sbf2.mainmenu;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.sun.org.apache.regexp.internal.RE;

import de.swagner.sbf2.DefaultScreen;
import de.swagner.sbf.GameInstance;
import de.swagner.sbf.GameScreen;
import de.swagner.sbf.Resources;
import de.swagner.sbf.background.BackgroundFXRenderer;
import de.swagner.sbf.help.Help;
import de.swagner.sbf.mainmenu.FactorySelector;
import de.swagner.sbf.settings.Settings;

/**
 * Created by nickfarow on 18/10/2016.
 */
public class MainMenu extends DefaultScreen implements  InputProcessor {
    Sprite title;
    Sprite credits;
    Sprite settings;
    FactorySelector p1;
    FactorySelector p2;
    FactorySelector p3;
    FactorySelector p4;

    BoundingBox collisionHelp = new BoundingBox();
    BoundingBox collisionMusic = new BoundingBox();
    BoundingBox collisionSetting = new BoundingBox();

    BackgroundFXRenderer backgroundFX = new BackgroundFXRenderer();
    Sprite blackFade;

    SpriteBatch titleBatch;
    SpriteBatch fadeBatch;

    float time = 0;
    float fade = 1.0f;

    int idP1 = -1;
    int idP2 = -1;
    int cnt = 0;
    int oldCnt = 0;
    int changeToScreen = -1;

    Ray collitionRay;

    private int width = 800;
    private  int height = 480;

    public MainMenu(Game game) {
        super(game);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {
        Resources.getInstance().reInit();

        GameInstance.getInstance().resetGame();

        changeToScreen = -1;

        backgroundFX = new BackgroundFXRenderer();

        title = Resources.getInstance().title;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        time += delta;

        if (time < 1f)
            return;

        backgroundFX.render();

//        titleBatch.begin();
//        titleBatch.end();



    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        return false;
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
