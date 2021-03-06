package wait4u.littlewing.snowballfight;

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

    public int e_idx;
    public int max_e_hp;
    public int e_snow_y;
    public int e_snow_x;
    public int e_snow_top; // TODO update geometry reserve
    public int e_snow_gap;
    public int e_snow_dx;
    public int e_ppang_item;
    public int e_ppang_time;
    public int e_lv;
    public int e_fire_time;
    public int e_wp;
    public int e_behv; // Use in enemy attack
    public int dis_count;

    static final float SPEED = 2f; // unit per second
    static final float JUMP_VELOCITY = 1f;
    public static final float SIZE = 0.5f; // half a unit

    public Vector2  position = new Vector2();
    Vector2  acceleration = new Vector2();
    Vector2  velocity = new Vector2();
    private Rectangle  bounds = new Rectangle();
    public State  state = State.IDLE;
    public boolean  facingLeft = true;

    private int hp = 30; // 120 def, small for fast debug
    // TODO private e_move_dir
    public int e_move_dir = 1; // Default e_move_dir = 1 to avoid init() and/or e_move_ai() call in original J2ME.
    private Texture bobTexture;
    private Sprite sprite;
    // snow or stone item used in firing
    public Item item;

    public Enemy(Vector2 position) {
        this.position = position;
//        this.bounds.height = SIZE;
//        bounds = new Rectangle(0, 0, 0, 0);
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
    public int getIdx() {
        return e_idx;
    }
    public void setIdx(int e_idx) {
        this.e_idx = e_idx;
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
     * This function simulate enemy moving like in real. It's based on screen size 120x160 and some arithmetic calculate.
     * */

    public void e_move_ai(Enemy[] enemies, int paramInt)
    {
        int i;
        if ((enemies[paramInt].position.x == 2) || ((enemies[1].position.x <= 9) && (paramInt == 1)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                enemies[paramInt].e_move_dir = -21;
            } else if (i == 2) {
                enemies[paramInt].e_move_dir = -11;
            } else if (i == 3) {
                enemies[paramInt].e_move_dir = 11;
            }
        }
        else if ((enemies[paramInt].position.x == 22) || ((enemies[0].position.x >= 14) && (paramInt == 0)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                enemies[paramInt].e_move_dir = 21;
            } else if (i == 2) {
                enemies[paramInt].e_move_dir = -11;
            } else if (i == 3) {
                enemies[paramInt].e_move_dir = 11;
            }
        }
        else if ((enemies[paramInt].position.y == 6) || (enemies[paramInt].position.y == 7))
        {
            i = get_random(4);
            if ((i == 1) || (i == 2)) {
                enemies[paramInt].e_move_dir = 11;
            } else if (i == 0) {
                enemies[paramInt].e_move_dir = 21;
            } else if (i == 1) {
                enemies[paramInt].e_move_dir = -21;
            }
        }
        else
        {
            i = get_random(8);
            if ((i == 0) || (i == 1)) {
                enemies[paramInt].e_move_dir = 21;
            } else if ((i == 2) || (i == 3)) {
                enemies[paramInt].e_move_dir = -21;
            } else if (i == 4) {
                enemies[paramInt].e_move_dir = 11;
            } else if (i == 5) {
                enemies[paramInt].e_move_dir = -11;
            } else {
                enemies[paramInt].e_move_dir = 1;
            }
        }
    }

    public void e_move(Enemy[] enemies, int paramInt)
    {
        int i = enemies[paramInt].e_move_dir;
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
            if ((enemies[paramInt].position.x != 2) && (i % 3 == 0)) {
                enemies[paramInt].position.x -= 1;
            }
            if (i == 31) {
                i = 100;
            }
        }
        else if ((i > -31) && (i <= -21))
        {
            i--;
            if ((enemies[paramInt].position.x != 22) && (i % 3 == 0)) {
                enemies[paramInt].position.x += 1;
            }
            if (i == -31) {
                i = 100;
            }
        }
        else if ((i >= 11) && (i < 14))
        {
            i++;
            if ((enemies[paramInt].position.y != 1) && (i % 2 == 0)) {
                enemies[paramInt].position.y -= 1;
            }
            if (i == 14) {
                i = 100;
            }
        }
        else if ((i > -14) && (i <= -11))
        {
            i--;
            if ((enemies[paramInt].position.y != 7) && (i % 2 == 0)) {
                enemies[paramInt].position.y += 1;
            }
            if (i == -14) {
                i = 100;
            }
        }
        enemies[paramInt].e_move_dir = i;
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
    public void e_attack_ai(Hero hero, Boss boss, Enemy[] enemies, int paramInt)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        if (paramInt > 100)
        {
            m = (int)(hero.position.x - boss.position.x);
            j = paramInt;
        }
        else
        {
            m = (int)(hero.position.x - enemies[paramInt].position.x);
            k = paramInt;
        }
        int n;
        if ((m >= -9) && (m <= -6))
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = -2;
                } else if (n == 1) {
                    i = -3;
                } else {
                    i = -4;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if ((m >= -5) && (m <= -2))
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = -1;
                } else if (n == 1) {
                    i = -2;
                } else {
                    i = -3;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if ((m >= -1) && (m <= 1))
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = -1;
                } else if (n == 1) {
                    i = 0;
                } else {
                    i = 1;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if ((m >= 2) && (m <= 5))
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = 1;
                } else if (n == 1) {
                    i = 2;
                } else {
                    i = 3;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if ((m >= 6) && (m <= 9))
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = 2;
                } else if (n == 1) {
                    i = 3;
                } else {
                    i = 4;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if (m >= 10)
        {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(3);
                if (n == 0) {
                    i = 4;
                } else {
                    i = 5;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        else if (m <= -10) {
            if ((enemies[k].e_lv >= 2) || (j > 100))
            {
                n = get_random(2);
                if (n == 0) {
                    i = -4;
                } else {
                    i = -5;
                }
            }
            else
            {
                i = get_random1(6);
            }
        }
        if (j < 100)
        {
            if (enemies[k].e_lv >= 2)
            {
                n = get_random(4);
                if (n == 1)
                {
                    int i1 = get_random(3);
                    if (i1 == 0) {
                        enemies[k].e_wp = 1;
                    } else if (i1 == 1) {
                        enemies[k].e_wp = 2;
                    } else {
                        enemies[k].e_wp = 3;
                    }
                }
                else
                {
                    enemies[k].e_wp = 0;
                }
            }
            // TODO update snow_dx by board height, it's seem to be too high, so snow fire to outer right/left
            enemies[k].e_behv = i;
            enemies[k].item.position.y = (int)enemies[k].position.y; // TODO why y do not multiple by cell (5)
            enemies[k].item.position.x = (int)(enemies[k].position.x); // orig: *5
            enemies[k].e_snow_gap = 0;
            enemies[k].e_snow_dx = i/2; // orig: i; i +/-1 +/-3 +/- 5 +/-2 ...
            enemies[k].e_snow_top = 1;
            enemies[k].e_idx = 3;
            enemies[k].dis_count = 1;
        }
        else
        {
            if (j < 103)
            {
                n = get_random(2);
                if (n == 0)
                {
                    n = get_random(3);
                    if (n == 0) {
                        boss.e_boss_wp = 1;
                    } else if (n == 1) {
                        boss.e_boss_wp = 2;
                    } else {
                        boss.e_boss_wp = 3;
                    }
                }
                else
                {
                    boss.e_boss_wp = 0;
                }
            }
            else
            {
                n = get_random(3);
                if (n == 0) {
                    boss.e_boss_wp = 1;
                } else if (n == 1) {
                    boss.e_boss_wp = 2;
                } else {
                    boss.e_boss_wp = 3;
                }
            }
            boss.e_boss_behv = i;
            boss.item.position.y = (int)boss.position.y;
            boss.item.position.x = (int)(boss.position.x * 5);
            boss.e_boss_snow_gap = 0;
            boss.e_boss_snow_dx = i;
            boss.e_boss_snow_top = 1;
            boss.e_boss_idx = 3;
            boss.boss_dis_count = 1;
        }
    }

    // TODO modify addition change by view port board width x height from original 240x320. The gap need modify.
    public void e_snow(int e_num, int e_boss, Enemy[] enemies, Boss boss, Hero hero)
    {
        for (int i = 0; i < e_num; i++) {
            if ( enemies[i].e_behv != 100)
            {
                enemies[i].item.position.y -= 1; // e_snow_y
                enemies[i].item.position.x += enemies[i].e_snow_dx;
                if ((enemies[i].e_snow_gap < 10) && (enemies[i].e_snow_top == 1))
                {
                    enemies[i].e_snow_gap += 2;
                    if (enemies[i].e_snow_gap == 10) {
                        enemies[i].e_snow_top = 2;
                    }
                }
                else
                {
                    enemies[i].e_snow_gap -= 1;
                }
                if ((int)enemies[i].item.position.y == 5) { // orig: 13
                    hero.check_hero((int)enemies[i].item.position.x, i);
                } else if (enemies[i].item.position.y <= 5) { // Reserved geometry y-axis => y <= 16 (orig: >= 16)
                    // May be BOTTOM_BOUND or addition added on draw Snow (in drawRunningScreen) so top_snow_y need update.
                    enemies[i].e_behv = 100;
                }
            }
        }
        if ((e_boss > 0) && (boss.e_boss_behv != 100))
        {
            boss.getItem().position.y -= 1; // currently use public Item
            boss.item.position.x += boss.e_boss_snow_dx;
            if ((boss.e_boss_snow_gap < 10) && (boss.e_boss_snow_top == 1))
            {
                boss.e_boss_snow_gap += 2;
                if (boss.e_boss_snow_gap == 10) {
                    boss.e_boss_snow_top = 2;
                }
            }
            else
            {
                boss.e_boss_snow_gap -= 1;
            }
            if ((int)boss.item.position.y == 13) {
                hero.check_hero((int)boss.item.position.x, 100);
            } else if ((int)boss.item.position.y >= 16) {
                boss.e_boss_behv = 100;
            }
        }
    }

}