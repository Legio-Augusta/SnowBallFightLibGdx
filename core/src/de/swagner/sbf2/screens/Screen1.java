package de.swagner.sbf2.screens;

import de.swagner.sbf2.Enemy;
import de.swagner.sbf2.Boss;
import de.swagner.sbf2.Hero;
import de.swagner.sbf2.Item;

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
    float enemySpeed = 5.0f;

    private boolean heroFireState = false;

    private int width;
    private int height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis

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

//    private Image imgLogo;
//    private Image imgMM;
//    private Image imgBk;
//    private Image imgSl;
//    private Image imgPl;
//    private Image imgCh;
//    private Image imgBack;
//    private Image imgAl;
//    private Image imgShadow;
//    private Image imgPok;
//    private Image imgPPang;
//    private Image imgPPang1;
//    private Image imgH_ppang;
//    private Image imgSnow_g;
//    private Image imgPwd;
//    private Image[] imgItem;
//    private Image[] imgItem_hyo;
//    private Image imgVill;
//    private Image imgSchool;
//    private Image imgShop;
//    private Image[] imgSpecial;
//    private Image imgSp;
//    private Image[] imgEffect;
//    private Image imgVictory;
//    private Image imgV;
//    private Image imgHero_v;
//    private Image imgLose;
//    private Image imgHero_l;
//    private Image imgStage_num;
//    private Image[] imgStage;

    private int stage;
    private int last_stage = 11;
    private int tmp_stage;
    private int school;
    private int state;

    private int pw_up;
    private int mana = 0;
    private int hp;
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
    private int e_num;
    private int e_t_num;

    private int e_time;
    private int e_dem;
    private int hit_idx;
    private int e_boss;
    private int boss_dis_count;
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

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        generalUpdate();
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
            Gdx.app.log("INFO", "Huey move dir = 0 AI :"+ huey.e_move_dir + " --x--" + huey.position.x + " y " + huey.position.y);
            huey.e_move_ai();
        }
        else if ((huey.e_move_dir < 100) && (huey.e_move_dir != 0) && (!huey.isDead()))
        {
            Gdx.app.log("INFO", "Huey move dir = 0 AI :"+ huey.e_move_dir + " --x--" + huey.position.x + " y " + huey.position.y);
            huey.e_move();
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
            Gdx.app.log("INFO", "Dewey move dir = 0 AI : "+ dewey.e_move_dir + " --x--" + dewey.position.x + " y " + dewey.position.y);
            dewey.e_move_ai2();
        }
        else if ((dewey.e_move_dir < 100) && (dewey.e_move_dir != 0) && (!dewey.isDead()))
        {
            Gdx.app.log("INFO", "Dewey move dir = 0 AI : "+ dewey.e_move_dir + " --x--" + dewey.position.x + " y " + dewey.position.y);
            dewey.e_move2();
        }

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

    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
    }

    private void loadTextures() {
    }

    private void drawBob() {
        int facex = 1;
        if(hero.facingLeft){
        }
        if (hero.state==hero.state.WALKING){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        else if(hero.state==hero.state.IDLE){
            batch.draw(heroTexture, hero.position.x * ppuX, hero.position.y * ppuY, facex* Enemy.SIZE * ppuX, Enemy.SIZE * ppuY);
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
        heroTexture = new Texture("data/samsung-white/hero3_1_60x80.png");
        hero = new Hero(new Sprite());
        hero.position.x = Gdx.graphics.getWidth()/2;
        hero.setBobTexture(new Texture("data/samsung-white/hero3_1_60x80.png"));
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

    protected void testDrawHero() {
        batch.draw(hero.getBobTexture(), (int)hero.position.x, (int)hero.position.y, 0, 0, 45, 60, 5/2, 5/2, 0, 0, 0, 45, 60, hero.facingLeft, false);
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
            if (this.boss_dis_count >= 1)
            {
                this.boss_dis_count += 1;
                if (this.boss_dis_count == 4)
                {
                    this.boss_dis_count = 0;
                    boss.setIdx(0);
                }
            }
            else if (this.boss_dis_count <= -1)
            {
                this.boss_dis_count -= 1;
                if (this.boss_dis_count == -10)
                {
                    this.e_boss = 0;
                    this.boss_dis_count = 0;
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

    protected void displayManualTextureDraw() {
        //  batch.draw(hero, (int)heroX, (int)heroY, 0, 0, 45, 60, 2, 2, 0, 0, 0, 45, 60, false, false);
        //  (Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
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
        int topBoundInCell = (int) (topBound / sgh_120_1080_screen_ratio);
        int bottomBoundInCell = topBoundInCell - 8; // We draw map of 24x32 cell

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
//                Gdx.app.log("INFO", "I like it when I feel your touch");
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
//        bobItem.acting();
        // 10 space between player and snow
//        batch.draw(bobItem.getItemTexture(), bobItem.position.x+(hero.getBobTexture().getWidth()*2-10), bobItem.position.y+hero.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
        batch.draw(bobItem.getItemTexture(), hero.position.x+(hero.getBobTexture().getWidth()*2-10), bobItem.position.y+hero.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
    }

    // TODO firePos, itemType, power, direction, angle (gap)
    protected void fireItem() {

    }

    protected void heroHitTarget() {

    }

    protected void enermyHitTarget() {

    }

    protected void handleHeroMoveBound() {
        bobTexture = hero.getBobTexture();
        float leftBound = 0;
        float rightBound = Gdx.graphics.getWidth() - bobTexture.getWidth();
        // TODO use object instead of global var, handle screen size
        if(hero.position.x < leftBound) {
//            hero.position.x = leftBound;
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
        Gdx.app.log("INFO", "Hero fire " + heroItem.toString());
        Gdx.app.log("INFO", "Huey fire " + huey.getBound().toString());
        Gdx.app.log("INFO", "Boss fire " + boss.getBound().toString());
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
}