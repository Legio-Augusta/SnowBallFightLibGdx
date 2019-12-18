package com.littlewing.sbf.paxbritan.bomber;

import com.badlogic.gdx.math.Vector2;

import com.littlewing.sbf.paxbritan.Bullet;
import com.littlewing.sbf.paxbritan.Resources;

public class Bomb extends Bullet {

	public Bomb(int id, Vector2 position, Vector2 facing) {
		super(id, position, facing);
		bulletSpeed = 150;
		this.velocity = new Vector2().set(facing).scl(bulletSpeed);
		damage = 300;
		
		this.set(Resources.getInstance().bomb);
		this.setOrigin(this.getWidth()/2, this.getHeight()/2);
	}
}
