package de.swagner.sbf2;

import com.badlogic.gdx.Game;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        setScreen(new de.swagner.sbf2.screens.Screen1(new SnowBallFight()));
//        setScreen(new de.swagner.sbf2.mainmenu.MainMenu(this));
    }

}