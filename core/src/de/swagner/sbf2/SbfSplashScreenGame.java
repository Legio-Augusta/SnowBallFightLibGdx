package de.swagner.sbf2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by nickfarow on 03/10/2016.
 */
public class SbfSplashScreenGame extends Game {
    private static long SPLASH_MINIMUM_MILLIS = 2000L;

    public SbfSplashScreenGame() {
        super();
    }

    public void create() {
        setScreen(new MainGameScreen());

        final long splash_start_time = System.currentTimeMillis();
        new Thread(new Runnable() {
            public void run() {

                Gdx.app.postRunnable(new Runnable() {
                    public void run() {
                        long splash_elapsed_time = System.currentTimeMillis() - splash_start_time;
                        if (splash_elapsed_time > SbfSplashScreenGame.SPLASH_MINIMUM_MILLIS) {
                            Timer.schedule(
                                    new Timer.Task() {
                                        public void run() {
                                            SbfSplashScreenGame.this.setScreen(new MainMenuScreen());
                                        }
                                    }, (float)(SbfSplashScreenGame.SPLASH_MINIMUM_MILLIS -splash_elapsed_time) / 1000f);
                        } else {
                            SbfSplashScreenGame.this.setScreen(new MainMenuScreen());
                        }
                    }
                });
            }
        }).start();
    }

    public void dispose() {
        // Dispose all resources
        getScreen().dispose();
        Gdx.app.exit();
    }
}
