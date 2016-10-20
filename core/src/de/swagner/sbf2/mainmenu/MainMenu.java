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
import de.swagner.sbf2.Resources;
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

    OrthographicCamera cam;

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

        p1 = new FactorySelector(new Vector2(055f, 150f), 1);
        p2 = new FactorySelector(new Vector2(180f, 150f), 1);

        titleBatch = new SpriteBatch();
        titleBatch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
        fadeBatch = new SpriteBatch();
        fadeBatch.getProjectionMatrix().setToOrtho2D(0, 0, 2, 2);


    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        if (width == 480 && height == 320) {
            cam = new OrthographicCamera(700, 466);
            this.width = 700;
            this.height = 466;
        } else if (width == 320 && height == 240) {
            cam = new OrthographicCamera(700, 525);
            this.width = 700;
            this.height = 525;
        } else if (width == 400 && height == 240) {
            cam = new OrthographicCamera(800, 480);
            this.width = 800;
            this.height = 480;
        } else if (width == 432 && height == 240) {
            cam = new OrthographicCamera(700, 389);
            this.width = 700;
            this.height = 389;
        } else if (width == 960 && height == 640) {
            cam = new OrthographicCamera(800, 533);
            this.width = 800;
            this.height = 533;
        }  else if (width == 1366 && height == 768) {
            cam = new OrthographicCamera(1280, 720);
            this.width = 1280;
            this.height = 720;
        } else if (width == 1366 && height == 720) {
            cam = new OrthographicCamera(1280, 675);
            this.width = 1280;
            this.height = 675;
        } else if (width == 1536 && height == 1152) {
            cam = new OrthographicCamera(1366, 1024);
            this.width = 1366;
            this.height = 1024;
        } else if (width == 1920 && height == 1152) {
            cam = new OrthographicCamera(1366, 854);
            this.width = 1366;
            this.height = 854;
        } else if (width == 1920 && height == 1200) {
            cam = new OrthographicCamera(1366, 800);
            this.width = 1280;
            this.height = 800;
        } else if (width > 1280) {
            cam = new OrthographicCamera(1280, 768);
            this.width = 1280;
            this.height = 768;
        } else if (width < 800) {
            cam = new OrthographicCamera(800, 480);
            this.width = 800;
            this.height = 480;
        } else {
            cam = new OrthographicCamera(width, height);
        }
        cam.position.x = 400;
        cam.position.y = 240;
        cam.update();
        backgroundFX.resize(width, height);
        titleBatch.getProjectionMatrix().set(cam.combined);

    }

    @Override
    public void render(float delta) {
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());

        time += delta;

        if (time < 1f)
            return;

        backgroundFX.render();

        titleBatch.begin();

        titleBatch.draw(title, 85f, 320f, 0, 0, 512, 64f, 1.24f, 1.24f, 0);
        p1.draw(titleBatch);

        titleBatch.end();

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
