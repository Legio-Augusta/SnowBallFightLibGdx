package com.littlewing.sbf.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;

import java.util.Random;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.littlewing.sbf.game.OverlapTester;

/** TODO
 * - issues: game gesture, user input;
 * - migrate old physical keyboard meme to new touch screen
 * - support full notch screen, orientation landscape, split screen.
 * - issue with button area, hint/hight light for easy of use
 * - iOS
 * - Ads and/or payment
 */

/**
 * Fixed position of button.
 * https://qph.fs.quoracdn.net/main-qimg-4f8b79be77bb76e9103a8ee0e6c35f2b
 * viewport vs pixel
 * mi note 7 1080 x 2340; real GDX resolution seem 2130 -> about 210 pixels top notch ?
 * 1200 x 1920 Nexus 7; 1440 x 2960 S9; S7 1440 x 2560
 */
public class GameScreen extends DefaultScreen implements InputProcessor {
    private static final int DEFAULT_DEM = 12;
    // RecordStore recordStore = null;
    private int game_state = 0;
    private int saved_gold = 200; // orig.10
    private int speed = 4;
    private int game_speed = 17; // orig.17
    private Random rnd = new Random();
    // private SnowBallFightME SJ;
    private Thread thread = null;
    private int screen = -1;
    private boolean gameOn = true;
    private String message;
    private int m_mode = 1;  // For shop, game...; appeared 128 times. It game_mode / game state ...
    private int s_play = 1;
    private int v_mode = 1;
    //  AudioClip audioClip = null;
    private int p_mode;
    private int ani_step;
    private Texture imgLogo;
    private Texture imgMM;
    private Texture imgBk;
    private Texture imgSl;
    private Texture imgPl;
    private Texture imgCh;
    private Texture imgChSwap; // NF fix for swap back n ford
    private Texture imgHeroIcon; // nickfarrow addition fix
    private Texture[] imgNum;
    private Texture imgBack;
    private Texture[] imgBacks; // nickfarrow
    private Texture[] imgHero;
    private Texture[] imgEnemy;
    private Texture[] imgBoss;
    private Texture[] imgBoss1; // nickfarrow
    private Texture[] imgBoss2; // nickfarrow
    private Texture[] imgBoss3; // nickfarrow
    private Texture[] imgBoss4; // nickfarrow
    private Texture imgAl;
    private Texture imgShadow;
    private Texture imgPok;
    private Texture imgPPang;
    private Texture imgPPang1;
    private Texture imgH_ppang;
    private Texture imgSnow_g;
    private Texture imgPwd;
    private Texture[] imgItem;
    private Texture[] imgItem_hyo;
    private Texture imgVill;
    private Texture imgSchool;
    private Texture imgShop;
    private Texture[] imgShops; // nickfarrow
    private Texture[] imgSpecial;
    private Texture imgSp;
    private Texture[] imgSps; // nickfarrow
    private Texture[] imgEffect;
    private Texture imgVictory;
    private Texture imgV;
    private Texture imgHero_v;
    private Texture imgLose;
    private Texture imgHero_l;
    private Texture imgStage_num;
    private Texture[] imgStage_nums; // nickfarrow
    private Texture ui; // Customized

    // GDX added to fix J2ME
    private Texture txt1;
    private Texture txt2;
    private Texture txt4;
    private Texture txt4b;

    private Texture title;
    private Texture present;
    private Texture sam_logo;
    private Texture http1;
    private Texture http2;
    private Texture allClear;

    private String current_music = "";
    public int af = 1; // sound on/off ?
    public static AssetManager manager;
    private String curent_music = "";
    // End GDX added to fix J2ME

    private Texture[] imgStage;
    private int stage;
    private int last_stage = 11;
    private int tmp_stage;
    private int school;
    private int state; // 1 2 3 10=allClear
    private int h_x;
    private int h_y;
    private int h_idx;
    private int h_timer;
    private int h_timer_p;
    private int pw_up;
    private int mana = 0;
    private int hp;
    private int max_hp;
    private int dem; // Damage by special effect
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
    private int s_item; // shop
    private int[] e_x;
    private int[] e_y;
    private int e_num;
    private int e_t_num;
    private int[] e_idx;
    private int[] e_hp;
    private int[] max_e_hp;
    private int[] e_snow_y;
    private int[] e_snow_x;
    private int[] e_snow_top;
    private int[] e_snow_gap;
    private int[] e_snow_dx;
    private int[] e_ppang_item;
    private int[] e_ppang_time;
    private int[] e_lv;
    private int[] e_fire_time;
    private int[] e_move_dir;
    private int e_time;
    private int e_dem;
    private int[] e_wp;
    private int[] e_behv;
    private int hit_idx;
    private int[] dis_count;
    private int e_boss;
    private int e_boss_idx;
    private int e_boss_x;
    private int e_boss_y;
    private int e_boss_fire_time;
    private int e_boss_hp;
    private int max_e_boss_hp;
    private int e_boss_snow_y;
    private int e_boss_snow_x;
    private int e_boss_snow_top;
    private int e_boss_snow_gap;
    private int e_boss_snow_dx;
    private int e_boss_wp;
    private int e_boss_behv;
    private int e_boss_move_dir;
    private int boss_dis_count;
    private int al;
    private int d_gauge;

    // In Emulator it's a square 128x128 and have blue blank bottom.
    private static int OLD_MOBI_W = 128; // Original Java Phone resolution. Damn many orig png have 128x => it should be 128 width tho.
    private static int OLD_MOBI_H = 160;  // JavaME height = 128 | 160px; Not affect internal draw.

    // SGH T199 128x160 ; E250 128x160
    private static float MIDP_SCL = (float)Gdx.graphics.getWidth()/ OLD_MOBI_W; // mostly 1080 / 128
    private static float SCALED_IMG_RATIO = 9; // Scaled png from original one.

    float SCALE_1080 = (float) GDX_WIDTH / 1080; // (float)GDX_HEIGHT/1920;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture fireBtnTexture;
    Texture snowWhiteBg;

    // Ratio 3:4 ~ 9:12 So with ratio 9:16 we have blank = 4/16
    // 128x160 <=> 1080x1350
    private static int GDX_WIDTH = Gdx.graphics.getWidth();
    private static int GDX_HEIGHT = Gdx.graphics.getHeight();

    // Debug
    int MIDP_H_SCALED = (int) (GDX_WIDTH / OLD_MOBI_W * 160);
    int EXCEEDED_H = Gdx.graphics.getHeight() - MIDP_H_SCALED;

    private static int BOTTOM_SPACE = (int)(GDX_HEIGHT /8); // May be change for fit touch button

    Rectangle upBtnRect, downBtnRect, leftBtnRect, rightBtnRect, optionBtnRect, speedUpBtnRect, speedDownBtnRect;
    Rectangle leftMenuBtn, rightMenuBtn;

    private int game_action = DUMP_ACTION_STATE;

    // Careful with key_code, it's different from Mobile brands, manufacturers
    private int key_code = DUMP_ACTION_STATE; // 0
    private static final int GAME_ACTION_OK = 8; // simulate KEY, gameAction in J2ME
    private static final int GAME_ACTION_LEFT = 2;
    private static final int GAME_ACTION_RIGHT = 5;
    private static final int GAME_ACTION_UP = 1; // It dupplicate w ACTION_OK; prev: 8
    private static final int GAME_ACTION_DOWN = 6;
    private static final int KEY_RIGHT_MENU = 35; // action = 0 // KEY_RIGHT_MENU = 35 ? -7 (from AA) # key
    private static final int KEY_LEFT_MENU = -6;  // action = 0; * key
    private static final int KEY_OK = -5;
    private static final int KEY_STAR = 0;
    private static final int KEY_NUM_3 = 0; // for item mode
    private static final int KEY_SHARP = 0;
    private static final int DUMP_ACTION_STATE = -6996;

    Vector3 touchPoint;
    // TouchStatus should be use Enum

    private ShapeRenderer shapeRenderer;

    Preferences prefs = Gdx.app.getPreferences("gamestate");
    private static final String PREF_VIBRATION      = "vibration";
    private static final String PREF_SOUND_ENABLED  = "soundenabled";
    private static final String PREF_SPEED          = "gamespeed";
    private static final String PREF_LEVEL          = "level";
    private static final String PREF_SAVEDGOLD      = "saved_gold";
    private static final String PREF_MANA           = "mana";
    private static final String PREF_GAME_STAGE     = "game_stage";
    private static final String PREF_LAST_GAME_STAGE = "last_game_stage";

    private Texture [] imgColor; // For fillRect with color
    private Texture imgKeyNum3;
    private Texture imgSpeedUp;
    private Texture imgSpeedDown;

    private Texture touch_pad;
    private Texture touch_pad_knob;

    private Texture imgMenuLeft;
    private Texture imgMenuRight;
    // downBtnRect, leftBtnRect, optionBtnRect, speedUpBtnRect, leftMenuBtn

    BitmapFont font;
    GlyphLayout layout;
    private Music music;
    private Music music_opening = Gdx.audio.newMusic(Gdx.files.internal("data/audio/night.wav")); // Donald_Christmas.mp3
    Viewport viewport;

    private int shitty = 0; // debug flag

    public GameScreen(Game game)
    {
        super(game);
        this.item_price[0] = 5;
        this.item_price[1] = 8;
        this.item_price[2] = 8;
        this.item_price[3] = 14;
        this.item_price[4] = 6;
        this.item_price[5] = 12;
        this.item_price[6] = 10;
        this.item_price[7] = 12;
        this.item_slot[0] = 3;
        this.item_slot[1] = 5;
        this.last_stage = getGameStage();
        this.last_stage = (this.last_stage <= 0) ? 32 : this.last_stage;
        this.stage = this.last_stage;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey( true );

        this.loadMusic();

        create();

        touchPoint = new Vector3();
    }

    private void loadMusic() {
        manager = new AssetManager();
        // TODO edit sound for louder; investigate sound vs music
        // Some player like notation player3, audacity... that contribute to make these mmf back to live.
        manager.load("data/audio/night_opening.mp3", Sound.class); // Music.class
        manager.load("data/audio/1_use_item.wav", Sound.class);
        manager.load("data/audio/6_snow_fly.wav", Sound.class);
        manager.load("data/audio/5_hit.wav", Sound.class);
        manager.load("data/audio/4_oh_oh.wav", Sound.class);
        manager.load("data/audio/special.mp3", Sound.class);
        manager.load("data/audio/lose.mp3", Sound.class);
        manager.load("data/audio/victory.mp3", Sound.class);
        manager.load("data/audio/Donald_Christmas.mp3", Sound.class);

        manager.finishLoading();
    }

    // Android GDX

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.key_code = DUMP_ACTION_STATE; // 0
        batch.begin();

        touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);

        // Gdx.app.log("DEBUG", "touch " + touchPoint.x + " y "+ (GDX_HEIGHT-touchPoint.y) + " key_code "+ this.key_code + " scrn "+ this.screen);
        game_action = getGameAction(pointer); // pointer

        if(isTouchedSpeedUp()) { // smaller value, shorter sleep
            this.dbg(" touch up ↓↑ ↓ " + this.game_speed);
            if(game_speed >= 12) {
                Gdx.input.vibrate(5);
                game_speed -= 3; // Base on shitty redmi note 7
            }
            setGameSpeed(game_speed);
        }
        if(isTouchedSpeedDown()) {
            this.dbg(" touch up down ↓↑ ↓ " + this.game_speed);
            if(game_speed <= 128) {
                Gdx.input.vibrate(5);
                game_speed += 3;
            }
            setGameSpeed(game_speed);
        }

        Gdx.input.vibrate(5);

        keyPressed();
        batch.end();

        showDeviceInfos();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        // Gdx.gl20.glClearColor(0, 0, 0, 1);
        // Gdx.gl20.glClearColor(255f/255f, 255f/255f, 255f/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = GDX_WIDTH /2;
        camera.position.y = GDX_HEIGHT /2;
        camera.update();

        batch.enableBlending();
        batch.begin();

        run();

        this.playOpeningMusic();

        // drawTouchPad();
        drawUI();
        batch.end();

        int lineWidth = 24;
        Color debug = new Color();
        shapeRenderer.begin(ShapeType.Line); // Filled
        shapeRenderer.setColor(Color.BLUE);

        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(upBtnRect.getX(), upBtnRect.getY(), upBtnRect.getWidth(), upBtnRect.getHeight());
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(downBtnRect.getX(), downBtnRect.getY(), downBtnRect.getWidth(), downBtnRect.getHeight());

//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(leftBtnRect.getX(), leftBtnRect.getY(), leftBtnRect.getWidth(), leftBtnRect.getHeight());
//        shapeRenderer.setColor(Color.ORANGE);
//        shapeRenderer.rect(rightBtnRect.getX(), rightBtnRect.getY(), rightBtnRect.getWidth(), rightBtnRect.getHeight());


//        Rectangle okBound2    = new Rectangle(GDX_WIDTH-(50+fireBtnTexture.getWidth())*SCALE_1080,                                     (int)(50*SCALE_1080), fireBtnTexture.getWidth()*SCALE_1080, fireBtnTexture.getHeight()*SCALE_1080);
//        shapeRenderer.setColor(Color.VIOLET);
//        shapeRenderer.rect(okBound2.getX(), okBound2.getY(), okBound2.getWidth(), okBound2.getHeight());

        // DKGRAY, LTGRAY, MAGENTA, GREEN, YELLOW
        int fire_w = fireBtnTexture.getWidth();
        int speed_up_h = imgSpeedUp.getHeight();

//        Rectangle testNum32 = new Rectangle(GDX_WIDTH-(50+ fire_w + fire_w/2 + imgKeyNum3.getWidth())*SCALE_1080, (40 + speed_up_h)*SCALE_1080, imgKeyNum3.getWidth()*SCALE_1080, imgKeyNum3.getHeight()*SCALE_1080);
//        shapeRenderer.setColor(Color.YELLOW);
//        shapeRenderer.rect(testNum32.getX(), testNum32.getY(), testNum32.getWidth(), testNum32.getHeight());
//        shapeRenderer.rect(testNum32.getX()-1, testNum32.getY()-1, testNum32.getWidth()+2, testNum32.getHeight()+2);

        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.rect(speedUpBtnRect.getX(), speedUpBtnRect.getY(), speedUpBtnRect.getWidth(), speedUpBtnRect.getHeight());
        shapeRenderer.rect(speedDownBtnRect.getX(), speedDownBtnRect.getY(), speedDownBtnRect.getWidth(), speedDownBtnRect.getHeight());

//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.rect(leftMenuBtn.getX(), leftMenuBtn.getY(), leftMenuBtn.getWidth(), leftMenuBtn.getHeight());
//        shapeRenderer.rect(rightMenuBtn.getX(), rightMenuBtn.getY(), rightMenuBtn.getWidth(), rightMenuBtn.getHeight());

        // TODO draw some debug bound rect: 128x160 scale rect, center point... for debug
        // some cordination of ie. select hight light Rect vs shop position which is right.

        Rectangle bound = new Rectangle(0, BOTTOM_SPACE, GDX_WIDTH, GDX_HEIGHT);
        Rectangle bound2 = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        Rectangle center = new Rectangle(GDX_WIDTH/2*SCALE_1080-5, (GDX_HEIGHT-BOTTOM_SPACE)/2*SCALE_1080-5, 10, 10);
        Rectangle vcenter_line = new Rectangle(GDX_WIDTH /2* SCALE_1080 -5, BOTTOM_SPACE, 10, GDX_HEIGHT-BOTTOM_SPACE - 15);
        Rectangle hcenter_line = new Rectangle(0, (GDX_HEIGHT +BOTTOM_SPACE)/2 -5, GDX_WIDTH, 10);

        shapeRenderer.setColor(Color.CORAL);
