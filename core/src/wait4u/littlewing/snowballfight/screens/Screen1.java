package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.Preferences;
import wait4u.littlewing.snowballfight.Item;
import wait4u.littlewing.snowballfight.Enemy;
import wait4u.littlewing.snowballfight.Boss;
import wait4u.littlewing.snowballfight.Hero;

import java.util.Random;

/**
 * Created by nickfarow on 13/10/2016.
 */

public class Screen1 extends DefaultScreen {
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture ttrSplash;
    Texture heroTexture;
    Texture fireBtnTexture;
    Texture snowShadowTexture;

    float heroSpeed = 10.0f; // 10 pixels per second.

    private boolean heroFireState = false;

    private int ppuX = 5;	// pixels per unit on the X axis
    private int ppuY = 5;	// pixels per unit on the Y axis

    private Hero hero;
    // TODO loop create sprite
    private Enemy huey;
    private Enemy dewey;
    private Boss boss;
    private Enemy[] enemies = new Enemy[2];

    private Texture bobTexture;

    float stateTime;

    private Random rnd = new Random();

    // Test touchpad
    private Stage touch_stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;

    public static final String TAG = "LOG";

    public static int GAMESTATE_WIN = 1;
    public static int GAMESTATE_LOSE = 2;
    public static int GAMESTATE_PLAYING = 0;
    public static int sgh_120_1080_screen_ratio = (5*9); // (5*9/2)
    public static int sgh_scale_ratio = Gdx.graphics.getWidth()/120; // 120 or 128px from original J2ME resolution.

    private static final String PREF_VIBRATION = "vibration";
    private static final String PREF_SOUND_ENABLED = "soundenabled";
    private static final String PREF_SPEED = "gamespeed";
    private static final String PREF_LEVEL = "level";
    private static final String PREF_SAVEDGOLD = "saved_gold";
    private static final String PREF_MANA= "mana";
    Preferences prefs = Gdx.app.getPreferences("gamestate");

    private static final int DEFAULT_DEM = 12;
    private int game_state = 0;
    private int saved_gold = 10;
    private int speed = 4;
    private int game_speed = 17;
    private int screen = -1;
    private boolean gameOn = true;
    private String message;
    private int m_mode = 1;
    private int s_play = 1;
    private int v_mode = 1;
    //  AudioClip audioClip = null;
    private int p_mode;
    private int ani_step;

    private int stage;
    private int last_stage = 11;
    private int tmp_stage;
    private int school;
    private int state;

    private int pw_up;
    private int mana = 0;
    private int max_hp;
    private int dem;
    private int wp;
    private int snow_pw;
    private int real_snow_pw;
    private int snow_last_y;
    private int snow_top_y;
    private int snow_gap;
    private int snow_y;
    private int snow_x;
    private int ppang;
    private int gold;
    private int[] item_slot = new int[5];
    private int ppang_item;
    private int ppang_time;
    private int special;
    private int item_mode;
    private int[] item_price = new int[8];
    private int del = -1;
    private int item_a_num;
    private int item_b_num;
    private int item_c_num = 2;
    private int item_d_num;
    private int b_item;
    private int s_item;
    private int e_num = 2; // set dafaut value for debug avoid eceed 2 item size
    private int e_t_num;

    private int e_time;
    private int e_dem;
    private int hit_idx;
    private int e_boss;
    private int al;
    private int d_gauge;

    public Screen1(Game game) {
        super(game);

        camera = newOrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        initSpriteBatchAndHeroTexture();
        initBobItem();
        setEnermyTexture();
        create();
    }

