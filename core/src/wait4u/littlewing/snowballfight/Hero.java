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
    public int h_idx;
    public int h_timer;
    public int h_timer_p;
    public int snow_pw;
    public int real_snow_pw;
    public int ppang_item;
    public int ppang_time;
    public int snow_x;
    public int snow_y; // TODO replaced by Item
    public int snow_last_y;
    public int snow_top_y;
    public int mana = 0;
    public int dem; // damage
    public int wp;
    public int ppang;

    public static final float SIZE = 0.5f; // half a unit

    public Vector2 position = new Vector2();
    private Rectangle bounds = new Rectangle();

    public int hp;
    public int max_hp = 106;
    public int snow_gap = 0;
    public int pw_up = 0;

    private Texture bobTexture;
    public Texture[] imgHero;
    private Sprite sprite;
    // snow or stone item used in firing
    public Item item;

    public Hero(Vector2 pos) {
        this.position = pos;
        this.bounds.height = SIZE;
        snow_x = (int)position.x;
        snow_y = (int)position.y;
        bounds = new Rectangle(0, 0, 0, 0);
        imgHero = new Texture[5];
        for (int m = 0; m < 5; m++) {
            imgHero[m] = new Texture("data/samsung-white/hero" + m + ".png");
        }
    }

    public Texture getImage() {
        return imgHero[h_idx];
    }
    public void setBobTexture(Texture texture) {
        bobTexture = texture;
    }

    public Texture getBobTexture() {
        return bobTexture;
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
        snow_y = 12; // (int)position.y
        snow_x = (int)position.x;
        snow_last_y = (18 + real_snow_pw*2); // orig: 9-real_snow_pw
        if (real_snow_pw % 2 == 0) {
            snow_top_y = (14 + real_snow_pw); // 10-real_snow_pw/2
        } else {
            snow_top_y = (13 + real_snow_pw);
        }
        snow_gap = 3;
        h_timer = 0;
        pw_up = 2;
//        MPlay(2);
    }

}