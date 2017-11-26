package wait4u.littlewing.snowballfight;

import com.badlogic.gdx.Game;
import wait4u.littlewing.snowballfight.screens.Screen1;
import wait4u.littlewing.snowballfight.screens.VictoryScreen;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class SnowBallFight extends Game {

    public void create () {
        setScreen(new Screen1(new SnowBallFight()));
        setScreen(new VictoryScreen(this));
    }

}