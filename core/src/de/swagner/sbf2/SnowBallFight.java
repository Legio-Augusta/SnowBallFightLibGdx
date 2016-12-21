package de.swagner.sbf2;

import com.badlogic.gdx.Game;
import de.swagner.sbf2.screens.Screen1;
import de.swagner.sbf2.screens.VictoryScreen;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        setScreen(new Screen1(new SnowBallFight()));
        setScreen(new VictoryScreen(this));
    }

}