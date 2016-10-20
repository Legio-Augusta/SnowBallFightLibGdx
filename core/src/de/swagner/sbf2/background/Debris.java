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
    // TODO load only bg as sbf
    // Apply random for SBF stage

    private float SPEED = 5.0f;
    private  float LIFETIME = MathUtils.random(8, 12);
    private float FADE_TIME = 2;

    private float random_direction = MathUtils.random(-360, 360);
    private float random_scale = MathUtils.random() * 0.75f + 0.5f;
    private  float random_speed = (MathUtils.random() * 2f) - 1f;
    private float random_opacity = MathUtils.random() * 0.35f + 0.6f;

    private Vector2 position = new Vector2();
    private Vector2 facing = new Vector2(1, 0);

    public boolean alive = true;

    private float since_alive = 0;

    private float delta;

    public Debris(Vector2 position) {
        super();
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }

    public void reset() {

    }
}