    public void create () {
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 10f*aspectRatio, 10f);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/gui/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/gui/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        touch_stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        touch_stage.addActor(touchpad);
        Gdx.input.setInputProcessor(touch_stage);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("data/gui/block.png"));
        blockSprite = new Sprite(blockTexture);

        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);
        blockSpeed = 5;
    }

    public void update() {

    }

    protected Preferences getPrefs() {
        if(prefs==null){
            prefs = Gdx.app.getPreferences("gamestate");
        }
        return prefs;
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean getVibraEnabled() {
        return getPrefs().getBoolean(PREF_VIBRATION, true);
    }

    public void setVibraEnabled(boolean vibra) {
        getPrefs().putBoolean(PREF_VIBRATION, vibra);
        getPrefs().flush();
    }

    public int getLevel() {
        return getPrefs().getInteger(PREF_LEVEL, 11);
    }

    public void setLevel(int level) {
        getPrefs().putInteger(PREF_LEVEL, level);
        getPrefs().flush();

    }
    public int getSavedgold() {
        return getPrefs().getInteger(PREF_SAVEDGOLD, 64);
    }

    public void setSavedGold(int saved_gold) {
        getPrefs().putInteger(PREF_SAVEDGOLD, saved_gold);
        getPrefs().flush();
    }

    public int getSavedMana() {
        return getPrefs().getInteger(PREF_MANA, 64);
    }

    public void setSavedMana(int saved_gold) {
        getPrefs().putInteger(PREF_MANA, saved_gold);
        getPrefs().flush();
    }

    public int getGameSpeed() {
        return getPrefs().getInteger(PREF_SPEED, 64);
    }

    public void setGameSpeed(int saved_gold) {
        getPrefs().putInteger(PREF_SPEED, saved_gold);
        getPrefs().flush();
    }

    public void render(float delta) {
        int viewPortHeight = (int)Gdx.graphics.getHeight()*3/4;
        int topBound = viewPortHeight + (int)Gdx.graphics.getHeight()/8;
        int bottomSpace = (int)Gdx.graphics.getHeight()/8; // May be change for fit touch button

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        generalUpdate();

        int j;
        if (screen == 6)        // normal playing screen, TODO may be use constants name
        {
            // paramGraphics.drawImage(this.imgBack, 0, 0, 20); // options|* button
            // TODO init topBound as global var to reuse
            // 1/8 as 1/4 (4/16 * scr_height) 4 = 16-12; 12 is from ratio 3x3 = 9 => 4X3 = 12

            batch.draw(hero.getBobTexture(), hero.position.x * 5 * sgh_scale_ratio, (int)83/160*viewPortHeight , hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight());
            if (ppang_time > 0)
            {
                if (ppang_item == 1) {
//                    paramGraphics.drawImage(this.imgItem_hyo[0], this.h_x * 5, 74, 0x10 | 0x1);
                } else {
//                    paramGraphics.drawImage(this.imgItem_hyo[1], this.h_x * 5, 83, 0x10 | 0x1);
                }
                ppang_time -= 1;
                if (ppang_time == 0) {
                    ppang_item = 0;
                }
            }
//            draw_enemy(paramGraphics);
            if (item_mode != 0)
            {
                if (message != "") {
                    draw_text();
                }
                for (int i = 1; i <= 5; i++) {
//                    paramGraphics.drawRect(i * 12 + 23, 110, 10, 9);
                }
                if (item_mode != 100)
                {
//                    paramGraphics.setColor(16711680);
//                    paramGraphics.drawRect(this.item_mode * 12 + 23, 110, 10, 9);
                }
                else if (item_mode == 100)
                {
                    item_mode = 0;
                }
            }
            if (pw_up == 2)
            {
//                paramGraphics.drawImage(this.imgShadow, this.snow_x * 5, this.snow_y * 7 + 4, 0x2 | 0x1);
//                paramGraphics.drawImage(this.imgItem[this.wp], this.snow_x * 5, this.snow_y * 7 - this.snow_gap + 4, 0x2 | 0x1);
            }
            else if (this.pw_up == 1)
            {
                if ((real_snow_pw > 0) && (ppang_item != 1))
                {
//                    paramGraphics.setColor(7196662);
                    if (hero.position.x >= 13)
                    {
//                        paramGraphics.fillRect(this.h_x * 5 - 16, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
//                        paramGraphics.drawImage(this.imgPwd, this.h_x * 5 - 15, 83, 0x10 | 0x1);
                    }
                    else
                    {
//                        paramGraphics.fillRect(this.h_x * 5 + 14, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
//                        paramGraphics.drawImage(this.imgPwd, this.h_x * 5 + 15, 83, 0x10 | 0x1);
                    }
                }
            }
            else if (this.pw_up == 0)
            {
                if (this.ppang <= -1)
                {
//                    paramGraphics.drawImage(this.imgPok, this.snow_x * 5, this.snow_y * 7 - 3, 0x2 | 0x1);
                    this.ppang -= 1;
                    if (this.ppang == -3) {
                        this.ppang = 0;
                    }
                }
                else if ((this.ppang >= 1) && (this.ppang <= 10))
                {
                    if (this.s_item != -10)
                    {
                        if (this.ppang < 3) {
//                            paramGraphics.drawImage(this.imgPPang, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
                        } else {
//                            paramGraphics.drawImage(this.imgPPang1, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
                        }
                    }
                    else if (this.ppang < 4) {
//                        paramGraphics.drawImage(this.imgEffect[0], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                    } else {
//                        paramGraphics.drawImage(this.imgEffect[1], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                    }
                    if (this.hit_idx != 10)
                    {
                        if (enemies[hit_idx].getHp() > 0)
                        {
//                            paramGraphics.setColor(16711680);
//                            paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15);
//                            paramGraphics.setColor(9672090);
//                            paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15 - 15 * this.e_hp[this.hit_idx] / this.max_e_hp[this.hit_idx]);
                        }
                    }
                    else if (hit_idx == 10)
                    {
                        if (boss.getHp() > 0)
                        {
//                            paramGraphics.setColor(16711680);
//                            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15);
                            // draw boss hp bar
//                            http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/graphics/glutils/ShapeRenderer.html
//                            batch.draw(boss.getBobTexture(), (boss.position.x* 5 + 12)*sgh_scale_ratio, (boss.position.y* 5 + 5)*sgh_scale_ratio, 3*sgh_scale_ratio, 15*sgh_scale_ratio);
//                            paramGraphics.setColor(9672090);
//                            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp);
                        }
                        if (al == 1) {
//                            paramGraphics.drawImage(this.imgAl, this.snow_x * 5 + 6, this.snow_y * 7 - 10, 0x2 | 0x1);
                        }
                    }
                    ppang += 1;
                    if (ppang == 6)
                    {
                        ppang = 0;
                        s_item = 0;
                        al = -1;
                    }
                }
                else if (ppang >= 50)
                {
//                    draw_sp_hyo(paramGraphics);
                }
                if (message != "") {
                    draw_text();
                }
            }
            else if (pw_up == -1)
            {
                pw_up = 0;
            }
            if (p_mode == 1)
            {
                try
                {
//                    paramGraphics.drawImage(Image.createImage("/ui.png"), 0, 109, 20);
                }
                catch (Exception localException1) {}
//                draw_item(paramGraphics);
                p_mode = 2;
            }
            if (this.d_gauge != 0) {
//                draw_gauge();
            }
            for (j = 0; j < e_num; j++) { // or count array elements enemies
                if (enemies[j].e_behv != 100)
                {
//                    paramGraphics.drawImage(this.imgShadow, this.e_snow_x[j], this.e_snow_y[j] * 6 + 17, 0x2 | 0x1);
//                    paramGraphics.drawImage(this.imgItem[this.e_wp[j]], this.e_snow_x[j], this.e_snow_y[j] * 6 + 13 - this.e_snow_gap[j], 0x2 | 0x1);
                }
            }
            if ((boss.e_boss_behv != 100) && (e_boss > 0))
            {
//                paramGraphics.drawImage(this.imgShadow, this.e_boss_snow_x, this.e_boss_snow_y * 6 + 17, 0x2 | 0x1);
//                paramGraphics.drawImage(this.imgItem[this.e_boss_wp], this.e_boss_snow_x, this.e_boss_snow_y * 6 + 13 - this.e_boss_snow_gap, 0x2 | 0x1);
            }
            if (del != -1) {
//                draw_item();
            }
            if (hero.h_timer_p <= -1) {
                if (hero.h_timer_p != -5)
                {
//                    paramGraphics.drawImage(this.imgH_ppang, this.h_x * 5 + 1, 81, 0x2 | 0x1);
                    hero.h_timer_p -= 1;
                }
                else if (hero.h_timer_p == -5)
                {
                    hero.h_timer_p = 0;
//                    paramGraphics.setColor(16711680);
//                    paramGraphics.fillRect(5, 113, 9, 12);
//                    paramGraphics.setColor(9342606);
                    if (hero.getHp() > 0) {
//                        paramGraphics.fillRect(5, 113, 9, 12 - 12 * this.hp / this.max_hp);
                    }
                    if (hero.getHp() <= 0)
                    {
                        state = 3;
                        game_state = 1;
                        gameOn = true;
                    }
                }
            }
            if (state == 2)
            {
                if (ani_step >= 3) {
//                    paramGraphics.drawImage(imgStage[0], 20, 60, 20);
                }
                if (ani_step >= 6) {
//                    paramGraphics.drawImage(imgStage[1], 35, 60, 20);
                }
                if (ani_step >= 9) {
//                    paramGraphics.drawImage(imgStage[2], 50, 60, 20);
                }
                if (ani_step >= 12) {
//                    paramGraphics.drawImage(imgStage[3], 65, 60, 20);
                }
                if (ani_step >= 15) {
//                    paramGraphics.drawImage(imgStage[4], 80, 60, 20);
                }
                if (ani_step >= 19) {
//                    paramGraphics.drawImage(imgStage_num, 95, 60, 20);
                }
            }
        }
        else if (screen == 2)
        {

        }
        else if (screen == 3)
        {

        }
        else if (screen == 31)
        {

        }
        else if (screen == 100)
        {

        }
        else if (screen == -88)
        {

        }
        else if (screen == 8)
        {

        }
        else if (screen == 9)
        {
            if ((ani_step == 1) || (ani_step == 46))
            {
                if (ani_step == 46)
                {
//                    destroyImage(9);
//                    loadImage(100);
                    pw_up = -1;
//                    paramGraphics.drawImage(imgBack, 0, 0, 20);
                    screen = 6;
                    ppang = 50;
                    for (j = 0; j < e_num; j++)
                    {
                        enemies[j].e_move_dir = 0;
//                        decs_e_hp(j);
                    }
                    if (e_boss > 0)
                    {
                        boss.e_boss_move_dir = 0;
//                        decs_e_hp(10);
                    }
                    dem = 12;
                    mana = 0;
                }
//                paramGraphics.setColor(16777215);
//                paramGraphics.fillRect(0, 25, 128, 84);
                for (j = 0; j < e_num; j++)
                {
                    if (enemies[j].position.x != -10) {
                        batch.draw(enemies[j].getBobTexture(), enemies[j].position.x * 5*sgh_scale_ratio, (enemies[j].position.y * 5 + 5)*sgh_scale_ratio, enemies[j].getBobTexture().getWidth(), enemies[j].getBobTexture().getHeight());
                    }
                    if (enemies[j].e_behv != 100)
                    {
//                        paramGraphics.drawImage(imgShadow, e_snow_x[j], e_snow_y[j] * 6 + 17, 0x2 | 0x1);
//                        paramGraphics.drawImage(imgItem[e_wp[j]], e_snow_x[j], e_snow_y[j] * 6 + 13 - e_snow_gap[j], 0x2 | 0x1);
                    }
                }
                if (e_boss > 0) {
//                    paramGraphics.drawImage(imgBoss[e_boss_idx], e_boss_x * 5, e_boss_y * 5, 0x10 | 0x1);
                }
            }
            if (this.special == 1)
            {
                if (ani_step <= 45) {
//                    paramGraphics.drawImage(imgSp, 158 - ani_step * 3, 0, 20);
                }
            }
            else if (special == 2)
            {
                if (ani_step <= 45) {
//                    paramGraphics.drawImage(imgSp, 158 - ani_step * 3, 0, 20);
                }
            }
            else if ((special == 3) && (ani_step <= 45)) {
//                paramGraphics.drawImage(imgSp, 168 - ani_step * 3, 30, 20);
            }
            batch.draw(hero.getBobTexture(), hero.position.x * 5 * sgh_scale_ratio, (int)(83/160)*viewPortHeight+bottomSpace, hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight());
        }
        else if (screen == 4)
        {

        }
        else if (screen == 5)
        {

        }
        else if (screen == -33)
        {

        }
        else if (screen == 200)
        {

        }
        else if (screen == 65335)
        {

        }
        else if (screen == 300)
        {

        }
        else if (screen == 77)
        {

        }
        else if (screen == -1)
        {

        }
        else if (screen == -2)
        {

        }
        else if (screen == 1000)
        {

        }
        else if (screen == -5)
        {

        }
        else if (screen == 1)
        {

        }

//        batch.setProjectionMatrix(camera.combined);

        if (huey.e_move_dir >= 100)
        {
            Gdx.app.log("INFO", "Huey move dir > 100:" + huey.e_move_dir + " --x-- "+ huey.position.x + " y "
                    + huey.position.y);
            huey.e_move_dir += 1;
            if (huey.e_move_dir == 120) {
                huey.e_move_dir = 0;
            }
        }
        else if ((huey.e_move_dir == 0) && (!huey.isDead()))
        {
            huey.e_move_ai_();
        }
        else if ((huey.e_move_dir < 100) && (huey.e_move_dir != 0) && (!huey.isDead()))
        {
            huey.e_move_();
        }

        if (dewey.e_move_dir >= 100)
        {
            Gdx.app.log("INFO", "Dewey move dir > 100:" + dewey.e_move_dir + " --x-- "+ dewey.position.x + " y "
                    + dewey.position.y);
            dewey.e_move_dir += 1;
            if (dewey.e_move_dir == 120) {
                dewey.e_move_dir = 0;
            }
        }
        else if ((dewey.e_move_dir == 0) && (!dewey.isDead()))
        {
            dewey.e_move_ai2();
        }
        else if ((dewey.e_move_dir < 100) && (dewey.e_move_dir != 0) && (!dewey.isDead()))
        {
            dewey.e_move2();
        }

        /*for (int i = 0; i < e_num; i++)
        {
            if (enemies[i].getHp() >= 0)
            {
                if ((e_time == enemies[i].e_fire_time) && (boss.get_random(3) != 1) && (enemies[i].e_ppang_item != 2)) {
                    enemies[i].e_attack_ai(hero, boss, enemies, i);
                }
                if (enemies[i].e_ppang_item != 2) {
                    if (enemies[i].e_idx == 0) {
                        enemies[i].e_idx = 1;
                    } else if (enemies[i].e_idx == 1) {
                        enemies[i].e_idx = 0;
                    }
                }
            }
            if (enemies[i].e_move_dir >= 100)
            {
                enemies[i].e_move_dir += 1;
                if (enemies[i].e_move_dir == 120) {
                    enemies[i].e_move_dir = 0;
                }
            }
            else if ((enemies[i].e_move_dir == 0) && (enemies[i].getHp() > 0) && (enemies[i].e_ppang_item != 2))
            {
                enemies[i].e_move_ai(enemies, i);
            }
            else if ((enemies[i].e_move_dir < 100) && (enemies[i].e_move_dir != 0) && (enemies[i].getHp() > 0))
            {
                enemies[i].e_move(enemies, i);
            }
        }*/ // End enemy attack n move ai

        if (boss.e_boss_move_dir >= 100)
        {
            boss.e_boss_move_dir += 1;
            if (boss.e_boss_move_dir == 115) {
                boss.e_boss_move_dir = 0;
            }
        }
        else if ((boss.e_boss_move_dir == 0) && (!boss.isDead()))
        {
            boss.boss_move_ai();
        }
        else if ((boss.e_boss_move_dir != 0) && (!boss.isDead()))
        {
            boss.boss_move();
        }

        drawSplashBatch();
        drawTouchPad();
        batch.enableBlending();
        batch.begin();
//        HeroAsset.hero1.draw(batch);

//        handleKeyMoveHero();
//        testDrawHero();

        drawFireBtn();
        handleFireTouch();

        testDrawInitPositionEnermy();
//        drawBob();
        batch.end();

        handleVictoryOrLose();
    }

    private void loadTextures() {
    }

    private void drawBob() {
        if (hero.state==hero.state.WALKING){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        else if(hero.state==hero.state.IDLE){
            batch.draw(heroTexture, hero.position.x * ppuX * sgh_scale_ratio, hero.position.y * ppuY * sgh_scale_ratio, hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight());
        }
    }

    public void generalUpdate() {
        testEnermyMoving();
        updateEnemyBound();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    protected OrthographicCamera newOrthographicCamera(int width, int height) {
        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height);

        return camera;
    }

    protected void setEnermyTexture() {
        // With ratio 4:3 on SGH X550 or X357 120x160, SGH 930 (S7) 1080x1920, to keep ratio 4:3 we need crop 1920 to use only 1440.
        // On 380px space, place 180 or 200px for fire, move btn. 200px on top is space.
        // So on J2ME original enemy seem to move on 1/4 of screen height (3 cell +1 /32 cell 5px).
        // That mean enemy should move on 1080 to 1440 on 360px vertical distance.
        // TODO handle screen size ratio multi screen.
        int enemyPosY = Gdx.graphics.getHeight()*3/4-180;
        int enemyStepY = 45; // 5 * 9 (5px from orig J2ME), 9 on scale up resolution.
//        huey = new Enemy(new Vector2(240, enemyPosY-enemyStepY));
        // Try J2ME original: 5px is old cell in map (120px = 24 * 5) In 1080 = 120 * 9. But because of sprite scale we try 4.5 instead of 9.
        huey = new Enemy(new Vector2(240/sgh_120_1080_screen_ratio, (enemyPosY-enemyStepY)/sgh_120_1080_screen_ratio ));
        dewey = new Enemy(new Vector2(480/sgh_120_1080_screen_ratio, enemyPosY/sgh_120_1080_screen_ratio));
        boss = new Boss(new Vector2(320/sgh_120_1080_screen_ratio, (enemyPosY-(3*enemyStepY))/sgh_120_1080_screen_ratio ));
        // TODO scale texture due to ratio, may be in draw()
        huey.setBobTexture("data/samsung-white/enemy0_0_106x.png");
        dewey.setBobTexture("data/samsung-white/enemy1_1_106x.png");
        boss.setBobTexture("data/samsung-white/boss2_0_120x.png");

        huey.setBound(new Rectangle(huey.position.x*sgh_120_1080_screen_ratio, huey.position.y*sgh_120_1080_screen_ratio, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight()));
        dewey.setBound(new Rectangle(dewey.position.x*sgh_120_1080_screen_ratio, dewey.position.y*sgh_120_1080_screen_ratio, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight()));
        boss.setBound(new Rectangle(boss.position.x*sgh_120_1080_screen_ratio, boss.position.y*sgh_120_1080_screen_ratio, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight()));
    }

    protected void initBobItem() {
        Vector2 facing = new Vector2(hero.position.x, Gdx.graphics.getHeight()*4/5);
        Item item = new Item(0, hero.position, facing);
        item.setTexture(new Texture("data/samsung-white/item3_0_36x.png"));
        hero.setItem(item);
        hero.getItem().setBound(new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight()) );
    }

    protected void initSpriteBatchAndHeroTexture () {
        batch = new SpriteBatch();
        ttrSplash = new Texture("data/samsung-white/menu_bg.png");
        heroTexture = new Texture("data/samsung-white/hero3_1_120x.png");
        hero = new Hero(new Sprite());
        hero.position.x = Gdx.graphics.getWidth()/2;
        // TODO scaleX, Y follow screen ratio
        hero.setBobTexture(new Texture("data/samsung-white/hero3_1_120x.png"));
        hero.position.y = Gdx.graphics.getHeight()/5-hero.getBobTexture().getHeight();

        fireBtnTexture = new Texture("data/samsung-white/fire.png");
        initBobItem();
    }

    protected void drawSplashBatch() {
        batch.disableBlending();
        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    protected void handleKeyMoveHero() {
    }

    protected void oldTestDrawInitPositionEnermy() {
        if(!huey.isDead()) {
            batch.draw(huey.getBobTexture(), (int)huey.position.x, (int)huey.position.y, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), huey.facingLeft, false);
            huey.facingLeft = !huey.facingLeft;
        }
        if(!dewey.isDead()) {
            batch.draw(dewey.getBobTexture(), (int)dewey.position.x, (int)dewey.position.y, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), dewey.facingLeft, false);
            dewey.facingLeft = !dewey.facingLeft;
        }
        if(!boss.isDead()) {
            batch.draw(boss.getBobTexture(), (int)boss.position.x, (int)boss.position.y, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 1, 1, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), boss.facingLeft, false);
            boss.facingLeft = !boss.facingLeft;
        }
    }

    /*
    * Try use old J2ME logic to render position of enemy
    * */
    protected void testDrawInitPositionEnermy() {
        if(!huey.isDead()) {
            batch.draw(huey.getBobTexture(), (int)huey.position.x*sgh_120_1080_screen_ratio, (int)huey.position.y*sgh_120_1080_screen_ratio, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), huey.facingLeft, false);
            huey.facingLeft = !huey.facingLeft;
        }
        if(!dewey.isDead()) {
            batch.draw(dewey.getBobTexture(), (int)dewey.position.x*sgh_120_1080_screen_ratio, (int)dewey.position.y*sgh_120_1080_screen_ratio, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), dewey.facingLeft, false);
            dewey.facingLeft = !dewey.facingLeft;
        }
        if(!boss.isDead()) {
            batch.draw(boss.getBobTexture(), (int)boss.position.x*sgh_120_1080_screen_ratio, (int)boss.position.y*sgh_120_1080_screen_ratio, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 1, 1, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), boss.facingLeft, false);
            boss.facingLeft = !boss.facingLeft;
        }
    }

    /*
    * J2ME original port to gdx
    * */
    public void draw_enemy()
    {
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i].position.x != -10) {
                batch.draw(enemies[i].getBobTexture(), (int) enemies[i].position.x * 5 * sgh_scale_ratio, enemies[i].position.y * 5 * sgh_scale_ratio + 5, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), false, false);
                if (enemies[i].e_ppang_time > 0) {
                    enemies[i].e_ppang_time -= 1;
                    // paramGraphics.drawImage(this.imgItem_hyo[(this.e_ppang_item[i] - 1)], this.e_x[i] * 5, this.e_y[i] * 5 + 1, 0x10 | 0x1);
                    if (enemies[i].e_ppang_time == 0) {
                        enemies[i].e_ppang_item = 0;
                        if (enemies[i].e_lv < 0) {
                            enemies[i].e_lv = (-enemies[i].e_lv);
                        }
                    }
                }
                if (enemies[i].dis_count >= 1) {
                    enemies[i].dis_count += 1;
                    if (enemies[i].dis_count == 4) {
                        enemies[i].dis_count = 0;
                        enemies[i].setIdx(0);
                    }
                } else if (enemies[i].dis_count <= -1) {
                    enemies[i].dis_count -= 1;
                    if (enemies[i].dis_count == -10) {
                        enemies[i].position.x = -10;
                        enemies[i].dis_count = 0;
                        this.e_t_num -= 1;
                        if ((this.e_t_num == 0) && (this.e_boss == 0)) {
                            this.item_mode = 0;
                            this.game_state = 2;
                            this.state = 3;
                            this.gameOn = true;
                        }
                    }
                }
            }
        }

        if (this.e_boss > 0)
        {
            batch.draw(boss.getBobTexture(), (int)boss.position.x * 5 *sgh_scale_ratio , (boss.position.y * 5)*sgh_scale_ratio, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 1, 1, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), false, false);
            if (boss.boss_dis_count >= 1)
            {
                boss.boss_dis_count += 1;
                if (boss.boss_dis_count == 4)
                {
                    boss.boss_dis_count = 0;
                    boss.setIdx(0);
                }
            }
            else if (boss.boss_dis_count <= -1)
            {
                boss.boss_dis_count -= 1;
                if (boss.boss_dis_count == -10)
                {
                    this.e_boss = 0;
                    boss.boss_dis_count = 0;
                    if (this.e_t_num == 0)
                    {
                        this.item_mode = 0;
                        this.game_state = 2;
                        this.state = 3;
                        this.gameOn = true;
                    }
                }
            }
        }
    }

    /*
    * Update 8-Nov-2017.
    * Try use original e_move() from J2ME.
    * */
    protected void testEnermyMoving() {
//        setSavedGold(32);
        Gdx.app.log("INFO", "Test recordStore " + getSavedgold());

        float leftBound = 0;
        float rightBoundHuey = (Gdx.graphics.getWidth() - huey.getBobTexture().getWidth())/sgh_120_1080_screen_ratio;
        int rightBoundDewey = (int) ((Gdx.graphics.getWidth() - dewey.getBobTexture().getWidth())/sgh_120_1080_screen_ratio);
        int rightBoundBoss = (int)((Gdx.graphics.getWidth() - boss.getBobTexture().getWidth())/sgh_120_1080_screen_ratio);

        // Ratio 3:4 ~ 9:12 So with ratio 9:16 we lost (not use) 4/16 = 1/4 of height.
        // Ie. 1920 we will cut 1/4 = 480px to keep ratio 3:4 1080:1440.
        // Bottom space used for fireBtn, so top should space only 240px
        int topBound =  (int)Gdx.graphics.getHeight()*3/4 + 240;
        Gdx.app.log("INFO", "Top = " + topBound);

        int topBoundInCell = (int) (topBound / sgh_120_1080_screen_ratio);
        int bottomBoundInCell = topBoundInCell - 8; // We draw map of 24x32 cell

        Gdx.app.log("INFO", "Bottom (in cell) = " + bottomBoundInCell);

        Random r = new Random();
        // TODO random position when hit rightBound, Horizontal front line enemy move by step up/down

        if(huey.position.x >= rightBoundHuey) {
            int i1 = r.nextInt(180) + Gdx.graphics.getWidth()/3;
            huey.position.x = i1/sgh_120_1080_screen_ratio;
        }
        if(huey.position.x <= leftBound) {
            huey.position.x = (r.nextInt(180) + hero.position.x)/sgh_120_1080_screen_ratio;
        }

        // Top bound
        if(huey.position.y >= topBoundInCell) {
            huey.position.y = r.nextInt(90)/sgh_120_1080_screen_ratio + bottomBoundInCell;
        }
        if(huey.position.y <= bottomBoundInCell) {
            huey.position.y = r.nextInt(180)/sgh_120_1080_screen_ratio + bottomBoundInCell;
        }

        dewey.check_move_outof_bound(0, rightBoundDewey, bottomBoundInCell, topBoundInCell);

        boss.check_move_outof_bound(0, rightBoundBoss, bottomBoundInCell-7, topBoundInCell);
    }

    protected void drawFireBtn() {
        batch.draw(fireBtnTexture, Gdx.graphics.getWidth()-50-fireBtnTexture.getWidth(), 50, fireBtnTexture.getWidth(), fireBtnTexture.getHeight());
    }

    protected void drawTouchPad() {
//        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //Move blockSprite with TouchPad. TODO remove test code
//        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);

        handleHeroMoveBound();
        updateHeroBullet();

        //Draw
        batch.enableBlending();
        batch.begin();
//        blockSprite.draw(batch);

        batch.draw(hero.getBobTexture(), hero.position.x, hero.position.y, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), 1, 1, 0, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), hero.facingLeft, false);
        hero.facingLeft = !hero.facingLeft;

        batch.end();
        touch_stage.act(Gdx.graphics.getDeltaTime());
        touch_stage.draw();
    }

    protected void handleFireTouch() {
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            // TODO find out why unproject make position wrong. Unproject deal with zoom or similar variables in touch screen
            // When zoom in/out ... the extract position may be difference. So the pointer pos after unproject may be reset to 0
//            camera.unproject(touchPos);
            Rectangle textureBounds=new Rectangle(Gdx.graphics.getWidth()-fireBtnTexture.getWidth()-50, Gdx.graphics.getHeight()-50-fireBtnTexture.getHeight(), fireBtnTexture.getWidth(),fireBtnTexture.getHeight());

            Item bobItem = hero.getItem();
            if(textureBounds.contains(touchPos.x, touchPos.y) && (heroFireState == false))
            {
                if(bobItem.position.y <= Gdx.graphics.getHeight()*4/5) {
                    heroFireState = true;
                } else {
                    bobItem.position.y = hero.position.y;
                }
            }
            if(heroFireState) {
                bobItem.setVelocity(new Vector2(0, 12));
                bobItem.velocity.scl(2);
//                Gdx.app.log("INFO", "snow x "+ bobItem.position.toString() + " delta "+ bobItem.getDelta() + " state " + heroFireState + " screen "+ Gdx.graphics.getHeight()*4/5);
                if(bobItem.position.y <= Gdx.graphics.getHeight()*4/5) {
                    bobItem.position.add(bobItem.velocity.x * bobItem.getDelta(), bobItem.velocity.y * 2);
                    // Update snow bound. TODO handle messing between hero.x and snow.x
                    bobItem.setBound(new Rectangle(hero.position.x, bobItem.position.y, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight()));
                    heroFire();
                    // collision
                    checkCollisionHeroToEnemy();
                } else {
                    heroFireState = false;
                    bobItem.setPosition(hero.position.x, hero.position.y);
                    // TODO handle smooth (fire one, impact one) instead of steped fire.
                    // Snow do not fire direct from player to enemy, it fly a distance then wait until next event like touch. So it is a bug.
                    Gdx.app.log("INFO", "Over rooftop " + bobItem.position.toString());
                }
            }
            Gdx.app.log("INFO", "Snow "+ bobItem.getBound().toString()+ "huey "+ huey.getBound().toString() + " dewey "+ dewey.getBound().toString() + " boss " + boss.getHp() + " screen ");
            if(bobItem.position.y > Gdx.graphics.getHeight()*4/5) {
                heroFireState = false;
                bobItem.setPosition(hero.position.x, hero.position.y);
            }
        }
    }

    protected void heroFire() {
        Item bobItem = hero.getItem();
        batch.draw(bobItem.getItemTexture(), hero.position.x+(hero.getBobTexture().getWidth()*2-10), bobItem.position.y+hero.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
    }

    protected void handleHeroMoveBound() {
        bobTexture = hero.getBobTexture();
        float leftBound = 0;
        float rightBound = Gdx.graphics.getWidth() - bobTexture.getWidth();
        // TODO use object instead of global var, handle screen size
        if(hero.position.x < leftBound) {
            hero.position.x = Gdx.graphics.getWidth()/2;
        }
        if(hero.position.x > rightBound) {
            hero.position.x = rightBound;
        }
        if((hero.position.x >= leftBound) && (hero.position.x <= rightBound) ) {
            hero.position.x = (hero.position.x + touchpad.getKnobPercentX() * heroSpeed);
        }
    }

    protected void checkCollisionHeroToEnemy() {
        Rectangle heroItem = hero.getItem().getBound();
        if(heroItem.overlaps(huey.getBound())) {
            huey.loseHp(hero.getItem().getDamage());
        }

        if(heroItem.overlaps(dewey.getBound())) {
            dewey.loseHp(hero.getItem().getDamage());
        }

        if(heroItem.overlaps(boss.getBound())) {
            boss.loseHp(hero.getItem().getDamage());
        }
    }

    protected void checkCollisionEnemyToHero() {

    }

    protected int getGameState() {
        if(hero.isDead()) {
            return 2;
        }
        if(huey.isDead() && dewey.isDead() && boss.isDead()) {
            return 1;
        }

        return 0;
    }

    protected void handleVictoryOrLose() {
        if(getGameState() == GAMESTATE_WIN) {
            game.setScreen(new VictoryScreen(game));
        }
        if(getGameState() == GAMESTATE_LOSE) {
            game.setScreen(new LoseScreen(game));
        }
    }

    protected void updateHeroBullet() {
        hero.setBound(new Rectangle(hero.position.x * sgh_120_1080_screen_ratio, hero.position.y * sgh_120_1080_screen_ratio, hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight()));
    }
    protected void updateEnemyBound() {
        huey.setBound(new Rectangle(huey.position.x * sgh_120_1080_screen_ratio, huey.position.y * sgh_120_1080_screen_ratio, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight()));
        dewey.setBound(new Rectangle(dewey.position.x*sgh_120_1080_screen_ratio, dewey.position.y*sgh_120_1080_screen_ratio, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight()));
        boss.setBound(new Rectangle(boss.position.x*sgh_120_1080_screen_ratio, boss.position.y*sgh_120_1080_screen_ratio, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight()));
    }

    public void check_building(int paramInt1, int paramInt2)
    {
        if ((paramInt1 == 43) && (paramInt2 == 22))
        {
            this.m_mode = 0;
            this.b_item = 0;
            this.s_item = 0;
        }
        else if ((paramInt1 == 71) && (paramInt2 == 22))
        {
            this.m_mode = 1;
            this.b_item = 0;
            this.s_item = 0;
        }
        else if ((paramInt1 == 92) && (paramInt2 == 46))
        {
            this.m_mode = 2;
            this.school = 1;
        }
        else if ((paramInt1 == 71) && (paramInt2 == 70))
        {
            if (this.last_stage > 20) {
                this.m_mode = 3;
            } else {
                this.m_mode = 100;
            }
            this.school = 2;
        }
        else if ((paramInt1 == 43) && (paramInt2 == 70))
        {
            if (this.last_stage > 30) {
                this.m_mode = 4;
            } else {
                this.m_mode = 100;
            }
            this.school = 3;
        }
        else if ((paramInt1 == 22) && (paramInt2 == 46))
        {
            if (this.last_stage > 40) {
                this.m_mode = 5;
            } else {
                this.m_mode = 100;
            }
            this.school = 4;
        }
        else
        {
            this.m_mode = -1;
        }
    }
    public int hero_move(int paramInt1, int paramInt2, int paramInt3)
    {
        if (paramInt3 == 0)
        {
            if ((paramInt2 == 46) && (paramInt1 >= 22) && (paramInt1 <= 92))
            {
                check_building(paramInt1, paramInt2);
                return 1;
            }
            return 0;
        }
        if (paramInt3 == 1)
        {
            if (((paramInt1 == 43) || (paramInt1 == 71)) && (paramInt2 >= 15) && (paramInt2 <= 71))
            {
                check_building(paramInt1, paramInt2);
                return 1;
            }
            return 0;
        }
        return 1;
    }
    public void draw_text() {

    }
    public void draw_text_box() {

    }
//    https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/constant-values.html#javax.microedition.lcdui.Canvas.UP
    public void keyPressed(int paramInt)
    {
        int viewPortHeight = Gdx.graphics.getHeight()*3/4;
        int topBound = viewPortHeight + (int)Gdx.graphics.getHeight()/8;
        int bottomSpace = (int)Gdx.graphics.getHeight()/8; // May be change for fit touch button

        int i;
        int j;
        if ((this.screen == 6) && (this.state == 1))
        {
            if ((getGameAction(paramInt) == 2) || (paramInt == 52))
            {
                if ((this.item_mode == 0) && (this.ppang_item != 2))
                {
                    if (hero.position.x != 2)
                    {
                        hero.position.x -= 1;
                        if (hero.h_idx == 0) {
                            hero.h_idx = 1;
                        } else if (hero.h_idx == 1) {
                            hero.h_idx = 0;
                        }
                    }
                }
                else if (item_mode != 0)
                {
                    if (item_mode != 1) {
                        item_mode -= 1;
                    }
                    message = "Item Mode";
//                    repaint();
                }
            }
            else if ((getGameAction(paramInt) == 5) || (paramInt == 54))
            {
                if ((this.item_mode == 0) && (this.ppang_item != 2))
                {
                    if (hero.position.x != 23)
                    {
                        hero.position.x += 1;
                        if (hero.h_idx == 0) {
                            hero.h_idx = 1;
                        } else if (hero.h_idx == 1) {
                            hero.h_idx = 0;
                        }
                    }
                }
                else if (this.item_mode != 0)
                {
                    if (this.item_mode != 5) {
                        this.item_mode += 1;
                    }
                    this.message = "Item Mode";
//                    repaint();
                }
            }
            else if ((getGameAction(paramInt) == 6) || (paramInt == 56))
            {
                if (mana >= 12) {
//                    use_special();
                } else {
                    message = "Insufficient Mana";
                }
            }
            else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53))
            {
                if (item_mode == 0)
                {
                    if ((pw_up == 0) && (ppang_item != 2))
                    {
                        snow_pw = 0;
                        real_snow_pw = 0;
                        pw_up = 1;
                        hero.h_idx = 2;
                    }
                    else if ((this.pw_up == 1) && (this.real_snow_pw > 0))
                    {
                        hero.h_idx = 4;
//                        make_attack();
                    }
                }
                else
                {
//                    use_item(item_mode - 1);
                    gameOn = true;
                }
            }
            else if (((paramInt == 35) || (paramInt == -7)) && (this.game_state == 0))
            {
                m_mode = 1;
                gameOn = false;
                screen = 100;
//                repaint();
            }
            else if ((paramInt == 51) && (game_state == 0))
            {
                i = 0;
                j = 0;
                while (i < 5)
                {
                    if (item_slot[i] != 0)
                    {
                        j = 1;
                        break;
                    }
                    i++;
                }
                if (j == 1)
                {
                    gameOn = false;
                    message = "Item Mode";
                    item_mode = 1;
//                    repaint();
                }
                else
                {
                    message = "No Item";
                }
            }
        }
        else if (screen == 100)
        {
            if (getGameAction(paramInt) == 1)
            {
                if (m_mode == 1) {
                    m_mode = 5;
                } else {
                    m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6)
            {
                if (m_mode == 5) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            else if (getGameAction(paramInt) == 2)
            {
                if (this.m_mode == 3) {
                    this.s_play = 1;
                }
            }
            else if (getGameAction(paramInt) == 5)
            {
                if (m_mode == 3) {
                    s_play = 2;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
                if (m_mode == 2)
                {
//                    goto_menu();
                }
                else
                {
                    if (this.m_mode == 5)
                    {
//                        SJ.destroyApp(true);
//                        SJ.notifyDestroyed();
                        return;
                    }
                    if (m_mode == 1)
                    {
                        screen = 6;
                        if (item_mode == 0)
                        {
                            gameOn = true;
                        }
                        else
                        {
                            message = "Item Mode";
//                            repaint();
                        }
                    }
                    else if (m_mode == 4)
                    {
                        screen = -5;
                    }
                }
            }
//            repaint();
        }
        else if (this.screen == 2)
        {
            if (getGameAction(paramInt) == 1)
            {
                if (m_mode <= 1) {
                    m_mode = 4;
                } else {
                    m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6)
            {
                if (this.m_mode >= 4) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || ((paramInt >= 49) && (paramInt <= 52)) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    m_mode = (paramInt - 48);
                }
                if (m_mode == 1) {
                    screen = -88;
                }
                if (m_mode == 3)
                {
                    screen = 4;
                    m_mode = 1;
                }
                if (m_mode == 2)
                {
                    m_mode = 1;
                    screen = 5;
                }
                if (m_mode == 4)
                {
//                    SJ.destroyApp(true);
//                    SJ.notifyDestroyed();
                    return;
                }
            }
//            repaint();
        }
        else if (this.screen == 3)
        {
            if (getGameAction(paramInt) == 1)
            {
                j = (int)hero.position.y - 8;
                if (hero_move((int)hero.position.x, j, 1) > 0) {
                    hero.position.y = j;
                }
            }
            else if (getGameAction(paramInt) == 6)
            {
                j = (int)hero.position.y + 8;
                if (hero_move((int)hero.position.x, j, 1) > 0) {
                    hero.position.y = j;
                }
            }
            else if (getGameAction(paramInt) == 5)
            {
                i = (int)hero.position.x + 7;
                if (hero_move(i, (int)hero.position.y, 0) > 0) {
                    hero.position.x = i;
                }
            }
            else if (getGameAction(paramInt) == 2)
            {
                i = (int)hero.position.x - 7;
                if (hero_move(i, (int)hero.position.y, 0) > 0) {
                    hero.position.x = i;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7))
            {
                if ((m_mode == 0) || (m_mode == 1))
                {
//                    loadImage(31);
                    screen = 31;
//                    repaint();
                }
                else if ((m_mode >= 2) && (m_mode <= 5))
                {
                    int k = -1;
                    if ((last_stage / 10 - school == 0) && (last_stage != 45)) {
                        k = last_stage;
                    }
//                    destroyImage(3);
                    message = "Loading";
//                    init_game(k);
                }
            }
            else if ((paramInt == 42) || (paramInt == -6))
            {
//                destroyImage(3);
//                loadImage(2);
                screen = 2;
                m_mode = 1;
            }
//            repaint();
        }
        else if (screen == 31)
        {
            if (getGameAction(paramInt) == 1) {
                s_item = 0;
            }
            if (getGameAction(paramInt) == 6) {
                s_item = 1;
            }
            if (getGameAction(paramInt) == 2)
            {
                if (b_item != 0) {
                    b_item -= 1;
                }
            }
            else if ((getGameAction(paramInt) == 5) && (b_item != 3)) {
                b_item += 1;
            }
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
                if (s_item == 1)
                {
                    m_mode = -1;
//                    destroyImage(31);
                    screen = 3;
                }
                else if (s_item == 0)
                {
                    i = 0;
                    if (m_mode == 0) {
                        i = 4;
                    }
                    if (saved_gold >= item_price[(b_item + i)])
                    {
                        j = input_item(b_item + i + 1);
                        if (j == 1)
                        {
                            saved_gold -= item_price[(b_item + i)];
                            message = "Purchasing Items";
                        }
                        else if (j == 0)
                        {
                            message = "Bag is full";
                        }
                        else if (j == 3)
                        {
                            message = "Duplicated item";
                        }
                    }
                    else
                    {
                        message = "not enough gold";
                    }
                }
            }
//            repaint();
        }
        else if (screen == 4)
        {
            if ((paramInt == 42) || (paramInt == -6))
            {
                screen = 2;
                if (speed == 5) {
                    game_speed = 8;
                }
                if (speed == 4) {
                    game_speed = 17;
                }
                if (speed == 3) {
                    game_speed = 24;
                }
                if (speed == 2) {
                    game_speed = 31;
                }
                if (speed == 1) {
                    game_speed = 38;
                }
                setGameSpeed(speed);

                m_mode = 3;
            }
            if (getGameAction(paramInt) == 1) {
                if (m_mode == 1) {
                    m_mode = 3;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {
                if (m_mode == 3) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            if (m_mode == 1)
            {
                if (getGameAction(paramInt) == 2) {
                    s_play = 1;
                }
                if (getGameAction(paramInt) == 5) {
                    s_play = 2;
                }
            }
            if (m_mode == 2)
            {
                if (getGameAction(paramInt) == 2) {
                    v_mode = 1;
                }
                if (getGameAction(paramInt) == 5) {
                    v_mode = 2;
                }
            }
            if (this.m_mode == 3)
            {
                if ((getGameAction(paramInt) == 2) && (this.speed != 1)) {
                    speed -= 1;
                }
                if ((getGameAction(paramInt) == 5) && (this.speed != 5)) {
                    speed += 1;
                }
            }
//            repaint();
        }
        else if (screen == 5)
        {
            if (getGameAction(paramInt) == 1) {
                if (m_mode == 1) {
                    m_mode = 3;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {
                if (m_mode == 3) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == 51) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    m_mode = (paramInt - 48);
                }
                if (m_mode == 1) {
                    screen = -33;
                }
                if (m_mode == 2) {
                    screen = -33;
                }
                if (m_mode == 3) {
                    screen = -33;
                }
            }
            if ((paramInt == 42) || (paramInt == -6))
            {
                screen = 2;
                m_mode = 2;
            }
//            repaint();
        }
        else if (screen == -5)
        {
            screen = 100;
//            repaint();
        }
        else if (screen == -88)
        {
            if (getGameAction(paramInt) == 1) {
                if (m_mode <= 1) {
                    m_mode = 2;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {
                if (m_mode >= 2) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    m_mode = (paramInt - 48);
                }
                if (m_mode == 1)
                {
                    last_stage = 11;
                    stage = 11;
                    saved_gold = 0;
                    mana = 0;

                }
//                destroyImage(2);
//                loadImage(3);
                hero.position.x = (57/120)*Gdx.graphics.getWidth();
                hero.position.y = (46/160)*viewPortHeight; // TODO reverse top/down of geometry
                this.m_mode = -1;
                this.screen = 3;
            }
            if ((paramInt == 42) || (paramInt == -6))
            {
                this.screen = 2;
                this.m_mode = 1;
            }
//            repaint();
        }
        else if (this.screen == -33)
        {
            if ((paramInt == 42) || (paramInt == -6))
            {
//                loadImage(2);
                m_mode = 1;
                screen = 5;
//                repaint();
            }
        }
        else if (this.screen == 300)
        {
//            MPlay(3);
            m_mode = -1;
//            destroyImage(2);
//            loadImage(3);
            hero.position.x = (57/120)*Gdx.graphics.getWidth()/sgh_120_1080_screen_ratio;
            hero.position.y = (46/120)*(Gdx.graphics.getHeight()*3/4)/sgh_120_1080_screen_ratio;
            saved_gold += gold;
            setSavedGold(saved_gold);
            setSavedMana(mana);

            ani_step = 0;
            screen = 3;
        }
        else if (screen == -1)
        {
//            loadImage(-2);
            screen = -2;
//            repaint();
        }
        else if (screen == -2)
        {
//            loadImage(2);
            screen = 1;
//            repaint();
        }
        else if (screen == 1)
        {
            if ((paramInt == 35) || (paramInt == -7))
            {
                screen = -88;
                m_mode = 1;
            }
            else if ((paramInt == 42) || (paramInt == -6))
            {
                screen = 2;
            }
//            repaint();
        }
        else if (screen == 1000)
        {
//            loadImage(2);
            screen = 300;
//            repaint();
        }
    }

    public void run()
    {
        for (;;)
        {
            if (gameOn)
            {
                if (screen == 6)
                {
                    if (state == 1)
                    {
                        try
                        {
                            Thread.sleep(game_speed);
                        }
                        catch (Exception localException1) {}
                        if (pw_up == 1)
                        {
//                            setPower();
                            if (hero.h_idx == 2) {
                                hero.h_idx = 3;
                            } else if (hero.h_idx == 3) {
                                hero.h_idx = 2;
                            }
                        }
                        else if (pw_up == 2)
                        {
                            if (hero.h_timer < 4)
                            {
                                hero.h_timer += 1;
                                if (hero.h_timer == 4) {
                                    hero.h_idx = 0;
                                }
                            }
                            if (snow_y > snow_last_y)
                            {
                                snow_y -= 1;
                                if (snow_y > snow_top_y) {
                                    snow_gap += 3;
                                } else if (snow_y < snow_top_y) {
                                    snow_gap -= 3;
                                }
                            }
                            else
                            {
//                                check_ppang();
                            }
                        }
                        this.e_time += 1;
                        for (int i = 0; i < e_num; i++)
                        {
                            if (enemies[i].getHp() >= 0)
                            {
                                if ((e_time == enemies[i].e_fire_time) && (boss.get_random(3) != 1) && (enemies[i].e_ppang_item != 2)) {
                                    enemies[i].e_attack_ai(hero, boss, enemies, i);
                                }
                                if (enemies[i].e_ppang_item != 2) {
                                    if (enemies[i].e_idx == 0) {
                                        enemies[i].e_idx = 1;
                                    } else if (enemies[i].e_idx == 1) {
                                        enemies[i].e_idx = 0;
                                    }
                                }
                            }
                            if (enemies[i].e_move_dir >= 100)
                            {
                                enemies[i].e_move_dir += 1;
                                if (enemies[i].e_move_dir == 120) {
                                    enemies[i].e_move_dir = 0;
                                }
                            }
                            else if ((enemies[i].e_move_dir == 0) && (enemies[i].getHp() > 0) && (enemies[i].e_ppang_item != 2))
                            {
                                enemies[i].e_move_ai(enemies, i);
                            }
                            else if ((enemies[i].e_move_dir < 100) && (enemies[i].e_move_dir != 0) && (enemies[i].getHp() > 0))
                            {
                                enemies[i].e_move(enemies, i);
                            }
                        }
                        if (e_boss > 0)
                        {
                            if (boss.getHp() >= 0)
                            {
                                if ((e_time == boss.e_boss_fire_time) && (boss.get_random(3) != 1)) {
                                    if ((this.e_boss == 1) || (this.e_boss == 2)) {
                                        enemies[0].e_attack_ai(hero, boss, enemies, 101);
                                    } else {
                                        enemies[0].e_attack_ai(hero, boss, enemies, 102);
                                    }
                                }
                                if (boss.e_boss_idx == 0) {
                                    boss.e_boss_idx = 1;
                                } else if (boss.e_boss_idx == 1) {
                                    boss.e_boss_idx = 0;
                                }
                            }
                            if (boss.e_boss_move_dir >= 100)
                            {
                                boss.e_boss_move_dir += 1;
                                if (boss.e_boss_move_dir == 115) {
                                    boss.e_boss_move_dir = 0;
                                }
                            }
                            else if ((boss.e_boss_move_dir == 0) && (boss.getHp() > 0))
                            {
                                boss.boss_move_ai();
                            }
                            else if ((boss.e_boss_move_dir != 0) && (boss.getHp() > 0))
                            {
                                boss.boss_move();
                            }
                        }
                        if ((e_num == 3) || (e_num == 4))
                        {
                            if (e_time == 21) {
                                e_time = 0;
                            }
                        }
                        else if ((e_num == 2) && (e_time == 18)) {
                            e_time = 0;
                        }
//                        e_snow();
                        if (gameOn)
                        {
//                            repaint();
//                            serviceRepaints();
                        }
                    }
                    else if (state == 2)
                    {
                        if ((ani_step >= 1) && (ani_step <= 20)) {
                            ani_step += 1;
                        }
                        if (ani_step == 0)
                        {
//                            loadImage(-6);
                            ani_step = 1;
                        }
                        else if ((ani_step >= 1) && (ani_step <= 19))
                        {
//                            repaint();
//                            serviceRepaints();
                        }
                        else if (ani_step == 20)
                        {
//                            destroyImage(-6);
                            state = 1;
                        }
                    }
                    else if (state == 3)
                    {
                        if (game_state == 2)
                        {
                            screen = 201;
//                            MPlay(7);
                            gold = (school * 6 + boss.get_random(7) + 5);
                        }
                        else if (game_state == 1)
                        {
                            screen = 65336;
                            gold = 3;
                        }
                    }
                }
                else if (screen == 8)
                {
                    if ((ani_step < 50) && (ani_step > 0)) {
                        ani_step += 1;
                    }
//                    repaint();
//                    serviceRepaints();
                }
                else if (screen == 9)
                {
                    if ((ani_step < 46) && (ani_step >= 0)) {
                        ani_step += 1;
                    }
//                    repaint();
//                    serviceRepaints();
                }
                else if (screen == 200)
                {
                    if ((ani_step < 51) && (ani_step >= 0))
                    {
                        ani_step += 1;
//                        repaint();
//                        serviceRepaints();
                    }
                    else
                    {
                        gameOn = false;
//                        destroyImage(200);
                        if (state != 10)
                        {
//                            loadImage(2);
                            screen = 300;
                        }
                        else
                        {
                            screen = 1000;
                        }
//                        repaint();
                    }
                }
                else if (screen == 201)
                {
                    ani_step = 0;
                    if (last_stage / 10 == school)
                    {
                        if (stage % 10 != 4)
                        {
                            stage += 1;
                        }
                        else if (stage != 44)
                        {
                            stage += 10;
                            stage = (stage - stage % 10 + 1);
                        }
                        else
                        {
                            stage = 45;
                            state = 10;
                        }
                        last_stage = stage;
                    }

                    screen = 200;
                }
                else if (screen == 65336)
                {
                    item_mode = 0;
                    ani_step = 0;
//                    loadImage(65336);
//                    MPlay(6);
                    screen = 65335;
                }
                else if (screen == 65335)
                {
                    if (ani_step <= 100)
                    {
                        ani_step += 1;
//                        repaint();
//                        serviceRepaints();
                    }
                    else
                    {
                        gameOn = false;
//                        loadImage(2);
                        screen = 300;
//                        repaint();
                    }
                }
            }
            else {
                try
                {
                    Thread.sleep(100L);
                }
                catch (Exception localException2) {}
            }
        }
    }

    public int input_item(int paramInt)
    {
        for (int i = 0; i < 5; i++) {
            if ((item_slot[i] == paramInt) && (paramInt <= 8)) {
                return 3;
            }
        }
        for (int j = 0; j < 5; j++) {
            if (item_slot[j] == 0)
            {
                item_slot[j] = paramInt;
                if (paramInt == 1) {
                    item_a_num = 2;
                } else if (paramInt == 2) {
                    item_b_num = 2;
                } else if (paramInt == 3) {
                    item_c_num = 2;
                } else if (paramInt == 4) {
                    item_d_num = 2;
                }
                return 1;
            }
        }
        return 0;
    }

    /*
    * Simulate J2ME keyCode
    * https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/constant-values.html#javax.microedition.lcdui.Canvas.UP
    * */
    public int getGameAction(int keyCode) {

        return 0;
    }
}