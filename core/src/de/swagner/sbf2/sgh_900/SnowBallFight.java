package de.swagner.sbf2.sgh_900;

/**
 * Created by nickfarow on 21/10/2016.
 */

import de.swagner.sbf2.sgh_900.microedition.lcdui.Display;
import de.swagner.sbf2.sgh_900.microedition.midlet.MIDlet;

public class SnowBallFight extends MIDlet
{
    public void startApp()
    {
        Display localDisplay = Display.getDisplay(this);
        localDisplay.setCurrent(new GameScreen(this));
    }

    public void destroyApp(boolean paramBoolean) {}

    public void pauseApp() {}
}
