package de.swagner.sbf2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.swagner.sbf.bomber.Bomb;
import de.swagner.sbf.frigate.Missile;
import de.swagner.sbf.particlesystem.ExplosionParticleEmitter;
import de.swagner.sbf.particlesystem.SparkParticleEmitter;

/**
 * Created by nickfarow on 21/10/2016.
 */
public class GameInstance {

    public boolean debugMode = false;

    public Array<Ship> fighters = new Array<Ship>();
    public Array<Ship> factorys = new Array<Ship>();
    public Array<Ship> bombers = new Array<Ship>();
    public Array<Ship> frigates = new Array<Ship>();

    public Array<Bullet> bullets = new Array<Bullet>;

    public de.swagner.sbf.particlesystem.BubbleParticleEmitter bubbleParticles = new de.swagner.sbf.particlesystem.BubbleParticleEmitter();
    public de.swagner.sbf.particlesystem.BigBubbleParticleEmitter bigBubbleParticles = new de.swagner.sbf.particlesystem.BigBubbleParticleEmitter();

    public SparkParticleEmitter sparkParticles = new SparkParticleEmitter();
    public ExplosionParticleEmitter explosionParticles = new ExplosionParticleEmitter();
    public int difficultyConfig = 0;
    public int factoryHealthConfig = 0;
    public int antiAliasConfig = 0;

    public static GameInstance instance;

    public static GameInstance getInstance() {
        if (instance == null) {
            instance = new GameInstance();
        }
        return instance;
    }

    public void resetGame() {
        fighters.clear();
        factorys.clear();
        bombers.clear();
        frigates.clear();
        bullets.clear();

        bubbleParticles.dispose();
        bigBubbleParticles.dispose();
        sparkParticles.dispose();
        explosionParticles.dispose();

        bubbleParticles = new de.swagner.sbf.particlesystem.BubbleParticleEmitter();
        bigBubbleParticles = new de.swagner.sbf.particlesystem.BigBubbleParticleEmitter();

        sparkParticles = new SparkParticleEmitter();
        explosionParticles = new ExplosionParticleEmitter();

        Preferences prefs = Gdx.app.getPreferences("paxbritannica");
        GameInstance.getInstance().difficultyConfig = prefs.getInteger("difficulty", 0);
        GameInstance.getInstance().factoryHealthConfig = prefs.getInteger("factoryHealth", 0);
        GameInstance.getInstance().antiAliasConfig = prefs.getInteger("antiAliasConfig", 0);
    }

    public void bulletHit(Ship ship, Bullet bullet) {
        bullet.facing.nor();
        float offset = 0;
        if(ship instanceof de.swagner.sbf.factory.FactoryProduction) offset = 50;
        if(ship instanceof de.swagner.sbf.frigate.Frigate) offset = 20;
        if(ship instanceof de.swagner.sbf.bomber.Bomber) offset = 10;
        if(ship instanceof de.swagner.sbf.fighter.Fighter) offset = 10;
        Vector2 pos = new Vector2().set(bullet.collisionCenter.x + (offset * bullet.facing.x), bullet.collisionCenter.y + (offset * bullet.facing.y));

        // ugh . . .
        Vector2 bullet_vel = new Vector2().set(bullet.velocity);

        Vector2 bullet_dir;
        if (bullet_vel.dot(bullet_vel) == 0) {
            bullet_dir = new Vector2();
        } else {
            bullet_dir = bullet_vel.nor();
        }
        Vector2 vel = new Vector2(bullet_dir.x * 1.5f, bullet_dir.y * 1.5f);

        if (bullet instanceof de.swagner.sbf.fighter.Laser) {
            laser_hit(pos, vel);
        } else if (bullet instanceof Bomb) {
            explosionParticles.addMediumExplosion(((Bomb) bullet).position);
        } else if (bullet instanceof de.swagner.sbf.frigate.Missile) {
            explosionParticles.addTinyExplosion(((Missile) bullet).position);
        }
    }

    public void laser_hit(Vector2 pos, Vector2 vel) {
        sparkParticles.addLaserExplosion(pos, vel);
    }

    public void explode(Ship ship) {
        explode(ship, ship.collisionCenter);
    }

    public void explode(Ship ship, Vector2 pos) {

        if (ship instanceof de.swagner.sbf.factory.FactoryProduction) {
            explosionParticles.addBigExplosion(pos);
        } else if (ship instanceof de.swagner.sbf.bomber.Bomber) {
            explosionParticles.addMediumExplosion(pos);
        } else if (ship instanceof de.swagner.sbf.frigate.Frigate) {
            explosionParticles.addMediumExplosion(pos);
        }else {
            explosionParticles.addSmallExplosion(pos);
        }
    }
}