//        shapeRenderer.rect(bound.getX(), bound.getY(), bound.getWidth(), bound.getHeight());
//        shapeRenderer.rect(bound.getX()+1, bound.getY()+1, bound.getWidth()-2, bound.getHeight()-2);

        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(bound2.getX(), bound2.getY(), bound2.getWidth(), bound2.getHeight());
        shapeRenderer.rect(bound2.getX()+1, bound2.getY()+1, bound2.getWidth()-2, bound2.getHeight()-2);

        shapeRenderer.setColor(Color.FOREST);
        shapeRenderer.rect(vcenter_line.getX(), vcenter_line.getY(), vcenter_line.getWidth(), vcenter_line.getHeight());
        shapeRenderer.rect(vcenter_line.getX()+1, vcenter_line.getY()+1, vcenter_line.getWidth()-2, vcenter_line.getHeight()-2);

        shapeRenderer.setColor(Color.SALMON);
        shapeRenderer.rect(hcenter_line.getX(), hcenter_line.getY(), hcenter_line.getWidth(), hcenter_line.getHeight());
        shapeRenderer.rect(hcenter_line.getX()+1, hcenter_line.getY()+1, hcenter_line.getWidth()-2, hcenter_line.getHeight()-2);

        Rectangle top = new Rectangle(50, 0, GDX_WIDTH -100, 10);
        shapeRenderer.setColor(Color.LIME);
        shapeRenderer.rect(top.getX(), top.getY(), top.getWidth(), top.getHeight());

        Rectangle bot = new Rectangle(50, (GDX_HEIGHT) - 10, GDX_WIDTH -100, 10); // SCALE_1080
        shapeRenderer.setColor(Color.CORAL);
        shapeRenderer.rect(bot.getX(), bot.getY(), bot.getWidth(), bot.getHeight());

        shapeRenderer.end();
    }

    private void playOpeningMusic() {
        if(music_opening != null) {
            if(!music_opening.isPlaying()) {
                music_opening.play();
                music_opening.setLooping(false);
            }
        }
    }

    @Override
    public void hide() {

    }

    public void create () {
        batch = new SpriteBatch();

        //Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GDX_WIDTH, GDX_HEIGHT);
//        int VP_WIDTH = 1080;
//        int VP_HEIGHT = 1920;
        // This seem take no effect on 16:9 (
        // ViewPort make effect; (show custom bound) on ie. 1080 x 2160 screen.
//        viewport = new FitViewport(GDX_WIDTH, GDX_WIDTH, camera); // TODO investigate is this shit make effect ?
//        viewport.apply();

//        camera.position.x = GDX_WIDTH /2;
//        camera.position.y = GDX_WIDTH /2;
//        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        camera.update();

        Gdx.input.setInputProcessor(this); // TODO use an InputProcessor object

        this.loadTextures();
        this.initGamePad();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(5);

        this.layout = new GlyphLayout(); //dont do this every frame! Store it as member
    }

    public void resize(int width, int height) {
//        viewport.update(width,height);
//        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        // Gdx.gl20.glClearColor(0, 180f/255f, 221f/255f, 1); // Samsung blue orig.
//        Gdx.gl20.glClearColor(109f/255f, 207f/255f, 246f/255f, 1); // SKY
        // Gdx.gl20.glClearColor(255f/255f, 255f/255f, 255f/255f, 1); // WHITE
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Oh damn notch, bottom space
    protected boolean isTouchedUp() {
        this.key_code = -1;
        return OverlapTester.pointInRectangle(upBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedDown() {
        this.key_code = -2;
        return OverlapTester.pointInRectangle(downBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedLeft() {
        this.key_code = -3;
        return OverlapTester.pointInRectangle(leftBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedRight() {
        this.key_code = -4;
        return OverlapTester.pointInRectangle(rightBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedOption() {
        return OverlapTester.pointInRectangle(optionBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedOK() {
        this.key_code = KEY_OK;
        Rectangle textureBounds = new Rectangle(GDX_WIDTH -(fireBtnTexture.getWidth()+50)* SCALE_1080, GDX_HEIGHT -(50+fireBtnTexture.getHeight())* SCALE_1080, fireBtnTexture.getWidth()* SCALE_1080,fireBtnTexture.getHeight()* SCALE_1080);
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }

    protected boolean isTouchedNum3() {
        Rectangle textureBounds=new Rectangle(GDX_WIDTH -(fireBtnTexture.getWidth()+50+imgKeyNum3.getWidth()+(int)fireBtnTexture.getWidth()/2)* SCALE_1080, GDX_HEIGHT -(40+imgSpeedUp.getHeight()+imgKeyNum3.getHeight())* SCALE_1080, imgKeyNum3.getWidth()* SCALE_1080,imgKeyNum3.getHeight()* SCALE_1080);
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }
    protected boolean isTouchedMenuLeft() {
        // Gdx.app.log("DEBUG Clip", "x: " + leftMenuBtn.x + " y " + leftMenuBtn.y + " w " + leftMenuBtn.getWidth() + " h " + leftMenuBtn.getHeight());
        this.key_code = KEY_LEFT_MENU;
        return OverlapTester.pointInRectangle(leftMenuBtn, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedMenuRight() {
        // Gdx.app.log("DEBUG Clip", "x: " + rightMenuBtn.x + " y " + rightMenuBtn.y + " w " + rightMenuBtn.getWidth() + " h " + rightMenuBtn.getHeight());
        this.key_code = KEY_RIGHT_MENU;
        return OverlapTester.pointInRectangle(rightMenuBtn, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }

    protected boolean isTouchedSpeedUp() {
        return OverlapTester.pointInRectangle(speedUpBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }
    protected boolean isTouchedSpeedDown() {
        return OverlapTester.pointInRectangle(speedDownBtnRect, touchPoint.x, (GDX_HEIGHT -touchPoint.y) );
    }

    protected Preferences getPrefs() {
        if(prefs==null){
            prefs = Gdx.app.getPreferences("gamestate");
        }
        return prefs;
    }

    public void empty_func() {}

    // TODO reorder, convert (IMagick) by RGB order ie. 0 1 2 prefix respectively
    public int getEnumColor(int color) {
        switch(color) {
            case 0:
                return 4; // gray (should be black)
            case 10173:
                return 1; // med blue ~ blue
            case 20361: // 004F89 dark blue
                return 1; // It look very similar with some Gdx.Color.* ie. TEAL
            case 25054:
                return 1; // med blue
            case 44783:
                return 3; // light blue
            case 4960985:
                return 3; // light blue
            case 7196662:
                return 3; // light blue
            case 6974058:
                return 4; // gray ~ light gray
            case 9342606:
                return 4; // gray
            case 9672090:
                return 4; // light gray ~ gray
            case 13434777: // light green (grass)
                return 15; // light green grass
            case 14994350:
                return 2; // light yellow ~ light orange
            case 15132390:
                return 4;
            case 12698049: // C1C1C1 light gray
                return 4; // light gray ~
            case 16711680:
                return 0; // red
            case 16775065:
                return 2; // light yellow
            case 16777062: // FFFF66
                return 2;
            case 16777164:
                return 2; // light yellow
            case 16777215:
                return 5; // white
            default:
                return 0;
        }
    }

    // Fixme due to GDX bug (?), color set not take effect, so try approximate by Color.RED/MAGENTA ...
    // https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/Color.html
    // public float[] getFloatColor(int color) {
    public Color getGDXtColor(int color) {
        // float colorFloat[] = {1.0f, 1.0f, 1.0f};
        // CYAN #00ffff; Maroon  #800000; Coral #FF7F50; Chartreuse #7FFF00; BROWN #964B00;
        // FIREBRICK #B22222; FOREST #228B22; Gold FFD700; 	GOLDENROD DAA520; LT Gray d3d3d3; Lime 32cd32, it should be Bfff00;
        // OLIVE 808000; navy 000080; orange ff7f00; pink ffc0cb; Purple 800080; ROYAL 4169E1; SCARLET ff2400; Salmon FA8072;
        // SLATE 708090; sky 80daeb; TAN D2B48C; TEAL 008080; VIOLET  7f00ff;
        // These color above based on GG search, not official GDX; And Droid color is much more limitted.

        switch(color) {
            case 16777215:
                return Color.WHITE; // white
            case 7196662: // 6DCFF6
                // colorFloat = new float[]{0.427f, 0.812f, 0.965f}; // 109f/255f, 207f/255f, 246f/255f
                return Color.SKY;  // light blue
            case 9342606: // 8E8E8E
                // colorFloat = new float[]{0.557f, 0.557f, 0.557f}; // gray // 142f/255f, 142f/255f, 142f/255f
                return Color.GRAY;
            case 15132390: // E6E6E6
                // colorFloat = new float[]{0.902f, 0.902f, 0.902f}; // light gray ~ // 230f/255f, 230f/255f, 230f/255f
                return Color.LIGHT_GRAY;
            case 14994350: // E4CBAE
                // colorFloat = new float[]{0.894f, 0.796f, 0.682f}; // light yellow ~ light orange
                return Color.ORANGE; // ? it's wheat color;
            case 10173: // 0027BD
                // colorFloat = new float[]{0.0f, 0.153f, 0.741f}; // med blue ~ blue
                return Color.BLUE;
            case 16777164: // FFFFCC
                // colorFloat = new float[]{1.0f, 1.0f, 0.8f}; // light yellow
                return Color.WHITE; // YELLOW; // LIME
            case 0:
                // colorFloat = new float[]{0.0f, 0.0f, 0.0f}; // gray (should be black)
                return Color.DARK_GRAY; // fixme due to white snow bg bug
            case 25054: // 0061DE
                // colorFloat = new float[]{0.0f, 0.38f, 0.871f}; // med blue
                return Color.NAVY;
            case 44783: // 00AEEF
                // colorFloat = new float[]{0.0f, 0.682f, 0.937f}; // light blue
                return Color.SKY;
            case 4960985: // 4BB2D9
                // colorFloat = new float[]{0.294f, 0.698f, 0.851f}; // light blue
                return Color.SKY;
            case 6974058: // 6A6A6A
                // colorFloat = new float[]{0.416f, 0.416f, 0.416f}; // gray ~ light gray
                return Color.LIGHT_GRAY;
            case 8421504: // 808080
                return Color.GRAY;
            case 9672090: // 93959A
                // colorFloat = new float[]{0.576f, 0.584f, 0.604f}; // light gray ~ gray
                return Color.LIGHT_GRAY;
            case 10790052: // A4A4A4
                return Color.LIGHT_GRAY;
            case 13434726: // CCFF66
                return Color.GREEN; // LIME is most closely match but GDX.LIME too dark.
            case 16775065: // FFF799
                // colorFloat = new float[]{1.0f, 0.969f, 0.6f}; // light yellow
                return Color.YELLOW;
            case 16711680: // FF0000
                // colorFloat = new float[]{1.0f, 0.0f, 0.0f}; // red
                return Color.RED;
            default:
                return Color.WHITE;
        }
    }

    /**
     * @param x, y
     * @param width, height
     * @param color interger (color in int ie. 16777215)
     */
    public void fillRect(SpriteBatch paramGraphics, int x, int y, int width, int height, int color) {
        color = this.getEnumColor(color); // Simpled implement version of color

        // Hard code default width x height of color img: 12x12 px
        float scaleY = (float) (height* MIDP_SCL / 12);
        float scaleX = (float) (width* MIDP_SCL / 12);
        // (Texture, float x, float destroy_n_e, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
        int pos_x = (int) (MIDP_SCL *x);
        int pos_y = (int) ((OLD_MOBI_H - y)* MIDP_SCL - imgColor[color].getHeight()*scaleY + BOTTOM_SPACE);

        paramGraphics.draw(imgColor[color], pos_x, pos_y, 0, 0, imgColor[5].getWidth(), imgColor[5].getHeight(), scaleX, scaleY, 0, 0, 0, (int)(imgColor[5].getWidth()*scaleX), (int)(imgColor[5].getHeight()*scaleY), false, false);
    }

    // anchor 20 may be TOP|LEFT = 16+4 ? = 0x11 | 0x4
    // Graphics.HCENTER: 1 Graphics.VCENTER: 2; LEFT: 4; RIGHT: 8; TOP: 16; BOTTOM: 32; BASELINE: 64; SOLID: 0; DOTTED: 1 dupli ?
    // http://www.it.uc3m.es/florina/docencia/j2me/midp/docs/api/javax/microedition/lcdui/Graphics.html#VCENTER
    // https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/javax/microedition/lcdui/Graphics.html#BASELINE
    public void drawImage2(SpriteBatch paramGraphics, Texture image, int pos_x, int pos_y, int anchor)
    {
        if (image == null) {
            this.dbg("Null image drawImage2");
            return;
        }

        int img_height = (int)(image.getHeight() / 9 * MIDP_SCL); // img_128 scaled to x9; so it map to 1080 scale should be: img_height / 9 * 1080 / 128 = img_height / 9 * MIDP_SCL (~8.43)
        int img_width = (int)(image.getWidth() / 9 * MIDP_SCL);

        int position_x = (int) (pos_x* MIDP_SCL);
        int position_y = (int) ((OLD_MOBI_H - pos_y)* MIDP_SCL - img_height + BOTTOM_SPACE); // Default TOP | LEFT

        switch(anchor) {
            case 20: // TOP | LEFT ; 0x10 | 0x4
                break;
            case 17:  // TOP | HCENTER ; 0x10 | 0x1; Remember horizontal center mean Y/2 anchor;
                // not use OLD_MOBI_W /2; (MIDP pos_x already calculated); // img_width already scaled to Droid;
                position_x = (int) ( position_x - img_width/2 ); // HCENTER mean x/2 (middle of horizontal line)
                break;
            case 3:  // VCENTER | HCENTER
                // It's complex if centered by image (0,0) by image center not screen Geometry;
                // MIDP: h/2 + height/2 => Droid: h -(h/2 +height/2) = h/2 - height/2;
                position_x = (int) (position_x - img_width/2); // MIDP: w/2 - x/2 (width/2), h/2 + y/2 (height/2);
                position_y = (int) ( (OLD_MOBI_H - pos_y)* MIDP_SCL - img_height/2 + BOTTOM_SPACE);
                break;
            default:
                break;
        }

        // batch.draw(image, (int)(pos_x*MIDP_SCL), position_y, image.getWidth()*SCALE_1080, image.getHeight()*SCALE_1080); // draw no scale

        // Draw Image with scale param, can anchor to 1080x1920 work without / with minimal change in geometry ?
        float scaleX = (float) (MIDP_SCL / SCALED_IMG_RATIO); // 1080 / 128 / 9
        float scaleY = (float) (MIDP_SCL / SCALED_IMG_RATIO);

        if (pos_x == this.h_x) {
            //this.dbg("aaa pos x y "+ position_x + " " + position_y);
        }
        paramGraphics.draw(image, (int)(position_x), position_y, 0, 0, image.getWidth(), image.getHeight(), scaleX, scaleY, 0, 0, 0, (int)(image.getWidth()), (int)(image.getHeight()), false, false);
    }

    public void drawImage(SpriteBatch paramGraphics)
    {
        // paramGraphics.setClip(0, 0, 240, 320);
        // this.drawImage2(paramGraphics,  3, 0, 300); // double check 3 is index
        // Can not use for now since now Array Of Images has been build.
    }

    // Not used
    public void drawRect(int x, int y, int width, int height, int color) {
        this.fillRect(this.batch, x, y, width, height, color);
    }

    // Temporary draw single line
    public void drawRect(SpriteBatch paramGraphics, int x, int y, int width, int height, int color)
    {
        this.fillRect(paramGraphics, x, y, width, 1, color);
        this.fillRect(paramGraphics, x+width-1, y, 1, height, color);
        this.fillRect(paramGraphics, x, y+height-1, width, 1, color);
        this.fillRect(paramGraphics, x, y, 1, height, color);
    }

    public void drawString(SpriteBatch paramGraphics, String paramString, int x, int y, int anchor, int color)
    {
        if(paramString == null) {
            return;
        }

        // int stringWidth = font.getBounds(paramString).width; // new API getBounds are gone
        layout.setText(font, paramString);
        int stringWidth = (int) layout.width; // contains the width of the current set text
        int stringHeight = (int) layout.height;

        int position_x = (int) (x * MIDP_SCL); // It seem x have already calculated.
        int position_y = (int) ((OLD_MOBI_H -y)* MIDP_SCL + BOTTOM_SPACE) - stringHeight/2;

        switch(anchor) {
            case 20: // TOP | LEFT ; 0x10 | 0x4
                break;
            case 17:  // TOP | HCENTER ; 0x10 | 0x1; Remember horizontal center mean X/2 anchor;
                position_x = (int) ( (x * MIDP_SCL) - stringWidth/2); // HCENTER mean in the middle of horizontal line;
                // position_y = (int) ( (OLD_MOBI_H - y)*MIDP_SCL - stringHeight/2 + BOTTOM_SPACE); // 8 as 1/2 line height
                break;
            case 3:  // VCENTER | HCENTER
                // stringWidth() alternative; oh may be this is why AA use image stack for custom text;
                position_x = (int) (position_x - stringWidth/2); // MIDP: w/2 - x/2 (width/2), h/2 + y/2 (height/2);
                position_y = (int) ( (OLD_MOBI_H - y)* MIDP_SCL - stringHeight/2 + BOTTOM_SPACE); // not use -stringHeight/2 since it too hight above;
                break;
            default:
                break;
        }

        Color colorGDX = this.getGDXtColor(color);
        font.setColor(colorGDX);

        font.draw(paramGraphics, paramString, position_x * SCALE_1080, position_y * SCALE_1080);
    }

    /*
     * Simulate J2ME keyCode
     * https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/constant-values.html#javax.microedition.lcdui.Canvas.UP
     * */
    public int getGameAction2(int keyCode) {
        return game_action;
    }

    // TODO messed with key press stack ? or cache/release mechanism.
    public int getGameAction(int pointer) { // int pointer
        if(isTouchedUp()) {
            Gdx.input.vibrate(15);
            this.key_code = -1;
            this.dbg("††† up ↑-↑-↑-" + upBtnRect.getX() + "," + upBtnRect.getWidth() + " " + upBtnRect.getY() + ","+(GDX_HEIGHT -upBtnRect.getHeight()) + " +" + upBtnRect.getHeight()  +  " key= " + this.key_code + " action= " + game_action + " after " + GAME_ACTION_UP + " " + touchPoint.x + " y " + touchPoint.y);
            this.game_action = GAME_ACTION_UP;
            return GAME_ACTION_UP;
        }
        if(isTouchedDown()) { // Careful with game state, ie. item_mode = 0
            Gdx.input.vibrate(15);
            this.key_code = -2;
            this.dbg("††† down ↓-↓-↓-↓" + touchPoint.x + " y " + touchPoint.y);
            this.game_action = GAME_ACTION_DOWN;
            return GAME_ACTION_DOWN;
        }
        if(isTouchedLeft() && Gdx.input.isTouched()) {
            Gdx.input.vibrate(15);
            this.key_code = -3;
            this.dbg("††† << ---"  + touchPoint.x + " y " + touchPoint.y);
            this.game_action = GAME_ACTION_LEFT;
            return GAME_ACTION_LEFT;
        }
        if(isTouchedRight()) {
            this.key_code = -4;
            Gdx.input.vibrate(15);
            this.dbg("††† >> ---" + touchPoint.x + " y " + touchPoint.y);
            this.game_action = GAME_ACTION_RIGHT;
            return GAME_ACTION_RIGHT;
        }
        if (isTouchedMenuLeft()) {
            this.key_code = KEY_LEFT_MENU;
            Gdx.input.vibrate(15);
            this.dbg("††† Menu L ---" + touchPoint.x + " y " + touchPoint.y);
            this.game_action = 0;
            return 0;
        }
        // TODO show or represent SELECT #; OPTIONS # for user
        if (isTouchedMenuRight()) {
            this.key_code = KEY_RIGHT_MENU;
            Gdx.input.vibrate(15);
            this.dbg("††† Menu R ---" + touchPoint.x + " y " + touchPoint.y);
            this.game_action = 0;
            return 0;
        }

        if(isTouchedOK()) {
            this.key_code = KEY_OK; // -5
            Gdx.input.vibrate(15);
            // this.dbg("††† OK ---" + touchPoint.x + " y " + touchPoint.y);
            this.game_action = GAME_ACTION_OK;
            return GAME_ACTION_OK;
        }

        this.game_action = DUMP_ACTION_STATE;
        return DUMP_ACTION_STATE; // 0
    }

    // FIXME anchor with RECTANGLE
    protected void drawUI() {
        // It seem single image can have it's own event Listener such as: touchDown/Up; See bellow
        // https://github.com/BrentAureli/ControllerDemo/blob/master/core/src/com/brentaureli/overlaydemo/Controller.java
        // TODO use custom IMAGE addEventListener for more UI refine. Can image used as Texture ?
        int fire_w = fireBtnTexture.getWidth();
        int fire_h = fireBtnTexture.getHeight();
        int menu_left_w = imgMenuLeft.getWidth();
        int menu_left_h = imgMenuLeft.getHeight();
        int menu_right_w = imgMenuRight.getWidth();
        int menu_right_h = imgMenuRight.getHeight();

        int speed_up_w = imgSpeedUp.getWidth();
        int speed_up_h = imgSpeedUp.getHeight();
        int speed_down_w = imgSpeedDown.getWidth();

        batch.draw(fireBtnTexture, GDX_WIDTH -(50+fireBtnTexture.getWidth())* SCALE_1080, (int)(50* SCALE_1080), fireBtnTexture.getWidth()* SCALE_1080, fireBtnTexture.getHeight()* SCALE_1080);
        batch.draw(imgKeyNum3, GDX_WIDTH -(50+ fire_w + fire_w/2 + imgKeyNum3.getWidth())* SCALE_1080, (40 + speed_up_h)* SCALE_1080, imgKeyNum3.getWidth()* SCALE_1080, imgKeyNum3.getHeight()* SCALE_1080);

        batch.draw(touch_pad, 20* SCALE_1080, 20* SCALE_1080, touch_pad.getWidth()* SCALE_1080, touch_pad.getHeight()* SCALE_1080);
        batch.draw(touch_pad_knob, (20+touch_pad.getWidth()/2-touch_pad_knob.getWidth()/2)* SCALE_1080, (20+touch_pad.getHeight()/2-touch_pad_knob.getHeight()/2)* SCALE_1080, touch_pad_knob.getWidth()* SCALE_1080, touch_pad_knob.getHeight()* SCALE_1080);

        batch.draw(imgSpeedUp, GDX_WIDTH -(50+fire_w  + fire_w/2 + speed_up_w)* SCALE_1080, 20* SCALE_1080, speed_up_w* SCALE_1080, imgSpeedUp.getHeight()* SCALE_1080);
        batch.draw(imgSpeedDown, GDX_WIDTH -(50+fire_w + fire_w/2 + 2*speed_down_w)* SCALE_1080, 20* SCALE_1080, speed_down_w* SCALE_1080, imgSpeedDown.getHeight()* SCALE_1080);

//        batch.draw(imgMenuLeft, GDX_WIDTH-(50+fire_w + fire_w/2)*SCALE_1080, 20*SCALE_1080 + 100*SCALE_1080 +20, menu_left_w*SCALE_1080, menu_left_h*SCALE_1080);
        batch.draw(imgMenuLeft, 20, (140 + menu_right_h)* SCALE_1080 +20, menu_left_w* SCALE_1080, menu_left_h* SCALE_1080);
//        batch.draw(imgMenuRight, GDX_WIDTH-(50+fire_w + fire_w/2 + menu_left_w)*SCALE_1080, 20*SCALE_1080 + 100*SCALE_1080 +20, menu_right_w*SCALE_1080, menu_right_h*SCALE_1080);
        batch.draw(imgMenuRight, GDX_WIDTH -(50+fire_w + fire_w/2 + menu_left_w)* SCALE_1080, (140 +menu_right_h)* SCALE_1080 +20, menu_right_w* SCALE_1080, menu_right_h* SCALE_1080);

    }

    // TODO use texture region; Optimize reuse stuff, object;
    private void loadTextures() {
        fireBtnTexture = new Texture("data/gui/fire.png");
        // Yeah convert Imagick suck, 1kb 1080x1920 may be alpha shit make it black.
        snowWhiteBg = new Texture("data/sprites/white_background2.png");

        this.imgBacks = new Texture[4];
        for (int i = 0; i < 4; i++) {
            this.imgBacks[i] = new Texture("data/sprites/back" + (i+1) + ".png");
        }

        // This one fix school = 0 -> back0.png not exist
        school = (school <= 0) ? 1 : school;
        imgBack = imgBacks[school-1];

        imgSpecial = new Texture[3];
        for (int i = 0; i < 3; i++) {
            imgSpecial[i] = new Texture("data/sprites/special" + i + ".png");
        }

        tmp_stage =  (tmp_stage <= 0) ? 1 : tmp_stage;
        tmp_stage = (tmp_stage > 4) ? 4 : tmp_stage;
        imgStage_num = new Texture("data/sprites/stage" + tmp_stage + ".png");
        imgStage_nums = new Texture[4]; // nickfarrow
        for (int i=0; i < 4; i++) {
            imgStage_nums[i] = new Texture("data/sprites/stage" + (i+1) + ".png");
        }

        ui = new Texture("data/sprites/ui.png");  // h:160p (1080p)
        imgStage = new Texture[5];
        for (int i = 0; i < 5; i++) {
            imgStage[i] = new Texture("data/sprites/word-" + i + ".png");
        }

        /**
         * #0 for red
         * #1 for light blue, #3 light blue 2 6DCFF6
         * #2 for light yellow, #4 gray 93959A #5 for white
         */
        imgColor = new Texture[16];
        for (int i = 0; i < 16; i++) {
            imgColor[i] = new Texture("data/gui/color-" + i + ".png");
        }

        imgKeyNum3 = new Texture("data/gui/use_item_btn.png");
        imgSpeedUp = new Texture("data/gui/speed_up.png");
        imgSpeedDown = new Texture("data/gui/speed_down.png");
        touch_pad = new Texture("data/gui/touchBackground.png");
        touch_pad_knob = new Texture("data/gui/touchKnob.png");

        imgMenuLeft = new Texture("data/gui/left_btn.png");
        imgMenuRight = new Texture("data/gui/right_btn.png");

        // Old load textures
        int i;

        this.imgMM = new Texture("data/sprites/mm.png");
        this.imgBk = new Texture("data/sprites/bk.png");
        this.imgSl = new Texture("data/sprites/sl.png");
        this.imgPl = new Texture("data/sprites/play.png");
        this.imgCh = new Texture("data/sprites/check.png");
        this.imgChSwap = new Texture("data/sprites/check.png");
        this.imgHeroIcon = new Texture("data/sprites/hero_icon.png"); // nickfarrow

        // 6
        this.imgHero = new Texture[5];
        this.imgEnemy = new Texture[4];
        this.imgItem = new Texture[9];
        this.imgItem_hyo = new Texture[2];
        this.imgItem_hyo[0] = new Texture("data/sprites/hyo0.png");
        this.imgItem_hyo[1] = new Texture("data/sprites/hyo1.png");
        for (i = 0; i < 5; i++) {
            this.imgHero[i] = new Texture("data/sprites/hero" + i + ".png");
        }
        if (get_random(2) == 0) {
            for (int j = 0; j < 4; j++) {
                this.imgEnemy[j] = new Texture("data/sprites/enemy0" + j + ".png");
            }
        } else {
            for (int k = 0; k < 4; k++) {
                this.imgEnemy[k] = new Texture("data/sprites/enemy1" + k + ".png");
            }
        }
        for (int m = 0; m < 9; m++) {
            this.imgItem[m] = new Texture("data/sprites/item" + m + ".png");
        }
        //System.gc();
        this.imgSnow_g = new Texture("data/sprites/snow_gauge.png");
        this.imgPwd = new Texture("data/sprites/power.png");
        this.imgShadow = new Texture("data/sprites/shadow0.png");
        this.imgPok = new Texture("data/sprites/pok.png");
        this.imgPPang = new Texture("data/sprites/bbang0.png");
        this.imgPPang1 = new Texture("data/sprites/bbang1.png");
        this.imgH_ppang = new Texture("data/sprites/h_bbang.png");
        // this.imgCh = new Texture("data/sprites/check.png");
        this.imgAl = new Texture("data/sprites/al.png");
        this.imgEffect = new Texture[2];
        this.imgEffect[0] = new Texture("data/sprites/effect0.png");
        this.imgEffect[1] = new Texture("data/sprites/effect1.png");

        // 100
        this.imgBack = new Texture("data/sprites/back" + this.school + ".png");

        // 7
        this.imgBoss1 = new Texture[4]; // nickfarrow
        this.imgBoss2 = new Texture[4];
        this.imgBoss3 = new Texture[4];
        this.imgBoss4 = new Texture[4];
        for (i = 0; i < 4; i++) {
            this.imgBoss1[i] = new Texture("data/sprites/boss1" + i + ".png");
        }
        for (i = 0; i < 4; i++) {
            this.imgBoss2[i] = new Texture("data/sprites/boss2" + i + ".png");
        }
        for (i = 0; i < 4; i++) {
            this.imgBoss3[i] = new Texture("data/sprites/boss3" + i + ".png");
        }
        for (i = 0; i < 4; i++) {
            this.imgBoss4[i] = new Texture("data/sprites/boss4" + i + ".png");
        }

        this.imgBoss = this.imgBoss1;

        // -6
        this.imgStage = new Texture[5];
        for (i = 0; i < 5; i++) {
            this.imgStage[i] = new Texture("data/sprites/word-" + i + ".png");
        }
        this.imgStage_num = new Texture("data/sprites/stage" + this.tmp_stage + ".png");

        // 8
        this.imgSpecial = new Texture[3];
        for (i = 0; i < 3; i++) {
            this.imgSpecial[i] = new Texture("data/sprites/special" + i + ".png");
        }
        this.gameOn = true;

        // 9
        this.imgSp = new Texture("data/sprites/sp" + this.special + ".png");
        this.imgSps = new Texture[4]; // 4 instead of 3 for index match; so sp0 and sp1 are same.
        for (i=0; i < 4; i++) {
            this.imgSps[i] = new Texture("data/sprites/sp" + i + ".png");
        }

        // 3
        this.imgVill = new Texture("data/sprites/village.png");
        // this.imgCh = new Texture("data/sprites/check.png");
        this.imgSchool = new Texture("data/sprites/school.png");
        this.title = new Texture("data/sprites/title.png");
        this.txt1 = new Texture("data/sprites/txt1.png"); // TODO make original clean white text as orig. Scalled too blur.
        this.txt2 = new Texture("data/sprites/txt2.png");
        this.txt4 = new Texture("data/sprites/txt4.png");
        this.txt4b = new Texture("data/sprites/txt4b.png");

        // 31 fix me, this should be load all at start
        this.imgShops = new Texture[2];
        this.imgShops[0] = new Texture("data/sprites/shop0.png");
        this.imgShops[1] = new Texture("data/sprites/shop1.png");

        // 200 VICTORY ?
        this.imgVictory = new Texture("data/sprites/victory.png");
        this.imgV = new Texture("data/sprites/v.png");
        this.imgHero_v = new Texture("data/sprites/hero-vic.png");

        // 65336
        this.imgLose = new Texture("data/sprites/lose.png");
        this.imgHero_l = new Texture("data/sprites/hero-lose.png");

        // 1
        this.imgNum = new Texture[10];
        for (i = 0; i < 10; i++) {
            this.imgNum[i] = new Texture("data/sprites/" + i + ".png");
        }
        this.imgLogo = new Texture("data/sprites/logo.png");

        // Inline image /texture created on the fly
        this.present = new Texture("data/sprites/present.png");
        this.sam_logo = new Texture("data/sprites/sam_logo.png");
        this.http1 = new Texture("data/sprites/http1.png");
        this.http2 = new Texture("data/sprites/http2.png");
        this.allClear = new Texture("data/sprites/allClear.jpg");
    }

    // Careful with new Texture => only reassign to loaded texture
    private void adjustTextures(int paramInt) { // nickfarrow -> for update/reload textures based on game state
        if (paramInt == -6) {
        }
        else if (paramInt == -2)
        {
        }
        else if (paramInt == 1)
        {}
        else if (paramInt == 2)  // MENU_SCREEN / INSTRUCTION_SCREEN = 2
        {}
        else if (paramInt == 3)  // VILLAGE, imgCh -> hero_icon
        {
            this.imgCh = this.imgHeroIcon; // TODO double check restore chk icon on other screen.
        }
        else if (paramInt == 6)
        {
            this.tmp_stage = (this.tmp_stage > 4) ? 4 : this.tmp_stage; // NF fix, not sure it affect allClear screen;
            this.imgStage_num = imgStage_nums[this.tmp_stage-1]; // Fixme outofbound
        }
        else if (paramInt == 7)
        {
            if (this.e_boss == 1) {
                this.imgBoss = this.imgBoss1;
            } else if (this.e_boss == 2) {
                this.imgBoss = this.imgBoss2;
            } else if (this.e_boss == 3) {
                this.imgBoss = this.imgBoss3;
            } else if (this.e_boss == 4) {
                this.imgBoss = this.imgBoss4;
            }
        }
        else if (paramInt == 8)
        {
        }
        else if (paramInt == 9)
        {
            this.special = (this.special > 3) ? 3: this.special; // sp 1 2 3 but array index = 0 1 2
            this.imgSp = this.imgSps[this.special];
        }
        else if (paramInt == 31)  // SHOP_SCREEN = 31
        {
            if (this.m_mode == 1) {
                this.imgShop = imgShops[0];
            }
            if (this.m_mode == 0) {
                this.imgShop = imgShops[1];
            }
        }
        else if (paramInt == 100)
        {
            this.imgBack = imgBacks[school-1];
        }
        else if (paramInt == 200)
        {}
        else if (paramInt == 65336)
        {}
    }

    private void dbg(String log) {
        Gdx.app.log("DEBUG", "††† " + log);
    }

    private void showDeviceInfos() {
        if (shitty == 0) {
            shitty++;
            this.dbg("††† MIDP_SCL " + MIDP_SCL + " scr width " + GDX_WIDTH + " screen height " + GDX_HEIGHT);
        }
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

    public void setGameSpeed(int speed) {
        getPrefs().putInteger(PREF_SPEED, speed);
        getPrefs().flush();
    }
    public int getGameStage() {
        return getPrefs().getInteger(PREF_GAME_STAGE, 11);
    }

    public void setGameStage(int game_stage) {
        getPrefs().putInteger(PREF_GAME_STAGE, game_stage);
        getPrefs().flush();
    }
    public int getLastGameStage() {
        return getPrefs().getInteger(PREF_LAST_GAME_STAGE, 11);
    }

    public void setLastGameStage(int last_game_stage) {
        getPrefs().putInteger(PREF_LAST_GAME_STAGE, last_game_stage);
        getPrefs().flush();
    }

    private void sleepAbit(int time) {
        try
        {
            Thread.sleep(time);
        }
        catch (Exception localException1) {}
    }

    private void initGamePad() {
        // Use rectangle until figure out how to work with BoundingBox multi input.
        this.upBtnRect = new Rectangle((20+(200/3))* SCALE_1080, (20+(400/3))* SCALE_1080, 132* SCALE_1080, 70* SCALE_1080 + 50); // + 150 Jan-2
        this.downBtnRect = new Rectangle((20+(200/3))* SCALE_1080, 20* SCALE_1080, 72* SCALE_1080, 70* SCALE_1080);

        this.leftBtnRect = new Rectangle(20* SCALE_1080, (20+(200/6))* SCALE_1080, 70* SCALE_1080, 140* SCALE_1080);
        this.rightBtnRect = new Rectangle((20+(400/3))* SCALE_1080, (20+(200/6))* SCALE_1080, 2*70* SCALE_1080, 140* SCALE_1080);

        this.optionBtnRect = new Rectangle(GDX_WIDTH /2+150* SCALE_1080, GDX_HEIGHT /8, GDX_WIDTH /2-180* SCALE_1080, 70* SCALE_1080);

        this.speedUpBtnRect = new Rectangle(GDX_WIDTH -(275+200)* SCALE_1080, 20* SCALE_1080 + 0*100* SCALE_1080 + 20, 200* SCALE_1080, 100* SCALE_1080);
        this.speedDownBtnRect = new Rectangle(GDX_WIDTH -(275+400)* SCALE_1080, 20* SCALE_1080 + 100* SCALE_1080 + 20, 200* SCALE_1080, 100* SCALE_1080);

        this.leftMenuBtn = new Rectangle(10* SCALE_1080, 250* SCALE_1080, 200* SCALE_1080, 100* SCALE_1080);
        // GDX_HEIGHT - (140 +menu_right_h)
        this.rightMenuBtn = new Rectangle(GDX_WIDTH -(275+200)* SCALE_1080, 250* SCALE_1080, 200* SCALE_1080, 100* SCALE_1080);
    }

    // end Android GDX
    /*--------------------------------------------------------------------------------------------*/

    public void addScore(String paramString, int paramInt)
    {
    }

    public void printScore(String paramString, int paramInt) {}

    public void loadImage(int paramInt)
    {
        this.adjustTextures(paramInt);
    }

    public void destroyImage(int paramInt) {}

    public void init_game(int paramInt)
    {
        this.screen = 77;
        //repaint();
        //serviceRepaints();
        this.game_state = 0;
        this.p_mode = 1;
        this.h_x = 5;
        this.h_y = 8;
        this.h_idx = 0;
        this.max_hp = 106*5*2; // fixme
        this.hp = this.max_hp;
        this.wp = 0;
        this.pw_up = 0;
        this.snow_pw = 0;
        this.real_snow_pw = 0;
        this.dem = 12;
        this.ppang = 0;
        this.al = -1;
        this.ppang_time = 0;
        this.ppang_item = 0;
        make_enemy(paramInt);
        this.d_gauge = 2;
        this.screen = 6;
        this.item_mode = 0;
        loadImage(6);
        loadImage(100);
        if (this.e_boss > 0) {
            loadImage(7);
        }
        this.state = 2;
        this.ani_step = 0;
        // startThread();
        this.gameOn = true;
    }

    // last_stage or rand supposed to be not greater than 4; as logic only check stage (=last%10); else ie. last_stage = 46 % 10 = 6 out of condition check.
    // The orig code seem not a bug because of it have mechanism check it not = 45; (init_game call have checked it).
    // But some how (I have modified many) it become 46 as last_stage (allClear n after). Or may be this is a bug in the past.
    // I have experienced it on old phone too. => In programming aspect it should be checked. May be device limitted resource forced this ?
    public void make_e_num(int last_stage_or_rand, int paramSchool)
    {
        if (paramSchool == 1)
        {
            if (last_stage_or_rand == 1)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 2)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 3)
            {
                this.e_boss = 1;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 4)
            {
                this.e_boss = 3;
                this.e_num = 2;
            }
        }
        else if (paramSchool == 2)
        {
            if (last_stage_or_rand == 1)
            {
                this.e_boss = 0;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 2)
            {
                this.e_boss = 0;
                this.e_num = 3;
            }
            else if (last_stage_or_rand == 3)
            {
                this.e_boss = 2;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 4)
            {
                this.e_boss = 3;
                this.e_num = 3;
            }
        }
        else if (paramSchool == 3)
        {
            if (last_stage_or_rand == 1)
            {
                this.e_boss = 0;
                this.e_num = 3;
            }
            else if (last_stage_or_rand == 2)
            {
                this.e_boss = 2;
                this.e_num = 2;
            }
            else if (last_stage_or_rand == 3)
            {
                this.e_boss = 0;
                this.e_num = 4;
            }
            else if (last_stage_or_rand == 4)
            {
                this.e_boss = 3;
                this.e_num = 4;
            }
        }
        else if (paramSchool == 4) {
            if (last_stage_or_rand == 1)
            {
                this.e_boss = 1;
                this.e_num = 3;
            }
            else if (last_stage_or_rand == 2)
            {
                this.e_boss = 2;
                this.e_num = 3;
            }
            else if (last_stage_or_rand == 3)
            {
                this.e_boss = 3;
                this.e_num = 4;
            }
            else if (last_stage_or_rand == 4)
            {
                this.e_boss = 4;
                this.e_num = 4;
            } else {
                this.e_boss = 4;
                this.e_num = 4;
            }
        }
        this.e_t_num = this.e_num;
        this.tmp_stage = last_stage_or_rand;
    }

    public void make_enemy(int paramInt)
    {
        // this.dbg("aaa " + this.last_stage + "  school " + this.school + " state " + this.state + " ani_step " + this.ani_step);
        if (paramInt < 0) {
            make_e_num(get_random(2) + 2, this.school);
        } else {
            this.last_stage = (this.last_stage > 45) ? 45 : this.last_stage; // fixme magic number; investigate state = 10 as allClear
            make_e_num(this.last_stage % 10, this.school);
        }

        this.e_x = new int[this.e_num];
        this.e_y = new int[this.e_num];
        this.e_hp = new int[this.e_num];
        this.max_e_hp = new int[this.e_num];
        this.e_lv = new int[this.e_num];
        this.e_idx = new int[this.e_num];
        this.e_behv = new int[this.e_num];
        this.e_snow_y = new int[this.e_num];
        this.e_snow_x = new int[this.e_num];
        this.e_snow_gap = new int[this.e_num];
        this.e_snow_top = new int[this.e_num];
        this.e_snow_dx = new int[this.e_num];
        this.e_fire_time = new int[this.e_num];
        this.e_wp = new int[this.e_num];
        this.e_ppang_item = new int[this.e_num];
        this.e_ppang_time = new int[this.e_num];
        this.e_move_dir = new int[this.e_num];
        this.dis_count = new int[this.e_num];
        this.e_time = 0;
        for (int i = 0; i < this.e_num; i++)
        {
            if ((this.school == 1) || (this.school == 2)) {
                this.e_hp[i] = (20 + this.school * 10);
            } else if (this.school == 3) {
                this.e_hp[i] = 54;
            } else if (this.school == 4) {
                this.e_hp[i] = 66;
            }
            this.max_e_hp[i] = this.e_hp[i];
            this.e_snow_y[i] = -10;
            this.e_behv[i] = 100;
            this.e_wp[i] = 0;
        }
        if (this.school < 3) {
            this.e_dem = (this.school + 7);
        } else if (this.school == 3) {
            this.e_dem = (this.school + 9);
        } else {
            this.e_dem = 14;
        }
        this.e_x[0] = (3 + get_random(3));
        this.e_y[0] = (1 + get_random(3));
        this.e_lv[0] = 3;
        this.e_fire_time[0] = 8;
        this.e_x[1] = (18 + get_random(3));
        this.e_y[1] = (1 + get_random(3));
        this.e_lv[1] = 3;
        this.e_fire_time[1] = 17;
        if (this.e_t_num >= 3)
        {
            this.e_x[2] = (13 + get_random(3));
            this.e_y[2] = (3 + get_random(2));
            this.e_lv[2] = 3;
            this.e_fire_time[2] = 20;
        }
        if (this.e_t_num == 4)
        {
            this.e_x[3] = 8;
            this.e_y[3] = 5;
            this.e_lv[3] = 3;
            this.e_fire_time[3] = 4;
        }
        this.e_boss_behv = 100;
        this.e_boss_snow_y = -10;
        this.e_boss_x = 10;
        this.e_boss_y = 6;
        this.e_boss_idx = 0;
        this.e_boss_hp = (this.e_boss * 10 + 30 + (this.school - 1) * 10);
        this.max_e_boss_hp = this.e_boss_hp;
        this.e_boss_fire_time = 2;
    }

    // It seem paint have problem, AA moved to run(); may be control flow or blank batch issues.
    public void paint(SpriteBatch paramGraphics)
    {
        int j;
        if (this.screen == 6) // RUNNING
        {
            // drawImage2(paramGraphics, this.snowWhiteBg, 0, 0, 20); // Use this require re-scale 1080 to fixed scale 1080/128
            batch.draw(this.snowWhiteBg, 0, 0+BOTTOM_SPACE+400); // fixme scale
            drawImage2(paramGraphics, this.ui, 0, 109, 20);
            drawImage2(paramGraphics, this.imgBack, 0, 0, 20);
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 25, 128, 84, 16777215);
            drawImage2(paramGraphics, this.imgHero[this.h_idx], this.h_x * 5, 83, 0x10 | 0x1); // = 17 tho. Graphics.TOP | HCENTER
            if (this.ppang_time > 0)
            {
                if (this.ppang_item == 1) {
                    drawImage2(paramGraphics, this.imgItem_hyo[0], this.h_x * 5, 74, 0x10 | 0x1);
                } else {
                    drawImage2(paramGraphics, this.imgItem_hyo[1], this.h_x * 5, 83, 0x10 | 0x1);
                }
                this.ppang_time -= 1;
                if (this.ppang_time == 0) {
                    this.ppang_item = 0;
                }
            }
            draw_enemy(paramGraphics);
            draw_item(this.batch); // nf
            if (this.item_mode != 0)
            {
                // paramGraphics.setColor(12698049);
                if (this.message != "") {
                    draw_text(paramGraphics);
                }
                for (int i = 1; i <= 5; i++) {
                    drawRect(paramGraphics, i * 12 + 23, 110, 10, 9, 12698049); // paramGraphics
                }
                if (this.item_mode != 100)
                {
                    // paramGraphics.setColor(16711680);
                    drawRect(paramGraphics, this.item_mode * 12 + 23, 110, 10, 9, 16711680);
                }
                else if (this.item_mode == 100)
                {
                    this.item_mode = 0;
                }
            }
            if (this.pw_up == 2)
            {
                drawImage2(paramGraphics, this.imgShadow, this.snow_x * 5, this.snow_y * 7 + 4, 0x2 | 0x1);
                drawImage2(paramGraphics, this.imgItem[this.wp], this.snow_x * 5, this.snow_y * 7 - this.snow_gap + 4, 0x2 | 0x1);
            }
            else if (this.pw_up == 1)
            {
                if ((this.real_snow_pw > 0) && (this.ppang_item != 1))
                {
                    // paramGraphics.setColor(7196662);
                    if (this.h_x >= 13)
                    {
                        fillRect(paramGraphics, this.h_x * 5 - 16, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3, 7196662);
                        drawImage2(paramGraphics, this.imgPwd, this.h_x * 5 - 15, 83, 0x10 | 0x1);
                    }
                    else
                    {
                        fillRect(paramGraphics, this.h_x * 5 + 14, 106 - this.real_snow_pw * 3, 3, this.real_snow_pw * 3, 7196662);
                        drawImage2(paramGraphics, this.imgPwd, this.h_x * 5 + 15, 83, 0x10 | 0x1);
                    }
                }
            }
            else if (this.pw_up == 0)
            {
                if (this.ppang <= -1)
                {
                    drawImage2(paramGraphics, this.imgPok, this.snow_x * 5, this.snow_y * 7 - 3, 0x2 | 0x1);
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
                            drawImage2(paramGraphics, this.imgPPang, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
                        } else {
                            drawImage2(paramGraphics, this.imgPPang1, this.snow_x * 5, this.snow_y * 7 - 6, 0x2 | 0x1);
                        }
                    }
                    else if (this.ppang < 4) {
                        drawImage2(paramGraphics, this.imgEffect[0], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                    } else {
                        drawImage2(paramGraphics, this.imgEffect[1], this.snow_x * 5, this.snow_y * 7 - 2, 0x2 | 0x1);
                    }
                    if (this.hit_idx != 10)
                    {
                        if (this.e_hp[this.hit_idx] > 0)
                        {
                            // paramGraphics.setColor(16711680);
                            fillRect(paramGraphics, this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15, 16711680);
                            // paramGraphics.setColor(9672090);
                            fillRect(paramGraphics, this.e_x[this.hit_idx] * 5 + 8, this.e_y[this.hit_idx] * 5 + 5, 3, 15 - 15 * this.e_hp[this.hit_idx] / this.max_e_hp[this.hit_idx], 9672090);
                        }
                    }
                    else if (this.hit_idx == 10)
                    {
                        if (this.e_boss_hp > 0)
                        {
                            // paramGraphics.setColor(16711680);
                            fillRect(paramGraphics, this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15, 16711680);
                            // paramGraphics.setColor(9672090);
                            fillRect(paramGraphics, this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp, 9672090);
                        }
                        if (this.al == 1) {
                            drawImage2(paramGraphics, this.imgAl, this.snow_x * 5 + 6, this.snow_y * 7 - 10, 0x2 | 0x1);
                        }
                    }
                    this.ppang += 1;
                    if (this.ppang == 6)
                    {
                        this.ppang = 0;
                        this.s_item = 0;
                        this.al = -1;
                    }
                }
                else if (this.ppang >= 50)
                {
                    draw_sp_hyo(paramGraphics);
                }
                if (this.message != "") {
                    draw_text(paramGraphics);
                }
            }
            else if (this.pw_up == -1)
            {
                this.pw_up = 0;
            }
            if (this.p_mode == 1)
            {
                //try
                //{
                    drawImage2(paramGraphics, ui, 0, 109, 20); // ui.png
                //}
                //catch (Exception localException1) {}
                draw_item(paramGraphics);
                this.p_mode = 2;
                //System.gc();
            }
            if (this.d_gauge != 0) {
                draw_gauge(paramGraphics);
            }
            for (j = 0; j < this.e_num; j++) {
                if (this.e_behv[j] != 100)
                {
                    drawImage2(paramGraphics, this.imgShadow, this.e_snow_x[j], this.e_snow_y[j] * 6 + 17, 0x2 | 0x1);
                    drawImage2(paramGraphics, this.imgItem[this.e_wp[j]], this.e_snow_x[j], this.e_snow_y[j] * 6 + 13 - this.e_snow_gap[j], 0x2 | 0x1);
                }
            }
            if ((this.e_boss_behv != 100) && (this.e_boss > 0))
            {
                drawImage2(paramGraphics, this.imgShadow, this.e_boss_snow_x, this.e_boss_snow_y * 6 + 17, 0x2 | 0x1);
                drawImage2(paramGraphics, this.imgItem[this.e_boss_wp], this.e_boss_snow_x, this.e_boss_snow_y * 6 + 13 - this.e_boss_snow_gap, 0x2 | 0x1);
            }
            if (this.del != -1) {
                draw_item(paramGraphics);
            }
            if (this.h_timer_p <= -1) {
                if (this.h_timer_p != -5)
                {
                    drawImage2(paramGraphics, this.imgH_ppang, this.h_x * 5 + 1, 81, 0x2 | 0x1);
                    this.h_timer_p -= 1;
                }
                else if (this.h_timer_p == -5)
                {
                    this.h_timer_p = 0;
                    // paramGraphics.setColor(16711680);
                    fillRect(paramGraphics, 5, 113, 9, 12, 16711680);
                    // paramGraphics.setColor(9342606);
                    if (this.hp > 0) {
                        fillRect(paramGraphics, 5, 113, 9, 12 - 12 * this.hp / this.max_hp, 9342606);
                    }
                    if (this.hp <= 0)
                    {
                        this.state = 3;
                        this.game_state = 1;
                        this.gameOn = true;
                    }
                }
            }
            if (this.state == 2)
            {
                if (this.ani_step >= 3) {
                    drawImage2(paramGraphics, this.imgStage[0], 20, 60, 20);
                }
                if (this.ani_step >= 6) {
                    drawImage2(paramGraphics, this.imgStage[1], 35, 60, 20);
                }
                if (this.ani_step >= 9) {
                    drawImage2(paramGraphics, this.imgStage[2], 50, 60, 20);
                }
                if (this.ani_step >= 12) {
                    drawImage2(paramGraphics, this.imgStage[3], 65, 60, 20);
                }
                if (this.ani_step >= 15) {
                    drawImage2(paramGraphics, this.imgStage[4], 80, 60, 20);
                }
                if (this.ani_step >= 19) {
                    drawImage2(paramGraphics, this.imgStage_num, 95, 60, 20);
                }
            }

            // Permanent draw mana + hp
            fillRect(paramGraphics, 5, 113, 9, 12 - 12 * this.hp / this.max_hp, 9342606);
            draw_gauge(paramGraphics);
        } // END RUNNING State check (screen = 6)
        else if (this.screen == 2)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "1.Play", 13, 23, 20, 16777164);
            drawString(paramGraphics, "2.Instructions", 13, 38, 20, 16777164);
            drawString(paramGraphics, "3.Configuration", 13, 53, 20, 16777164);
            drawString(paramGraphics, "4.Quit", 13, 68, 20, 16777164);
            drawImage2(paramGraphics, this.imgSl, 68, 115, 20);
            drawImage2(paramGraphics, this.imgCh, 3, this.m_mode * 15 + 11, 20);
        }
        else if (this.screen == 3)
        {
            drawImage2(paramGraphics, this.imgVill, 0, 0, 20);
            // paramGraphics.setColor(14994350);
            if (this.last_stage / 10 == 1)
            {
                drawImage2(paramGraphics, this.imgSchool, 78, 87, 3);
                drawImage2(paramGraphics, this.imgSchool, 49, 87, 3);
                drawImage2(paramGraphics, this.imgSchool, 19, 58, 3);
                fillRect(paramGraphics, 76, 73, 6, 5, 14994350);
                fillRect(paramGraphics, 47, 73, 6, 5, 14994350);
                // paramGraphics.setColor(15132390);
                fillRect(paramGraphics, 17, 44, 6, 5, 15132390);
            }
            else if (this.last_stage / 10 == 2)
            {
                drawImage2(paramGraphics, this.imgSchool, 49, 87, 3);
                drawImage2(paramGraphics, this.imgSchool, 19, 58, 3);
                // paramGraphics.setColor(14994350);
                fillRect(paramGraphics, 47, 73, 6, 5, 14994350);
                // paramGraphics.setColor(15132390);
                fillRect(paramGraphics, 17, 44, 6, 5, 15132390);
            }
            else if (this.last_stage / 10 == 3)
            {
                drawImage2(paramGraphics, this.imgSchool, 19, 58, 3);
                // paramGraphics.setColor(15132390);
                fillRect(paramGraphics, 17, 44, 6, 5, 15132390);
            }
            this.dbg(" hx hy " + this.h_x + " " + this.h_y);
            drawImage2(paramGraphics, this.imgCh, this.h_x, this.h_y, 20);
            if (this.m_mode != -1)
            {
                if (this.m_mode == 0) {
                    this.message = "Drugstore";
                } else if (this.m_mode == 1) {
                    this.message = "Item Shop";
                } else if (this.m_mode == 2) {
                    this.message = "Eastern Boys";
                } else if (this.m_mode == 3) {
                    this.message = "Southern Boys";
                } else if (this.m_mode == 4) {
                    this.message = "Western Boys";
                } else if (this.m_mode == 5) {
                    this.message = "Northern Boys";
                } else if (this.m_mode == 100) {
                    this.message = "No Admittance";
                }
                if (this.message != "") {
                    draw_text(paramGraphics);
                }
            }
            if ((this.ani_step == 0) && (this.last_stage > 20))
            {
                if (this.last_stage == 31) {
                    draw_text_box(paramGraphics, "Western Boys");
                } else if (this.last_stage == 41) {
                    draw_text_box(paramGraphics, "Northern Boys");
                } else if (this.last_stage == 21) {
                    draw_text_box(paramGraphics, "Southern Boys");
                }
                this.ani_step += 1;
                this.sleepAbit(100); // TODO not work. render loop seem caused this. if freeze before show message.
            }
        }
        else if (this.screen == 31)
        {
            drawImage2(paramGraphics, this.imgVill, 0, 0, 20);
            drawImage2(paramGraphics, this.imgShop, 24, 20, 20);
            drawImage2(paramGraphics, this.imgSl, 68, 115, 20);
            // paramGraphics.setColor(16777062);
            // This double draw seem for bold; buy / exit select.
            drawRect(paramGraphics, 27, this.s_item * 13 + 30, 29, 10, 16777062);
            drawRect(paramGraphics, 28, this.s_item * 13 + 31, 27, 8, 16777062);
            // paramGraphics.setColor(13434777);
            drawRect(paramGraphics, this.b_item * 16 + 32, 70, 15, 15, 13434777);
            // redraw for bold; it have a gap bug, may be caused by rounding integer.
            drawRect(paramGraphics, this.b_item * 16 + 33, 71, 13, 13, 13434777);
            draw_int(paramGraphics, this.saved_gold, 84, 96);
            if (this.m_mode == 1) {
                draw_int(paramGraphics, this.item_price[this.b_item], 42, 96);
            } else if (this.m_mode == 0) {
                draw_int(paramGraphics, this.item_price[(this.b_item + 4)], 42, 96);
            }
            if (this.message != "") {
                draw_text(paramGraphics);
            }
        }
        else if (this.screen == 100)
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 1, 20, 126, 90, 16777215);
            // paramGraphics.setColor(0);
            drawRect(paramGraphics, 0, 19, 127, 90, 0);
            drawRect(paramGraphics, 0, 21, 127, 86, 0);
            this.imgCh = this.imgChSwap; // nickfarrow fix
            drawImage2(paramGraphics, this.imgCh, 3, this.m_mode * 14 + 18, 20);
            drawString(paramGraphics, "Resume", 15, 28, 20, 0);
            drawString(paramGraphics, "MainMenu", 15, 42, 20, 0);
            drawString(paramGraphics, "Sound", 15, 56, 20, 0);
            if (this.s_play == 1)
            {
                // paramGraphics.setColor(255);
                drawString(paramGraphics, "On/", 69, 56, 20, 255);
                // paramGraphics.setColor(8421504);
                drawString(paramGraphics, "off", 96, 56, 20, 8421504);
            }
            else
            {
                // paramGraphics.setColor(8421504);
                drawString(paramGraphics, "on/", 69, 56, 20, 8421504);
                // paramGraphics.setColor(255);
                drawString(paramGraphics, "OFF", 93, 56, 20, 255);
            }
            // paramGraphics.setColor(0); // 0 = black; [Items Mode] orig is black.
            // TODO fix freeze on Instruction choosed.
            drawString(paramGraphics, "Instructions", 15, 70, 20, 0);
            drawString(paramGraphics, "Quit", 15, 84, 20, 0);
        }
        else if (this.screen == -88)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "1.New Game", 13, 27, 20, 16777164);
            drawString(paramGraphics, "2.Saved Game", 13, 44, 20, 16777164);
            drawImage2(paramGraphics, this.imgSl, 68, 115, 20);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
            drawImage2(paramGraphics, this.imgCh, 4, this.m_mode * 17 + 14, 20);
        }
        else if (this.screen == 8)
        {
            if ((this.ani_step == 1) || (this.ani_step == 2))
            {
                // paramGraphics.setColor(10173);
                fillRect(paramGraphics, 0, 40, 128, 60, 10173);
                drawImage2(paramGraphics, this.imgSpecial[0], 44, 70, 3);
                drawImage2(paramGraphics, this.imgSpecial[1], 44, 89, 3);
            }
            else if (this.ani_step == 8)
            {
                drawImage2(paramGraphics, this.imgSpecial[0], 44, 70, 3);
                drawImage2(paramGraphics, this.imgSpecial[1], 48, 89, 3);
            }
            else if (this.ani_step == 16)
            {
                drawImage2(paramGraphics, this.imgSpecial[0], 44, 70, 3);
                drawImage2(paramGraphics, this.imgSpecial[1], 51, 89, 3);
            }
            else if (this.ani_step == 23)
            {
                drawImage2(paramGraphics, this.imgSpecial[0], 44, 70, 3);
                drawImage2(paramGraphics, this.imgSpecial[1], 54, 89, 3);
            }
            else if (this.ani_step == 30)
            {
                drawImage2(paramGraphics, this.imgSpecial[0], 44, 70, 3);
                drawImage2(paramGraphics, this.imgSpecial[1], 55, 89, 3);
            }
            else if (this.ani_step == 37)
            {
                drawImage2(paramGraphics, this.imgSpecial[2], 58, 88, 3);
            }
            else if (this.ani_step == 50)
            {
                destroyImage(8);
                loadImage(9);
                this.ani_step = 0;
                this.screen = 9;
            }
        }
        else if (this.screen == 9)
        {
            if ((this.ani_step == 1) || (this.ani_step == 46))
            {
                if (this.ani_step == 46)
                {
                    destroyImage(9);
                    loadImage(100);
                    this.pw_up = -1;
                    drawImage2(paramGraphics, this.imgBack, 0, 0, 20);
                    this.screen = 6;
                    this.ppang = 50;
                    for (j = 0; j < this.e_num; j++)
                    {
                        this.e_move_dir[j] = 0;
                        decs_e_hp(j);
                    }
                    if (this.e_boss > 0)
                    {
                        this.e_boss_move_dir = 0;
                        decs_e_hp(10);
                    }
                    this.dem = 12;
                    this.mana = 0;
                }
                // paramGraphics.setColor(16777215);
                fillRect(paramGraphics, 0, 25, 128, 84, 16777215);
                for (j = 0; j < this.e_num; j++)
                {
                    if (this.e_x[j] != -10) {
                        drawImage2(paramGraphics, this.imgEnemy[this.e_idx[j]], this.e_x[j] * 5, this.e_y[j] * 5 + 5, 0x10 | 0x1);
                    }
                    if (this.e_behv[j] != 100)
                    {
                        drawImage2(paramGraphics, this.imgShadow, this.e_snow_x[j], this.e_snow_y[j] * 6 + 17, 0x2 | 0x1);
                        drawImage2(paramGraphics, this.imgItem[this.e_wp[j]], this.e_snow_x[j], this.e_snow_y[j] * 6 + 13 - this.e_snow_gap[j], 0x2 | 0x1);
                    }
                }
                if (this.e_boss > 0) {
                    drawImage2(paramGraphics, this.imgBoss[this.e_boss_idx], this.e_boss_x * 5, this.e_boss_y * 5, 0x10 | 0x1);
                }
            }
            if (this.special == 1)
            {
                if (this.ani_step <= 45) {
                    drawImage2(paramGraphics, this.imgSp, 158 - this.ani_step * 3, 0, 20);
                }
            }
            else if (this.special == 2)
            {
                if (this.ani_step <= 45) {
                    drawImage2(paramGraphics, this.imgSp, 158 - this.ani_step * 3, 0, 20);
                }
            }
            else if ((this.special == 3) && (this.ani_step <= 45)) {
                drawImage2(paramGraphics, this.imgSp, 168 - this.ani_step * 3, 30, 20);
            }
            drawImage2(paramGraphics, this.imgHero[0], this.h_x * 5, 83, 0x10 | 0x1);
        }
        else if (this.screen == 4)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "Sound", 12, 23, 20, 16777164);
            if (this.s_play == 1)
            {
                drawString(paramGraphics, "ON /", 62, 23, 20, 16777164); // color required ? WHITE as default may be use default/no param;
                // paramGraphics.setColor(10790052);
                drawString(paramGraphics, "off", 95, 23, 20, 10790052);
                // paramGraphics.setColor(16777164);
            }
            if (this.s_play == 2)
            {
                // paramGraphics.setColor(10790052);
                drawString(paramGraphics, "on /", 62, 23, 20, 10790052);
                // paramGraphics.setColor(16777164);
                drawString(paramGraphics, "OFF", 94, 23, 20, 16777164);
            }
            drawString(paramGraphics, "Vibration ", 12, 41, 20, 16777164);
            if (this.v_mode == 1)
            {
                drawString(paramGraphics, "ON /", 62, 59, 20, 16777164);
                // paramGraphics.setColor(10790052);
                drawString(paramGraphics, "off", 95, 59, 20, 10790052);
                // paramGraphics.setColor(16777164);
            }
            if (this.v_mode == 2)
            {
                // paramGraphics.setColor(10790052);
                drawString(paramGraphics, "on /", 62, 59, 20, 10790052);
                // paramGraphics.setColor(16777164);
                drawString(paramGraphics, "OFF", 94, 59, 20, 16777164);
            }
            drawString(paramGraphics, "Speed ", 14, 77, 20, 16777164);
            drawString(paramGraphics, "[ " + String.valueOf(this.speed) + " ]", 68, 77, 20, 16777164);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
            if (this.m_mode < 3) {
                drawImage2(paramGraphics, this.imgCh, 4, this.m_mode * 18 + 9, 20);
            } else {
                drawImage2(paramGraphics, this.imgCh, 4, this.m_mode * 18 + 27, 20);
            }
        }
        else if (this.screen == 5)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "1.Control Keys", 10, 25, 20, 16777164);
            drawString(paramGraphics, "2.Offense items", 10, 42, 20, 16777164);
            drawString(paramGraphics, "3.Defense items", 10, 59, 20, 16777164);
            drawImage2(paramGraphics, this.imgCh, 3, this.m_mode * 17 + 12, 20);
            drawImage2(paramGraphics, this.imgSl, 68, 115, 20);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
        }
        else if (this.screen == -33)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
            destroyImage(2);

            if (this.m_mode == 1) {
                drawImage2(paramGraphics, txt4, 5, 25, 20);
            }
            if (this.m_mode == 2)
            {
                fillRect(paramGraphics, 6, 23, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 45, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 61, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 84, 10, 10, 16777164);
                drawImage2(paramGraphics, imgItem[1], 7, 24, 20); // item1.png
                drawImage2(paramGraphics, imgItem[2], 7, 46, 20); // item2.png
                drawImage2(paramGraphics, imgItem[3], 7, 62, 20); // item3.png
                drawImage2(paramGraphics, imgItem[4], 7, 85, 20); // item4.png
                drawImage2(paramGraphics, txt2, 23, 25, 20); // txt2.png
            }
            if (this.m_mode == 3)
            {
                fillRect(paramGraphics, 6, 23, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 38, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 53, 10, 10, 16777164);
                fillRect(paramGraphics, 6, 67, 10, 10, 16777164);
                drawImage2(paramGraphics, imgItem[5], 7, 24, 20); // item5.png
                drawImage2(paramGraphics, imgItem[6], 7, 39, 20); // item6.png
                drawImage2(paramGraphics, imgItem[7], 7, 54, 20); // item7.png
                drawImage2(paramGraphics, imgItem[8], 7, 68, 20); // item8.png
                drawImage2(paramGraphics, txt1, 23, 25, 20); // txt1.png
            }
        }
        else if (this.screen == 200)
        {
            if ((this.ani_step >= 13) && (this.ani_step < 27))
            {
                // paramGraphics.setColor(16777215);
                // fillRect(paramGraphics, 0, 60, 128, 47, 16777215);
                drawImage2(paramGraphics, this.imgHero_v, this.h_x * 5, 83, 0x10 | 0x1);
            }
            else if ((this.ani_step >= 28) && (this.ani_step < 50))
            {
                drawImage2(paramGraphics, this.imgV, this.h_x * 5 + 8, 87, 0x10 | 0x1);
                this.sleepAbit(100);

                if (this.ani_step > 41) {
                    drawImage2(paramGraphics, this.imgVictory, 60, 60, 0x10 | 0x1);
                }
            }
            else if (this.ani_step == 50)
            {
                this.ani_step = -1;
            }
        }
        else if (this.screen == 65335)
        {
            int k;
            if (this.ani_step < 30)
            {
                // paramGraphics.setColor(0);
                for (k = 0; k < 11; k++) {
                    fillRect(paramGraphics, 0, k * 10, this.ani_step * 4 + 12, 5, 0);
                }
            }
            else if (this.ani_step < 65)
            {
                // paramGraphics.setColor(0);
                for (k = 0; k < 11; k++) {
                    fillRect(paramGraphics, 0, k * 10 + 5, (this.ani_step - 30) * 7 - k * 10, 5, 0);
                }
            }
            else if ((this.ani_step >= 65) && (this.ani_step <= 100))
            {
                if (this.ani_step > 90) {
                    drawImage2(paramGraphics, this.imgLose, 60, 60, 0x10 | 0x1);
                }
                drawImage2(paramGraphics, this.imgHero_l, this.h_x * 5, 87, 0x10 | 0x1);
            }
        }
        else if (this.screen == 300)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "Good Job!", 15, 23, 20, 16777164);
            // paramGraphics.setColor(13434726);
            drawString(paramGraphics, "Acquired", 15, 41, 20, 13434726);
            drawString(paramGraphics, "Gold:", 48, 57, 20, 13434726);
            drawString(paramGraphics, String.valueOf(this.gold), 92, 57, 20, 13434726);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "press any key", 10, 83, 20, 16777164);
            drawString(paramGraphics, "to continue", 37, 97, 20, 16777164);
        }
        else if (this.screen == 77)
        {
            draw_text(paramGraphics);
        }
        else if (this.screen == -1) // Logo
        {
            loadImage(1);
            drawImage2(paramGraphics, this.imgLogo, 0, 0, 20);
            MPlay(0);
            // destroyImage(1);
        }
        else if (this.screen == -2) // SamSung Funclub
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 0, 128, 135, 16777215); // fix me it seem white color have alpha or smth make it black/blank.
            // paramGraphics.setColor(25054);
            fillRect(paramGraphics, 0, 0, 128, 22, 25054);
            fillRect(paramGraphics, 0, 71, 128, 84, 25054);

            drawImage2(batch, present, 64, 5, 0x10 | 0x1); // present.png // 83 x 11;
            drawImage2(batch, sam_logo, 64, 28, 0x10 | 0x1); // sam_logo.png // 96 x 33; bg white so this width < 128 not matter.
            drawImage2(batch, http1, 7, 77, 20); // http1.png
            drawImage2(batch, http2, 7, 103, 20); // http2.png
        }
        else if (this.screen == GameScreens.ALL_CLEAR_SCREEN) // 1000
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 25, 120, 85, 16777215);
            // drawImage2(paramGraphics, allClear, 64, 10, 0x10 | 0x1); // allClear.png
            batch.draw(this.allClear, 0, 0+BOTTOM_SPACE);
        }
        else if (this.screen == -5)
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 1, 20, 126, 90, 16777215);
            // paramGraphics.setColor(0);
            drawRect(paramGraphics, 0, 19, 127, 90, 0);
            drawRect(paramGraphics, 0, 21, 127, 86, 0);

            drawImage2(paramGraphics, txt4b, 4, 30, 20); // txt4b
        }
        else if (this.screen == 1)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            drawImage2(paramGraphics, title, 64, 35, 0x10 | 0x1); // title.png

            drawImage2(paramGraphics, this.imgPl, 68, 115, 20);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
            //System.gc();
        }
        // draw_gauge(paramGraphics);
    }

    public void repaint() { // Override orig J2ME
    }

    public void draw_text_box(SpriteBatch paramGraphics, String paramString)
    {
        // paramGraphics.setColor(44783);
        fillRect(paramGraphics, 0, 42, 128, 38, 44783);
        // paramGraphics.setColor(20361);
        drawRect(paramGraphics, 0, 42, 127, 38, 20361);
        // paramGraphics.setColor(0);
        drawString(paramGraphics, paramString, 9, 46, 20, 0);
        drawString(paramGraphics, "challenged you!!", 4, 60, 20, 0);
    }

    public void draw_text(SpriteBatch paramGraphics)
    {
        // paramGraphics.setColor(44783);
        fillRect(paramGraphics, 0, 52, 128, 19, 44783);
        // paramGraphics.setColor(20361);
        drawRect(paramGraphics, 0, 52, 127, 19, 20361);
        // paramGraphics.setColor(0);

        if (this.message == null) {
            this.dbg("Null message");
            return;
        }
        int i = this.message.length(); // orig MIDP have this line; no idea what it does; may be jar error.
        drawString(paramGraphics, this.message, 64, 53, 0x10 | 0x1, 0);
        this.message = "";
    }

    public void draw_item(SpriteBatch paramGraphics)
    {
        // this.dbg(" del " + this.del + " item slot " + (Arrays.toString(this.item_slot)));
        if (this.del == -1)
        {
            for (int i = 0; i < 5; i++) {
                if (this.item_slot[i] != 0) {
                    drawImage2(paramGraphics, this.imgItem[this.item_slot[i]], 12 * i + 37, 111, 20);
                }
            }
        }
        else
        {
            // paramGraphics.setColor(6974058);
            fillRect(paramGraphics, this.del * 12 + 37, 111*0, 8, 8, 6974058);
            this.del = -1;
        }
    }

    public void draw_sp_hyo(SpriteBatch paramGraphics)
    {
        for (int i = 0; i < this.e_num; i++) {
            if (this.e_hp[i] >= 0)
            {
                // paramGraphics.setColor(16711680);
                fillRect(paramGraphics, this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15, 16711680);
                // paramGraphics.setColor(9672090);
                fillRect(paramGraphics, this.e_x[i] * 5 + 8, this.e_y[i] * 5 + 5, 3, 15 - 15 * this.e_hp[i] / this.max_e_hp[i], 9672090);
                if (this.ppang <= 51) {
                    drawImage2(paramGraphics, this.imgEffect[0], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
                } else if (this.ppang <= 54) {
                    drawImage2(paramGraphics, this.imgEffect[1], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
                }
            }
        }
        if ((this.e_boss_hp >= 0) && (this.e_boss > 0))
        {
            // paramGraphics.setColor(16711680);
            fillRect(paramGraphics, this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15, 16711680);
            // paramGraphics.setColor(9672090);
            fillRect(paramGraphics, this.e_boss_x * 5 + 12, this.e_boss_y * 5 + 5, 3, 15 - 15 * this.e_boss_hp / this.max_e_boss_hp, 9672090);
            if (this.ppang <= 51) {
                drawImage2(paramGraphics, this.imgEffect[0], this.e_boss_x * 5, this.e_boss_y * 5 + 5, 0x10 | 0x1);
            } else if (this.ppang <= 54) {
                drawImage2(paramGraphics, this.imgEffect[1], this.e_boss_x * 5, this.e_boss_y * 5 + 6, 0x10 | 0x1);
            }
        }
        if (this.ppang != 55)
        {
            this.ppang += 1;
        }
        else
        {
            for (int j = 0; j < this.e_num; j++) {
                if (this.e_hp[j] > 0)
                {
                    if (this.special == 2)
                    {
                        this.e_ppang_time[j] = 65;
                        this.e_ppang_item[j] = 2;
                    }
                    else if (this.special == 3)
                    {
                        this.e_ppang_time[j] = 80;
                        this.e_ppang_item[j] = 1;
                        this.e_lv[j] = (-this.e_lv[j]);
                    }
                    this.e_move_dir[j] = 0;
                }
            }
            this.ppang = 0;
            this.special = 0;
        }
    }

    public void draw_enemy(SpriteBatch paramGraphics)
    {
        for (int i = 0; i < this.e_num; i++) {
            if (this.e_x[i] != -10)
            {
                drawImage2(paramGraphics, this.imgEnemy[this.e_idx[i]], this.e_x[i] * 5, this.e_y[i] * 5 + 5, 0x10 | 0x1);
                if (this.e_ppang_time[i] > 0)
                {
                    this.e_ppang_time[i] -= 1;
                    drawImage2(paramGraphics, this.imgItem_hyo[(this.e_ppang_item[i] - 1)], this.e_x[i] * 5, this.e_y[i] * 5 + 1, 0x10 | 0x1);
                    if (this.e_ppang_time[i] == 0)
                    {
                        this.e_ppang_item[i] = 0;
                        if (this.e_lv[i] < 0) {
                            this.e_lv[i] = (-this.e_lv[i]);
                        }
                    }
                }
                if (this.dis_count[i] >= 1)
                {
                    this.dis_count[i] += 1;
                    if (this.dis_count[i] == 4)
                    {
                        this.dis_count[i] = 0;
                        this.e_idx[i] = 0;
                    }
                }
                else if (this.dis_count[i] <= -1)
                {
                    this.dis_count[i] -= 1;
                    if (this.dis_count[i] == -10)
                    {
                        this.e_x[i] = -10;
                        this.dis_count[i] = 0;
                        this.e_t_num -= 1;
                        if ((this.e_t_num == 0) && (this.e_boss == 0))
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
        if (this.e_boss > 0)
        {
            drawImage2(paramGraphics, this.imgBoss[this.e_boss_idx], this.e_boss_x * 5, this.e_boss_y * 5, 0x10 | 0x1);
            if (this.boss_dis_count >= 1)
            {
                this.boss_dis_count += 1;
                if (this.boss_dis_count == 4)
                {
                    this.boss_dis_count = 0;
                    this.e_boss_idx = 0;
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

    public void draw_gauge(SpriteBatch paramGraphics)
    {
        if (this.d_gauge == 2)
        {
            // paramGraphics.setColor(16775065);
            fillRect(paramGraphics, 118, 111, 8, 8, 16775065);
            if (this.wp != 0) {
                drawImage2(paramGraphics, this.imgItem[this.wp], 122, 111, 0x10 | 0x1);
            }
        }
        if (this.mana != 0)
        {
            // paramGraphics.setColor(16711680);
            fillRect(paramGraphics, 30, 124, this.mana, 1, 16711680);
            if (this.mana == 36)
            {
                fillRect(paramGraphics, 39, 123, 3, 3, 16711680);
                fillRect(paramGraphics, 51, 123, 3, 3, 16711680);
                fillRect(paramGraphics, 63, 123, 3, 3, 16711680);
            }
            else if (this.mana >= 24)
            {
                fillRect(paramGraphics, 39, 123, 3, 3, 16711680);
                fillRect(paramGraphics, 51, 123, 3, 3, 16711680);
            }
            else if (this.mana >= 12)
            {
                fillRect(paramGraphics, 39, 123, 3, 3, 16711680);
            }
        }
        else if (this.mana == 0)
        {
            // paramGraphics.setColor(4960985);
            fillRect(paramGraphics, 30, 124, 36, 1, 4960985);
            fillRect(paramGraphics, 39, 123, 3, 3, 4960985);
            fillRect(paramGraphics, 51, 123, 3, 3, 4960985);
            fillRect(paramGraphics, 63, 123, 3, 3, 4960985);
        }
        this.d_gauge = 0;
    }

    public void draw_int(SpriteBatch paramGraphics, int number, int paramInt2, int paramInt3)
    {
        int i = 0;
        if (number / 100 > 0) {
            i = 3;
        } else if (number / 10 > 0) {
            i = 2;
        } else {
            i = 1;
        }
        int[] arrayOfInt = new int[i];
        if (i == 3) // saved game mechanism ?
        {
            arrayOfInt[2] = (number / 100);
            arrayOfInt[1] = (number / 10 % 10);
            arrayOfInt[0] = (number % 10);
        }
        else if (i == 2)
        {
            arrayOfInt[1] = (number / 10);
            arrayOfInt[0] = (number % 10);
        }
        else if (i == 1)
        {
            arrayOfInt[0] = number;
        }
        for (int j = i; j > 0; j--) {
            if (number < 10) {
                drawImage2(paramGraphics, this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2, paramInt3, 20);
            } else {
                drawImage2(paramGraphics, this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2 - 2, paramInt3, 20);
            }
        }

        // arrayOfInt[(j - 1) out of range exception is COOL! it prevent saved gold > 1000 ? so not easy hack kk :)
    }

    public void run()
    {
        if (this.gameOn)
        {
            if (this.screen == 6) // RUNNING
            {
                if (this.state == 1)
                {
                    try
                    {
                        Thread.sleep(this.game_speed);
                    }
                    catch (Exception localException1) {}

                    if (this.pw_up == 1)
                    {
                        setPower();
                        if (this.h_idx == 2) {
                            this.h_idx = 3;
                        } else if (this.h_idx == 3) {
                            this.h_idx = 2;
                        }
                    }
                    else if (this.pw_up == 2)
                    {
                        if (this.h_timer < 4)
                        {
                            this.h_timer += 1;
                            if (this.h_timer == 4) {
                                this.h_idx = 0;
                            }
                        }
                        if (this.snow_y > this.snow_last_y)
                        {
                            this.snow_y -= 1;
                            if (this.snow_y > this.snow_top_y) {
                                this.snow_gap += 3;
                            } else if (this.snow_y < this.snow_top_y) {
                                this.snow_gap -= 3;
                            }
                        }
                        else
                        {
                            check_ppang();
                        }
                    }
                    this.e_time += 1;
                    for (int i = 0; i < this.e_num; i++)
                    {
                        if (this.e_hp[i] >= 0)
                        {
                            if ((this.e_time == this.e_fire_time[i]) && (get_random(3) != 1) && (this.e_ppang_item[i] != 2)) {
                                e_attack_ai(i);
                            }
                            if (this.e_ppang_item[i] != 2) {
                                if (this.e_idx[i] == 0) {
                                    this.e_idx[i] = 1;
                                } else if (this.e_idx[i] == 1) {
                                    this.e_idx[i] = 0;
                                }
                            }
                        }
                        if (this.e_move_dir[i] >= 100)
                        {
                            this.e_move_dir[i] += 1;
                            if (this.e_move_dir[i] == 120) {
                                this.e_move_dir[i] = 0;
                            }
                        }
                        else if ((this.e_move_dir[i] == 0) && (this.e_hp[i] > 0) && (this.e_ppang_item[i] != 2))
                        {
                            //System.out.println("E move dir = 0 AI: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
                            e_move_ai(i);
                        }
                        else if ((this.e_move_dir[i] < 100) && (this.e_move_dir[i] != 0) && (this.e_hp[i] > 0))
                        {
                            //System.out.println("E move dir < 100: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
                            e_move(i);
                        }
                    }
                    if (this.e_boss > 0)
                    {
                        if (this.e_boss_hp >= 0)
                        {
                            if ((this.e_time == this.e_boss_fire_time) && (get_random(3) != 1)) {
                                if ((this.e_boss == 1) || (this.e_boss == 2)) {
                                    e_attack_ai(101);
                                } else {
                                    e_attack_ai(102);
                                }
                            }
                            if (this.e_boss_idx == 0) {
                                this.e_boss_idx = 1;
                            } else if (this.e_boss_idx == 1) {
                                this.e_boss_idx = 0;
                            }
                        }
                        if (this.e_boss_move_dir >= 100)
                        {
                            this.e_boss_move_dir += 1;
                            if (this.e_boss_move_dir == 115) {
                                this.e_boss_move_dir = 0;
                            }
                        }
                        else if ((this.e_boss_move_dir == 0) && (this.e_boss_hp > 0))
                        {
                            boss_move_ai();
                        }
                        else if ((this.e_boss_move_dir != 0) && (this.e_boss_hp > 0))
                        {
                            boss_move();
                        }
                    }
                    if ((this.e_num == 3) || (this.e_num == 4))
                    {
                        if (this.e_time == 21) {
                            this.e_time = 0;
                        }
                    }
                    else if ((this.e_num == 2) && (this.e_time == 18)) {
                        this.e_time = 0;
                    }
                    e_snow();
                    if (this.gameOn)
                    {
                         repaint();
                        // serviceRepaints();
                    }
                }
                else if (this.state == 2)
                {
                    if ((this.ani_step >= 1) && (this.ani_step <= 20)) {
                        this.ani_step += 1;
                    }
                    if (this.ani_step == 0)
                    {
                        loadImage(-6);
                        this.ani_step = 1;
                    }
                    else if ((this.ani_step >= 1) && (this.ani_step <= 19))
                    {
                        repaint();
                        // serviceRepaints();
                    }
                    else if (this.ani_step == 20)
                    {
                        destroyImage(-6);
                        this.state = 1;
                    }
                }
                else if (this.state == 3)
                {
                    if (this.game_state == 2)
                    {
                        this.screen = 201;
                        MPlay(7);
                        this.gold = (this.school * 6 + get_random(7) + 5);
                    }
                    else if (this.game_state == 1)
                    {
                        this.screen = 65336; // lose
                        this.gold = 3;
                    }

                    setSavedMana(this.mana);
                    setSavedGold(getSavedgold()+ this.gold);
                    setGameStage(this.stage);
                }
            }
            else if (this.screen == 8) // SPECIAL_ANIMATION
            {
                if ((this.ani_step < 50) && (this.ani_step > 0)) {
                    this.ani_step += 1;
                }
                repaint();
                // serviceRepaints();
            }
            else if (this.screen == 9) // SPECIAL_SCREEN
            {
                if ((this.ani_step < 46) && (this.ani_step >= 0)) {
                    this.ani_step += 1;
                }
                repaint();
                // serviceRepaints();
            }
            else if (this.screen == 200) // VICTORY
            {
                if ((this.ani_step < 51) && (this.ani_step >= 0))
                {
                    this.ani_step += 1;
                    repaint();
                    // serviceRepaints();
                }
                else
                {
                    this.gameOn = false; // Game ending here.
                    destroyImage(200);
                    //System.gc();
                    if (this.state != 10)
                    {
                        loadImage(2);
                        this.sleepAbit(100);
                        this.screen = 300;
                        this.sleepAbit(100);
                    }
                    else
                    {
                        this.screen = GameScreens.ALL_CLEAR_SCREEN; // 1000;
                    }
                    repaint();
                }
            }
            else if (this.screen == 201)
            {
                this.last_stage = (this.last_stage > 45) ? 45 : this.last_stage; // NF

                this.ani_step = 0;
                if (this.last_stage / 10 == this.school)
                {
                    if (this.stage % 10 != 4)
                    {
                        this.stage += 1;
                    }
                    else if (this.stage != 44)
                    {
                        this.stage += 10;
                        this.stage = (this.stage - this.stage % 10 + 1);
                        // May be this increment cause stage > 45 -> other error after allClear
                    }
                    else
                    {
                        this.stage = 45;
                        this.state = 10; // All clear
                    }
                    this.last_stage = this.stage;

                    setGameStage(this.stage);
                    setLastGameStage(this.last_stage);
                }
                destroyImage(6);
                destroyImage(7);
                destroyImage(100);
                loadImage(200);
                this.screen = 200;
            }
            else if (this.screen == 65336)
            {
                this.item_mode = 0;
                this.ani_step = 0;
                destroyImage(6);
                destroyImage(7);
                destroyImage(100);
                loadImage(65336);
                MPlay(6);
                this.screen = 65335;
            }
            else if (this.screen == 65335) // LOSE_SCREEN
            {
                if (this.ani_step <= 100)
                {
                    this.ani_step += 1;
                    repaint();
                    // serviceRepaints();
                }
                else
                {
                    this.gameOn = false;
                    destroyImage(65336);
                    loadImage(2);
                    System.gc();
                    this.screen = 300;
                    repaint();
                }
            }
        }
        else {
            // this.dbg("ßßß ---- Game not running!");
        }

        paint(batch);
    }

    public void setPower()
    {
        if (this.snow_pw < 22)
        {
            this.snow_pw += 1;
            this.real_snow_pw = (this.snow_pw / 3);
        }
    }

    public void make_attack()
    {
        this.snow_y = 12;
        this.snow_x = this.h_x;
        this.snow_last_y = (9 - this.real_snow_pw);
        if (this.real_snow_pw % 2 == 0) {
            this.snow_top_y = (10 - this.real_snow_pw / 2);
        } else {
            this.snow_top_y = (9 - this.real_snow_pw / 2);
        }
        this.snow_gap = 3;
        this.h_timer = 0;
        this.pw_up = 2;
        MPlay(2);
    }

    public int get_random(int paramInt)
    {
        int i = this.rnd.nextInt() % paramInt;
        if (i < 0) {
            i = -i;
        }
        return i;
    }

    public int get_random1(int paramInt)
    {
        int i = this.rnd.nextInt() % paramInt;
        if (i == 0) {
            i = -5;
        }
        return i;
    }

    public void e_attack_ai(int paramInt)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        int m = 0;
        if (paramInt > 100)
        {
            m = this.h_x - this.e_boss_x;
            j = paramInt;
        }
        else
        {
            m = this.h_x - this.e_x[paramInt];
            k = paramInt;
        }
        int n;
        if ((m >= -9) && (m <= -6))
        {
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if ((this.e_lv[k] >= 2) || (j > 100))
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
            if (this.e_lv[k] >= 2)
            {
                n = get_random(4);
                if (n == 1)
                {
                    int i1 = get_random(3);
                    if (i1 == 0) {
                        this.e_wp[k] = 1;
                    } else if (i1 == 1) {
                        this.e_wp[k] = 2;
                    } else {
                        this.e_wp[k] = 3;
                    }
                }
                else
                {
                    this.e_wp[k] = 0;
                }
            }
            this.e_behv[k] = i;
            this.e_snow_y[k] = this.e_y[k];
            this.e_snow_x[k] = (this.e_x[k] * 5);
            this.e_snow_gap[k] = 0;
            this.e_snow_dx[k] = i;
            this.e_snow_top[k] = 1;
            this.e_idx[k] = 3;
            this.dis_count[k] = 1;
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
                        this.e_boss_wp = 1;
                    } else if (n == 1) {
                        this.e_boss_wp = 2;
                    } else {
                        this.e_boss_wp = 3;
                    }
                }
                else
                {
                    this.e_boss_wp = 0;
                }
            }
            else
            {
                n = get_random(3);
                if (n == 0) {
                    this.e_boss_wp = 1;
                } else if (n == 1) {
                    this.e_boss_wp = 2;
                } else {
                    this.e_boss_wp = 3;
                }
            }
            this.e_boss_behv = i;
            this.e_boss_snow_y = this.e_boss_y;
            this.e_boss_snow_x = (this.e_boss_x * 5);
            this.e_boss_snow_gap = 0;
            this.e_boss_snow_dx = i;
            this.e_boss_snow_top = 1;
            this.e_boss_idx = 3;
            this.boss_dis_count = 1;
        }
    }

    public void e_snow()
    {
        for (int i = 0; i < this.e_num; i++) {
            if (this.e_behv[i] != 100)
            {
                this.e_snow_y[i] += 1;
                this.e_snow_x[i] += this.e_snow_dx[i];
                if ((this.e_snow_gap[i] < 10) && (this.e_snow_top[i] == 1))
                {
                    this.e_snow_gap[i] += 2;
                    if (this.e_snow_gap[i] == 10) {
                        this.e_snow_top[i] = 2;
                    }
                }
                else
                {
                    this.e_snow_gap[i] -= 1;
                }
                if (this.e_snow_y[i] == 13) {
                    check_hero(this.e_snow_x[i], i);
                } else if (this.e_snow_y[i] >= 16) {
                    this.e_behv[i] = 100;
                }
            }
        }
        if ((this.e_boss > 0) && (this.e_boss_behv != 100))
        {
            this.e_boss_snow_y += 1;
            this.e_boss_snow_x += this.e_boss_snow_dx;
            if ((this.e_boss_snow_gap < 10) && (this.e_boss_snow_top == 1))
            {
                this.e_boss_snow_gap += 2;
                if (this.e_boss_snow_gap == 10) {
                    this.e_boss_snow_top = 2;
                }
            }
            else
            {
                this.e_boss_snow_gap -= 1;
            }
            if (this.e_boss_snow_y == 13) {
                check_hero(this.e_boss_snow_x, 100);
            } else if (this.e_boss_snow_y >= 16) {
                this.e_boss_behv = 100;
            }
        }
    }

    public void check_hero(int paramInt1, int paramInt2)
    {
        int i = 0;
        int j;
        if (paramInt2 != 100)
        {
            if (this.e_behv[paramInt2] <= 0)
            {
                i = 5;
                j = 9;
            }
            else
            {
                i = 9;
                j = 5;
            }
        }
        else if (this.e_boss_behv <= 0)
        {
            i = 5;
            j = 9;
        }
        else
        {
            i = 9;
            j = 5;
        }
        if ((paramInt1 - this.h_x * 5 <= j) && (paramInt1 - this.h_x * 5 >= -i))
        {
            int k = -1;
            if (paramInt2 != 100)
            {
                this.e_behv[paramInt2] = 100;
                k = this.e_wp[paramInt2];
            }
            else
            {
                this.e_boss_behv = 100;
                k = this.e_boss_wp;
            }
            this.h_timer_p = -1;
            if (this.hp > 0) {
                if (k == 0)
                {
                    this.hp -= this.e_dem;
                }
                else if (k == 1)
                {
                    this.ppang_item = 1;
                    this.ppang_time = 20;
                    this.hp -= this.e_dem * 2 / 3;
                }
                else if (k == 2)
                {
                    this.ppang_item = 1;
                    this.ppang_time = 20;
                    this.hp -= this.e_dem;
                }
                else if (k == 3)
                {
                    this.ppang_item = 2;
                    this.ppang_time = 20;
                    this.hp -= this.e_dem * 2 / 3;
                }
            }
            MPlay(4);
            call_vib(1);
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
            if ((this.e_boss_x != 2) && (this.e_boss_move_dir % 2 == 0)) {
                this.e_boss_x -= 1;
            }
            if (this.e_boss_move_dir == 31) {
                this.e_boss_move_dir = 100;
            }
        }
        else if ((this.e_boss_move_dir > -31) && (this.e_boss_move_dir <= -21))
        {
            this.e_boss_move_dir -= 1;
            if ((this.e_boss_x != 22) && (this.e_boss_move_dir % 2 == 0)) {
                this.e_boss_x += 1;
            }
            if (this.e_boss_move_dir == -31) {
                this.e_boss_move_dir = 100;
            }
        }
    }

    public void boss_move_ai()
    {
        if (this.e_boss_x == 2)
        {
            this.e_boss_move_dir = -21;
        }
        else if (this.e_boss_x == 22)
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

    public void e_move_ai(int paramInt)
    {
        int i;
        if ((this.e_x[paramInt] == 2) || ((this.e_x[1] <= 9) && (paramInt == 1)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir[paramInt] = -21;
            } else if (i == 2) {
                this.e_move_dir[paramInt] = -11;
            } else if (i == 3) {
                this.e_move_dir[paramInt] = 11;
            }
        }
        else if ((this.e_x[paramInt] == 22) || ((this.e_x[0] >= 14) && (paramInt == 0)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir[paramInt] = 21;
            } else if (i == 2) {
                this.e_move_dir[paramInt] = -11;
            } else if (i == 3) {
                this.e_move_dir[paramInt] = 11;
            }
        }
        else if ((this.e_y[paramInt] == 6) || (this.e_y[paramInt] == 7))
        {
            i = get_random(4);
            if ((i == 1) || (i == 2)) {
                this.e_move_dir[paramInt] = 11;
            } else if (i == 0) {
                this.e_move_dir[paramInt] = 21;
            } else if (i == 1) {
                this.e_move_dir[paramInt] = -21;
            }
        }
        else
        {
            i = get_random(8);
            if ((i == 0) || (i == 1)) {
                this.e_move_dir[paramInt] = 21;
            } else if ((i == 2) || (i == 3)) {
                this.e_move_dir[paramInt] = -21;
            } else if (i == 4) {
                this.e_move_dir[paramInt] = 11;
            } else if (i == 5) {
                this.e_move_dir[paramInt] = -11;
            } else {
                this.e_move_dir[paramInt] = 1;
            }
        }
    }

    public void e_move(int paramInt)
    {
        int i = this.e_move_dir[paramInt];
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
            if ((this.e_x[paramInt] != 2) && (i % 3 == 0)) {
                this.e_x[paramInt] -= 1;
            }
            if (i == 31) {
                i = 100;
            }
        }
        else if ((i > -31) && (i <= -21))
        {
            i--;
            if ((this.e_x[paramInt] != 22) && (i % 3 == 0)) {
                this.e_x[paramInt] += 1;
            }
            if (i == -31) {
                i = 100;
            }
        }
        else if ((i >= 11) && (i < 14))
        {
            i++;
            if ((this.e_y[paramInt] != 1) && (i % 2 == 0)) {
                this.e_y[paramInt] -= 1;
            }
            if (i == 14) {
                i = 100;
            }
        }
        else if ((i > -14) && (i <= -11))
        {
            i--;
            if ((this.e_y[paramInt] != 7) && (i % 2 == 0)) {
                this.e_y[paramInt] += 1;
            }
            if (i == -14) {
                i = 100;
            }
        }
        this.e_move_dir[paramInt] = i;
    }

    public void use_special()
    {
        this.screen = 8;
        this.ani_step = 1;
        this.real_snow_pw = 0;
        this.snow_pw = 0;
        this.h_idx = 0;
        this.gameOn = false;
        destroyImage(100);
        loadImage(8);
        if (this.mana == 36)
        {
            this.special = 3;
            this.dem = 24;
        }
        else if (this.mana >= 24)
        {
            this.special = 2;
            this.dem = 12;
        }
        else if (this.mana >= 12)
        {
            this.special = 1;
            this.dem = 12;
        }
        this.d_gauge = 1;
        MPlay(5);  // Something weird here, may be MPlay and call_vib on Real devices have some special effect
        // that make gameOn back to true.
        // I remmember that in this running state there are some (or may be often) similar bug on real divices.
        // It hang on this special screen.
        // call_vib(3);
        this.gameOn = true; // nickfarrow 21-Jan
        // Effect run OK, but special-effect (freeze, cleaner) is not updated by special
        // TODO fix effect/special effect not match
    }

    public void decs_e_hp(int paramInt)
    {
        this.hit_idx = paramInt;
        if (this.mana != 36) {
            if (this.mana <= 10) {
                this.mana += 2;
            } else {
                this.mana += 1;
            }
        }
        if (paramInt != 10)
        {
            if (this.wp == 0)
            {
                this.e_hp[paramInt] -= this.dem;
            }
            else if (this.wp == 1)
            {
                this.e_ppang_time[paramInt] = 70;
                this.e_ppang_item[paramInt] = 1;
                this.e_lv[paramInt] = (-this.e_lv[paramInt]);
                this.e_hp[paramInt] -= this.dem;
            }
            else if (this.wp == 2)
            {
                this.s_item = -10;
                this.e_hp[paramInt] -= 19;
            }
            else if (this.wp == 3)
            {
                this.e_ppang_time[paramInt] = 65;
                this.e_ppang_item[paramInt] = 2;
                this.e_hp[paramInt] -= this.dem / 2;
                this.e_move_dir[paramInt] = 0;
            }
            else if (this.wp == 4)
            {
                this.e_ppang_time[paramInt] = 75;
                this.e_ppang_item[paramInt] = 1;
                this.e_lv[paramInt] = (-this.e_lv[paramInt]);
                this.s_item = -10;
                this.e_hp[paramInt] -= this.dem * 2;
            }
            if (this.e_hp[paramInt] < 0)
            {
                this.e_idx[paramInt] = 2;
                this.dis_count[paramInt] = -1;
            }
        }
        else if (paramInt == 10)
        {
            if (this.wp == 4)
            {
                this.s_item = -10;
                this.e_boss_hp -= this.dem * 2;
            }
            else if (this.wp == 2)
            {
                this.s_item = -10;
                this.e_boss_hp -= 19;
            }
            else
            {
                this.e_boss_hp -= this.dem;
            }
            if (this.e_boss_hp < 0)
            {
                this.e_boss_idx = 2;
                this.boss_dis_count = -1;
            }
            if (this.al == 1) {
                this.e_boss_hp -= 5;
            }
        }
        MPlay(3);
    }

    public void check_ppang()
    {
        this.d_gauge = 2;
        int j;
        for (int i = 0; i < this.e_num; i++)
        {
            j = this.e_x[i];
            if ((j - this.snow_x >= -1) && (j - this.snow_x <= 1))
            {
                int k = this.e_y[i];
                if ((k >= 0) && (k <= 4))
                {
                    if ((k + this.real_snow_pw == 7) || (k + this.real_snow_pw == 8))
                    {
                        this.ppang = 1;
                        decs_e_hp(i);
                        break;
                    }
                }
                else if ((k + this.real_snow_pw == 8) || (k + this.real_snow_pw == 9))
                {
                    this.ppang = 1;
                    decs_e_hp(i);
                    break;
                }
            }
            this.ppang = -1;
        }
        if ((this.e_boss > 0) && (this.e_boss_x - this.snow_x >= -1) && (this.e_boss_x - this.snow_x <= 1))
        {
            j = this.e_boss_y + this.real_snow_pw - 1;
            if ((j == 9) || (j == 7) || (j == 8))
            {
                if (j == 7) {
                    this.al = 1;
                }
                this.ppang = 1;
                decs_e_hp(10);
            }
        }
        this.pw_up = -1;
        if (this.wp != 0) {
            this.wp = 0;
        }
    }

    public void startThread()
    {
        if (this.thread == null)
        {
            // this.thread = new Thread(this);
            // this.thread.start();
        }
    }

    public void goto_menu()
    {
        destroyImage(6);
        destroyImage(7);
        destroyImage(100);
        loadImage(2); // MENU_SCREEN / INSTRUCTION_SCREEN = 2
        this.m_mode = 1;
        this.screen = 2;
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

    // int1 int2 seem to be hero_x, hero_y (h_x, h_y)
    // Init h_x/y 57, 46
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
            // Go up/down -> Shop/ Southern boy, Western Boy
            if (((paramInt1 == 43) || (paramInt1 == 71)) && (paramInt2 >= 15) && (paramInt2 <= 71))
            {
                check_building(paramInt1, paramInt2);
                return 1;
            }
            return 0;
        }
        return 1;
    }

    public int input_item(int paramInt)
    {
        for (int i = 0; i < 5; i++) {
            if ((this.item_slot[i] == paramInt) && (paramInt <= 8)) {
                return 3;
            }
        }
        for (int j = 0; j < 5; j++) {
            if (this.item_slot[j] == 0)
            {
                this.item_slot[j] = paramInt;
                if (paramInt == 1) {
                    this.item_a_num = 2;
                } else if (paramInt == 2) {
                    this.item_b_num = 2;
                } else if (paramInt == 3) {
                    this.item_c_num = 2;
                } else if (paramInt == 4) {
                    this.item_d_num = 2;
                }
                return 1;
            }
        }
        return 0;
    }

    public void use_item(int itemMode)
    {
        if ((this.item_slot[itemMode] > 0) && (this.item_slot[itemMode] <= 4))
        {
            this.wp = this.item_slot[itemMode];
            if (this.wp == 1)
            {
                this.item_a_num -= 1;
                if (this.item_a_num == 0) {
                    delete_item(1);
                }
            }
            else if (this.wp == 2)
            {
                this.item_b_num -= 1;
                if (this.item_b_num == 0) {
                    delete_item(2);
                }
            }
            else if (this.wp == 3)
            {
                this.item_c_num -= 1;
                if (this.item_c_num == 0) {
                    delete_item(3);
                }
            }
            else if (this.wp == 4)
            {
                this.item_d_num -= 1;
                if (this.item_d_num == 0) {
                    delete_item(4);
                }
            }
            this.d_gauge = 2;
        }
        else if (this.item_slot[itemMode] == 5)
        {
            delete_item(5);
            this.hp += this.max_hp / 3;
            if (this.hp > this.max_hp) {
                this.hp = this.max_hp;
            }
            this.h_timer_p = -4;
        }
        else if (this.item_slot[itemMode] == 6)
        {
            delete_item(6);
            this.mana += 10;
            if (this.mana > 36) {
                this.mana = 36;
            }
            this.d_gauge = 1;
        }
        else if (this.item_slot[itemMode] == 7)
        {
            delete_item(7);
            this.hp = this.max_hp;
            this.h_timer_p = -4;
        }
        else if (this.item_slot[itemMode] == 8)
        {
            delete_item(8);
            this.hp += this.max_hp / 3;
            if (this.hp > this.max_hp) {
                this.hp = this.max_hp;
            }
            this.h_timer_p = -4;
            this.ppang_item = 0;
            this.ppang_time = 0;
        }
        this.item_mode = 100;
        MPlay(1);
    }

    public void delete_item(int paramInt)
    {
        for (int i = 0; i < 5; i++) {
            if (this.item_slot[i] == paramInt)
            {
                this.item_slot[i] = 0;
                this.del = i;
                return;
            }
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
    public void keyPressed() // int paramInt J2ME simulate from virtual Droid/iOS keyboard
    {
        // getGameAction(0);
        int paramInt = this.key_code;

        int i;
        int j;
        if ((this.screen == 6) && (this.state == 1)) // RUNNING
        {
            // this.dbg(" isTouchedNum3 " + isTouchedNum3() + " getGameAction(paramInt) " + getGameAction(paramInt));
            if ((getGameAction(paramInt) == 2) || (paramInt == 52))  // NUM_4 or left key
            {
                if ((this.item_mode == 0) && (this.ppang_item != 2))
                {
                    if (this.h_x != 2)
                    {
                        this.h_x -= 1;
                        if (this.h_idx == 0) {
                            this.h_idx = 1;
                        } else if (this.h_idx == 1) {
                            this.h_idx = 0;
                        }
                    }
                }
                else if (this.item_mode != 0)
                {
                    if (this.item_mode != 1) {
                        this.item_mode -= 1;
                    }
                    this.message = "Item Mode";
                    repaint();
                }
            }
            else if ((getGameAction(paramInt) == 5) || (paramInt == 54))  // NUM_6 or RIGHT_KEY (may be LEFT_KEY by keyboard view)
            {
                if ((this.item_mode == 0) && (this.ppang_item != 2))
                {
                    if (this.h_x != 23)
                    {
                        this.h_x += 1;
                        if (this.h_idx == 0) {
                            this.h_idx = 1;
                        } else if (this.h_idx == 1) {
                            this.h_idx = 0;
                        }
                    }
                }
                else if (this.item_mode != 0)
                {
                    if (this.item_mode != 5) {
                        this.item_mode += 1;
                    }
                    this.message = "Item Mode";
                    repaint();
                }
            }
            else if ((getGameAction(paramInt) == 6) || (paramInt == 56)) // GAME_ACTION_DOWN = 6  // NUM_8 or DOWN KEY
            {
                if (this.mana >= 12) {
                    use_special();
                } else {
                    this.message = "Insufficient Mana";
                }
            }
            // KEY_UP = 50 (fire can be use up key), -5 = OK keycode
            // else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53) || isTouchedOK())
            else if ( ( (paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53) ) && !isTouchedNum3()) // OK, NUM_5 etc isTouchedUp()
            {
                if (this.item_mode == 0)
                {
                    if ((this.pw_up == 0) && (this.ppang_item != 2))
                    {
                        this.snow_pw = 0;
                        this.real_snow_pw = 0;
                        this.pw_up = 1;
                        this.h_idx = 2;
                    }
                    else if ((this.pw_up == 1) && (this.real_snow_pw > 0))
                    {
                        this.h_idx = 4;
                        make_attack();
                    }
                }
                else
                {
                    use_item(this.item_mode - 1);
                    this.gameOn = true;
                }
            }
            else if (((paramInt == 35) || (paramInt == -7)) && (this.game_state == 0)) // RIGHT_MENU, # (pound_key) => options|# action
            {
                this.m_mode = 1;
                this.gameOn = false;
                this.screen = 100;
                repaint();
                // this.dbg("††† m_mode " + this.m_mode + " screen " + this.screen + " on " + this.gameOn);
            }
            else if ((paramInt == 51) && (this.game_state == 0) || isTouchedNum3()) // ITEM_MODE (NUM_3)
            {

                i = 0;
                j = 0;
                while (i < 5)
                {
                    if (this.item_slot[i] != 0)
                    {
                        j = 1;
                        break;
                    }
                    i++;
                }
                if (j == 1)
                {
                    this.gameOn = false;
                    this.message = "Item Mode";
                    this.item_mode = 1;
                    repaint();
                }
                else
                {
                    this.message = "No Item";
                }
            }
        }
        else if (this.screen == 100)
        {
            if (getGameAction(paramInt) == 1)
            {
                if (this.m_mode == 1) {
                    this.m_mode = 5;
                } else {
                    this.m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6)  // GAME_ACTION_DOWN = 6
            {
                if (this.m_mode == 5) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
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
                if (this.m_mode == 3) {
                    this.s_play = 2;
                }
            }
            // GAME_ACTION_UP = 1; prev: 8
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {  // GAME_ACTION_UP = 1; prev:8
                if (this.m_mode == 2)
                {
                    goto_menu();
                }
                else
                {
                    if (this.m_mode == 5)
                    {
                        // this.SJ.destroyApp(true);
                        // this.SJ.notifyDestroyed();
                        Gdx.app.exit();
                        return;
                    }
                    if (this.m_mode == 1)
                    {
                        this.screen = 6;
                        if (this.item_mode == 0)
                        {
                            this.gameOn = true;
                        }
                        else
                        {
                            this.message = "Item Mode";
                            repaint();
                        }
                    }
                    else if (this.m_mode == 4)
                    {
                        this.screen = -5;
                    }
                }
            }
            repaint();
        }
        else if (this.screen == 2)
        {
            if (getGameAction(paramInt) == 1)
            {
                if (this.m_mode <= 1) {
                    this.m_mode = 4;
                } else {
                    this.m_mode -= 1;
                }
            }
            else if (getGameAction(paramInt) == 6)   // GAME_ACTION_DOWN = 6
            {
                if (this.m_mode >= 4) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            // GAME_ACTION_UP = 1; prev: 8
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || ((paramInt >= 49) && (paramInt <= 52)) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    this.m_mode = (paramInt - 48);
                }
                if (this.m_mode == 1) {
                    this.screen = -88;
                }
                if (this.m_mode == 3)
                {
                    this.screen = 4;
                    this.m_mode = 1;
                }
                if (this.m_mode == 2)
                {
                    this.m_mode = 1;
                    this.screen = 5;
                }
                if (this.m_mode == 4)
                {
                    // this.SJ.destroyApp(true);
                    // this.SJ.notifyDestroyed();
                    Gdx.app.exit();
                    return;
                }
            }
            repaint();
        }
        else if (this.screen == 3) // VILLAGE_SCREEN
        {
            if (getGameAction(paramInt) == 1 && (getGameAction(paramInt) == GAME_ACTION_UP)) // debug
            {
                j = this.h_y - 8;
                if (hero_move(this.h_x, j, 1) > 0) {
                    this.h_y = j;
                }
            }
            else if (getGameAction(paramInt) == 6)   // GAME_ACTION_DOWN = 6
            {
                j = this.h_y + 8;
                if (hero_move(this.h_x, j, 1) > 0) {
                    this.h_y = j;
                }
            }
            else if (getGameAction(paramInt) == 5)
            {
                i = this.h_x + 7;
                if (hero_move(i, this.h_y, 0) > 0) {
                    this.h_x = i;
                }
            }
            else if (getGameAction(paramInt) == 2)
            {
                i = this.h_x - 7;
                if (hero_move(i, this.h_y, 0) > 0) {
                    this.h_x = i;
                }
            }
            // GAME_ACTION_UP = 1; prev:8; messing Action vs Action2
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7))   // GAME_ACTION_UP = 1; prev:8 ;
            {
                // m_mode = 0;
                if ((this.m_mode == 0) || (this.m_mode == 1))
                {
                    loadImage(31);  // SHOP_SCREEN = 31
                    this.screen = 31;  // SHOP_SCREEN = 31
                    repaint();
                }
                else if ((this.m_mode >= 2) && (this.m_mode <= 5))
                {
                    int k = -1;
                    this.last_stage = (this.last_stage > 45) ? 45 : this.last_stage;
                    if ((this.last_stage / 10 - this.school == 0) && (this.last_stage != 45)) {
                        k = this.last_stage;
                    }
                    destroyImage(3);
                    this.message = "Loading";
                    init_game(k);
                }
            }
            else if ((paramInt == 42) || (paramInt == -6))
            {
                destroyImage(3);
                loadImage(2);
                this.screen = 2;  // MAIN_MENU_SCREEN / INSTRUCTION
                this.m_mode = 1;
            }
            repaint();
        } // END cond VILLAGE_SCREEN
        else if (this.screen == 31)
        {
            if (getGameAction(paramInt) == 1) {
                this.s_item = 0;
            }
            if (getGameAction(paramInt) == 6) {    // GAME_ACTION_DOWN = 6
                this.s_item = 1;
            }
            if (getGameAction(paramInt) == 2)
            {
                if (this.b_item != 0) {
                    this.b_item -= 1;
                }
            }
            else if ((getGameAction(paramInt) == 5) && (this.b_item != 3)) {
                this.b_item += 1;
            }
            // TODO investigate all getGameAction vs getGameAction2
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {   // GAME_ACTION_UP = 1; pre8
                if (this.s_item == 1)
                {
                    this.m_mode = -1;
                    destroyImage(31);
                    this.screen = 3; // VILLAGE
                }
                else if (this.s_item == 0)
                {
                    i = 0;
                    if (this.m_mode == 0) {
                        i = 4;
                    }
                    if (this.saved_gold >= this.item_price[(this.b_item + i)])
                    {
                        j = input_item(this.b_item + i + 1);
                        if (j == 1)
                        {
                            this.saved_gold -= this.item_price[(this.b_item + i)];
                            this.message = "Purchasing Items";
                        }
                        else if (j == 0)
                        {
                            this.message = "Bag is full";
                        }
                        else if (j == 3)
                        {
                            this.message = "Duplicated item";
                        }
                    }
                    else
                    {
                        this.message = "not enough gold";
                    }
                }
            }
            repaint();
        }
        else if (this.screen == 4)
        {
            if ((paramInt == 42) || (paramInt == -6))
            {
                this.screen = 2;
                if (this.speed == 5) {
                    this.game_speed = 8;
                }
                if (this.speed == 4) {
                    this.game_speed = 17;
                }
                if (this.speed == 3) {
                    this.game_speed = 24;
                }
                if (this.speed == 2) {
                    this.game_speed = 31;
                }
                if (this.speed == 1) {
                    this.game_speed = 38;
                }

                setGameSpeed(speed);
                addScore("config", 1);
                this.m_mode = 3;
            }
            if (getGameAction(paramInt) == 1) {
                if (this.m_mode == 1) {
                    this.m_mode = 3;
                } else {
                    this.m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {    // GAME_ACTION_DOWN = 6
                if (this.m_mode == 3) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            if (this.m_mode == 1)
            {
                if (getGameAction(paramInt) == 2) {
                    this.s_play = 1;
                }
                if (getGameAction(paramInt) == 5) {
                    this.s_play = 2;
                }
            }
            if (this.m_mode == 2)
            {
                if (getGameAction(paramInt) == 2) {
                    this.v_mode = 1;
                }
                if (getGameAction(paramInt) == 5) {
                    this.v_mode = 2;
                }
            }
            if (this.m_mode == 3)
            {
                if ((getGameAction(paramInt) == 2) && (this.speed != 1)) {
                    this.speed -= 1;
                }
                if ((getGameAction(paramInt) == 5) && (this.speed != 5)) {
                    this.speed += 1;
                }
            }
            repaint();
        }
        else if (this.screen == 5)
        {
            if (getGameAction(paramInt) == 1) {
                if (this.m_mode == 1) {
                    this.m_mode = 3;
                } else {
                    this.m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {    // GAME_ACTION_DOWN = 6
                if (this.m_mode == 3) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            // GAME_ACTION_UP = 1; pre8
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == 51) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    this.m_mode = (paramInt - 48);
                }
                if (this.m_mode == 1) {
                    this.screen = -33;
                }
                if (this.m_mode == 2) {
                    this.screen = -33;
                }
                if (this.m_mode == 3) {
                    this.screen = -33;
                }
            }
            if ((paramInt == 42) || (paramInt == -6))
            {
                this.screen = 2;
                this.m_mode = 2;
            }
            repaint();
        }
        else if (this.screen == -5) // MANUAL
        {
            // this.screen = 100;
            repaint();
        }
        else if (this.screen == -88)  // NEW_GAME_MENU_SCREEN
        {
            if (getGameAction(paramInt) == 1) {
                if (this.m_mode <= 1) {
                    this.m_mode = 2;
                } else {
                    this.m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {    // GAME_ACTION_DOWN = 6
                if (this.m_mode >= 2) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            // GAME_ACTION_UP = 1; pre8
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    this.m_mode = (paramInt - 48);
                }
                if (this.m_mode == 1) // this one seem on INIT() or new game (vs load saved game)
                {
                    this.last_stage = 11;
                    this.stage = 11;
                    this.saved_gold = 400; // nickfarrow
                    this.mana = 0;
                    // addScore("hero", 0);
                }
                destroyImage(2);
                loadImage(3); // VILLAGE, imgCh -> hero_icon
                this.imgCh = this.imgHeroIcon; // nickfarrow temp fix
                this.h_x = 57;
                this.h_y = 46;
                this.m_mode = -1;
                this.screen = 3;
            }
            if ((paramInt == 42) || (paramInt == -6))
            {
                this.screen = 2;
                this.m_mode = 1;
            }

            repaint();
        }
        else if (this.screen == -33) // LIST SCREEN
        {
            if ((paramInt == 42) || (paramInt == -6))
            {
                loadImage(2);
                this.m_mode = 1;
                this.screen = 5;
                repaint();
                System.gc();
            }
        }
        else if (this.screen == 300)
        {
            MPlay(3);
            this.m_mode = -1;
            destroyImage(2);
            loadImage(3);
            this.h_x = 57;
            this.h_y = 46;
            this.saved_gold += this.gold;
            // addScore("hero", 0);
            this.ani_step = 0;
            this.screen = 3;
            // TODO may be reset imgCh to imgHeroIcon
            repaint();
            System.gc();
        }
        else if (this.screen == -1) // TODO use constant // Logo start Screen LOGO_SCREEN
        {
            loadImage(-2);
            this.screen = -2;
            repaint();
        }
        else if (this.screen == -2) // SAMSUNG_FUNCLUB_SCREEN
        {
            loadImage(2);
            this.screen = 1;
            repaint();
        }
        else if (this.screen == 1)
        {
            if ((paramInt == 35) || (paramInt == -7))
            {
                this.screen = -88; // NEW_GAME_MENU_SCREEN
                this.m_mode = 1;
            }
            // https://j2me.fandom.com/wiki/Canvas_KeyCodes
            // -6 = CK (Center key = OK ?)
            // temporary use key_code as KEmulator
            else if ( (paramInt == 42) || (paramInt == -6) || (paramInt == -5) || (getGameAction(paramInt) == GAME_ACTION_OK) )
            {
                this.screen = 2;
            }
            repaint();
        }
        else if (this.screen == 1000) // ALL_CLEAR
        {
            loadImage(2);
            System.gc();
            this.screen = 300;
            repaint();
        }

    }

    // TODO use best (I forgot tool) for convert mmf to midi and/or to mp3.
    // Default FormatFactory seem to lost some track of original sound.
    public void MPlay(int paramInt) {
        if (paramInt == 0) {
            // str = "data/audio/night_opening.wav"; // 9.mmf
            // this.playSound("9", 0, true);
            // this.playSound("Donald_Christmas", 0, true);
        } else if (paramInt == 1) {
            //str = "data/audio/1_use_item.wav"; // /1.mmf
            this.playSound("1_use_item", 0, false);
        } else if (paramInt == 2) {
            //str = "data/audio/6_snow_fly.wav"; // /6.mmf
            this.playSound("6_snow_fly", 0, false);
        } else if (paramInt == 3) {
            //str = "data/audio/5_hit.wav"; // /5.mmf
            this.playSound("5_hit", 0, false);
        } else if (paramInt == 4) {
            //str = "data/audio/4_oh_oh.wav"; // /4.mmf
            this.playSound("4_oh_oh", 0, false);
        } else if (paramInt == 5) {
            //str = "data/audio/special.mp3"; // /8.mmf
            this.playSound("special", 0, true);
        } else if (paramInt == 6) {
            //str = "data/audio/lose.mp3"; // /3.mmf
            this.playSound("lose", 0, true);
        } else if (paramInt == 7) {
            //str = "data/audio/victory.mp3"; // 0.mmf
            this.playSound("victory", 0, true);
        } else if (paramInt == 10) {
            this.playSound("Donald_Christmas", 0, true);
        }
    }

    // TODO make plasma, missile sound louder use tool like Audacity. But low volume may be good effect simulate explosion far away
    public void playSound(String paramString, int paramInt, boolean isMP3)
    {
        this.current_music = paramString; // Use for stop sound

        if (this.af == 0) {
            return;
        }
        if (this.music != null)
        {
        }
        try
        {
            if(this.music != null){
                // this.music.dispose();
                this.music = null;
            }
            this.music_opening = null;

            if(!isMP3) {
                this.manager.get("data/audio/"+ paramString + ".wav", Sound.class).play();
            } else {
                this.manager.get("data/audio/"+ paramString + ".mp3", Sound.class).play();
            }
        }
        catch (Exception localException)
        {
            System.out.println("Error play sound");
        }
    }

    public void stopSound()
    {
        if (this.music != null)
        {
            this.music.stop();
            this.music = null;
        }
        // It seem only music can stop, sound only play one ?
        // this.manager.get("audio/"+ this.current_music + ".wav", Sound.class).play();
    }


    public void call_vib(int paramInt)
    {
        if (this.v_mode == 1) {
//      Vibration.start(paramInt, 3);
        }
    }

    public void showNotify()
    {
        this.p_mode = 1;
        this.d_gauge = 2;
    }

    private static class GameScreens {
        public static final String BAR_VALUE = "BAR";
        public static final int LOGO_SCREEN = -1;
        public static final int SAMSUNG_FUNCLUB_SCREEN = -2;
        public static final int MANUAL_SCREEN = -5;
        public static final int DRAW_LIST_SCREEN = -33;
        public static final int NEW_GAME_MENU_SCREEN = -88;

        public static final int TITLE_MENU_SCREEN = 1;
        public static final int INSTRUCTION_SCREEN = 2;  // MAIN_MENU_SCREEN
        public static final int VILLAGE_SCREEN = 3;
        public static final int SOUND_SPEED_SETTING_SCREEN = 4;
        public static final int GUIDE_MENU_SCREEN = 5;
        public static final int RUNNING_SCREEN = 6;

        public static final int SPECIAL_ANIMATION_SCREEN = 8;
        public static final int SPECIAL_SCREEN = 9;
        public static final int SHOP_SCREEN = 31;
        public static final int TEXT_SCREEN = 77;

        public static final int SOUND_SETTING_SCREEN = 100;
        public static final int VICTORY_SCREEN = 200;
        public static final int VICTORY_SCREEN2 = 201;
        public static final int GOD_JOB_SCREEN = 300;

        public static final int ALL_CLEAR_SCREEN = 1000;
        public static final int LOSE_SCREEN = 65335;
        public static final int LOSE_SCREEN2 = 65336;
    }

    // Game mode and menu mode ...
    private static class GameMode {
        public static final int DRUG_STORE = 0;
        public static final int ITEM_SHOP = 1;
        public static final int EASTERN_BOY = 2;
        public static final int SOUTHERN_BOY = 3;
        public static final int WESTERN_BOY = 4;
        public static final int NORTHERN_BOY = 5;
        public static final int NO_ADMITTANCE = 100;

        //this.m_mode = (paramInt - 48);
    }

    // Dump area
}
