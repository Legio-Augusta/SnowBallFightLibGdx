package de.swagner.sbf2;

/**
 * Created by nickfarow on 17/10/2016.
 */

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Enemy {

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

    public Enemy(Vector2 position) {
        this.position = position;
        this.bounds.height = SIZE;
        bounds = new Rectangle(0, 0, 0, 0);
    }
    public void update(Matrix3 delta) {
        position.add(velocity.cpy().mul(delta));
    }

    public Enemy(Texture texture) {
        bobTexture = texture;
    }

    public Enemy(Sprite sprite) {
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

    /*
     * Try ony huey instead of array
     * */
    public void e_move() {
        int i = this.e_move_dir;
        if ((i >= 1) && (i < 8))
        {
            i++;
            if (i == 8) {
                i = 100;
            }
        }
        else if ((i >= 21) && (i < 31))
        {
            i++;
            if ((this.position.x != 2) && (i % 3 == 0)) {
                this.position.x -= 1;
            }
            if (i == 31) {
                i = 100;
            }
        }
        else if ((i > -31) && (i <= -21))
        {
            i--;
            if ((this.position.x != 22) && (i % 3 == 0)) {
                this.position.x += 1;
            }
            if (i == -31) {
                i = 100;
            }
        }
        else if ((i >= 11) && (i < 14))
        {
            i++;
            if ((this.position.y != 1) && (i % 2 == 0)) {
                this.position.y -= 1;
            }
            if (i == 14) {
                i = 100;
            }
        }
        else if ((i > -14) && (i <= -11))
        {
            i--;
            if ((this.position.y != 7) && (i % 2 == 0)) {
                this.position.y += 1;
            }
            if (i == -14) {
                i = 100;
            }
        }
        this.e_move_dir = i;
    }

    public void e_move_ai() {
        int i;
        if (((int)this.position.x == 2))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir = -21;
            } else if (i == 2) {
                this.e_move_dir = -11;
            } else if (i == 3) {
                this.e_move_dir = 11;
            }
        }
        else if (((int)this.position.x == 22) || (((int)this.position.x >= 14)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir = 21;
            } else if (i == 2) {
                this.e_move_dir = -11;
            } else if (i == 3) {
                this.e_move_dir = 11;
            }
        }
        else if (((int)this.position.y == 6) || ((int)this.position.y == 7))
        {
            i = get_random(4);
            if ((i == 1) || (i == 2)) {
                this.e_move_dir = 11;
            } else if (i == 0) {
                this.e_move_dir = 21;
            } else if (i == 1) {
                this.e_move_dir = -21;
            }
        }
        else
        {
            i = get_random(8);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir = 21;
            } else if ((i == 2) || (i == 3)) {
                this.e_move_dir = -21;
            } else if (i == 4) {
                this.e_move_dir = 11;
            } else if (i == 5) {
                this.e_move_dir = -11;
            } else {
                this.e_move_dir = 1;
            }
        }
    }

    public void e_move_ai2() {
        int i;
        if (((int)this.position.x == 2))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir = -21;
            } else if (i == 2) {
                this.e_move_dir = -11;
            } else if (i == 3) {
                this.e_move_dir = 11;
            }
        }
        else if (((int)this.position.y == 6) || ((int)this.position.y == 7))
        {
            i = get_random(4);
            if ((i == 1) || (i == 2)) {
                this.e_move_dir = 11;
            } else if (i == 0) {
                this.e_move_dir = 21;
            } else if (i == 1) {
                this.e_move_dir = -21;
            }
        }
        else
        {
            i = get_random(8);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir = 21;
            } else if ((i == 2) || (i == 3)) {
                this.e_move_dir = -21;
            } else if (i == 4) {
                this.e_move_dir = 11;
            } else if (i == 5) {
                this.e_move_dir = -11;
            } else {
                this.e_move_dir = 1;
            }
        }
    }
    public void e_move2() {
        // Dewey
        int i2 = this.e_move_dir;
        if ((i2 >= 1) && (i2 < 8))
        {
            i2++;
            if (i2 == 8) {
                i2 = 100;
            }
        }
        else if ((i2 >= 21) && (i2 < 31))
        {
            i2++;
            if ((this.position.x != 2) && (i2 % 3 == 0)) {
                this.position.x -= 1;
            }
            if (i2 == 31) {
                i2 = 100;
            }
        }
        else if ((i2 > -31) && (i2 <= -21))
        {
            i2--;
            if ((this.position.x != 22) && (i2 % 3 == 0)) {
                this.position.x += 1;
            }
            if (i2 == -31) {
                i2 = 100;
            }
        }
        else if ((i2 >= 11) && (i2 < 14))
        {
            i2++;
            if ((this.position.y != 1) && (i2 % 2 == 0)) {
                this.position.y -= 1;
            }
            if (i2 == 14) {
                i2 = 100;
            }
        }
        else if ((i2 > -14) && (i2 <= -11))
        {
            i2--;
            if ((this.position.y != 7) && (i2 % 2 == 0)) {
                this.position.y += 1;
            }
            if (i2 == -14) {
                i2 = 100;
            }
        }
        this.e_move_dir = i2;
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