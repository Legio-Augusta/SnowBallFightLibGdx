package wait4u.littlewing.paxbritan;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Ship {

	private float buffer = 500;
	public float damage=0;
	public float bulletSpeed = 0f;

	public Bullet(int id, Vector2 position, Vector2 facing) {
		super(id, position, facing);
	}

	@Override
	public void draw(Batch batch) {
		if(alive == false) return;
		if( !wait4u.littlewing.paxbritan.Targeting.onScreen(collisionCenter,buffer)) {
			alive = false;
		} else if(velocity.len()<=5) {
			alive = false;
			wait4u.littlewing.paxbritan.GameInstance.getInstance().explosionParticles.addTinyExplosion(collisionCenter);
		} else {		
			super.draw(batch);
		}
		
	}
}
