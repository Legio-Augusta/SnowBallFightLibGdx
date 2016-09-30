package de.swagner.sbf.particlesystem;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SparkParticleEmitter extends ParticleEmitter {

	Vector2 particleVelocity = new Vector2();
	
	public SparkParticleEmitter() {
		super();
				
		life = 1.0f;
		damping =5.95f;

		set(de.swagner.sbf.Resources.getInstance().spark);
	}

	public void addLaserExplosion(Vector2 position, Vector2 velocity) {
		for (int i = 1; i <= 10; ++i) {
			random.set(MathUtils.cos((float) ((MathUtils.random() * MathUtils.PI * 2f) * Math.sqrt(MathUtils.random()))),
					(float) (MathUtils.sin(MathUtils.random() * MathUtils.PI * 2f) * Math.sqrt(MathUtils.random())));

			particleVelocity.set(-velocity.x + random.x, -velocity.y + random.y);
			addParticle(position, particleVelocity, 1f, 1);
		}
	}

}
