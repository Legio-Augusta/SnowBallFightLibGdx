package wait4u.littlewing.paxbritan;

import com.badlogic.gdx.Game;

import wait4u.littlewing.paxbritan.mainmenu.MainMenu;

public class PaxBritannica extends Game {
	@Override 
	public void create () {
		setScreen(new MainMenu(this));
	}
}
