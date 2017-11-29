package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

/**
 * Created by nickfarow on 13/10/2016.
 */

public class GameScreen extends DefaultScreen {
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture ttrSplash;
    Texture snowWhiteBg;
    Texture heroTexture;
    Texture fireBtnTexture;

    float heroSpeed = 0.3f; // 1 cell step (screen width devided to about 24 cell).

    private boolean heroFireState = false;

    private Hero hero;
    private Boss boss;
    private Enemy[] enemies;

    private Texture bobTexture;

    private Stage touch_stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;

    public static final String TAG = "LOG";

    public static int GAMESTATE_WIN = 1;
    public static int GAMESTATE_LOSE = 2;
    public static int GAMESTATE_PLAYING = 0;
    // Ratio 3:4 ~ 9:12 So with ratio 9:16 we lost (not use) 4/16 = 1/4 of height.
    // Ie. 1920 we will cut 1/4 = 480px to keep ratio 3:4 1080:1440.
    // Bottom space used for fireBtn, so top should space only 240px
    private static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private static int SGH_120_CELL = 5; // 5 pixel per cell in original 120px SGH
    private static int SGH_SCALE_RATIO = (int)Gdx.graphics.getWidth()/120; // 120 or 128px from original J2ME resolution.
    private static int CELL_WIDTH = SGH_120_CELL*SGH_SCALE_RATIO;
    private static int VIEW_PORT_HEIGHT = (int)SCREEN_HEIGHT*3/4;
    private static int TOP_BOUND = VIEW_PORT_HEIGHT + (int)SCREEN_HEIGHT/8;
    private static int BOTTOM_SPACE = (int)SCREEN_HEIGHT/8; // May be change for fit touch button
    // TODO scale sprite texture (on draw())
    // With ratio 4:3 on SGH X550 or X357 120x160, SGH 930 (S7) 1080x1920, to keep ratio 4:3 we need crop 1920 to use only 1440.
    // On 380px space, place 180 or 200px for fire, move btn. 200px on top is space.
    // So on J2ME original enemy seem to move on 1/4 of screen height (3 cell +1 /32 cell 5px).
    // That mean enemy should move on 1080 to 1440 on 360px vertical distance.
    private static int SMALL_GAP = 32; // 32px for gap

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
    private int screen = -1; //-1; 6 = running
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
    private int dem; // damage
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
    private int e_num = 2; // set dafaut value for debug avoid eceed 2 item size TODO init then remove this
    private int e_t_num;

    private int e_time;
    private int e_dem; // enemy damage
    private int hit_idx;
    private int e_boss; // TODO init this and remove debug. The flag (and/or number) of boss enemy
    private int al;
    private int d_gauge; // gauge rule (power fire)
    private int game_action = 0;
    private static final int GAME_ACTION_OK = 8; // simulate KEY, gameAction in J2ME
    private static final int GAME_ACTION_LEFT = 2;
    private static final int GAME_ACTION_RIGHT = 5;
    private static final int GAME_ACTION_UP = 8;
    private static final int GAME_ACTION_DOWN = 6;
    private static final int KEY_RIGHT_MENU = 35;
    private static final int KEY_STAR = 0;
    private static final int KEY_NUM_3 = 0; // for item mode
    private static final int KEY_SHARP = 0;

    private ShapeRenderer shapeRenderer;

    private Texture imgLogo;
    private Texture imgMM;
    private Texture imgBk;
    private Texture imgSl;
    private Texture imgPl;
    private Texture imgCh;
    private Texture imgBack;
    private Texture imgAl;
    private Texture imgShadow;
    private Texture imgPok;
    private Texture imgPPang;
    private Texture imgPPang1;
    private Texture imgH_ppang;
    private Texture imgSnow_g;
    private Texture imgPwd;
    private Texture [] imgItem;
    private Texture [] imgItem_hyo;
    private Texture imgVill;
    private Texture imgSchool;
    private Texture imgShop;
    private Texture [] imgSpecial;
    private Texture imgSp;
    private Texture [] imgEffect;
    private Texture imgVictory;
    private Texture imgV;
    private Texture imgHero_v;
    private Texture imgLose;
    private Texture imgHero_l;
    private Texture imgStage_num;
    private Texture ui;
    private Texture [] imgStage;

/*    public GameScreen(Game game) {
        super(game);
        item_price[0] = 5;
        item_price[1] = 8;
        item_price[2] = 8;
        item_price[3] = 14;
        item_price[4] = 6;
        item_price[5] = 12;
        item_price[6] = 10;
        item_price[7] = 12;
//        printScore("hero", 0);
//        printScore("config", 1);
        item_slot[0] = 3;
        item_slot[1] = 5;
        stage = last_stage = 11; // TODO use sharedPreference

        camera = newOrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        // Calculate global var width/height, view port ...
        create();
        init_game(-1);
    }*/
    public GameScreen(Game game, int screen) {
        super(game);
        item_price[0] = 5;
        item_price[1] = 8;
        item_price[2] = 8;
        item_price[3] = 14;
        item_price[4] = 6;
        item_price[5] = 12;
        item_price[6] = 10;
        item_price[7] = 12;
//        printScore("hero", 0);
//        printScore("config", 1);
        item_slot[0] = 3;
        item_slot[1] = 5;
        stage = last_stage = 11; // TODO use sharedPreference

        camera = newOrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        // Calculate global var width/height, view port ...
        create();
        screen = screen;

        Gdx.app.log("INFO", "Inside screen = " + screen);
        init_game(-1);
    }

    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        //Create camera
        float aspectRatio = (float) SCREEN_WIDTH / (float) SCREEN_HEIGHT;
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
        touchpad.setBounds(15, 15, 200, 200);

        //Create a Stage and add TouchPad
        touch_stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT), batch);
        touch_stage.addActor(touchpad);
        Gdx.input.setInputProcessor(touch_stage);

        loadTextures();
    }

    public void update() {

    }

    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
//        monitorEnemyPosition();

        batch.enableBlending();

        batch.begin();

        int j;
        if (screen == 6)        // normal playing screen, TODO may be use constants name
        {
            drawRunningScreen();
        }
        else if (screen == 2) {
            drawInstructionScreen();
        }
        else if (screen == 3) {
            drawVillageScreen();
        }
        else if (screen == 31) {
            drawShopScreen();
        }
        else if (screen == 100) {
            drawSoundSettingScreen();
        }
        else if (screen == -88) {
            drawNewGameMenu();
        }
        else if (screen == 8) {
            drawSpecialAnimation();
        }
        else if (screen == 9) {
            drawSpecialScreen();
        }
        else if (screen == 4) {
            drawSoundSpeedSetting();
        }
        else if (screen == 5) {
            drawGuideMenu();
        }
        else if (screen == -33) {
            drawListItems();
        }
        else if (screen == 200) {
            drawVictoryScreen();
        }
        else if (screen == 65335) {
            drawLoseScreen();
        }
        else if (screen == 300) {
            drawGoodJob();
        }
        else if (screen == 77) {
            drawTextScreen();
        }
        else if (screen == -1) {
//            drawLogoScreen();
            game.setScreen(new LogoScreen(game));
            screen = 6;
        }
        else if (screen == -2) {
//            drawSamsungLogo();
        }
        else if (screen == 1000) {
            drawAllClear();
        }
        else if (screen == -5) {
            drawManualScreen();
        }
        else if (screen == 1) {
//            drawTitleScreen();
        }

//        batch.setProjectionMatrix(camera.combined);

        state = 1;
        run();

        batch.end();

        drawTouchPad();

        batch.begin();
        drawFireBtn();
        handleFireTouch();

