package com.littlewing.sbf.paxbritan;

import com.badlogic.gdx.Game;

import com.littlewing.sbf.paxbritan.mainmenu.MainMenu;

public class PaxBritannica extends Game {
	@Override 
	public void create () {
		setScreen(new MainMenu(this));
	}
}
