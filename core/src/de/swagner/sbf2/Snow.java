package de.swagner.sbf2;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nickfarow on 21/10/2016.
 */

public class Snow extends Item {

    private float buffer = 500;
    public float damage=0;
    public float bulletSpeed = 0f;

    public Snow(int id, Vector2 position, Vector2 facing) {
        super(id, position, facing);
    }

    @Override
    public void draw(Batch batch) {
        if(alive == false) return;
    }
}
