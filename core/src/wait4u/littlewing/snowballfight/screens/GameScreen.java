package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Preferences;
import wait4u.littlewing.snowballfight.Enemy;
import wait4u.littlewing.snowballfight.Boss;
import wait4u.littlewing.snowballfight.Hero;

import wait4u.littlewing.snowballfight.OverlapTester;

/**
 * Created by nickfarow on 13/10/2016.
 */

public class GameScreen extends DefaultScreen implements InputProcessor {
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

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture snowWhiteBg;
    Texture fireBtnTexture;
    // private BoundingBox useItem

    // Use rectangle until figure out how to work with BoundingBox multi input.
    Rectangle upBtnRect = new Rectangle(20+(200/3), 20+(400/3), 72, 70);
    Rectangle downBtnRect = new Rectangle(20+(200/3), 20, 72, 70);
    Rectangle leftBtnRect = new Rectangle(20, 20+(200/6), 2*70, 140);
    Rectangle rightBtnRect = new Rectangle(20+(400/3), 20+(200/6), 70, 140);
    Rectangle optionBtnRect = new Rectangle(SCREEN_WIDTH/2+150, SCREEN_HEIGHT/8, SCREEN_WIDTH/2-180, 70);
    Rectangle speedUpBtnRect = new Rectangle(SCREEN_WIDTH-275-200, 20, 200, 100);
    Rectangle speedDownBtnRect = new Rectangle(SCREEN_WIDTH-275-400, 20, 200, 100);

    private Hero hero;
    private Boss boss;
    private Enemy[] enemies;
    // private Stage touch_stage;
    // private Touchpad touchpad;
    // private Touchpad.TouchpadStyle touchpadStyle;
    Vector3 touchPoint;
    // private Skin touchpadSkin;
    // private Drawable touchBackground;
    // private Drawable touchKnob;

    private static final String PREF_VIBRATION = "vibration";
    private static final String PREF_SOUND_ENABLED = "soundenabled";
    private static final String PREF_SPEED = "gamespeed";
    private static final String PREF_LEVEL = "level";
    private static final String PREF_SAVEDGOLD = "saved_gold";
    private static final String PREF_MANA= "mana";
    private static final String PREF_GAME_STAGE= "game_stage";
    Preferences prefs = Gdx.app.getPreferences("gamestate");

    private int game_state = 0;
    private int saved_gold = 10;
    private int speed = 4;
    private int game_speed = 24; // in milliseconds orig: 17
    private int screen = -1; //-1; 6 = running
    private boolean gameOn = true;
    private String message;
    private int m_mode = 1;
    private int s_play = 1;
    private int v_mode = 1;
    private int p_mode;
    private int ani_step;
    private int stage;
    private int last_stage;
    private int tmp_stage;
    private int school;
    private int state;

    private int gold;
    private int[] item_slot = new int[5];
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
    private int e_num; // set default value for debug avoid exceed 2 item size TODO init then remove this
    private int e_t_num;

    private int e_time;
    private int e_dem;
    private int hit_idx;
    private int e_boss; // TODO init this and remove debug. The flag (and/or number) of boss enemy
    private int al;
    private int d_gauge; // gauge rule (power fire)
    private int game_action = 0;
    private int key_code = 0;
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
    private Texture [] imgSpecial;
    private Texture imgSp;
    private Texture [] imgEffect;
    private Texture imgStage_num;
    private Texture ui;
    private Texture [] imgStage;

    private Texture [] imgColor; // For fillRect with color
    private Texture imgKeyNum3;
    private Texture imgSpeedUp;
    private Texture imgSpeedDown;
    private Texture touch_pad;
    private Texture touch_pad_knob;
    BitmapFont font;
    private Music music;
    // Ray collisionRay;

    public GameScreen(Game game, int param_screen, int param_school) {
        super(game);
        Gdx.input.setCatchBackKey( true );
        Gdx.input.setInputProcessor(this);
        item_price[0] = 5;
        item_price[1] = 8;
        item_price[2] = 8;
        item_price[3] = 14;
        item_price[4] = 6;
        item_price[5] = 12;
        item_price[6] = 10;
        item_price[7] = 12;
        // Default let player has some items, TODO implement item buy use saved gold
        item_slot[0] = 3;
        item_slot[1] = 5;
        item_slot[2] = 2;
        item_slot[3] = 7;
        item_slot[4] = 4;
        last_stage = getGameStage();
        if(last_stage <= 0) {
            last_stage = 32;
        }
        stage = last_stage;
        school = param_school;

        //camera = new OrthographicCamera();
        //camera.setToOrtho(true, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Calculate global var width/height, view port ...
        create();
        this.screen = param_screen;

        touchPoint = new Vector3();
        game_speed = getGameSpeed();
        init_game(stage);

        // TODO use ratio
        // touchKeyUpArea = new BoundingBox(new Vector3(20+(200/3), 20+(200/3), 0),new Vector3(27+400/3, 20+200, 0));
        // touchKeyDownArea = new BoundingBox(new Vector3(20+(200/3), 20, 0),new Vector3(27+400/3, 20+200/3, 0));
        // touchKeyLeftArea = new BoundingBox(new Vector3(20+(200/3), 20+(200/6), 0),new Vector3(27+400/3, 20+150, 0));
        // touchKeyRightArea = new BoundingBox(new Vector3(20+(400/3), 20+(200/6), 0),new Vector3(27+400/3, 20+200, 0));
        // touchOptionsArea = new BoundingBox(new Vector3(20, 20+(200/6), 0),new Vector3(20, 20+150, 0));
    }

    public void create () {
        batch = new SpriteBatch();

        //Create camera
        float aspectRatio = (float) SCREEN_WIDTH / (float) SCREEN_HEIGHT;
        camera = new OrthographicCamera();
        camera.position.x = SCREEN_WIDTH/2;
        camera.position.y = SCREEN_HEIGHT/2;
        camera.update();

        /* Tempory disable touchpad
        camera.setToOrtho(false, 10f*aspectRatio, 10f); // Touchpad

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("data/gui/touchBackground.png"));
        touchpadSkin.add("touchKnob", new Texture("data/gui/touchKnob.png"));
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
        Gdx.input.setInputProcessor(touch_stage); */

        Gdx.input.setInputProcessor(this);

        loadTextures();
        initHeroTexture();
        //initEnemy();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);
    }

    public void update() {

    }

    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = SCREEN_WIDTH/2;
        camera.position.y = SCREEN_HEIGHT/2;
        camera.update();

        batch.enableBlending();
        batch.begin();

        int j;
        if (screen == 6)  // normal playing screen, TODO may be use constants name
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
        else if (screen == 200) { // Victory
            game.setScreen(new VictoryScreen(game));
        }
        else if (screen == 65335) {
            game.setScreen(new LoseScreen(game));
        }
        else if (screen == 300) {
            drawGoodJob();
        }
        else if (screen == 77) {
            drawTextScreen();
        }
        else if (screen == -1) {
            game.setScreen(new LogoScreen(game));
            screen = 6;
        }
        else if (screen == -2) {
            game.setScreen(new SamsungFunclubScreen(game));
        }
        else if (screen == 1000) {
            drawAllClear();
        }
        else if (screen == -5) {
            drawManualScreen();
        }
        else if (screen == 1) {
            game.setScreen(new TitleMenuScreen(game));
        }

        run();

