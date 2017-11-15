
package wait4u.littlewing.cubocy;

import wait4u.littlewing.cubocy.screens.MainMenu;
import com.badlogic.gdx.Game;

public class Cubocy extends Game {
	@Override
	public void create () {
		setScreen(new MainMenu(this));
	}
}
