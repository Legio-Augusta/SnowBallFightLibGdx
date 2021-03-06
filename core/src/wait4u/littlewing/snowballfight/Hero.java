package wait4u.littlewing.snowballfight;

/**
 * Created by Admin on 11/13/2017.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Hero {

    public enum State {
        IDLE, WALKING, JUMPING, DYING, FIRING, FREEZE, HANGING
    }

    public int h_idx;
    public int h_timer;
    public int h_timer_p;
    public int snow_pw;
    public int real_snow_pw;

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
    private int max_hp = 106;
    // TODO private e_move_dir
    public int e_move_dir = 1; // Default e_move_dir = 1 to avoid init() and/or e_move_ai() call in original J2ME.
    public int snow_gap = 0;
    public int pw_up = 0;

    private Texture bobTexture;
    private Sprite sprite;
    // snow or stone item used in firing
    private Item item;

    public Hero(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        bounds = new Rectangle(0, 0, 0, 0);
    }
    public void update(Matrix3 delta) {
        position.add(velocity.cpy().mul(delta));
    }

    public Hero(Texture texture) {
        bobTexture = texture;
    }

    public Hero(Sprite sprite) {
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
    public int getMaxHp() {
        return this.max_hp;
    }

    public void loseHp(int damage) {
        this.hp -= damage;
    }
    public void setPower() {
        if (snow_pw < 22) {
            snow_pw += 1;
            real_snow_pw = (snow_pw / 3);
        }
    }

    public boolean isDead() {
        return (this.hp <= 0) ? true : false;
    }

    /*
    * Try reset/update enemy position when it hit bound. TODO use vector or another better solution.
    * */
    public void check_move_outof_bound(int leftBound, int rightBound, int bottomBound, int topBound) {
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

    public int hero_move(int paramInt1, int paramInt2, int paramInt3)
    {
        return 0;
    }
    public void check_hero(int paramInt1, int paramInt2) {

    }

    public void make_attack() {
        item.position.y = 12;
        item.position.x = position.x;
        item.position.y = (9 - real_snow_pw);
        if (real_snow_pw % 2 == 0) {
            item.top_y = (10 - real_snow_pw / 2);
        } else {
            item.top_y = (9 - real_snow_pw / 2);
        }
        snow_gap = 3;
        h_timer = 0;
        pw_up = 2;
//        MPlay(2);
    }

}