package wait4u.littlewing.paxbritan.bomber;

import com.badlogic.gdx.math.Vector2;

import wait4u.littlewing.paxbritan.Bullet;
import wait4u.littlewing.paxbritan.Resources;

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
