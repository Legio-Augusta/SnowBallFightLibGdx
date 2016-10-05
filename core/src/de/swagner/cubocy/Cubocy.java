
package de.swagner.cubocy;

import de.swagner.cubocy.screens.MainMenu;
import com.badlogic.gdx.Game;

public class Cubocy extends Game {
	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}
}
