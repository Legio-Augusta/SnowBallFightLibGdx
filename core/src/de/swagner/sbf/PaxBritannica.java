package de.swagner.sbf;

import com.badlogic.gdx.Game;

public class PaxBritannica extends Game {
	@Override 
	public void create () {
		setScreen(new de.swagner.sbf.mainmenu.MainMenu(this));
	}
}
