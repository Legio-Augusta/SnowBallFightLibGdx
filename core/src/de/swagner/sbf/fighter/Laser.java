package de.swagner.sbf.fighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Laser extends de.swagner.sbf.Bullet {

	float delta;
	
	public Laser(int id, Vector2 position, Vector2 facing) {
		super(id, position, facing);
		
		bulletSpeed = 1000;
		damage = 10;
		
		this.velocity = new Vector2().set(facing).scl(bulletSpeed);
		
		this.set(de.swagner.sbf.Resources.getInstance().laser);
		this.setOrigin(0,0);
	}
	
	@Override
	public void draw(Batch batch) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		velocity.scl( (float) Math.pow(1.03f, delta * 30.f));
		super.draw(batch);
	}
	

}
