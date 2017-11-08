package de.swagner.sbf2;

/**
 * Created by nickfarow on 17/10/2016.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bob {

    public enum State {
        IDLE, WALKING, JUMPING, DYING, FIRING, FREEZE, HANGING
    }

    static final float SPEED = 2f; // unit per second
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // half a unit

    public Vector2  position = new Vector2();
    Vector2  acceleration = new Vector2();
    Vector2  velocity = new Vector2();
    private Rectangle  bounds = new Rectangle();
    public State  state = State.IDLE;
    public boolean  facingLeft = true;

    private int hp = 12; // 120
    // TODO private e_move_dir
    public int e_move_dir = 1; // Default e_move_dir = 1 to avoid init() and/or e_move_ai() call in original J2ME.
    private Texture bobTexture;
    private Sprite sprite;
    // snow or stone item used in firing
    private Item item;

    public Bob(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        bounds = new Rectangle(0, 0, 0, 0);
    }
    public void update(Matrix3 delta) {
        position.add(velocity.cpy().mul(delta));
    }

    public Bob(Texture texture) {
        bobTexture = texture;
    }

    public Bob(Sprite sprite) {
        this.sprite = new Sprite(sprite);
    }
    public void setBobTexture(Texture texture) {
        bobTexture = texture;
    }

    public void setBobTexture(String image) {
        bobTexture = new Texture(image);
    }

    public Texture getBobTexture() {
        return bobTexture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    public Item getItem() {
        return this.item;
    }

    public void setBound(Rectangle rect) {
        this.bounds = rect;
    }
    public Rectangle getBound() {
        return this.bounds;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getHp() {
        return this.hp;
    }

    public void loseHp(int damage) {
        this.hp -= damage;
    }

    public boolean isDead() {
        return (this.hp <= 0) ? true : false;
    }

}