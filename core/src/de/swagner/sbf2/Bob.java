package de.swagner.sbf2;

/**
 * Created by nickfarow on 17/10/2016.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bob {

    public enum State {
        IDLE, WALKING, JUMPING, DYING
    }

    static final float SPEED = 2f; // unit per second
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // half a unit

    public Vector2  position = new Vector2();
    Vector2  acceleration = new Vector2();
    Vector2  velocity = new Vector2();
    Rectangle  bounds = new Rectangle();
    public State  state = State.IDLE;
    public boolean  facingLeft = true;

    private Texture bobTexture;

    public Bob(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;
    }
    public void update(Matrix3 delta) {
        position.add(velocity.cpy().mul(delta));
    }

    public Bob(Texture texture) {
        bobTexture = texture;
    }

    public void setBobTexture(Texture texture) {
        bobTexture = texture;
    }

    public void setBobTexture(String image) {
        bobTexture = new Texture(image);;
    }

    public Texture getBobTexture() {
        return bobTexture;
    }
}