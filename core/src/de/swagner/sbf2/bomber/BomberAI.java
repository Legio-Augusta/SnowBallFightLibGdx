package de.swagner.sbf2.bomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import de.swagner.sbf2.Item;

/**
 * Created by nickfarow on 21/10/2016.
 */
public class BomberAI {
    private float APPROACH_DISTANCE = 210;
    private float COOLDOWN_DURATION = 0.6f;
    private float MAX_SHOTS = 4;

    private float cooldown_timer = 0;
    private float shots_counter = MAX_SHOTS;
    private int approach_sign = 1;
    float delta;

    // 0 = approach
    // 1 = turn
    // 2 = shoot
    // 3 = move_away
    private int state = 0;

    //recylce
    Vector2 target_direction = new Vector2();

    public Item target;

    private Bomber bomber;

    public BomberAI(Bomber bomber) {
        this.bomber = bomber;
    }

    public void retarget() {

    }

    public void reviseApproach() {
        if (MathUtils.random() < 0.5) {
            approach_sign = 1;
        } else {
            approach_sign = -1;
        }
    }

    public void update() {

    }
}
