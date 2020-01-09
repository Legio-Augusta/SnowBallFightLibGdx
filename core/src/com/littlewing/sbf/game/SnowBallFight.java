package com.littlewing.sbf.game;

import com.badlogic.gdx.Game;
import com.littlewing.sbf.game.screens.GameScreen;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        setScreen(new GameScreen(this));
    }

}