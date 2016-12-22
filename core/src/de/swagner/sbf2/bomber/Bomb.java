package de.swagner.sbf2.bomber;

import com.badlogic.gdx.math.Vector2;

import de.swagner.sbf2.Snow;

/**
 * Created by nickfarow on 21/10/2016.
 */
public class Bomb extends Snow {

    public Bomb(int id, Vector2 position, Vector2 facing) {
        super(id, position, facing);
        bulletSpeed = 150;
        this.velocity = new Vector2().set(facing).scl(bulletSpeed);
        damage = 300;

        this.setOrigin(this.getWidth()/2, this.getHeight()/2);
    }
}