//        drawInitPositionEnermy();
        batch.end();

        handleVictoryOrLose();
    }

    private void loadTextures() {
        imgLogo = new Texture("data/samsung-white/logo.png");
        imgMM = new Texture("data/samsung-white/mm.png");
        imgBk = new Texture("data/samsung-white/bk.png");
        imgSl = new Texture("data/samsung-white/sl.png");
        imgPl = new Texture("data/samsung-white/play.png");
        imgCh = new Texture("data/samsung-white/check.png");
        imgBack = new Texture("data/samsung-white/back1.png");
        imgAl = new Texture("data/samsung-white/al.png");
        imgShadow = new Texture("data/samsung-white/shadow0.png");
        imgPok = new Texture("data/samsung-white/pok.png");
        imgPPang = new Texture("data/samsung-white/bbang0.png");
        imgPPang1 = new Texture("data/samsung-white/bbang1.png");
        imgH_ppang = new Texture("data/samsung-white/h_bbang.png");
        imgSnow_g = new Texture("data/samsung-white/snow_gauge.png");
        imgPwd = new Texture("data/samsung-white/power.png");

        // TODO init Item object item value, not just Texture
        imgItem = new Texture[9];
        for (int m = 0; m < 9; m++) {
            imgItem[m] = new Texture("data/samsung-white/item" + m + ".png");
        }
        imgItem_hyo = new Texture[2];
        imgItem_hyo[0] = new Texture("data/samsung-white/hyo0.png");
        imgItem_hyo[1] = new Texture("data/samsung-white/hyo1.png");

        imgVill = new Texture("data/samsung-white/village.png");
        imgSchool = new Texture("data/samsung-white/school.png");
        imgShop = new Texture("data/samsung-white/shop0.png");
        imgSpecial = new Texture[3];
        for (int i = 0; i < 3; i++) {
            imgSpecial[i] = new Texture("data/samsung-white/special" + i + ".png");
        }
        imgSp = new Texture("data/samsung-white/sp1.png");
        // TODO use texture region
        imgEffect = new Texture[2];
        imgEffect[0] = new Texture("data/samsung-white/effect0.png");
        imgEffect[0] = new Texture("data/samsung-white/effect1.png");

        imgVictory = new Texture("data/samsung-white/victory.png");
        imgV = new Texture("data/samsung-white/v.png");
        imgHero_v = new Texture("data/samsung-white/hero-vic.png");
        imgLose = new Texture("data/samsung-white/lose.png");
        imgHero_l = new Texture("data/samsung-white/hero-lose.png");
        imgStage_num = new Texture("data/samsung-white/word-1.png");
        ui = new Texture("data/samsung-white/ui.png");  // h:160p (1080p)
        imgStage = new Texture[5];
        for (int i = 0; i < 5; i++) {
            imgStage[i] = new Texture("data/samsung-white/word-" + i + ".png");
        }

    }

    public void monitorEnemyPosition() {
        enemyMoving();
        updateEnemyBound();
    }

    @Override
    public void show() {
        Gdx.app.log("INFO", "Screen = " + screen);
        Gdx.app.log("INFO", "Debug Item prices = " + item_price[0]);
    }

    @Override
    public void pause() {
        // TODO save game state
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

    public void initEnemy() {
        if(e_num <= 2) {
            e_num = 2;
        }
        boss = new Boss(new Vector2(SCREEN_WIDTH/2/CELL_WIDTH, TOP_BOUND/CELL_WIDTH-6 ));
        boss.setBobTexture("data/samsung-white/boss2_0_120x.png");

        enemies = new Enemy[e_num];
        for (int i = 0; i < enemies.length; i++) {
            // Add some random on start position of enemies
            int enemyPosY = TOP_BOUND - 3*CELL_WIDTH;
            int enemyStartPositionX = (int) SCREEN_WIDTH / 2 / CELL_WIDTH;
            if(i%2 == 0) {
                enemyStartPositionX += boss.get_random(6);
            } else {
                enemyStartPositionX -= boss.get_random(6);
            }
            enemies[i] = new Enemy(new Vector2(enemyStartPositionX, enemyPosY ));
            enemies[i].setBobTexture("data/samsung-white/enemy0_0_106x.png");
            enemies[i].setBound(new Rectangle(enemies[i].position.x*CELL_WIDTH, enemies[i].position.y*CELL_WIDTH, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight()));
        }

        for(int i=0; i < e_num; i++) {
            Vector2 e_facing = new Vector2(enemies[i].position.x, enemies[i].position.y);
            Item e_item = new Item(0, enemies[i].position, e_facing);
            e_item.setTexture(new Texture("data/samsung-white/item3_0_36x.png"));
            enemies[i].setItem(e_item);
            enemies[i].getItem().setBound(new Rectangle(e_item.getX(), e_item.getY(), e_item.getWidth(), e_item.getHeight()) );
        }

        Vector2 boss_facing = new Vector2(boss.position.x, boss.position.y);
        Item boss_item = new Item(0, boss.position, boss_facing);
        boss_item.setTexture(new Texture("data/samsung-white/item3_0_36x.png"));
        boss.setItem(boss_item);
        boss.getItem().setBound(new Rectangle(boss_item.getX(), boss_item.getY(), boss_item.getWidth(), boss_item.getHeight()) );
    }
    public void init_game(int paramInt)
    {
        initSpriteBatchAndHeroTexture();
        initEnemy();
//        game.setScreen(new LogoScreen(game));

//        screen = 77;

//        repaint(); // TODO find gdx equivalent method or handle this function. May be multi Screen help ? Does global vars remain ?
//        serviceRepaints();
        game_state = 0;
        p_mode = 1;
        hero.position.x = 5;
        hero.position.y = 8 + (int)((BOTTOM_SPACE+ui.getHeight())/CELL_WIDTH); // orig 8
        hero.h_idx = 0;
        hero.setHp(hero.getMaxHp()); //106
        wp = 0;
        hero.pw_up = 0;
        hero.snow_pw = 0;
        hero.real_snow_pw = 0;
        dem = 12;
        ppang = 0;
        al = -1;
        ppang_time = 0;
        ppang_item = 0;
        make_enemy(paramInt);
        d_gauge = 2;

//        screen = 6;
        item_mode = 0;
//        loadImage(6);
//        loadImage(100);
        if (e_boss > 0) {
//            loadImage(7);
        }
        state = 2;
        ani_step = 0;
//        startThread();
        gameOn = true;

        Gdx.app.log("INFO", "inside init screen= " + screen);
    }

    protected void initBobItem() {
        // TODO we do not need facing so may be remove it
        Vector2 facing = new Vector2(hero.position.x, SCREEN_HEIGHT*4/5/CELL_WIDTH);
        Item item = new Item(0, hero.position, facing);
        item.setTexture(new Texture("data/samsung-white/item3_0_36x.png"));
        hero.setItem(item);
        hero.getItem().setBound(new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight()) );
    }

    protected void initSpriteBatchAndHeroTexture () {
//        batch = new SpriteBatch();
        snowWhiteBg = new Texture("data/samsung-white/white_bg.png");
        heroTexture = new Texture("data/samsung-white/hero3_1_120x.png");
        hero = new Hero(new Sprite());
        hero.position.x = SCREEN_WIDTH/2/CELL_WIDTH;
        hero.setBobTexture(new Texture("data/samsung-white/hero3_1_120x.png"));
        hero.position.y = (BOTTOM_SPACE+ui.getHeight()+SMALL_GAP)/CELL_WIDTH;

        fireBtnTexture = new Texture("data/samsung-white/fire.png");
        initBobItem();
    }

    protected void drawSplashBatch() {
//        batch.disableBlending();
        batch.begin();
//        batch.draw(ttrSplash, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.draw(snowWhiteBg, 0, BOTTOM_SPACE, SCREEN_WIDTH, VIEW_PORT_HEIGHT+BOTTOM_SPACE);
        batch.end();
    }

    /*
    * Try use old J2ME logic to render position of enemy
    * */
    protected void drawInitPositionEnermy() {
        for(int i=0; i < e_num; i++) {
            if(!enemies[i].isDead()) {
                batch.draw(enemies[i].getBobTexture(), (int)enemies[i].position.x*CELL_WIDTH, (int)enemies[i].position.y*CELL_WIDTH, 0, 0, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight(), 1, 1, 0, 0, 0, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight(), enemies[i].facingLeft, false);
            }
        }
        if(!boss.isDead()) {
            batch.draw(boss.getBobTexture(), (int)boss.position.x*CELL_WIDTH, (int)boss.position.y*CELL_WIDTH, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 1, 1, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), boss.facingLeft, false);
            boss.facingLeft = !boss.facingLeft;
        }
    }

    public void draw_sp_hyo() {
        for (int i = 0; i < e_num; i++) {
            if (hero.getHp() >= 0)
            {
//                batch.setColor(16711680);
//                batch.fillRect(this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15);
//                batch.setColor(9672090);
//                batch.fillRect(this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15 - 15 * this.e_hp[i] / this.max_e_hp[i]);
                if (ppang <= 51) {
                    batch.draw(imgEffect[0], enemies[i].position.x * 5 * SGH_SCALE_RATIO, (enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO );
                } else if (ppang <= 54) {
                    batch.draw(imgEffect[1], enemies[i].position.x * 5 * SGH_SCALE_RATIO, (enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO ) ;
                }
            }
        }
        if ((boss.getHp() >= 0) && (e_boss > 0))
        {
//            paramGraphics.setColor(16711680);
//            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15);
//            paramGraphics.setColor(9672090);
//            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp);
            if (ppang <= 51) {
                batch.draw(imgEffect[0], boss.position.x * 5 * SGH_SCALE_RATIO, (boss.position.y * 5 + 5)*SGH_SCALE_RATIO );
            } else if (ppang <= 54) {
                batch.draw(imgEffect[1], boss.position.x * 5 * SGH_SCALE_RATIO, (boss.position.y * 5 + 6)*SGH_SCALE_RATIO );
            }
        }
        if (ppang != 55) {
            ppang += 1;
        }
        else {
            for (int j = 0; j < e_num; j++) {
                if (enemies[j].getHp() > 0)
                {
                    if (special == 2) {
                        enemies[j].e_ppang_time = 65;
                        enemies[j].e_ppang_item = 2;
                    }
                    else if (special == 3) {
                        enemies[j].e_ppang_time = 80;
                        enemies[j].e_ppang_item = 1;
                        enemies[j].e_lv = (-enemies[j].e_lv);
                    }
                    enemies[j].e_move_dir = 0;
                }
            }
            ppang = 0;
            special = 0;
        }
    }

    /*
    * J2ME original port to gdx
    * */
    public void draw_enemy() {
        for (int i = 0; i < enemies.length; i++) {
            if ((int)enemies[i].position.x != -10) {
                batch.draw(enemies[i].getBobTexture(), (int) enemies[i].position.x * 5 * SGH_SCALE_RATIO, enemies[i].position.y * 5 * SGH_SCALE_RATIO + 5, 0, 0, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight(), 1, 1, 0, 0, 0, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight(), false, false);
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
                        e_t_num -= 1;
                        if ((e_t_num == 0) && (e_boss == 0)) {
                            item_mode = 0;
                            game_state = 2;
                            state = 3;
                            gameOn = true;
                        }
                    }
                }
            }
        }

        if (e_boss > 0)
        {
            batch.draw(boss.getBobTexture(), (int)boss.position.x * 5 *SGH_SCALE_RATIO , (boss.position.y * 5)*SGH_SCALE_RATIO, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 1, 1, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), false, false);
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
                    e_boss = 0;
                    boss.boss_dis_count = 0;
                    if (e_t_num == 0)
                    {
                        item_mode = 0;
                        game_state = 2;
                        state = 3;
                        gameOn = true;
                    }
                }
            }
        }
    }

    /*
    * Try use original e_move() from J2ME.
    * */
    protected void enemyMoving() {
        int rightBoundEnemy = (int)(SCREEN_WIDTH - enemies[0].getBobTexture().getWidth())/CELL_WIDTH;
        int rightBoundBoss = (int)((SCREEN_WIDTH - boss.getBobTexture().getWidth())/CELL_WIDTH);

        int topBoundInCell = (int) (TOP_BOUND / CELL_WIDTH);
        int bottomBoundInCell = topBoundInCell - 8; // We draw map of 24x32 cell

        for(int i = 0; i < e_num; i++) {
            enemies[i].check_move_outof_bound(0, rightBoundEnemy, bottomBoundInCell, topBoundInCell);
        }

        boss.check_move_outof_bound(0, rightBoundBoss, bottomBoundInCell-7, topBoundInCell);
    }
    protected void updateEnemyBound() {
        for(int i = 0; i < e_num; i++) {
            enemies[i].setBound(new Rectangle(enemies[i].position.x * CELL_WIDTH, enemies[i].position.y * CELL_WIDTH, enemies[i].getBobTexture().getWidth(), enemies[i].getBobTexture().getHeight()));
        }
        boss.setBound(new Rectangle(boss.position.x*CELL_WIDTH, boss.position.y*CELL_WIDTH, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight()));
    }

    protected void drawFireBtn() {
        batch.draw(fireBtnTexture, SCREEN_WIDTH-50-fireBtnTexture.getWidth(), 50, fireBtnTexture.getWidth(), fireBtnTexture.getHeight());
    }

    protected void drawTouchPad() {
        camera.update();

        handleHeroMoveBound();
        updateHeroBullet();

        //Draw
        batch.enableBlending();
//        batch.begin();
        //blockSprite.draw(batch);

//        batch.draw(hero.getBobTexture(), hero.position.x*CELL_WIDTH, hero.position.y*CELL_WIDTH, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), 1, 1, 0, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), hero.facingLeft, false);
//        hero.facingLeft = !hero.facingLeft;

//        batch.end();
        touch_stage.act(Gdx.graphics.getDeltaTime());
        touch_stage.draw();
    }

    protected void handleFireTouch() {
        if(Gdx.input.isTouched())
        {
            game_action = GAME_ACTION_OK;
            Vector3 touchPos=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            // TODO find out why unproject make position wrong. Unproject deal with zoom or similar variables in touch screen
            // When zoom in/out ... the extract position may be difference. So the pointer pos after unproject may be reset to 0

            // camera.unproject(touchPos);
            Rectangle textureBounds=new Rectangle(SCREEN_WIDTH-fireBtnTexture.getWidth()-50, SCREEN_HEIGHT-50-fireBtnTexture.getHeight(), fireBtnTexture.getWidth(),fireBtnTexture.getHeight());

            Item bobItem = hero.getItem();
            if(textureBounds.contains(touchPos.x, touchPos.y) && (heroFireState == false))
            {
                if(bobItem.position.y <= SCREEN_HEIGHT*4/5/CELL_WIDTH) {
                    heroFireState = true;
                } else {
                    bobItem.position.y = hero.position.y;
                }
            }
            if(heroFireState) {
                bobItem.setVelocity(new Vector2(0, 12));
                bobItem.velocity.scl(2);
                if(bobItem.position.y <= SCREEN_HEIGHT*4/5/CELL_WIDTH) {
                    bobItem.position.add(bobItem.velocity.x * bobItem.getDelta()/CELL_WIDTH, bobItem.velocity.y * 2/CELL_WIDTH);
                    bobItem.setBound(new Rectangle(hero.position.x*CELL_WIDTH, bobItem.position.y*CELL_WIDTH, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight()));
                    heroFire();
                    checkCollisionHeroToEnemy();
                } else {
                    heroFireState = false;
                    bobItem.setPosition(hero.position.x, hero.position.y);
                    // TODO handle smooth (fire one, impact one) instead of steped fire.
                    // Snow do not fire direct from player to enemy, it fly a distance then wait until next event like touch. So it is a bug.
                    Gdx.app.log("INFO", "Over rooftop " + bobItem.position.toString());
                }
            }
            Gdx.app.log("INFO", "Snow "+ bobItem.getBound().toString()+ "huey "+ enemies[0].getBound().toString() + " dewey "+ enemies[1].getBound().toString() + " boss " + boss.getHp() + " screen ");
            if(bobItem.position.y > SCREEN_HEIGHT*4/5) {
                heroFireState = false;
                bobItem.setPosition(hero.position.x, hero.position.y);
            }
        }
    }

    protected void heroFire() {
        Item bobItem = hero.getItem();
        batch.draw(bobItem.getItemTexture(), hero.position.x*CELL_WIDTH+(hero.getBobTexture().getWidth()), bobItem.position.y*CELL_WIDTH+hero.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
    }

    protected void handleHeroMoveBound() {
        bobTexture = hero.getBobTexture();
        float leftBound = 0;
        float rightBound = (int)(SCREEN_WIDTH - bobTexture.getWidth())/CELL_WIDTH;
        // TODO use object instead of global var, handle screen size
        if(hero.position.x < leftBound) {
            hero.position.x = 0;
        }
        if(hero.position.x > rightBound) {
            hero.position.x = rightBound;
        }
        if((hero.position.x >= leftBound) && (hero.position.x <= rightBound) ) {
            hero.position.x = (hero.position.x + touchpad.getKnobPercentX() * heroSpeed);
        }
        // TODO find where touch event (dragged) call, can it be overrided ?
        // convert touch event to key event (getGameAction)
        if(touchpad.getKnobPercentX() > 0) {
            game_action = GAME_ACTION_RIGHT;
        } else if(touchpad.getKnobPercentX() < 0){
            game_action = GAME_ACTION_LEFT;
        }
        if(touchpad.getKnobPercentY() > 0) {
            game_action = GAME_ACTION_UP;
        } else if(touchpad.getKnobPercentY() < 0) {
            game_action = GAME_ACTION_DOWN;
        }

        keyPressed();
    }

    protected void checkCollisionHeroToEnemy() {
        Rectangle heroItem = hero.getItem().getBound();
        for(int i=0; i < e_num; i++) {
            if(heroItem.overlaps(enemies[i].getBound())) {
                enemies[i].loseHp(hero.getItem().getDamage());
            }
        }

        if(heroItem.overlaps(boss.getBound())) {
            boss.loseHp(hero.getItem().getDamage());
        }
    }

    protected int getGameState() {
        if(hero.isDead()) {
            return 2;
        }
        if(boss.isDead()) {
            int temp_state = 1;
            for(int i=0; i < e_num; i++) {
                if(!enemies[i].isDead()) {
                    temp_state = 0;
                    return 0;
                }
            }
            return temp_state;
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
        hero.setBound(new Rectangle(hero.position.x * CELL_WIDTH, hero.position.y * CELL_WIDTH, hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight()));
    }

    public void check_building(int paramInt1, int paramInt2) {
        if ((paramInt1 == 43) && (paramInt2 == 22))
        {
            m_mode = 0;
            b_item = 0;
            s_item = 0;
        }
        else if ((paramInt1 == 71) && (paramInt2 == 22))
        {
            m_mode = 1;
            b_item = 0;
            s_item = 0;
        }
        else if ((paramInt1 == 92) && (paramInt2 == 46))
        {
            m_mode = 2;
            school = 1;
        }
        else if ((paramInt1 == 71) && (paramInt2 == 70))
        {
            if (last_stage > 20) {
                m_mode = 3;
            } else {
                m_mode = 100;
            }
            this.school = 2;
        }
        else if ((paramInt1 == 43) && (paramInt2 == 70))
        {
            if (last_stage > 30) {
                m_mode = 4;
            } else {
                m_mode = 100;
            }
            this.school = 3;
        }
        else if ((paramInt1 == 22) && (paramInt2 == 46))
        {
            if (last_stage > 40) {
                m_mode = 5;
            } else {
                m_mode = 100;
            }
            school = 4;
        }
        else
        {
            m_mode = -1;
        }
    }
    public int hero_move(int paramInt1, int paramInt2, int paramInt3) {
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
        }
        return 1;
    }
    public void draw_text() {

    }
    public void draw_item() {
        if (del == -1) {
            for (int i = 0; i < 5; i++) {
                if (item_slot[i] != 0) {
                    batch.draw(imgItem[item_slot[i]], 12 * i + 37, 111); // 20 as J2ME canvas anchor
                }
            }
        }
        else {
//            batch.setColor(6974058);
//            batch.fillRect(this.del * 12 + 37, 111, 8, 8);
            this.del = -1;
        }
    }

    /*
    *    https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/constant-values.html#javax.microedition.lcdui.Canvas.UP
    *    key code = -5 game action = 8 OK
    *    key code = 35 game action = 0 #
    *    key code = -2 game action = 6 DOWN
    *    key code = -4 game action = 5 LEFT
    *    key code = -1 game action = 1 UP ~ OK
    *    key code = 35 game action = 0 #
    *    key code = 49 game action = 9 KEY_2 = UP ?
    *    key code = 51 game action = 10 KEY_3 (use item)
    *    key code = -7 game action = 0 RIGHT_MENU
    */
    public void keyPressed() {
        int paramInt = 0;
        int i;
        int j;
        if ((screen == 6) && (state == 1))
        {
            if ((getGameAction(paramInt) == 2) || (paramInt == 52)) // NUM_4 or left key
            {
                if ((item_mode == 0) && (ppang_item != 2))
                {
                    if ((int)hero.position.x != 2)
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
            else if ((getGameAction(paramInt) == 5) || (paramInt == 54)) // NUM_6 or RIGHT_KEY (may be LEFT_KEY by keyboard view)
            {
                if ((item_mode == 0) && (ppang_item != 2))
                {
                    if ((int)hero.position.x != 23)
                    {
                        hero.position.x += 1;
                        if (hero.h_idx == 0) {
                            hero.h_idx = 1;
                        } else if (hero.h_idx == 1) {
                            hero.h_idx = 0;
                        }
                    }
                }
                else if (item_mode != 0)
                {
                    if (item_mode != 5) {
                        item_mode += 1;
                    }
                    message = "Item Mode";
//                    repaint();
                }
            }
            else if ((getGameAction(paramInt) == 6) || (paramInt == 56)) // NUM_8 or DOWN KEY
            {
                if (mana >= 12) {
//                    use_special();
                } else {
                    message = "Insufficient Mana";
                }
            }
            // KEY_UP = 50 (fire can be use up key), -5 = OK keycode
            else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53)) // OK, NUM_5 etc
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
                    else if ((pw_up == 1) && (real_snow_pw > 0))
                    {
                        hero.h_idx = 4;
                        hero.make_attack();
                    }
                }
                else
                {
//                    use_item(item_mode - 1);
                    gameOn = true;
                }
            }
            else if (((paramInt == 35) || (paramInt == -7)) && (game_state == 0)) // RIGHT_MENU, # (pound_key) => options|# action
            {
                m_mode = 1;
                gameOn = false;
                screen = 100;
//                repaint();
            }
            else if ((paramInt == 51) && (game_state == 0)) // ITEM_MODE (NUM_3)
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
            if (getGameAction(paramInt) == 1) // UP
            {
                if (m_mode == 1) {
                    m_mode = 5;
                } else {
                    m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6) // DOWN
            {
                if (m_mode == 5) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            else if (getGameAction(paramInt) == 2)
            {
                if (m_mode == 3) {
                    s_play = 1;
                }
            }
            else if (getGameAction(paramInt) == 5) // LEFT
            {
                if (m_mode == 3) {
                    s_play = 2;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) { // 35, -7 = right menu, action 8 = OK
                if (m_mode == 2)
                {
//                    goto_menu();
                }
                else
                {
                    if (m_mode == 5)
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
            if (getGameAction(paramInt) == 1) // UP
            {
                if (m_mode <= 1) {
                    m_mode = 4;
                } else {
                    m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6) // DOWN
            {
                if (m_mode >= 4) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            // RIGHT MENU ?
            // KEY_NUM1	49
            // KEY_NUM2	50
            // KEY_NUM3	51
            // KEY_NUM4	52
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || ((paramInt >= 49) && (paramInt <= 52)) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    m_mode = (paramInt - 48); // - keycode to get mode
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
        else if (screen == 3)
        {
            if (getGameAction(paramInt) == 1) // UP
            {
                j = (int)hero.position.y - 8;
                if (hero_move((int)hero.position.x, j, 1) > 0) {
                    hero.position.y = j;
                }
            }
            else if (getGameAction(paramInt) == 6) // DOWN
            {
                j = (int)hero.position.y + 8;
                if (hero_move((int)hero.position.x, j, 1) > 0) {
                    hero.position.y = j;
                }
            }
            else if (getGameAction(paramInt) == 5) // LEFT
            {
                i = (int)hero.position.x + 7;
                if (hero_move(i, (int)hero.position.y, 0) > 0) {
                    hero.position.x = i;
                }
            }
            else if (getGameAction(paramInt) == 2) // UP ?
            {
                i = (int)hero.position.x - 7;
                if (hero_move(i, (int)hero.position.y, 0) > 0) {
                    hero.position.x = i;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) // RIGHT or OK
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
                    init_game(k);
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
            if (getGameAction(paramInt) == 1) { // UP
                s_item = 0;
            }
            if (getGameAction(paramInt) == 6) { // DOWN
                s_item = 1;
            }
            if (getGameAction(paramInt) == 2)
            {
                if (b_item != 0) {
                    b_item -= 1;
                }
            }
            else if ((getGameAction(paramInt) == 5) && (b_item != 3)) { // LEFT
                b_item += 1;
            }
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) { //  RIGHT menu or OK
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
            if (getGameAction(paramInt) == 1) { // UP
                if (m_mode == 1) {
                    m_mode = 3;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) { // DOWN
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
                if (getGameAction(paramInt) == 5) { // LEFT
                    s_play = 2;
                }
            }
            if (m_mode == 2)
            {
                if (getGameAction(paramInt) == 2) {
                    v_mode = 1;
                }
                if (getGameAction(paramInt) == 5) { // LEFT
                    v_mode = 2;
                }
            }
            if (this.m_mode == 3)
            {
                if ((getGameAction(paramInt) == 2) && (speed != 1)) {
                    speed -= 1;
                }
                if ((getGameAction(paramInt) == 5) && (speed != 5)) { // LEFT
                    speed += 1;
                }
            }
//            repaint();
        }
        else if (screen == 5)
        {
            if (getGameAction(paramInt) == 1) { // UP
                if (m_mode == 1) {
                    m_mode = 3;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) { // LEFT
                if (m_mode == 3) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            // RIGHT MENU or RIGHT, KEY_NUM
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
            if ((paramInt == 42) || (paramInt == -6)) // * STAR
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
            if (getGameAction(paramInt) == 1) { // UP
                if (m_mode <= 1) {
                    m_mode = 2;
                } else {
                    m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) { // LEFT
                if (m_mode >= 2) {
                    m_mode = 1;
                } else {
                    m_mode += 1;
                }
            }
            // RIGHT MENU, RIGHT or KEY_CODE
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
                // Vile screen, so do not need cell width ratio
                hero.position.x = (57/120)*SCREEN_WIDTH;
                hero.position.y = (46/160)*VIEW_PORT_HEIGHT; // TODO reverse top/down of geometry, fix redundant by double or float ?
                m_mode = -1;
                screen = 3;
            }
            if ((paramInt == 42) || (paramInt == -6)) // STAR *
            {
                screen = 2;
                m_mode = 1;
            }
//            repaint();
        }
        else if (screen == -33)
        {
            if ((paramInt == 42) || (paramInt == -6)) // * STAR
            {
//                loadImage(2);
                m_mode = 1;
                screen = 5;
//                repaint();
            }
        }
        else if (screen == 300)
        {
//            MPlay(3);
            m_mode = -1;
//            destroyImage(2);
//            loadImage(3);
            hero.position.x = (57/120)*SCREEN_WIDTH;
            hero.position.y = (46/120)*(Gdx.graphics.getHeight()*3/4);
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
            if ((paramInt == 35) || (paramInt == -7)) // R_MENU
            {
                screen = -88;
                m_mode = 1;
            }
            else if ((paramInt == 42) || (paramInt == -6)) // STAR *
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
            if (gameOn)
            {
                if (screen == 6) // Game running screen
                {
                    if (state == 1)
                    {
/*                        try
                        {
                            Thread.sleep(game_speed); // TODO use this for level and game speed control.
                            // Or use gdx way to auto change speed based on device CPU power
                        }
                        catch (Exception localException1) {}*/
                        if (pw_up == 1) // hero fire
                        {
                            hero.setPower();
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
                        } // End enemy attack n move ai
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
                        enemies[0].e_snow(e_num, e_boss, enemies, boss, hero);
                        if (gameOn)
                        {
//                            repaint();
//                            serviceRepaints();
                        }
                    }
                    else if (state == 2) {
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
                    else if (state == 3) {
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
                else if (screen == 8) {
                    if ((ani_step < 50) && (ani_step > 0)) {
                        ani_step += 1;
                    }
//                    repaint();
//                    serviceRepaints();
                }
                else if (screen == 9) {
                    if ((ani_step < 46) && (ani_step >= 0)) {
                        ani_step += 1;
                    }
//                    repaint();
//                    serviceRepaints();
                }
                else if (screen == 200) {
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
                else if (screen == 201) {
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
                } // end screen 201
                else if (screen == 65336) {
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
            } // end is GameOn
            else {
                /*try
                {
                    Thread.sleep(100L);
                }
                catch (Exception localException2) {}*/
            }
    } // End run()

    public int input_item(int paramInt) {
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

        Gdx.app.log("INFO", "Game action = " + game_action);
        return game_action;
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

    public void drawRunningScreen() {
        monitorEnemyPosition(); // custom inject function to correct enemy position. TODO figure out do we have to match all thing together or just inject simple "guard" method

        int j;
        batch.draw(imgBack, 0, VIEW_PORT_HEIGHT+BOTTOM_SPACE); // options|* button

        // ShapeRenderer has drawback, so do not use it.
        // TODO use TextureRegion to draw rectangle with color, pixel (mattdesl/lwjgl-basics)
        // The order of render in original J2ME is bk1 to white snow board. This way require exactly position draw of bk1 and whiteboard.
        // In order easier to draw batch, we need change order of image draw ie. bk1 will be overlaped by white bg if white bg not scale and draw propertly by its position.
        int snowBoardHeight = VIEW_PORT_HEIGHT - ui.getHeight();
        // Scale by Y vertically, bg is in square ratio.
        batch.draw(snowWhiteBg, 0, BOTTOM_SPACE+ui.getHeight(), snowBoardHeight, snowBoardHeight); // VIEW_PORT_HEIGHT
        batch.draw(hero.getBobTexture(), hero.position.x * 5 * SGH_SCALE_RATIO, BOTTOM_SPACE+ui.getHeight()+SMALL_GAP , hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight()); // orig 83
        if (ppang_time > 0)
        {
            if (ppang_item == 1) {
                batch.draw(imgItem_hyo[0], hero.position.x * CELL_WIDTH, (74/160*VIEW_PORT_HEIGHT)+BOTTOM_SPACE); // orig y:74 TODO may be use relative position by hero.y
            } else {
                batch.draw(imgItem_hyo[1], hero.position.x * CELL_WIDTH, (83/160*VIEW_PORT_HEIGHT)+BOTTOM_SPACE); // orig y:83
            }
            ppang_time -= 1;
            if (ppang_time == 0) {
                ppang_item = 0;
            }
        }
        draw_enemy();

        if (item_mode != 0) {
            if (message != "") {
                draw_text();
            }
            for (int i = 1; i <= 5; i++) {
//                    paramGraphics.drawRect(i * 12 + 23, 110, 10, 9);
            }
            if (item_mode != 100)
            {
//                    paramGraphics.setColor(16711680); // red ? white ?
//                    paramGraphics.drawRect(this.item_mode * 12 + 23, 110, 10, 9); // TODO avoid hard code position, anchor point
            }
            else if (item_mode == 100)
            {
                item_mode = 0;
            }
        } // end item_mode
        if (pw_up == 2) {
            batch.draw(imgShadow, hero.getItem().position.x * 5*SGH_SCALE_RATIO, (hero.getItem().position.y * 7 + 4)*SGH_SCALE_RATIO);
                batch.draw(imgItem[wp], hero.position.x * 5 * SGH_SCALE_RATIO, (hero.position.y * 7 - snow_gap + 4)*SGH_SCALE_RATIO );
        }
        else if (pw_up == 1) {
            if ((real_snow_pw > 0) && (ppang_item != 1))
            {
//                    paramGraphics.setColor(7196662); // 6DCFF6 light_blue
                if ((int)hero.position.x >= 13)
                {
//                        paramGraphics.fillRect(this.h_x * 5 - 16, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
                    batch.draw(imgPwd, (hero.position.x * 5 - 15)*SGH_SCALE_RATIO, SCREEN_HEIGHT/2); // orig 83/160. TODO why this calc return 0, can .0f fix this ?
                }
                else
                {
//                        paramGraphics.fillRect(this.h_x * 5 + 14, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3);
                    batch.draw(imgPwd, (hero.position.x * + 15)*SGH_SCALE_RATIO, SCREEN_HEIGHT/2); // orig 83/160
                }
            }
        } // end pw_up = 1
        else if (pw_up == 0) {
            if (ppang <= -1)
            {
                batch.draw(imgPok, hero.getItem().position.x* 5* SGH_SCALE_RATIO, (hero.getItem().position.y * 7 - 3)*SGH_SCALE_RATIO);
                ppang -= 1;
                if (ppang == -3) {
                    ppang = 0;
                }
            }
            else if ((ppang >= 1) && (ppang <= 10))
            {
                if (s_item != -10)
                {
                    if (ppang < 3) {
                        batch.draw(imgPPang, hero.getItem().position.x * 5*SGH_SCALE_RATIO, (hero.getItem().position.y * 7 - 6)*SGH_SCALE_RATIO );
                    } else {
                        batch.draw(imgPPang1, hero.getItem().position.x * 5* SGH_SCALE_RATIO, (hero.getItem().position.y * 7 - 6)*SGH_SCALE_RATIO );
                    }
                }
                else if (ppang < 4) {
//                        paramGraphics.drawImage(this.imgEffect[0], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                } else {
//                        paramGraphics.drawImage(this.imgEffect[1], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                }
                if (this.hit_idx != 10)
                {
                    if (enemies[hit_idx].getHp() > 0)
                    {
//                            paramGraphics.setColor(16711680); // FFFFFF
//                            paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15);

//                            paramGraphics.setColor(9672090);
//                            paramGraphics.fillRect(this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15 - 15 * this.e_hp[this.hit_idx] / this.max_e_hp[this.hit_idx]);
                    }
                }
                else if (hit_idx == 10)
                {
                    if (boss.getHp() > 0)
                    {
//                            paramGraphics.setColor(16711680); // FFFFFF
//                            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15);
                        // draw boss hp bar
                        batch.draw(boss.getBobTexture(), (boss.position.x* 5 + 12)*SGH_SCALE_RATIO, (boss.position.y* 5 + 5)*SGH_SCALE_RATIO, 3*SGH_SCALE_RATIO, 15*SGH_SCALE_RATIO);
//                            paramGraphics.setColor(9672090); // 0093959A gray
//                            paramGraphics.fillRect(this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp);
                    }
                    if (al == 1) {
                        batch.draw(imgAl, (hero.getItem().position.x * 5 + 6)*SGH_SCALE_RATIO, (hero.getItem().position.y * 7 - 10)*SGH_SCALE_RATIO);
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
            else if (ppang >= 50) {
                draw_sp_hyo();
            }
            if (message != "") {
                draw_text();
            }
        } // end pw_up = 0
        else if (pw_up == -1) {
            pw_up = 0;
        }
        if (p_mode == 1) {
            try
            {
                batch.draw(ui, 0, BOTTOM_SPACE); // orig: y:109
            }
            catch (Exception localException1) {}
            draw_item();
            p_mode = 2; // Change p_mode may be cause it draw only 1st time.
        }
        if (this.d_gauge != 0) {
                draw_gauge();
        }
        for (j = 0; j < e_num; j++) { // or count array elements enemies
            if (enemies[j].e_behv != 100)
            {
                batch.draw(imgShadow, enemies[j].getItem().position.x, (enemies[j].getItem().position.y * 6 + 17)*SGH_SCALE_RATIO );
                batch.draw(imgItem[enemies[j].e_wp], enemies[j].getItem().position.x, (enemies[j].getItem().position.y * 6 + 13 - enemies[j].e_snow_gap)*SGH_SCALE_RATIO );
            }
        }
        if ((boss.e_boss_behv != 100) && (e_boss > 0))
        {
            batch.draw(imgShadow, boss.e_boss_snow_x, boss.e_boss_snow_y * 6 + 17); // TODO change to item.position
            batch.draw(imgItem[boss.e_boss_wp], boss.e_boss_snow_x, (boss.e_boss_snow_y * 6 + 13 - boss.e_boss_snow_gap)*SGH_SCALE_RATIO );
        }
        if (del != -1) {
            draw_item();
        }
        if (hero.h_timer_p <= -1) {
            if (hero.h_timer_p != -5)
            {
                batch.draw(imgH_ppang, (hero.position.x * 5 + 1)*SGH_SCALE_RATIO, 81*SGH_SCALE_RATIO); // Update geometry J2ME different
                hero.h_timer_p -= 1;
            }
            else if (hero.h_timer_p == -5)
            {
                hero.h_timer_p = 0;
//                    paramGraphics.setColor(16711680);    // FFFFFF
//                    paramGraphics.fillRect(5, 113, 9, 12);
//                    paramGraphics.setColor(9342606); // 8E8E8E
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
        if (state == 2) // Draw s-t-a-g-e 1/2/3 etc animation
        {
            if (ani_step >= 3) {
                batch.draw(imgStage[0], 20*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);  // Anchor 20, below same value
            }
            if (ani_step >= 6) {
                batch.draw(imgStage[1], 35*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);
            }
            if (ani_step >= 9) {
                batch.draw(imgStage[2], 50*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);
            }
            if (ani_step >= 12) {
                batch.draw(imgStage[3], 65*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);
            }
            if (ani_step >= 15) {
                batch.draw(imgStage[4], 80*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);
            }
            if (ani_step >= 19) {
                batch.draw(imgStage_num, 95*SGH_SCALE_RATIO, 60*SGH_SCALE_RATIO);
            }
        }
    }

    public void drawSpecialScreen() {
        int j;
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
                    batch.draw(enemies[j].getBobTexture(), enemies[j].position.x * 5*SGH_SCALE_RATIO, (enemies[j].position.y * 5 + 5)*SGH_SCALE_RATIO, enemies[j].getBobTexture().getWidth(), enemies[j].getBobTexture().getHeight());
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
        batch.draw(hero.getBobTexture(), hero.position.x * 5 * SGH_SCALE_RATIO, (int)(83/160)*VIEW_PORT_HEIGHT+BOTTOM_SPACE, hero.getBobTexture().getWidth(), hero.getBobTexture().getHeight());
    }

    public void make_e_num(int paramInt1, int paramInt2)
    {
        if (paramInt2 == 1)
        {
            if (paramInt1 == 1)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (paramInt1 == 2)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (paramInt1 == 3)
            {
                this.e_boss = 1;
                this.e_num = 2;
            }
            else if (paramInt1 == 4)
            {
                this.e_boss = 3;
                this.e_num = 2;
            }
        }
        else if (paramInt2 == 2)
        {
            if (paramInt1 == 1)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (paramInt1 == 2)
            {
                this.e_boss = 0;
                this.e_num = 3;
            }
            else if (paramInt1 == 3)
            {
                this.e_boss = 2;
                this.e_num = 2;
            }
            else if (paramInt1 == 4)
            {
                this.e_boss = 3;
                this.e_num = 3;
            }
        }
        else if (paramInt2 == 3)
        {
            if (paramInt1 == 1)
            {
                this.e_boss = 0;
                this.e_num = 3;
            }
            else if (paramInt1 == 2)
            {
                this.e_boss = 2;
                this.e_num = 2;
            }
            else if (paramInt1 == 3)
            {
                this.e_boss = 0;
                this.e_num = 4;
            }
            else if (paramInt1 == 4)
            {
                this.e_boss = 3;
                this.e_num = 4;
            }
        }
        else if (paramInt2 == 4) {
            if (paramInt1 == 1)
            {
                this.e_boss = 1;
                this.e_num = 3;
            }
            else if (paramInt1 == 2)
            {
                this.e_boss = 2;
                this.e_num = 3;
            }
            else if (paramInt1 == 3)
            {
                this.e_boss = 3;
                this.e_num = 4;
            }
            else if (paramInt1 == 4)
            {
                this.e_boss = 4;
                this.e_num = 4;
            }
        }
        this.e_t_num = this.e_num;
        this.tmp_stage = paramInt1;
    }

    public void make_enemy(int paramInt)
    {
        if (paramInt < 0) { // new game ?
            make_e_num(boss.get_random(2) + 2, this.school);
        } else {
            make_e_num(this.last_stage % 10, this.school);
        }

        e_time = 0;
        for (int i = 0; i < this.e_num; i++)
        {
            if ((this.school == 1) || (this.school == 2)) {
                enemies[i].setHp(20 + this.school * 10);
            } else if (this.school == 3) {
                enemies[i].setHp(54);
            } else if (this.school == 4) {
                enemies[i].setHp(66);
            }
            enemies[i].max_e_hp = enemies[i].getHp();
            enemies[i].e_snow_y = -10;
            enemies[i].e_behv = 100;
            enemies[i].e_wp = 0;
        }
        if (this.school < 3) {
            this.e_dem = (this.school + 7);
        } else if (this.school == 3) {
            this.e_dem = (this.school + 9);
        } else {
            this.e_dem = 14;
        }
        enemies[0].position.x = (3 + boss.get_random(3));
        // We have to change because of different projection and/or screen size. Collision may be affected but we can use GDX way not manual calc.
        enemies[0].position.y = (1 + boss.get_random(3)); // orig: 1 + get_random(3).
        enemies[0].e_lv = 3;
        enemies[0].e_fire_time = 8;
        enemies[1].position.x = (18 + boss.get_random(3));
        enemies[1].position.y = (1 + boss.get_random(3));
        enemies[1].e_lv = 3;
        enemies[1].e_fire_time = 17;
        if (this.e_t_num >= 3)
        {
            enemies[2].position.x = (13 + boss.get_random(3));
            enemies[2].position.y = (3 + boss.get_random(2));
            enemies[2].e_lv = 3;
            enemies[2].e_fire_time = 20;
        }
        if (this.e_t_num == 4)
        {
            enemies[3].position.x = 8;
            enemies[3].position.y = 5;
            enemies[3].e_lv = 3;
            enemies[3].e_fire_time = 4;
        }
        boss.e_boss_behv = 100;
        boss.e_boss_snow_y = -10;
        boss.position.x = 10;
        boss.position.y = 6;
        boss.e_boss_idx = 0;
        boss.e_boss_hp = (this.e_boss * 10 + 30 + (this.school - 1) * 10);
        boss.max_e_boss_hp = boss.e_boss_hp;
        boss.e_boss_fire_time = 2;
    }

    /*
      Simulate fillRect in ShapeRenderer or J2ME Canvas. TODO color HEX, TextureRegion store cells color.
     */
    public void fillRect(int x, int y, int width, int height) {
        batch.draw(snowWhiteBg, 0, BOTTOM_SPACE, SCREEN_WIDTH, VIEW_PORT_HEIGHT+BOTTOM_SPACE);
    }

    public void drawInstructionScreen() { // screen = 2
        /*batch.drawImage(this.imgMM, 0, 0, 20);
        batch.setColor(16777164);
        batch.drawString("1.Play", 13, 23, 20);
        batch.drawString("2.Instructions", 13, 38, 20);
        batch.drawString("3.Configuration", 13, 53, 20);
        batch.drawString("4.Quit", 13, 68, 20);
        batch.drawImage(this.imgSl, 68, 115, 20);
        batch.drawImage(this.imgCh, 3, this.m_mode * 15 + 11, 20);*/
    }
    public void drawVillageScreen() { // screen = 3
    }
    public void drawShopScreen() { // screen = 31
/*        paramGraphics.drawImage(this.imgShop, 24, 20, 20);
        paramGraphics.setColor(16777062);
        paramGraphics.drawRect(27, this.s_item * 13 + 30, 29, 10);
        paramGraphics.drawRect(28, this.s_item * 13 + 31, 27, 8);
        paramGraphics.setColor(13434777);
        paramGraphics.drawRect(this.b_item * 16 + 32, 70, 15, 15);
        paramGraphics.drawRect(this.b_item * 16 + 33, 71, 13, 13);
        draw_int(paramGraphics, this.saved_gold, 84, 96);
        if (this.m_mode == 1) {
            draw_int(paramGraphics, this.item_price[this.b_item], 42, 96);
        } else if (this.m_mode == 0) {
            draw_int(paramGraphics, this.item_price[(this.b_item + 4)], 42, 96);
        }
        if (this.message != "") {
            draw_text(paramGraphics);
        }*/
    }
    public void drawSoundSettingScreen() { // screen 100
        /*paramGraphics.setColor(16777215);
        paramGraphics.fillRect(1, 20, 126, 90);
        paramGraphics.setColor(0);
        paramGraphics.drawRect(0, 19, 127, 90);
        paramGraphics.drawRect(0, 21, 127, 86);
        paramGraphics.drawImage(this.imgCh, 3, this.m_mode * 14 + 18, 20);
        paramGraphics.drawString("Resume", 15, 28, 20);
        paramGraphics.drawString("MainMenu", 15, 42, 20);
        paramGraphics.drawString("Sound", 15, 56, 20);
        if (this.s_play == 1)
        {
            paramGraphics.setColor(255);
            paramGraphics.drawString("On/", 69, 56, 20);
            paramGraphics.setColor(8421504);
            paramGraphics.drawString("off", 96, 56, 20);
        }
        else
        {
            paramGraphics.setColor(8421504);
            paramGraphics.drawString("on/", 69, 56, 20);
            paramGraphics.setColor(255);
            paramGraphics.drawString("OFF", 93, 56, 20);
        }
        paramGraphics.setColor(0);
        paramGraphics.drawString("Instructions", 15, 70, 20);
        paramGraphics.drawString("Quit", 15, 84, 20);*/
    }
    public void drawNewGameMenu() { // screen -88

    }
    public void drawSpecialAnimation() { // screen 8
/*        if ((this.ani_step == 1) || (this.ani_step == 2))
        {
            paramGraphics.setColor(10173);
            paramGraphics.fillRect(0, 40, 128, 60);
            paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
            paramGraphics.drawImage(this.imgSpecial[1], 44, 89, 3);
        }
        else if (this.ani_step == 8)
        {
            paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
            paramGraphics.drawImage(this.imgSpecial[1], 48, 89, 3);
        }
        else if (this.ani_step == 16)
        {
            paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
            paramGraphics.drawImage(this.imgSpecial[1], 51, 89, 3);
        }
        else if (this.ani_step == 23)
        {
            paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
            paramGraphics.drawImage(this.imgSpecial[1], 54, 89, 3);
        }
        else if (this.ani_step == 30)
        {
            paramGraphics.drawImage(this.imgSpecial[0], 44, 70, 3);
            paramGraphics.drawImage(this.imgSpecial[1], 55, 89, 3);
        }
        else if (this.ani_step == 37)
        {
            paramGraphics.drawImage(this.imgSpecial[2], 58, 88, 3);
        }
        else if (this.ani_step == 50)
        {
            destroyImage(8);
            loadImage(9);
            this.ani_step = 0;
            this.screen = 9;
        }*/
    }
    public void drawSoundSpeedSetting() { // screen 4
/*        paramGraphics.drawImage(this.imgMM, 0, 0, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("Sound", 12, 23, 20);
        if (this.s_play == 1)
        {
            paramGraphics.drawString("ON /", 62, 23, 20);
            paramGraphics.setColor(10790052);
            paramGraphics.drawString("off", 95, 23, 20);
            paramGraphics.setColor(16777164);
        }
        if (this.s_play == 2)
        {
            paramGraphics.setColor(10790052);
            paramGraphics.drawString("on /", 62, 23, 20);
            paramGraphics.setColor(16777164);
            paramGraphics.drawString("OFF", 94, 23, 20);
        }
        paramGraphics.drawString("Vibration ", 12, 41, 20);
        if (this.v_mode == 1)
        {
            paramGraphics.drawString("ON /", 62, 59, 20);
            paramGraphics.setColor(10790052);
            paramGraphics.drawString("off", 95, 59, 20);
            paramGraphics.setColor(16777164);
        }
        if (this.v_mode == 2)
        {
            paramGraphics.setColor(10790052);
            paramGraphics.drawString("on /", 62, 59, 20);
            paramGraphics.setColor(16777164);
            paramGraphics.drawString("OFF", 94, 59, 20);
        }
        paramGraphics.drawString("Speed ", 14, 77, 20);
        paramGraphics.drawString("[ " + String.valueOf(this.speed) + " ]", 68, 77, 20);
        paramGraphics.drawImage(this.imgBk, 2, 115, 20);
        if (this.m_mode < 3) {
            paramGraphics.drawImage(this.imgCh, 4, this.m_mode * 18 + 9, 20);
        } else {
            paramGraphics.drawImage(this.imgCh, 4, this.m_mode * 18 + 27, 20);
        }*/
    }
    public void drawGuideMenu() { // screen 5
        /*paramGraphics.drawImage(this.imgMM, 0, 0, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("1.Control Keys", 10, 25, 20);
        paramGraphics.drawString("2.Offense items", 10, 42, 20);
        paramGraphics.drawString("3.Defense items", 10, 59, 20);
        paramGraphics.drawImage(this.imgCh, 3, this.m_mode * 17 + 12, 20);
        paramGraphics.drawImage(this.imgSl, 68, 115, 20);
        paramGraphics.drawImage(this.imgBk, 2, 115, 20);*/
    }
    public void drawListItems() { // screen -33
        /*paramGraphics.drawImage(this.imgMM, 0, 0, 20);
        paramGraphics.drawImage(this.imgBk, 2, 115, 20);
        destroyImage(2);
        paramGraphics.setColor(16777164);
        try
        {
            if (this.m_mode == 1) {
                paramGraphics.drawImage(Image.createImage("/txt4.png"), 5, 25, 20);
            }
            if (this.m_mode == 2)
            {
                paramGraphics.fillRect(6, 23, 10, 10);
                paramGraphics.fillRect(6, 45, 10, 10);
                paramGraphics.fillRect(6, 61, 10, 10);
                paramGraphics.fillRect(6, 84, 10, 10);
                paramGraphics.drawImage(Image.createImage("/item1.png"), 7, 24, 20);
                paramGraphics.drawImage(Image.createImage("/item2.png"), 7, 46, 20);
                paramGraphics.drawImage(Image.createImage("/item3.png"), 7, 62, 20);
                paramGraphics.drawImage(Image.createImage("/item4.png"), 7, 85, 20);
                paramGraphics.drawImage(Image.createImage("/txt2.png"), 23, 25, 20);
            }
            if (this.m_mode == 3)
            {
                paramGraphics.fillRect(6, 23, 10, 10);
                paramGraphics.fillRect(6, 38, 10, 10);
                paramGraphics.fillRect(6, 53, 10, 10);
                paramGraphics.fillRect(6, 67, 10, 10);
                paramGraphics.drawImage(Image.createImage("/item5.png"), 7, 24, 20);
                paramGraphics.drawImage(Image.createImage("/item6.png"), 7, 39, 20);
                paramGraphics.drawImage(Image.createImage("/item7.png"), 7, 54, 20);
                paramGraphics.drawImage(Image.createImage("/item8.png"), 7, 68, 20);
                paramGraphics.drawImage(Image.createImage("/txt1.png"), 23, 25, 20);
            }
        }
        catch (Exception localException2) {}
        System.gc();*/
    }
    public void drawVictoryScreen() { // screen 200

    }
    public void drawLoseScreen() { // screen 65335

    }
    public void drawGoodJob() { // screen 300
        /*paramGraphics.drawImage(this.imgMM, 0, 0, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("Good Job!", 15, 23, 20);
        paramGraphics.setColor(13434726); // CCFF66
        paramGraphics.drawString("Acquired", 15, 41, 20);
        paramGraphics.drawString("Gold:", 48, 57, 20);
        paramGraphics.drawString(String.valueOf(this.gold), 92, 57, 20);
        paramGraphics.setColor(16777164);
        paramGraphics.drawString("press any key", 10, 83, 20);
        paramGraphics.drawString("to continue", 37, 97, 20);*/
    }
    public void drawTextScreen() { // screen 77
//        draw_text(paramGraphics);
    }
    public void drawLogoScreen() { // screen -1

    }
    public void drawSamsungLogo() { // screen -2

    }
    public void drawAllClear() {
/*        paramGraphics.setColor(16777215);
        paramGraphics.fillRect(0, 25, 120, 85);
        try
        {
            paramGraphics.drawImage(Image.createImage("/allClear.png"), 64, 10, 0x10 | 0x1);
        }
        catch (Exception localException4) {}*/
    }
    public void drawManualScreen() { // screen -5
/*        paramGraphics.setColor(16777215);
        paramGraphics.fillRect(1, 20, 126, 90);
        paramGraphics.setColor(0);
        paramGraphics.drawRect(0, 19, 127, 90);
        paramGraphics.drawRect(0, 21, 127, 86);
        try
        {
            paramGraphics.drawImage(Image.createImage("/txt4b.png"), 4, 30, 20);
        }
        catch (Exception localException5) {}
        System.gc();*/
    }
    public void drawTitleScreen() { // screen 1

    }

    public void draw_gauge()
    {
        /*if (this.d_gauge == 2)
        {
            paramGraphics.setColor(16775065);
            paramGraphics.fillRect(118, 111, 8, 8);
            if (this.wp != 0) {
                paramGraphics.drawImage(this.imgItem[this.wp], 122, 111, 0x10 | 0x1);
            }
        }
        if (this.mana != 0)
        {
            paramGraphics.setColor(16711680);
            paramGraphics.fillRect(30, 124, this.mana, 1);
            if (this.mana == 36)
            {
                paramGraphics.fillRect(39, 123, 3, 3);
                paramGraphics.fillRect(51, 123, 3, 3);
                paramGraphics.fillRect(63, 123, 3, 3);
            }
            else if (this.mana >= 24)
            {
                paramGraphics.fillRect(39, 123, 3, 3);
                paramGraphics.fillRect(51, 123, 3, 3);
            }
            else if (this.mana >= 12)
            {
                paramGraphics.fillRect(39, 123, 3, 3);
            }
        }
        else if (this.mana == 0)
        {
            paramGraphics.setColor(4960985);
            paramGraphics.fillRect(30, 124, 36, 1);
            paramGraphics.fillRect(39, 123, 3, 3);
            paramGraphics.fillRect(51, 123, 3, 3);
            paramGraphics.fillRect(63, 123, 3, 3);
        }
        this.d_gauge = 0;*/
    }

    public void check_ppang() {
        d_gauge = 2;
        int j;
        for (int i = 0; i < e_num; i++)
        {
            j = (int)enemies[i].position.x;
            if ((j - snow_x >= -1) && (j - snow_x <= 1))
            {
                int k = (int)enemies[i].position.y;
                if ((k >= 0) && (k <= 4))
                {
                    if ((k + real_snow_pw == 7) || (k + real_snow_pw == 8))
                    {
                        ppang = 1;
                        decs_e_hp(i);
                        break;
                    }
                }
                else if ((k + real_snow_pw == 8) || (k + real_snow_pw == 9))
                {
                    ppang = 1;
                    decs_e_hp(i);
                    break;
                }
            }
            ppang = -1;
        }
        if ((e_boss > 0) && (boss.position.x - snow_x >= -1) && (boss.position.x - snow_x <= 1))
        {
            j = (int)(boss.position.y + real_snow_pw - 1);
            if ((j == 9) || (j == 7) || (j == 8))
            {
                if (j == 7) {
                    al = 1;
                }
                ppang = 1;
                decs_e_hp(10);
            }
        }
        pw_up = -1;
        if (wp != 0) {
            wp = 0;
        }
    }
    public void decs_e_hp(int paramInt)
    {
        hit_idx = paramInt;
        if (mana != 36) {
            if (mana <= 10) {
                mana += 2;
            } else {
                mana += 1;
            }
        }
        if (paramInt != 10)
        {
            if (wp == 0) {
                enemies[paramInt].setHp(enemies[paramInt].getHp()-dem);
            }
            else if (wp == 1) {
                enemies[paramInt].e_ppang_time = 70;
                enemies[paramInt].e_ppang_item = 1;
                enemies[paramInt].e_lv = (-enemies[paramInt].e_lv);
                enemies[paramInt].setHp(enemies[paramInt].getHp() - dem);
            }
            else if (wp == 2) {
                s_item = -10;
                enemies[paramInt].setHp(enemies[paramInt].getHp() - 19);
            }
            else if (wp == 3) {
                enemies[paramInt].e_ppang_time = 65;
                enemies[paramInt].e_ppang_item = 2;
                enemies[paramInt].setHp(enemies[paramInt].e_lv - dem / 2);
                enemies[paramInt].e_move_dir = 0;
            }
            else if (wp == 4) {
                enemies[paramInt].e_ppang_time = 75;
                enemies[paramInt].e_ppang_item = 1;
                enemies[paramInt].e_lv = (-enemies[paramInt].e_lv);
                s_item = -10;
                enemies[paramInt].setHp(enemies[paramInt].e_lv -= dem * 2);
            }
            if (enemies[paramInt].getHp() < 0) {
                enemies[paramInt].e_idx = 2;
                enemies[paramInt].dis_count = -1;
            }
        }
        else if (paramInt == 10)
        {
            if (wp == 4) {
                s_item = -10;
                boss.e_boss_hp -= dem * 2;
            }
            else if (wp == 2) {
                s_item = -10;
                boss.e_boss_hp -= 19;
            }
            else {
                boss.e_boss_hp -= dem;
            }
            if (boss.e_boss_hp < 0) {
                boss.e_boss_idx = 2;
                boss.boss_dis_count = -1;
            }
            if (al == 1) {
                boss.e_boss_hp -= 5;
            }
        }
//        MPlay(3);
    }

}