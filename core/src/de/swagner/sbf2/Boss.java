package de.swagner.sbf2;

/**
 * Created by Admin on 11/10/2017.
 * TODO inherite Bob or refactoring for best practice.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Boss {

    public enum State {
        IDLE, WALKING, JUMPING, DYING, FIRING, FREEZE, HANGING
    }

    static final float SPEED = 2f; // unit per second
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // half a unit

    public Vector2 position = new Vector2();
    Vector2  acceleration = new Vector2();
    Vector2  velocity = new Vector2();
    private Rectangle bounds = new Rectangle();
    public State  state = State.IDLE;
    public boolean  facingLeft = true;

    private int hp = 12; // 120
    // TODO private e_move_dir
    public int e_boss_move_dir = 1; // Default e_move_dir = 1 to avoid init() and/or e_move_ai() call in original J2ME.
    private Texture bobTexture;
    private Sprite sprite;
    // snow or stone item used in firing
    private Item item;

    public Boss(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        bounds = new Rectangle(0, 0, 0, 0);
    }
    public void update(Matrix3 delta) {
        position.add(velocity.cpy().mul(delta));
    }

    public Boss(Texture texture) {
        bobTexture = texture;
    }

    public Boss(Sprite sprite) {
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

    public void e_move() {

    }
    public void e_move_ai() {

    }
    /*
    * Try reset/update enemy position when it hit bound. TODO use vector or another better solution.
    * */
    public void check_move_outof_bound(int leftBound, int rightBound, int bottomBound, int topBound) {
        Random r = new Random();

        if(this.position.x >= rightBound) {
            this.position.x = 12 + get_random(12);
        }
        if(this.position.x <= leftBound) {
            this.position.x = get_random(4);
        }
        if(this.position.y >= topBound) {
            this.position.y = 2 + get_random(4) + bottomBound;
        }
        if(this.position.y <= bottomBound) {
            this.position.y = get_random(4) + bottomBound;
        }
    }

    public void boss_move()
    {
        if ((this.e_boss_move_dir >= 1) && (this.e_boss_move_dir < 8))
        {
            this.e_boss_move_dir += 1;
            if (this.e_boss_move_dir == 8) {
                this.e_boss_move_dir = 100;
            }
        }
        else if ((this.e_boss_move_dir >= 21) && (this.e_boss_move_dir < 31))
        {
            this.e_boss_move_dir += 1;
            if (((int)this.position.x != 2) && (this.e_boss_move_dir % 2 == 0)) {
                this.position.x -= 1;
            }
            if (this.e_boss_move_dir == 31) {
                this.e_boss_move_dir = 100;
            }
        }
        else if ((this.e_boss_move_dir > -31) && (this.e_boss_move_dir <= -21))
        {
            this.e_boss_move_dir -= 1;
            if (((int)this.position.x != 22) && (this.e_boss_move_dir % 2 == 0)) {
                this.position.x += 1;
            }
            if (this.e_boss_move_dir == -31) {
                this.e_boss_move_dir = 100;
            }
        }
    }

    public void boss_move_ai()
    {
        if ((int)this.position.x == 2)
        {
            this.e_boss_move_dir = -21;
        }
        else if ((int)this.position.x == 22)
        {
            this.e_boss_move_dir = 21;
        }
        else
        {
            int i = get_random(6);
            if ((i == 0) || (i == 1)) {
                this.e_boss_move_dir = 21;
            } else if ((i == 2) || (i == 3)) {
                this.e_boss_move_dir = -21;
            } else {
                this.e_boss_move_dir = 1;
            }
        }
    }

    public int get_random(int paramInt)
    {
        Random rnd = new Random();
        int i = rnd.nextInt() % paramInt;
        if (i < 0) {
            i = -i;
        }
        return i;
    }

    public int get_random1(int paramInt)
    {
        Random rnd = new Random();
        int i = rnd.nextInt() % paramInt;
        if (i == 0) {
            i = -5;
        }
        return i;
    }

}