        // drawTouchPad();
        drawUI();
        batch.end();
    }

    private void loadTextures() {
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

        imgSpecial = new Texture[3];
        for (int i = 0; i < 3; i++) {
            imgSpecial[i] = new Texture("data/samsung-white/special" + i + ".png");
        }
        // TODO use texture region
        imgEffect = new Texture[2];
        imgEffect[0] = new Texture("data/samsung-white/effect0.png");
        imgEffect[1] = new Texture("data/samsung-white/effect1.png");

        imgStage_num = new Texture("data/samsung-white/stage1.png"); // tmp_stage +
        ui = new Texture("data/samsung-white/ui.png");  // h:160p (1080p)
        imgStage = new Texture[5];
        for (int i = 0; i < 5; i++) {
            imgStage[i] = new Texture("data/samsung-white/word-" + i + ".png");
        }

        /**
         * #0 for red
         * #1 for light blue, #3 light blue 2 6DCFF6
         * #2 for light yellow, #4 gray 93959A #5 for white
         */
        imgColor = new Texture[6];
        for (int i = 0; i < 6; i++) {
            imgColor[i] = new Texture("data/samsung-white/color-" + i + ".png");
        }

        imgKeyNum3 = new Texture("data/samsung-white/use_item_btn.png");
        imgSpeedUp = new Texture("data/samsung-white/speed_up.png");
        imgSpeedDown = new Texture("data/samsung-white/speed_down.png");
        touch_pad = new Texture("data/gui/touchBackground.png");
        touch_pad_knob = new Texture("data/gui/touchKnob.png");
    }

    public void monitorEnemyPosition() {
        enemyMoving();
        updateEnemyBound();
    }

    @Override
    public void show() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);
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
        //viewport.update(width, height);
    }

    @Override
    public void hide() {

    }

    public void initEnemy() {
        boss = new Boss(new Vector2(SCREEN_WIDTH/2/CELL_WIDTH, TOP_BOUND/CELL_WIDTH-4), school);

        enemies = new Enemy[e_num];
        for (int i = 0; i < e_num; i++) {
            // Add some random on start position of enemies
            int enemyPosY = (TOP_BOUND/CELL_WIDTH)-3;
            int enemyStartPositionX = (int) SCREEN_WIDTH / 2 / CELL_WIDTH;
            if(i%2 == 0) {
                enemyStartPositionX += hero.get_random(6);
            } else {
                enemyStartPositionX -= hero.get_random(6);
            }
            enemies[i] = new Enemy(new Vector2(enemyStartPositionX, enemyPosY ));
            enemies[i].setBound(new Rectangle(enemies[i].position.x*CELL_WIDTH, enemies[i].position.y*CELL_WIDTH, enemies[i].getImage().getWidth(), enemies[i].getImage().getHeight()));
        }
    }
    public void init_game(int paramInt)
    {
        initHeroTexture();
        //initEnemy();
        // game.setScreen(new LogoScreen(game));
        // screen = 77;

        // repaint(); // TODO find gdx equivalent method or handle this function. May be multi Screen help ? Does global vars remain ?
        game_state = 0;
        p_mode = 1;
        hero.position.x = 5;
        hero.position.y = (int)((BOTTOM_SPACE+ui.getHeight()+2)/CELL_WIDTH); // orig 8
        hero.h_idx = 0;
        hero.hp = hero.max_hp;
        hero.wp = 0;
        hero.pw_up = 0;
        hero.snow_pw = 0;
        hero.real_snow_pw = 0;
        hero.dem = 12;
        hero.ppang = 0;
        al = -1;
        hero.ppang_time = 0;
        hero.ppang_item = 0;
        make_enemy(paramInt);
        //initEnemy();
        d_gauge = 2;

        // screen = 6;
        item_mode = 0;

        // loadImage(6);
        // loadImage(100);
        if (e_boss > 0) {
            // loadImage(7);
        }
        state = 2;
        ani_step = 0;
        // startThread();
        gameOn = true;
    }

    protected void initHeroTexture () {
        snowWhiteBg = new Texture("data/samsung-white/white_bg.png");
        hero = new Hero(new Vector2(SCREEN_WIDTH/2/CELL_WIDTH, (BOTTOM_SPACE+ui.getHeight()+SMALL_GAP)/CELL_WIDTH));

        fireBtnTexture = new Texture("data/samsung-white/fire.png");
    }

    public void draw_sp_hyo() {
        for (int i = 0; i < e_num; i++) {
            if (hero.getHp() >= 0)
            {
                fillRect( (int)(enemies[i].position.x * 5 + 8)*SGH_SCALE_RATIO, (int)(enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO, 27, 15*SGH_SCALE_RATIO, 0); // (16711680)
                fillRect( (int)(enemies[i].position.x * 5 + 8)*SGH_SCALE_RATIO, (int)(enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO, 27, (15 - 15 * enemies[i].e_hp / enemies[i].max_e_hp)*SGH_SCALE_RATIO, 4); // 9672090 gray
                if (hero.ppang <= 51) {
                    batch.draw(imgEffect[0], enemies[i].position.x * 5 * SGH_SCALE_RATIO, (enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO, imgEffect[0].getWidth(), imgEffect[0].getHeight());
                } else if (hero.ppang <= 54) {
                    batch.draw(imgEffect[1], enemies[i].position.x * 5 * SGH_SCALE_RATIO, (enemies[i].position.y * 5 + 5)*SGH_SCALE_RATIO);
                }
            }
        }
        if ((boss.e_boss_hp >= 0) && (e_boss > 0))
        {
            fillRect( (int)(boss.position.x * 5 + 12)*SGH_SCALE_RATIO, (int)(SCREEN_HEIGHT-(boss.position.y * 5 + 5)*SGH_SCALE_RATIO), 27, 15*SGH_SCALE_RATIO, 0); // setColor(16711680);
            fillRect( (int)(boss.position.x * 5 + 12)*SGH_SCALE_RATIO, (int)(SCREEN_HEIGHT-(boss.position.y * 5 + 5)*SGH_SCALE_RATIO), 27, (15 - 15 * boss.e_boss_hp / boss.max_e_boss_hp)*SGH_SCALE_RATIO, 4); // setColor(9672090); // gray
            if (hero.ppang <= 51) {
                batch.draw(imgEffect[0], boss.position.x * 5 * SGH_SCALE_RATIO, (boss.position.y * 5 + 5)*SGH_SCALE_RATIO, imgEffect[0].getWidth(), imgEffect[0].getHeight());
            } else if (hero.ppang <= 54) {
                batch.draw(imgEffect[1], boss.position.x * 5 * SGH_SCALE_RATIO, (boss.position.y * 5 + 6)*SGH_SCALE_RATIO, imgEffect[1].getWidth(), imgEffect[1].getHeight());
            }
        }
        if (hero.ppang != 55) {
            hero.ppang += 1;
        }
        else {
            for (int j = 0; j < e_num; j++) {
                if (enemies[j].e_hp > 0)
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
            hero.ppang = 0;
            special = 0;
        }
    }

    /*
    * J2ME original port to gdx
    * */
    public void draw_enemy() {
        monitorEnemyPosition();

        for (int i = 0; i < enemies.length; i++) {
            if ((int)enemies[i].position.x != -10) {
                batch.draw(enemies[i].getImage(), (int) enemies[i].position.x * 5 * SGH_SCALE_RATIO, enemies[i].position.y * 5 * SGH_SCALE_RATIO + 5, 0, 0, enemies[i].getImage().getWidth(), enemies[i].getImage().getHeight(), 1, 1, 0, 0, 0, enemies[i].getImage().getWidth(), enemies[i].getImage().getHeight(), false, false);
                if (enemies[i].e_ppang_time > 0) {
                    enemies[i].e_ppang_time -= 1;
                    batch.draw(imgItem_hyo[(enemies[i].e_ppang_item - 1)], enemies[i].position.x * 5*SGH_SCALE_RATIO, (enemies[i].position.y * 5 + 1)*SGH_SCALE_RATIO, imgItem_hyo[(enemies[i].e_ppang_item - 1)].getWidth(), imgItem_hyo[(enemies[i].e_ppang_item - 1)].getHeight());
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
                        enemies[i].e_idx = 0;
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
            batch.draw(boss.getImage(), (int)boss.position.x * 5 *SGH_SCALE_RATIO , (boss.position.y * 5)*SGH_SCALE_RATIO, 0, 0, boss.getImage().getWidth(), boss.getImage().getHeight(), 1, 1, 0, 0, 0, boss.getImage().getWidth(), boss.getImage().getHeight(), false, false);
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
        int rightBoundEnemy = (int)(SCREEN_WIDTH - enemies[0].getImage().getWidth())/CELL_WIDTH;
        int rightBoundBoss = (int)((SCREEN_WIDTH - boss.getImage().getWidth())/CELL_WIDTH);

        int topBoundInCell = (int) (TOP_BOUND / CELL_WIDTH);
        int bottomBoundInCell = topBoundInCell - 8; // We draw map of 24x32 cell

        for(int i = 0; i < e_num; i++) {
            enemies[i].check_move_outof_bound(0, rightBoundEnemy, bottomBoundInCell, topBoundInCell);
        }

        boss.check_move_outof_bound(0, rightBoundBoss, bottomBoundInCell-7, topBoundInCell);
    }
    protected void updateEnemyBound() {
        for(int i = 0; i < e_num; i++) {
            enemies[i].setBound(new Rectangle(enemies[i].position.x * CELL_WIDTH, enemies[i].position.y * CELL_WIDTH, enemies[i].getImage().getWidth(), enemies[i].getImage().getHeight()));
        }
        boss.setBound(new Rectangle(boss.position.x*CELL_WIDTH, boss.position.y*CELL_WIDTH, boss.getImage().getWidth(), boss.getImage().getHeight()));
    }

    protected void drawUI() {
        batch.draw(fireBtnTexture, SCREEN_WIDTH-50-fireBtnTexture.getWidth(), 50, fireBtnTexture.getWidth(), fireBtnTexture.getHeight());
        batch.draw(imgKeyNum3, SCREEN_WIDTH-50-fireBtnTexture.getWidth()-fireBtnTexture.getWidth()/2 - imgKeyNum3.getWidth(), BOTTOM_SPACE-imgKeyNum3.getHeight(), imgKeyNum3.getWidth(), imgKeyNum3.getHeight());
        batch.draw(imgSpeedUp, SCREEN_WIDTH-50-fireBtnTexture.getWidth()-fireBtnTexture.getWidth()/2 - imgSpeedUp.getWidth(), 20, imgSpeedUp.getWidth(), imgSpeedUp.getHeight());
        batch.draw(imgSpeedDown, SCREEN_WIDTH-50-fireBtnTexture.getWidth()-fireBtnTexture.getWidth()/2 - 2*imgSpeedDown.getWidth(), 20, imgSpeedDown.getWidth(), imgSpeedDown.getHeight());
        batch.draw(touch_pad, 20, 20);
        batch.draw(touch_pad_knob, 20+touch_pad.getWidth()/2-touch_pad_knob.getWidth()/2, 20+touch_pad.getHeight()/2-touch_pad_knob.getHeight()/2);

        fillRect(40, BOTTOM_SPACE+ui.getHeight()-(12 - 12 * hero.hp / hero.max_hp)*SGH_SCALE_RATIO-24, 81+5, (12 - 12 * hero.hp / hero.max_hp)*SGH_SCALE_RATIO, 4);
    }

    protected void drawTouchPad() {
        camera.update();
        batch.enableBlending();
        //touch_stage.act(Gdx.graphics.getDeltaTime());
        batch.flush();
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

    /**
     * TODO handle box and border box, batch.begin
     */
    public void draw_text(String str, int x, int y, int color) {
        if( (str != null) && (str.length() > 0) ) {
            if(font != null) {
                switch(color) {
                    case 0:
                        font.setColor(1, 0, 0, 1);
                        break;
                    case 1:
                        font.setColor(0, 1, 1, 1);
                        break;
                    case 2:
                        font.setColor(0, 0, 1, 1);
                        break;
                    case 3:
                        font.setColor(1, 1, 1, 1);
                        break;
                    default:
                        font.setColor(1, 1, 1, 1);
                        break;
                }
                fillRect(0, y-100, SCREEN_WIDTH, 19*SGH_SCALE_RATIO, 3); // #00AEEF light blue
                font.draw(batch, str, x, y);
                // int i = message.length();
                // setColor(20361) // #004F89 dark blue
                //drawRect(0, 52, 127, 19);
                // setColor(0) // black
                // drawString(message, 64, 53);
                message = "";
            }
        }
    }
    public void draw_item() {
        if (del == -1) {
            for (int i = 0; i < 5; i++) {
                if (item_slot[i] != 0) {
                    if(i < 3) {
                        batch.draw(imgItem[item_slot[i]], (12 * i + 34)*SGH_SCALE_RATIO-4, 35*SGH_SCALE_RATIO); // 20 as J2ME canvas anchor orig: 111
                    } else {
                        batch.draw(imgItem[item_slot[i]], (12 * i + 32)*SGH_SCALE_RATIO, 35*SGH_SCALE_RATIO); // 20 as J2ME canvas anchor orig: 111
                    }
                }
            }
        } else {
            fillRect(del * 12 + 37, 49*SGH_SCALE_RATIO, 72, 72, 4); // setColor(6974058); // 6A6A6A use gray
            del = -1;
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
            if ((getGameAction(paramInt) == GAME_ACTION_LEFT) || (key_code == 52)) // NUM_4 or left key
            {
                if ((item_mode == 0) && (hero.ppang_item != 2)) // hero frezed ?
                {
                    if ((int)hero.position.x != 1)
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
                    draw_text(message, 30*SGH_SCALE_RATIO, SCREEN_HEIGHT/2+SCREEN_HEIGHT/8-200, 3);
                    // repaint();
                }
            }
            else if ((getGameAction(paramInt) == GAME_ACTION_RIGHT) || (paramInt == 54) ) // NUM_6 or RIGHT_KEY (may be LEFT_KEY by keyboard view)
            {
                if ((item_mode == 0) && (hero.ppang_item != 2))
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
                    // repaint();
                }
            }
            else if ((getGameAction() == GAME_ACTION_DOWN) || (paramInt == 56)) // NUM_8 or DOWN KEY
            {
                if (hero.mana >= 12) {
                     use_special();
                } else {
                    message = "Insufficient Mana";
                }
            }
            // KEY_UP = 50 (fire can be use up key), -5 = OK keycode
            else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53) || isTouchedOK()) // OK, NUM_5 etc isTouchedUp()
            {
                if (item_mode == 0)
                {
                    if ((hero.pw_up == 0) && (hero.ppang_item != 2))
                    {
                        hero.snow_pw = 0;
                        hero.real_snow_pw = 0;
                        hero.pw_up = 1;
                        hero.h_idx = 2;
                    }
                    else if ((hero.pw_up == 1) && (hero.real_snow_pw > 0))
                    {
                        hero.h_idx = 4;
                        hero.make_attack();
                    }
                }
                else
                {
                    use_item(item_mode - 1);
                    gameOn = true;
                }
            }
            else if (((paramInt == 35) || (paramInt == -7)) && (game_state == 0)) // RIGHT_MENU, # (pound_key) => options|# action
            {
                m_mode = 1;
                gameOn = false;
                screen = 100;
                // repaint();
            }
            else if ((paramInt == 51) && (game_state == 0) || isTouchedNum3()) // ITEM_MODE (NUM_3)
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
                if (j == 1) {
                    gameOn = false;
                    message = "Item Mode";
                    item_mode = 1;
                    // repaint();
                }
                else {
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
            else if (getGameAction(paramInt) == GAME_ACTION_LEFT)
            {
                if (m_mode == 3) {
                    s_play = 1;
                }
            }
            else if (getGameAction(paramInt) == GAME_ACTION_RIGHT)
            {
                if (m_mode == 3) {
                    s_play = 2;
                }
            }
            else if ((paramInt == 35) || (getGameAction(paramInt) == GAME_ACTION_OK) || (paramInt == -7)) { // 35, -7 = right menu, action 8 = OK
                if (m_mode == 2)
                {
                    // goto_menu();
                } else {
                    if (m_mode == 5)
                    {
                        // SJ.destroyApp(true);
                        // SJ.notifyDestroyed();
                        return;
                    }
                    if (m_mode == 1) {
                        screen = 6;
                        if (item_mode == 0) {
                            gameOn = true;
                        }
                        else {
                            message = "Item Mode";
                            // repaint();
                        }
                    }
                    else if (m_mode == 4) {
                        screen = -5;
                    }
                }
            }
            // repaint();
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
            // RIGHT MENU ? KEY_NUM1	49 KEY_NUM2	50 KEY_NUM3	51 KEY_NUM4	52
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
                    // SJ.destroyApp(true);
                    // SJ.notifyDestroyed();
                    return;
                }
            }
            // repaint();
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
                    // loadImage(31);
                    screen = 31;
                    // repaint();
                }
                else if ((m_mode >= 2) && (m_mode <= 5))
                {
                    int k = -1;
                    if ((last_stage / 10 - school == 0) && (last_stage != 45)) {
                        k = last_stage;
                    }
                    // destroyImage(3);
                    message = "Loading";
                    init_game(k);
                }
            }
            else if ((paramInt == 42) || (paramInt == -6)) {
                // destroyImage(3);
                // loadImage(2);
                screen = 2;
                m_mode = 1;
            }
            // repaint();
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
                    // destroyImage(31);
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
                        if (j == 1) {
                            saved_gold -= item_price[(b_item + i)];
                            message = "Purchasing Items";
                        }
                        else if (j == 0) {
                            message = "Bag is full";
                        }
                        else if (j == 3) {
                            message = "Duplicated item";
                        }
                    }
                    else {
                        message = "not enough gold";
                    }
                }
            }
            // repaint();
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
            // repaint();
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
            // repaint();
        }
        else if (screen == -5)
        {
            screen = 100;
            // repaint();
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
                if (m_mode == 1) {
                    last_stage = 11;
                    stage = 11;
                    saved_gold = 0;
                    hero.mana = 0;
                }
                // destroyImage(2);
                // loadImage(3);
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
            // repaint();
        }
        else if (screen == -33)
        {
            if ((paramInt == 42) || (paramInt == -6)) // * STAR
            {
                // loadImage(2);
                m_mode = 1;
                screen = 5;
                // repaint();
            }
        }
        else if (screen == 300)
        {
            MPlay(3);
            m_mode = -1;
            // loadImage(3);
            hero.position.x = 57*SGH_SCALE_RATIO;
            hero.position.y = 46*SGH_SCALE_RATIO;
            saved_gold += gold;
            setSavedGold(saved_gold);
            setSavedMana(hero.mana);

            ani_step = 0;
            screen = 3;
        }
        else if (screen == -1)
        {
            // loadImage(-2);
            screen = -2;
            // repaint();
        }
        else if (screen == -2)
        {
            // loadImage(2);
            screen = 1;
            // repaint();
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
            // repaint();
        }
        else if (screen == 1000)
        {
            // loadImage(2);
            screen = 300;
            // repaint();
        }
    }

    public void run()
    {
        if (gameOn) {
            if (screen == 6) // Game running screen
            {
                if (state == 1)
                {
                    try {
                        Thread.sleep(game_speed); // TODO use this for level and game speed control.
                        // Or use gdx way to auto change speed based on device CPU power
                    }
                    catch (Exception localException1) {}

                    if (hero.pw_up == 1) // hero fire
                    {
                        hero.setPower();
                        if (hero.h_idx == 2) {
                            hero.h_idx = 3;
                        } else if (hero.h_idx == 3) {
                            hero.h_idx = 2;
                        }
                    }
                    else if (hero.pw_up == 2)
                    {
                        if (hero.h_timer < 4)
                        {
                            hero.h_timer += 1;
                            if (hero.h_timer == 4) {
                                hero.h_idx = 0;
                            }
                        }
                        if (hero.snow_y < hero.snow_last_y) // > with original geometry top-left point to down and right
                        {
                            hero.snow_y += 1;
                            if (hero.snow_y < hero.snow_top_y) {
                                hero.snow_gap += 3;
                            } else if (hero.snow_y > hero.snow_top_y) {
                                hero.snow_gap -= 3;
                            }
                        }
                        else
                        {
                             check_ppang();
                        }
                    }
                    this.e_time += 1;
                    for (int i = 0; i < e_num; i++)
                    {
                        if (enemies[i].e_hp >= 0)
                        {
                            if ((e_time == enemies[i].e_fire_time) && (hero.get_random(3) != 1) && (enemies[i].e_ppang_item != 2)) {
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
                        else if ((enemies[i].e_move_dir == 0) && (enemies[i].e_hp > 0) && (enemies[i].e_ppang_item != 2))
                        {
                            enemies[i].e_move_ai(enemies, i);
                        }
                        else if ((enemies[i].e_move_dir < 100) && (enemies[i].e_move_dir != 0) && (enemies[i].e_hp > 0))
                        {
                            enemies[i].e_move(enemies, i);
                        }
                    } // End enemy attack n move ai
                    if (e_boss > 0)
                    {
                        if (boss.e_boss_hp >= 0)
                        {
                            if ((e_time == boss.e_boss_fire_time) && (hero.get_random(3) != 1)) {
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
                        else if ((boss.e_boss_move_dir == 0) && (boss.e_boss_hp > 0))
                        {
                            boss.boss_move_ai();
                        }
                        else if ((boss.e_boss_move_dir != 0) && (boss.e_boss_hp > 0))
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
                        // repaint();
                        // serviceRepaints();
                    }
                }
                // ani_step used in many screen, ie. draw STAGE ani. Tempory run over this case to avoid drawScreen ?
                else if (state == 2) {
                    if ((ani_step >= 1) && (ani_step <= 20)) {
                        ani_step += 1;
                    }
                    if (ani_step == 0)
                    {
                        // loadImage(-6);
                        ani_step = 1;
                    }
                    else if ((ani_step >= 1) && (ani_step <= 19))
                    {
                        // repaint();
                        // serviceRepaints();
                    }
                    else if (ani_step == 20)
                    {
                        // destroyImage(-6);
                        state = 1;
                    }
                }
                else if (state == 3) {
                    if (game_state == 2) {
                        screen = 201;
                        MPlay(7);
                        gold = (school * 6 + hero.get_random(7) + 5);
                    }
                    else if (game_state == 1) {
                        screen = 65336; // lose
                        gold = 3;
                    }
                    setSavedMana(hero.mana);
                    setSavedGold(getSavedgold()+ gold);
                    if(stage %10 < 4) { // next level
                        stage += 1;
                        setGameStage(stage);
                    }
                }
            }
            else if (screen == 8) {
                if ((ani_step < 50) && (ani_step > 0)) {
                    ani_step += 1;
                }
                // repaint();
                // serviceRepaints();
            }
            else if (screen == 9) {
                if ((ani_step < 46) && (ani_step >= 0)) {
                    ani_step += 1;
                }
                // repaint();
                // serviceRepaints();
            }
            else if (screen == 200) {
                if ((ani_step < 51) && (ani_step >= 0))
                {
                    ani_step += 1;
                    // repaint();
                    // serviceRepaints();
                }
                else
                {
                    gameOn = false;
                    // destroyImage(200);
                    if (state != 10)
                    {
                        // loadImage(2);
                        screen = 300;
                    }
                    else
                    {
                        screen = 1000;
                    }
                    // repaint();
                }
            }
            else if (screen == 201) {
                ani_step = 0;
                if (last_stage / 10 == school)
                {
                    if (stage % 10 != 4) {
                        stage += 1;
                    }
                    else if (stage != 44) {
                        stage += 10;
                        stage = (stage - stage % 10 + 1);
                    }
                    else {
                        stage = 45;
                        state = 10;
                    }
                    last_stage = stage;
                }

                screen = 200;
                game.setScreen(new VictoryScreen(game));
            } // end screen 201
            else if (screen == 65336) {  // lose
                item_mode = 0;
                ani_step = 0;
                // loadImage(65336);
                MPlay(6);
                screen = 65335;
            }
            else if (screen == 65335)
            {
                if (ani_step <= 100)
                {
                    ani_step += 1;
                    // repaint();
                    // serviceRepaints();
                }
                else
                {
                    gameOn = false;
                    // loadImage(2);
                    screen = 300;
                    // repaint();
                }
            }
        } // end is GameOn
        else {
            try {
                Thread.sleep(100L);
            }
            catch (Exception localException2) {}
        }
    } // End run()

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        batch.begin();
        // TODO use boundingBox and touchAreas
        float leftBound = 0;
        float rightBound = (int)(SCREEN_WIDTH - hero.getImage().getWidth())/CELL_WIDTH;
        // TODO use object instead of global var, handle screen size
        if(hero.position.x < leftBound) {
            hero.position.x = 0;
        }
        if(hero.position.x > rightBound) {
            hero.position.x = rightBound;
        }
        // TODO find where touch event (dragged) call, can it be overrided ?
        // BoundingBox can be use Rectangle as alternative ?
        // convert touch event to key event (getGameAction)
        touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);

        Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ upBtnRect.toString() + " saved "+ downBtnRect.toString());
        game_action = getGameAction();

        if(isTouchedSpeedUp()) { // smaller value, shorter sleep
            if(game_speed >= 12) {
                Gdx.input.vibrate(5);
                game_speed -= 8;
            }
            setGameSpeed(game_speed);
        }
        if(isTouchedSpeedDown()) {
            if(game_speed <= 128) {
                Gdx.input.vibrate(5);
                game_speed += 8;
            }
            setGameSpeed(game_speed);
        }
        /* collisionRay = camera.getPickRay(x, y);
        if (Intersector.intersectRayBoundsFast(collisionRay, touchKeyUpArea)) { // TODO may be need flag to avoid fire continuously
            game_action = GAME_ACTION_UP;
        }

        if(touchpad.getKnobPercentX() > 0) {
            game_action = GAME_ACTION_RIGHT;
        } else if(touchpad.getKnobPercentX() < 0){
            game_action = GAME_ACTION_LEFT;
        }
        if(touchpad.getKnobPercentY() > 0) {
            game_action = GAME_ACTION_UP;
        } else if(touchpad.getKnobPercentY() < 0) {
            game_action = GAME_ACTION_DOWN;
        }*/

        // Fire button touched
        Rectangle textureBounds=new Rectangle(SCREEN_WIDTH-fireBtnTexture.getWidth()-50, SCREEN_HEIGHT-50-fireBtnTexture.getHeight(), fireBtnTexture.getWidth(),fireBtnTexture.getHeight());
        if(textureBounds.contains(touchPoint.x, touchPoint.y)) {
            game_action = GAME_ACTION_OK;
        }

        // Use rectangle instead of collisionRay. TODO fix collisionRay null and multiplex many Gdx.input
        // TODO May be use OverlapTester Class for these task

        keyPressed();
        batch.end();

        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

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
        return game_action;
    }
    public int getGameAction() {
        // Gdx.app.log("INFO", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " bound x "+ upBtnRect.toString() + " down btn "+ downBtnRect.toString());
        if(OverlapTester.pointInRectangle(upBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
            return GAME_ACTION_UP;
        }
        if(OverlapTester.pointInRectangle(downBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
            if(item_mode == 0) {
                return GAME_ACTION_DOWN;
            }
        }
        if(OverlapTester.pointInRectangle(leftBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
            Gdx.input.vibrate(5);
            return GAME_ACTION_LEFT;
        }
        if(OverlapTester.pointInRectangle(rightBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
            Gdx.input.vibrate(5);
            return GAME_ACTION_RIGHT;
        }
        if(OverlapTester.pointInRectangle(optionBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
            return KEY_RIGHT_MENU;
        }

        return 0;
    }
    protected boolean isTouchedUp() {
        return OverlapTester.pointInRectangle(upBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedDown() {
        return OverlapTester.pointInRectangle(downBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedLeft() {
        return OverlapTester.pointInRectangle(leftBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedRight() {
        return OverlapTester.pointInRectangle(rightBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedOption() {
        return OverlapTester.pointInRectangle(optionBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedOK() {
        Rectangle textureBounds=new Rectangle(SCREEN_WIDTH-fireBtnTexture.getWidth()-50, SCREEN_HEIGHT-50-fireBtnTexture.getHeight(), fireBtnTexture.getWidth(),fireBtnTexture.getHeight());
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }
    protected boolean isTouchedNum3() {
        Rectangle textureBounds=new Rectangle(SCREEN_WIDTH-fireBtnTexture.getWidth()-50-imgKeyNum3.getWidth()-(int)fireBtnTexture.getWidth()/2, SCREEN_HEIGHT-BOTTOM_SPACE, imgKeyNum3.getWidth(),imgKeyNum3.getHeight());
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }
    protected boolean isTouchedSpeedUp() {
        return OverlapTester.pointInRectangle(speedUpBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedSpeedDown() {
        return OverlapTester.pointInRectangle(speedDownBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
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
        return getPrefs().getInteger(PREF_SPEED, 24);
    }

    public void setGameSpeed(int saved_gold) {
        getPrefs().putInteger(PREF_SPEED, saved_gold);
        getPrefs().flush();
    }
    public int getGameStage() {
        return getPrefs().getInteger(PREF_GAME_STAGE, 11);
    }

    public void setGameStage(int game_stage) {
        getPrefs().putInteger(PREF_GAME_STAGE, game_stage);
        getPrefs().flush();
    }

    /** Draw playing game screen (value code screen = 6). This will draw all related character like enemy, hero, enemy's item etc.
     * This will also draw S-T-A-G-E screen title on a game start. */
    public void drawRunningScreen() {
        // monitorEnemyPosition(); // custom inject function to correct enemy position. TODO figure out do we have to match all thing together or just inject simple "guard" method

        int j;
        batch.draw(imgBack, 0, VIEW_PORT_HEIGHT+BOTTOM_SPACE); // options|* button

        // ShapeRenderer has drawback, so do not use it.
        // TODO use TextureRegion to draw rectangle with color, pixel (mattdesl/lwjgl-basics)
        // The order of render in original J2ME is bk1 to white snow board. This way require exactly position draw of bk1 and whiteboard.
        // bk1 will be overlapped by white bg if white bg not scale and draw properly by its position.
        int snowBoardHeight = VIEW_PORT_HEIGHT - ui.getHeight();
        // Scale by Y vertically, bg is in square ratio.
        batch.draw(snowWhiteBg, 0, BOTTOM_SPACE+ui.getHeight(), snowBoardHeight, snowBoardHeight); // VIEW_PORT_HEIGHT
        batch.draw(hero.getImage(), hero.position.x * 5 * SGH_SCALE_RATIO, BOTTOM_SPACE+ui.getHeight()+SMALL_GAP , hero.getImage().getWidth(), hero.getImage().getHeight()); // orig 83
        if (hero.ppang_time > 0)
        {
            if (hero.ppang_item == 1) {
                batch.draw(imgItem_hyo[0], hero.position.x * CELL_WIDTH, 69*SGH_SCALE_RATIO); // orig y:74 TODO may be use relative position by hero.y
            } else {
                batch.draw(imgItem_hyo[1], hero.position.x * CELL_WIDTH-30, 48*SGH_SCALE_RATIO); // orig y:83; ui.getHeight will be wrong on scaled screen
            }
            hero.ppang_time -= 1;
            if (hero.ppang_time == 0) {
                hero.ppang_item = 0;
            }
        }
        draw_enemy();

        if (item_mode != 0) {
            if (message != "") {
                draw_text(message, 30*SGH_SCALE_RATIO, SCREEN_HEIGHT/2+SCREEN_HEIGHT/8, 3);
            }
            for (int i = 1; i <= 5; i++) {
                // drawRect(i * 12 + 23, 110, 10, 9);
            }
            if (item_mode != 100) {
                // drawRect(this.item_mode * 12 + 23, 110, 10, 9); // TODO avoid hard code position, anchor point // (16711680); // red ? white ?
                // ui overlap rectangle
                fillRect((item_mode * 12 + 21)*SGH_SCALE_RATIO, 45*SGH_SCALE_RATIO, 8*SGH_SCALE_RATIO,12, 0);
            }
            else if (item_mode == 100) {
                item_mode = 0;
            }
        } // end item_mode
        if (hero.pw_up == 2) {
            batch.draw(imgShadow, hero.snow_x * 5*SGH_SCALE_RATIO, (hero.snow_y * 6)*SGH_SCALE_RATIO); // orig: *7
            batch.draw(imgItem[hero.wp], hero.position.x * 5 * SGH_SCALE_RATIO, (hero.snow_y * 6 + hero.snow_gap)*SGH_SCALE_RATIO );
        }
        else if (hero.pw_up == 1) {
            if ((hero.real_snow_pw > 0) && (hero.ppang_item != 1))
            {
                // paramGraphics.setColor(7196662); // 6DCFF6 light_blue 2
                if ((int)hero.position.x >= 13) {
                    fillRect( (int)(hero.position.x * 5 - 5)*SGH_SCALE_RATIO+3, BOTTOM_SPACE+ui.getHeight()+SMALL_GAP, 27, hero.real_snow_pw * 27+16, 3);
                    // hero.position.y seem to be wrong
                    batch.draw(imgPwd, (hero.position.x * 5-6)*SGH_SCALE_RATIO, BOTTOM_SPACE+ui.getHeight()+SMALL_GAP); // orig 83/160. TODO why this calc return 0, can .0f fix this ?
                }
                else {
                    fillRect((int)(hero.position.x * 5 + 17)*SGH_SCALE_RATIO+3, (int)(BOTTOM_SPACE+ui.getHeight()+SMALL_GAP), 27, hero.real_snow_pw * 27+16, 3);
                    batch.draw(imgPwd, (hero.position.x * 5+16)*SGH_SCALE_RATIO, BOTTOM_SPACE+ui.getHeight()+SMALL_GAP); // orig 83/160
                }
            }
        } // end pw_up = 1
        else if (hero.pw_up == 0) {
            if (hero.ppang <= -1) {
                batch.draw(imgPok, hero.snow_x* 5* SGH_SCALE_RATIO, (hero.snow_y * 6 - 3)*SGH_SCALE_RATIO);
                hero.ppang -= 1;
                if (hero.ppang == -3) {
                    hero.ppang = 0;
                }
            }
            else if ((hero.ppang >= 1) && (hero.ppang <= 10)) {
                if (s_item != -10)
                {
                    if (hero.ppang < 3) {
                        batch.draw(imgPPang, hero.snow_x * 5*SGH_SCALE_RATIO, (hero.snow_y * 7 - 6)*SGH_SCALE_RATIO );
                    } else {
                        batch.draw(imgPPang1, hero.snow_x * 5* SGH_SCALE_RATIO, (hero.snow_y * 7 - 6)*SGH_SCALE_RATIO );
                    }
                }
                else if (hero.ppang < 4) {
                    batch.draw(imgEffect[0], hero.snow_x * CELL_WIDTH, (hero.snow_y * 7 - 2)*SGH_SCALE_RATIO );
                } else {
                    batch.draw(imgEffect[1], hero.snow_x * CELL_WIDTH, (hero.snow_y * 7 - 2)*SGH_SCALE_RATIO );
                }
                if (this.hit_idx != 10) {
                    if (enemies[hit_idx].e_hp > 0)
                    {
                        fillRect( (int)(enemies[hit_idx].position.x * 5 + 10)*SGH_SCALE_RATIO, (int)(enemies[hit_idx].position.y * 5 + 5)*SGH_SCALE_RATIO, 27, 15*SGH_SCALE_RATIO, 0); // 16711680); // FF0000
                        fillRect( (int)(enemies[hit_idx].position.x * 5 + 10)*SGH_SCALE_RATIO, (int)(enemies[hit_idx].position.y * 5 + 5 + 15 - 15 * enemies[hit_idx].e_hp / enemies[hit_idx].max_e_hp)*SGH_SCALE_RATIO, 27, (15 - 15 * enemies[hit_idx].e_hp / enemies[hit_idx].max_e_hp)*SGH_SCALE_RATIO, 4); // 9672090
                    }
                }
                else if (hit_idx == 10) {
                    if (boss.e_boss_hp > 0)
                    {
                        fillRect( (int)(boss.position.x * 5 + 12)*SGH_SCALE_RATIO, (int)(boss.position.y * 5 + 5)*SGH_SCALE_RATIO, 27, 135, 0); // (16711680); // FF0000
                        // draw boss hp bar
                        batch.draw(boss.getImage(), (boss.position.x* 5 + 12)*SGH_SCALE_RATIO, (boss.position.y* 5 + 5)*SGH_SCALE_RATIO, 3*SGH_SCALE_RATIO, 15*SGH_SCALE_RATIO);
                        fillRect( (int)(boss.position.x * 5 + 12)*SGH_SCALE_RATIO, (int)(boss.position.y * 5 + 5)*SGH_SCALE_RATIO, 27, (15 - 15 * boss.e_boss_hp / boss.max_e_boss_hp)*SGH_SCALE_RATIO, 4); // (9672090); // 0093959A gray
                    }
                    if (al == 1) {
                        batch.draw(imgAl, (hero.snow_x * 5 + 6)*SGH_SCALE_RATIO, (hero.snow_y * 7 - 10)*SGH_SCALE_RATIO);
                    }
                }
                hero.ppang += 1;
                if (hero.ppang == 6) {
                    hero.ppang = 0;
                    s_item = 0;
                    al = -1;
                }
            }
            else if (hero.ppang >= 50) {
                draw_sp_hyo();
            }
            if (message != "") {
                draw_text(message, (int)(SCREEN_WIDTH/3), SCREEN_HEIGHT/2+SCREEN_HEIGHT/8, 3);
            }
        } // end pw_up = 0
        else if (hero.pw_up == -1) {
            hero.pw_up = 0;
        }
        if (p_mode == 1) {
            try
            {
                batch.draw(ui, 0, BOTTOM_SPACE); // orig: y:109
            }
            catch (Exception localException1) {}
            draw_item();
            // p_mode = 2; // Change p_mode may be cause it draw only 1st time.
            // Canvas has showNotify() call each time view visible ? In this function, SBF set p_mode back to 1.
        }
        if (d_gauge != 0) {
                draw_gauge();
        }
        for (j = 0; j < e_num; j++) { // or count array elements enemies
            if (enemies[j].e_behv != 100) {
                // Be careful with * CELL_WIDTH; original snow_x do not multiple by CELL_WIDTH (5px). This will cause snow gap slide horizontal.
                // e_snow_x is real position, not Cell map
                // Be carefull with item and snow align centered, it require both scale item n shadow match.
                batch.draw(imgShadow, enemies[j].e_snow_x, (enemies[j].e_snow_y * 5-5)*SGH_SCALE_RATIO ); // orig: y*6+17;
                batch.draw(imgItem[enemies[j].e_wp], enemies[j].e_snow_x, (enemies[j].e_snow_y * 5 + 9 - enemies[j].e_snow_gap)*SGH_SCALE_RATIO ); // orig *6 + 13
            }
        }
        if ((boss.e_boss_behv != 100) && (e_boss > 0))
        {
            batch.draw(imgShadow, boss.e_boss_snow_x, boss.e_boss_snow_y * 6 + 17);
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
                fillRect(45, BOTTOM_SPACE+16, 81, 12*SGH_SCALE_RATIO, 0); // 16711680);    // FF0000
                // paramGraphics.setColor(9342606); // 8E8E8E gray
                if (hero.hp > 0) {
                     fillRect(45, BOTTOM_SPACE+ui.getHeight()-(12 - 12 * hero.hp / hero.max_hp)*SGH_SCALE_RATIO-24, 81, (12 - 12 * hero.hp / hero.max_hp)*SGH_SCALE_RATIO, 4);
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
                batch.draw(imgStage[0], 20 * SGH_SCALE_RATIO,  VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);  // Anchor 20, below same value orig: 60 * SGH_SCALE_RATIO
            }
            if (ani_step >= 6) {
                batch.draw(imgStage[1], 35 * SGH_SCALE_RATIO, VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);
            }
            if (ani_step >= 9) {
                batch.draw(imgStage[2], 50 * SGH_SCALE_RATIO, VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);
            }
            if (ani_step >= 12) {
                batch.draw(imgStage[3], 65 * SGH_SCALE_RATIO, VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);
            }
            if (ani_step >= 15) {
                batch.draw(imgStage[4], 80 * SGH_SCALE_RATIO, VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);
            }
            if (ani_step >= 19) {
                batch.draw(imgStage_num, 95 * SGH_SCALE_RATIO, VIEW_PORT_HEIGHT/2 + BOTTOM_SPACE);
            }
        }
    }

    public void drawSpecialScreen() {
        int j;
        if ((ani_step == 1) || (ani_step == 46))
        {
            if (ani_step == 46)
            {
                // destroyImage(9);
                // loadImage(100);
                hero.pw_up = -1;
                batch.draw(imgBack, 0, VIEW_PORT_HEIGHT);
                screen = 6;
                hero.ppang = 50;
                for (j = 0; j < e_num; j++)
                {
                    enemies[j].e_move_dir = 0;
                    decs_e_hp(j);
                }
                if (e_boss > 0)
                {
                    boss.e_boss_move_dir = 0;
                    decs_e_hp(10);
                }
                hero.dem = 12;
                hero.mana = 0;
                setSavedMana(hero.mana);
            }
            fillRect(0, 135*SGH_SCALE_RATIO, SCREEN_WIDTH, 76*SGH_SCALE_RATIO, 5); // setColor(16777215); // FFFFFF

            for (j = 0; j < e_num; j++)
            {
                if (enemies[j].position.x != -10) {
                    batch.draw(enemies[j].getImage(), enemies[j].position.x * 5*SGH_SCALE_RATIO, (enemies[j].position.y * 5 + 5)*SGH_SCALE_RATIO, enemies[j].getImage().getWidth(), enemies[j].getImage().getHeight());
                }
                if (enemies[j].e_behv != 100) {
                    batch.draw(imgShadow, enemies[j].e_snow_x*CELL_WIDTH, (enemies[j].e_snow_y * 5-5)*SGH_SCALE_RATIO ); // orig: y*6+17;
                    batch.draw(imgItem[enemies[j].e_wp], enemies[j].e_snow_x*CELL_WIDTH, (enemies[j].e_snow_y * 5 + 13 - enemies[j].e_snow_gap)*SGH_SCALE_RATIO ); // orig *6 + 13
                }
            }
            if (e_boss > 0) {
                batch.draw(boss.getImage(), boss.position.x*CELL_WIDTH, boss.position.y*CELL_WIDTH); // idx
            }
        }
        if (this.special == 1)
        {
            if (ani_step <= 45) {
                if(special > 0) {
                    imgSp = new Texture("data/samsung-white/sp" + special + ".png");
                }
                batch.draw(imgSp, (158 - ani_step * 3)*SGH_SCALE_RATIO, 160*SGH_SCALE_RATIO-imgSp.getHeight());
            }
        }
        else if (special == 2)
        {
            if (ani_step <= 45) {
                if(special > 0) {
                    imgSp = new Texture("data/samsung-white/sp" + special + ".png");
                }
                batch.draw(imgSp, (158 - ani_step * 3)*SGH_SCALE_RATIO, 160*SGH_SCALE_RATIO-imgSp.getHeight());
            }
        }
        else if ((special == 3) && (ani_step <= 45)) {
            if(imgSp == null) {
                imgSp = new Texture("data/samsung-white/sp" + special + ".png");
            }
            batch.draw(imgSp, (168 - ani_step * 3)*SGH_SCALE_RATIO, 160*SGH_SCALE_RATIO-imgSp.getHeight());
        }
        batch.draw(hero.getImage(), hero.position.x * 5 * SGH_SCALE_RATIO, BOTTOM_SPACE+ui.getHeight(), hero.getImage().getWidth(), hero.getImage().getHeight());
    }

    public void make_e_num(int paramInt1, int paramInt2)
    {
        if (paramInt2 == 1)
        {
            if (paramInt1 == 1)
            {
                e_boss = 0;
                e_num = 2;
            }
            else if (paramInt1 == 2)
            {
                e_boss = 0;
                e_num = 2;
            }
            else if (paramInt1 == 3)
            {
                e_boss = 1;
                e_num = 2;
            }
            else if (paramInt1 == 4)
            {
                e_boss = 3;
                e_num = 2;
            }
        }
        else if (paramInt2 == 2)
        {
            if (paramInt1 == 1)
            {
                e_boss = 0;
                e_num = 2;
            }
            else if (paramInt1 == 2)
            {
                e_boss = 0;
                e_num = 3;
            }
            else if (paramInt1 == 3)
            {
                e_boss = 2;
                e_num = 2;
            }
            else if (paramInt1 == 4)
            {
                e_boss = 3;
                e_num = 3;
            }
        }
        else if (paramInt2 == 3)
        {
            if (paramInt1 == 1)
            {
                e_boss = 0;
                e_num = 3;
            }
            else if (paramInt1 == 2)
            {
                e_boss = 2;
                e_num = 2;
            }
            else if (paramInt1 == 3)
            {
                e_boss = 0;
                e_num = 4;
            }
            else if (paramInt1 == 4)
            {
                e_boss = 3;
                e_num = 4;
            }
        }
        else if (paramInt2 == 4) {
            if (paramInt1 == 1)
            {
                e_boss = 1;
                e_num = 3;
            }
            else if (paramInt1 == 2)
            {
                e_boss = 2;
                e_num = 3;
            }
            else if (paramInt1 == 3)
            {
                e_boss = 3;
                e_num = 4;
            }
            else if (paramInt1 == 4)
            {
                e_boss = 4;
                e_num = 4;
            }
        }
        e_t_num = e_num;
        tmp_stage = paramInt1;
    }

    public void make_enemy(int paramInt)
    {
        if (paramInt < 0) { // new game ?
            make_e_num(hero.get_random(2) + 2, school);
        } else {
            make_e_num(this.last_stage % 10, this.school);
        }
        //enemies = new Enemy[e_num];
        initEnemy();

        e_time = 0;

        if (school < 3) {
            e_dem = (school + 7);
        } else if (school == 3) {
            e_dem = (school + 9);
        } else {
            e_dem = 14;
        }

        for (int i = 0; i < e_num; i++)
        {
            if ((this.school == 1) || (this.school == 2)) {
                enemies[i].e_hp= 30 + this.school * 10; // 20 def
            } else if (this.school == 3) {
                enemies[i].e_hp = 54;
            } else if (this.school == 4) {
                enemies[i].e_hp = 66;
            } else {
                enemies[i].e_hp = 40;
            }
            enemies[i].max_e_hp = enemies[i].e_hp;
            enemies[i].e_snow_y = -10;
            enemies[i].e_behv = 100;
            enemies[i].e_wp = 0;
            enemies[i].e_dem = e_dem;
        }

        enemies[0].position.x = (3 + hero.get_random(3));
        // We have to change because of different projection and/or screen size. Collision may be affected but we can use GDX way not manual calc.
        enemies[0].position.y = ((TOP_BOUND/CELL_WIDTH-3) + hero.get_random(3)); // orig: 1 + get_random(3).
        enemies[0].e_lv = 3;
        enemies[0].e_fire_time = 8;
        enemies[1].position.x = (18 + hero.get_random(3));
        enemies[1].position.y = ((TOP_BOUND/CELL_WIDTH-3) + hero.get_random(3));
        enemies[1].e_lv = 3;
        enemies[1].e_fire_time = 17;
        if (this.e_t_num >= 3)
        {
            enemies[2].position.x = (13 + hero.get_random(3));
            enemies[2].position.y = ((TOP_BOUND/CELL_WIDTH-5)+ 3 + hero.get_random(2));
            enemies[2].e_lv = 3;
            enemies[2].e_fire_time = 20;
        }
        if (this.e_t_num == 4)
        {
            enemies[3].position.x = 8;
            enemies[3].position.y = 25; // orig: 5
            enemies[3].e_lv = 3;
            enemies[3].e_fire_time = 4;
        }
        boss.e_boss_behv = 100;
        boss.e_boss_snow_y = -10;
        boss.position.x = 10;
        boss.position.y = 22; // orig 6
        boss.e_boss_idx = 0;
        boss.e_boss_hp = (this.e_boss * 10 + 30 + (this.school - 1) * 10);
        boss.max_e_boss_hp = boss.e_boss_hp;
        boss.e_boss_fire_time = 2;
    }

    public void drawInstructionScreen() { // screen = 2
    }
    public void drawVillageScreen() { // screen = 3
    }
    public void drawShopScreen() { // screen = 31
    }
    public void drawSoundSettingScreen() { // screen 100
    }
    public void drawNewGameMenu() { // screen -88
    }
    public void drawSpecialAnimation() { // screen 8
        if ((ani_step == 1) || (ani_step == 2))
        {
            // setColor(10173); // #0027bd can use light blue
            fillRect(0, 40*SGH_SCALE_RATIO, SCREEN_WIDTH, 60*SGH_SCALE_RATIO, 1);
            batch.draw(imgSpecial[0], 44*SGH_SCALE_RATIO, 90*SGH_SCALE_RATIO);
            batch.draw(imgSpecial[1], 44*SGH_SCALE_RATIO, 99*SGH_SCALE_RATIO);
        }
        else if (ani_step == 8)
        {
            batch.draw(imgSpecial[0], 44*SGH_SCALE_RATIO, 90*SGH_SCALE_RATIO);
            batch.draw(imgSpecial[1], 48*SGH_SCALE_RATIO, 99*SGH_SCALE_RATIO);
        }
        else if (ani_step == 16)
        {
            batch.draw(imgSpecial[0], 44*SGH_SCALE_RATIO, 90*SGH_SCALE_RATIO);
            batch.draw(imgSpecial[1], 51*SGH_SCALE_RATIO, 99*SGH_SCALE_RATIO);
        }
        else if (ani_step == 23)
        {
            batch.draw(imgSpecial[0], 44*SGH_SCALE_RATIO, 90*SGH_SCALE_RATIO);
            batch.draw(imgSpecial[1], 54*SGH_SCALE_RATIO, 99*SGH_SCALE_RATIO);
        }
        else if (ani_step == 30)
        {
            batch.draw(imgSpecial[0], 44*SGH_SCALE_RATIO, 90*SGH_SCALE_RATIO);
            batch.draw(imgSpecial[1], 55*SGH_SCALE_RATIO, 99*SGH_SCALE_RATIO);
        }
        else if (ani_step == 37)
        {
            batch.draw(imgSpecial[2], 58*SGH_SCALE_RATIO, 98*SGH_SCALE_RATIO);
        }
        else if (ani_step == 50)
        {
            //loadImage(9);
            ani_step = 0;
            screen = 9;
        }
    }
    public void drawSoundSpeedSetting() { // screen 4

    }
    public void drawGuideMenu() {} // screen 5
    public void drawListItems() { // screen -33
    }

    public void drawLoseScreen() {} // screen 65335
    public void drawGoodJob() { // screen 300
        MPlay(3);
//        this.m_mode = -1;
//        //destroyImage(2);
//        //loadImage(3);
//        // Victory screen => village
//
//        hero.position.x = (int)(57/128*SCREEN_WIDTH);
//        hero.position.y = (int)(114/160*SCREEN_HEIGHT);
//        saved_gold += this.gold;
//        setSavedGold(saved_gold);
//        ani_step = 0;
//        screen = 3;
        //repaint();
    }
    public void drawTextScreen() { // screen 77
        draw_text(message, (int)(SCREEN_WIDTH/3), SCREEN_HEIGHT/2+SCREEN_HEIGHT/8, 3);
    }
    public void drawLogoScreen() {} // screen -1
    public void drawSamsungLogo() {} // screen -2
    public void drawAllClear() {}
    public void drawManualScreen() {} // screen -5

    public void draw_gauge()
    {
        if (d_gauge == 2)
        {
            // setColor(16775065); // FFF799 light yellow
            fillRect(118*SGH_SCALE_RATIO, 49*SGH_SCALE_RATIO, 8*SGH_SCALE_RATIO, 8*SGH_SCALE_RATIO, 2);
            if (hero.wp != 0) {
                batch.draw(this.imgItem[hero.wp], 122*SGH_SCALE_RATIO, 49*SGH_SCALE_RATIO + BOTTOM_SPACE);
            }
        }
        if (hero.mana != 0)
        {
            //setColor(16711680); // FF0000
            fillRect(28*SGH_SCALE_RATIO, 29*SGH_SCALE_RATIO+3, hero.mana*SGH_SCALE_RATIO, 20, 0);
            if (hero.mana == 36)
            {
                fillRect(37*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 0);
                fillRect(49*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 0);
                fillRect(61*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 0);
            }
            else if (hero.mana >= 24)
            {
                fillRect(37*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 0);
                fillRect(49*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 0);
            }
            else if (hero.mana >= 12)
            {
                fillRect(37*SGH_SCALE_RATIO, 29*SGH_SCALE_RATIO+3, 27, 36, 0);
            }
        }
        else if (hero.mana == 0)
        {
            //setColor(4960985); // 4BB2D9 light blue
            fillRect(28*SGH_SCALE_RATIO, 29*SGH_SCALE_RATIO+3, 36*SGH_SCALE_RATIO, 1*SGH_SCALE_RATIO, 2);
            fillRect(37*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 2);
            fillRect(49*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 2);
            fillRect(61*SGH_SCALE_RATIO, 28*SGH_SCALE_RATIO, 27, 36, 2);
        }
        d_gauge = 0;
    }

    public void check_ppang() {
        d_gauge = 2;
        int j;
        for (int i = 0; i < e_num; i++)
        {
            j = (int)enemies[i].position.x;
            if ((j - hero.snow_x >= -1) && (j - hero.snow_x <= 1))
            {
                int k = (int)enemies[i].position.y;
                if ((k >= 30) && (k <= 35)) // orig: 0,4
                {
                    // min e.y = 30 and min power to reach enemy is 2 (or 3 ?) => item hit at y = 33 ?
                    if ((k - hero.real_snow_pw == 27) || (k - hero.real_snow_pw == 28)) // max top enemy.y + max power = 36 + 8
                    {
                        hero.ppang = 1;
                        decs_e_hp(i);
                        break;
                    }
                }
                else if ((k - hero.real_snow_pw == 28) || (k - hero.real_snow_pw == 29))
                {
                    hero.ppang = 1;
                    decs_e_hp(i);
                    break;
                }
            }
            hero.ppang = -1;
        }
        if ((e_boss > 0) && (boss.position.x - hero.snow_x >= -1) && (boss.position.x - hero.snow_x <= 1))
        {
            j = (int)(boss.position.y - hero.real_snow_pw + 1); // orig + -
            if ((j == 16) || (j == 17) || (j == 18)) // 7 8 9
            {
                if (j == 17) {
                    al = 1;
                }
                hero.ppang = 1;
                decs_e_hp(10);
            }
        }
        hero.pw_up = -1;
        if (hero.wp != 0) {
            hero.wp = 0;
        }
    }
    public void use_special()
    {
        screen = 8;
        ani_step = 1;
        hero.real_snow_pw = 0;
        hero.snow_pw = 0;
        hero.h_idx = 0;
        // gameOn = false;
        // destroyImage(100); // imageBak
        // loadImage(8);
        if (hero.mana == 36)
        {
            special = 3;
            hero.dem = 24;
        }
        else if (hero.mana >= 24)
        {
            special = 2;
            hero.dem = 12;
        }
        else if (hero.mana >= 12)
        {
            special = 1;
            hero.dem = 12;
        }
        d_gauge = 1;
        MPlay(5);
        call_vib(3);
    }
    public void decs_e_hp(int paramInt)
    {
        hit_idx = paramInt;
        if (hero.mana != 36) {
            if (hero.mana <= 10) {
                hero.mana += 2;
            } else {
                hero.mana += 1;
            }
        }
        if (paramInt != 10)
        {
            if (hero.wp == 0) {
                enemies[paramInt].e_hp -= hero.dem;
            }
            else if (hero.wp == 1) {
                enemies[paramInt].e_ppang_time = 70;
                enemies[paramInt].e_ppang_item = 1;
                enemies[paramInt].e_lv = (-enemies[paramInt].e_lv);
                enemies[paramInt].e_hp  -= hero.dem;
            }
            else if (hero.wp == 2) {
                s_item = -10;
                enemies[paramInt].e_hp -= 19;
            }
            else if (hero.wp == 3) {
                enemies[paramInt].e_ppang_time = 65;
                enemies[paramInt].e_ppang_item = 2;
                enemies[paramInt].e_hp -= hero.dem / 2;
                enemies[paramInt].e_move_dir = 0;
            }
            else if (hero.wp == 4) {
                enemies[paramInt].e_ppang_time = 75;
                enemies[paramInt].e_ppang_item = 1;
                enemies[paramInt].e_lv = (-enemies[paramInt].e_lv);
                s_item = -10;
                enemies[paramInt].e_hp -= hero.dem * 2;
            }
            if (enemies[paramInt].e_hp < 0) {
                enemies[paramInt].e_idx = 2;
                enemies[paramInt].dis_count = -1;
            }
        }
        else if (paramInt == 10)
        {
            if (hero.wp == 4) {
                s_item = -10;
                boss.e_boss_hp -= hero.dem * 2;
            }
            else if (hero.wp == 2) {
                s_item = -10;
                boss.e_boss_hp -= 19;
            }
            else {
                boss.e_boss_hp -= hero.dem;
            }
            if (boss.e_boss_hp < 0) {
                boss.e_boss_idx = 2;
                boss.boss_dis_count = -1;
            }
            if (al == 1) {
                boss.e_boss_hp -= 5;
            }
        }
         MPlay(3);
    }
    public void fillRect(int x, int y, int width, int height, int color) {
        // Hard code default width x height of color img: 12x12 px
        int scaleY = height / 12;
        int scaleX = width / 12;
        // (Texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
        batch.draw(imgColor[color], x, y, 0, 0, imgColor[color].getWidth(), imgColor[color].getHeight(), scaleX, scaleY, 0, 0, 0, imgColor[color].getWidth()*scaleX, imgColor[color].getHeight()*scaleY, false, false);
    }
    public void drawRect(int x, int y, int width, int height, int color) {

    }

    public void use_item(int paramInt)
    {
        if(paramInt > item_slot.length) { // avoid out of bound
            paramInt = 0;
        }
        if ((item_slot[paramInt] > 0) && (item_slot[paramInt] <= 4))
        {
            hero.wp = item_slot[paramInt];
            if (hero.wp == 1)
            {
                item_a_num -= 1;
                if (item_a_num == 0) {
                    delete_item(1);
                }
            }
            else if (hero.wp == 2)
            {
                item_b_num -= 1;
                if (item_b_num == 0) {
                    delete_item(2);
                }
            }
            else if (hero.wp == 3)
            {
                item_c_num -= 1;
                if (item_c_num == 0) {
                    delete_item(3);
                }
            }
            else if (hero.wp == 4)
            {
                item_d_num -= 1;
                if (item_d_num == 0) {
                    delete_item(4);
                }
            }
            d_gauge = 2;
        }
        else if (item_slot[paramInt] == 5)
        {
            delete_item(5);
            hero.hp += hero.max_hp / 3;
            if (hero.hp > hero.max_hp) {
                hero.hp = hero.max_hp;
            }
            hero.h_timer_p = -4;
        }
        else if (this.item_slot[paramInt] == 6)
        {
            delete_item(6);
            hero.mana += 10;
            if (hero.mana > 36) {
                hero.mana = 36;
            }
            d_gauge = 1;
        }
        else if (this.item_slot[paramInt] == 7)
        {
            delete_item(7);
            hero.hp = hero.max_hp;
            hero.h_timer_p = -4;
        }
        else if (this.item_slot[paramInt] == 8)
        {
            delete_item(8);
            hero.hp += hero.max_hp / 3;
            if (hero.hp > hero.max_hp) {
                hero.hp = hero.max_hp;
            }
            hero.h_timer_p = -4;
            hero.ppang_item = 0;
            hero.ppang_time = 0;
        }
        item_mode = 100;
        MPlay(1);
    }

    public void delete_item(int paramInt)
    {
        for (int i = 0; i < 5; i++) {
            if (item_slot[i] == paramInt)
            {
                item_slot[i] = 0;
                del = i;
                return;
            }
        }
    }
    // TODO use best (I forgot tool) for convert mmf to midi and/or to mp3.
    // Default FormatFactory seem to lost some track of original sound.
    public void MPlay(int paramInt) {
        String str = null;
        // if (s_play == 1)
        if (paramInt == 0) {
            str = "9.mid";
        } else if (paramInt == 1) {
            str = "one.mid"; // /1.mmf
        } else if (paramInt == 2) {
            str = "lose.mp3"; // /6.mmf
        } else if (paramInt == 3) {
            str = "hit.mp3"; // /5.mmf
        } else if (paramInt == 4) {
            str = "four.mp3"; // /4.mmf
        } else if (paramInt == 5) {
            str = "special.mp3"; // /8.mmf
        } else if (paramInt == 6) {
            str = "lose.mp3"; // /3.mmf
        } else if (paramInt == 7) {
            str = "victory.mp3"; // 0.mid
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("data/audio/"+str));
        if(music != null) {
            if (!music.isPlaying()) {
                music.play();
                music.setLooping(false);
            }
        }
    }

    public void call_vib(int paramInt) {
        // if (this.v_mode == 1) { }
        Gdx.input.vibrate(paramInt*1000);
    }

}