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

import de.swagner.sbf2.DefaultScreen;
import de.swagner.sbf.GameInstance;
import de.swagner.sbf.GameScreen;
import de.swagner.sbf2.Resources;
import de.swagner.sbf.background.BackgroundFXRenderer;
import de.swagner.sbf.help.Help;
import de.swagner.sbf2.mainmenu.FactorySelector;
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

    Sprite help;

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
    int GAME_STATE_START = -1;

    Ray collisionRay;

    private int width = 800;
    private  int height = 480;

    public MainMenu(Game game) {
        super(game);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show()
    {
        Resources.getInstance().reInit();

        GameInstance.getInstance().resetGame();

        changeToScreen = -1;

        backgroundFX = new BackgroundFXRenderer();

        title = Resources.getInstance().title;

        help = Resources.getInstance().help;
        help.setPosition(75, 10);
        help.setColor(1,1,1,0.5f);
        collisionHelp.set(new Vector3(help.getVertices()[0], help.getVertices()[1], -10),new Vector3(help.getVertices()[10], help.getVertices()[11], 10));

        p1 = new FactorySelector(new Vector2(055f, 150f), 1);
        p2 = new FactorySelector(new Vector2(180f, 150f), 2);
        p3 = new FactorySelector(new Vector2(305f, 150f), 3);
        p4 = new FactorySelector(new Vector2(430f, 150f), 4);

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

        help.draw(titleBatch);

        titleBatch.draw(title, 85f, 320f, 0, 0, 512, 64f, 1.24f, 1.24f, 0);
        p1.draw(titleBatch);
        p2.draw(titleBatch);
        p3.draw(titleBatch);
        p4.draw(titleBatch);

        cnt = 0;
        if (p1.playerSelect || p1.cpuSelect)
            cnt++;
        if (p2.playerSelect || p2.cpuSelect)
            cnt++;
        if (p3.playerSelect || p3.cpuSelect)
            cnt++;
        if (p4.playerSelect || p4.cpuSelect)
            cnt++;
        if (cnt > 1) {
            // draw Countdown
        }
        if ((p1.picked && !(p1.playerSelect || p1.cpuSelect)) || (p2.picked && !(p2.playerSelect || p2.cpuSelect)) || (p3.picked && !(p3.playerSelect || p3.cpuSelect)) || (p4.picked && !(p4.playerSelect || p4.cpuSelect)) ) {
            // reset Countdown
        }

        titleBatch.end();

        if( (GAME_STATE_START > 0) || (changeToScreen == 2) ) {
            Array<Integer> playerList = new Array<Integer>();
            if(p1.playerSelect == true) {
                playerList.add(1);
            }
            if(p2.playerSelect == true) {
                playerList.add(2);
            }
            if(p3.playerSelect == true) {
                playerList.add(3);
            }
            if(p4.playerSelect == true) {
                playerList.add(4);
            }

            Array<Integer> cpuList = new Array<Integer>();
            if(p1.cpuSelect == true) {
                cpuList.add(1);
            }
            if(p2.cpuSelect == true) {
                cpuList.add(2);
            }
            if(p3.cpuSelect == true) {
                cpuList.add(3);
            }
            if(p4.cpuSelect == true) {
                cpuList.add(4);
            }

            game.setScreen(new GameScreen(game, playerList, cpuList));
        }


    }

    @Override
    public void hide() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE) {
            boolean exit = true;
            if(p1.picked) {
                p1.reset();
                exit = false;
            }
            if(p2.picked) {
                p2.reset();
                exit = false;
            }
            if(p3.picked) {
                p3.reset();
                exit = false;
            }
            if(p4.picked) {
                p4.reset();
                exit = false;
            }

            if(exit) {
                if(!(Gdx.app.getType() == ApplicationType.Applet)) {
                    Gdx.app.exit();
                }
            }
        }

        if(keycode == Input.Keys.A) {
            if (!p1.picked) {
                p1.picked = true;
            } else {
                p1.playerSelect = true;
                p1.cpuSelect = false;
            }
        }
        if(keycode == Input.Keys.F) {
            if (!p2.picked) {
                p2.picked = true;
            }  else {
                p2.playerSelect = true;
                p2.cpuSelect = false;
            }
        }
        if(keycode == Input.Keys.H) {
            if (!p3.picked) {
                p3.picked = true;
            }  else {
                p3.playerSelect = true;
                p3.cpuSelect = false;
            }
        }
        if(keycode == Input.Keys.L) {
            if (!p4.picked) {
                p4.picked = true;
            }  else {
                p4.playerSelect = true;
                p4.cpuSelect = false;
            }
        }

        if(keycode == Input.Keys.M) {
            if (cnt >= 1)
                return false;
            Preferences prefs = Gdx.app.getPreferences("paxbritannica");
            prefs.flush();
        }

        if(keycode == Input.Keys.F1) {
            if (cnt >= 1)
                return false;
            changeToScreen = 0;
        }

        if(keycode == Input.Keys.S) {
            if (cnt >= 1)
                return false;
            changeToScreen = 1;
        }

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

        collisionRay = cam.getPickRay(x, y);

        if (cnt > 4)
            return false;

        // check if ship is activated
        if (Intersector.intersectRayBoundsFast(collisionRay, p1.collision) && !p1.picked) {
            p1.picked = true;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p2.collision) && !p2.picked) {
            p2.picked = true;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p3.collision) && !p3.picked) {
            p3.picked = true;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p4.collision) && !p4.picked) {
            p4.picked = true;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p1.collisionPlayerSelect) && p1.picked && !p1.cpuSelect) {
            p1.playerSelect = true;
            p1.cpuSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p2.collisionPlayerSelect) && p2.picked && !p2.cpuSelect) {
            p2.playerSelect = true;
            p2.cpuSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p3.collisionPlayerSelect) && p3.picked && !p3.cpuSelect) {
            p3.playerSelect = true;
            p3.cpuSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p4.collisionPlayerSelect) && p4.picked && !p4.cpuSelect) {
            p4.playerSelect = true;
            p4.cpuSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p1.collisionCPUSelect) && p1.picked && !p1.playerSelect) {
            p1.cpuSelect = true;
            p1.playerSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p2.collisionCPUSelect) && p2.picked && !p2.playerSelect) {
            p2.cpuSelect = true;
            p2.playerSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p3.collisionCPUSelect) && p3.picked && !p3.playerSelect) {
            p3.cpuSelect = true;
            p3.playerSelect = false;
        } else if (Intersector.intersectRayBoundsFast(collisionRay, p4.collisionCPUSelect) && p4.picked && !p4.playerSelect) {
            p4.cpuSelect = true;
            p4.playerSelect = false;

            GAME_STATE_START = 1;
        }

//        if (Intersector.intersectRayBoundsFast(collitionRay, collisionMusic)) {
            // TODO music
//        }

        if (Intersector.intersectRayBoundsFast(collisionRay, collisionHelp)) {
            changeToScreen = 2;
        }

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
