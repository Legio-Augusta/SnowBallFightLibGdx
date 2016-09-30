package de.swagner.sbf.bomber;

import com.badlogic.gdx.math.Vector2;

public class Bomb extends de.swagner.sbf.Bullet {

	public Bomb(int id, Vector2 position, Vector2 facing) {
		super(id, position, facing);
		bulletSpeed = 150;
		this.velocity = new Vector2().set(facing).scl(bulletSpeed);
		damage = 300;
		
		this.set(de.swagner.sbf.Resources.getInstance().bomb);
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}
}
