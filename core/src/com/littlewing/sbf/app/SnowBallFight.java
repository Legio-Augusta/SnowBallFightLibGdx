package com.littlewing.sbf.app;

import com.badlogic.gdx.Game;
import com.littlewing.sbf.app.screens.LogoScreen;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        // TODO handle global vars in GameScreen. setScreen as repaint J2ME
        // Can put global var here ?
        setScreen(new LogoScreen(this));
//        setScreen(new GameScreen(new SnowBallFight()));
//        setScreen(new GameScreen(this));
//        setScreen(new VictoryScreen(this));
    }

}