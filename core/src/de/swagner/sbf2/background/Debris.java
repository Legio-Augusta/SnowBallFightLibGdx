package de.swagner.sbf2.background;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by nickfarow on 18/10/2016.
 */
public class Debris extends Sprite {

    private float SPEED = 5.0f;
    private  float LIFETIME = MathUtils.random(8, 12);
}

