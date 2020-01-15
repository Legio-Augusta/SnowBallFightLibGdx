package com.littlewing.sbf.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;

import java.util.Random;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.littlewing.sbf.game.OverlapTester;
import com.littlewing.sbf.game.J2ME_API_Port;

// Original J2ME with new API function porting

/** TODO
 * Biggest issues: game gesture, user input;
 * - migrate old physical keyboard meme to new touch screen
 * - new screen size
 * - long time issue with button area, hint/hight light for easy of use
 * - iOS
 * - Ads and/or payment
 */

/**
 * Responsive design pain in the ass. fixxed position of button.
 * The original version is for fixxed size screen.
 * only way for handle million device is if/else ?
 * https://qph.fs.quoracdn.net/main-qimg-4f8b79be77bb76e9103a8ee0e6c35f2b
 * viewport vs pixel
 * mi note 7 1080 x 2340
 * 1200 x 1920 (Nexus 7) 600x960
 * 1440 x 2960 (S9) 360x740
 * S7 1440 x 2560 (old remember this guy when it can change Resolution setin) 360x640
 */
public class GameScreen extends DefaultScreen implements InputProcessor {
    private static final int DEFAULT_DEM = 12;
    // RecordStore recordStore = null;
    private int game_state = 0;
    private int saved_gold = 1000; // orig.10
    private int speed = 1; // orig.4
    private int game_speed = 5; // orig.17
    private Random rnd = new Random();
    // private SnowBallFightME SJ;
    private Thread thread = null;
    private int screen = -1;
    private boolean gameOn = true;
    private String message;
    private int m_mode = 1;
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
    private Texture[] imgNum;
    private Texture imgBack;
    private Texture[] imgHero;
    private Texture[] imgEnemy;
    private Texture[] imgBoss;
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
    private Texture[] imgSpecial;
    private Texture imgSp;
    private Texture[] imgEffect;
    private Texture imgVictory;
    private Texture imgV;
    private Texture imgHero_v;
    private Texture imgLose;
    private Texture imgHero_l;
    private Texture imgStage_num;
    private Texture ui; // Customized

    private Texture[] imgStage;
    private int stage;
    private int last_stage = 31; // orig.11
    private int tmp_stage;
    private int school;
    private int state;
    private int h_x;
    private int h_y;
    private int h_idx;
    private int h_timer;
    private int h_timer_p;
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

    public J2ME_API_Port helper = new J2ME_API_Port();

    private static float MOBI_SCL = (float)Gdx.graphics.getWidth()/240; // FIXME 4.5 is not integer
    private static int MOBI_H = 320;  // JavaME height = 320px
    private static int MOBI_W = 240; // Original Java Phone resolution.
    float SCALE = (float)SCREEN_HEIGHT/1920;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture fireBtnTexture;

    // Ratio 3:4 ~ 9:12 So with ratio 9:16 we lost (not use) 4/16 = 1/4 of height.
    // Ie. 1920 we will cut 1/4 = 480px to keep ratio 3:4 1080:1440.
    // Bottom space used for fireBtn, so top should space only 240px
    private static int SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    private static int BOTTOM_SPACE = (int)(SCREEN_HEIGHT/8 + 20*MOBI_SCL); // May be change for fit touch button

    // Use rectangle until figure out how to work with BoundingBox multi input.
    Rectangle upBtnRect = new Rectangle((20+(200/3))*SCALE, (20+(400/3))*SCALE, 72*SCALE, 70*SCALE);
    Rectangle downBtnRect = new Rectangle((20+(200/3))*SCALE, 20*SCALE, 72*SCALE, 70*SCALE);
    Rectangle leftBtnRect = new Rectangle(20*SCALE, (20+(200/6))*SCALE, 70*SCALE, 140*SCALE);
    Rectangle rightBtnRect = new Rectangle((20+(400/3))*SCALE, (20+(200/6))*SCALE, 2*70*SCALE, 140*SCALE);
    Rectangle optionBtnRect = new Rectangle(SCREEN_WIDTH/2+150*SCALE, SCREEN_HEIGHT/8, SCREEN_WIDTH/2-180*SCALE, 70*SCALE);
//    Rectangle leftMenuBtn = new Rectangle(SCREEN_WIDTH-(275+400)*SCALE, 20*SCALE, 200*SCALE, 100*SCALE);
    Rectangle leftMenuBtn = new Rectangle(SCREEN_WIDTH-(275+400)*SCALE, 20*SCALE, 200*SCALE, 100*SCALE);
    Rectangle rightMenuBtn = new Rectangle(SCREEN_WIDTH-(275+200)*SCALE, 20*SCALE, 200*SCALE, 100*SCALE);

    private int game_action = 0;
    private int key_code = 0;
    private static final int GAME_ACTION_OK = 8; // simulate KEY, gameAction in J2ME
    private static final int GAME_ACTION_LEFT = 2;
    private static final int GAME_ACTION_RIGHT = 5;
    private static final int GAME_ACTION_UP = 8;
    private static final int GAME_ACTION_DOWN = 6;
    private static final int KEY_RIGHT_MENU = -7; // action = 0
    private static final int KEY_LEFT_MENU = -6;  // action = 0
    private static final int KEY_OK = -5;

    Vector3 touchPoint;
    TouchStatus touchStatus = TouchStatus.NONE;

    enum TouchStatus {
        TOUCH_DOWN, TOUCH_UP, NONE
    }

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
    BitmapFont font;
    private Music music;
    Viewport viewport;

    public GameScreen(Game game)
    {
        super(game);
        // this.SJ = paramSnowBallFight;
        this.item_price[0] = 5;
        this.item_price[1] = 8;
        this.item_price[2] = 8;
        this.item_price[3] = 14;
        this.item_price[4] = 6;
        this.item_price[5] = 12;
        this.item_price[6] = 10;
        this.item_price[7] = 12;
//        printScore("hero", 0);
//        printScore("config", 1);
        this.item_slot[0] = 3;
        this.item_slot[1] = 5;
        this.stage = this.last_stage;

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey( true );

        // Calculate global var width/height, view port ...
        create();

        touchPoint = new Vector3();

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
        // this.key_code = 0;
        batch.begin();

        touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);

        Gdx.app.log("DEBUG", "touch " + touchPoint.x + " y "+ (SCREEN_HEIGHT-touchPoint.y) + " key_code "+ this.key_code + " scrn "+ this.screen);
        game_action = getGameAction2(pointer);

        if (isTouchedMenuLeft()) {
            this.key_code = KEY_LEFT_MENU;
            Gdx.input.vibrate(5);
        } else if (isTouchedMenuRight()) {
            this.key_code = KEY_RIGHT_MENU;
            Gdx.input.vibrate(5);
        } else if (isTouchedNum3()) {
            this.key_code = 57;
        }

        Gdx.input.vibrate(5);
        this.key_code = 57;

        keyPressed();
        batch.end();

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // if(isTouchedMenuLeft() || isTouchedMenuRight() || isTouchedOK() || isTouchedUp() || isTouchedDown() || isTouchedLeft() || isTouchedRight()) {
        if(isTouchedLeft() || isTouchedRight()) {
            // Only apply tricky way on fighting scene
//            if(this.archAngel.screen == 25) {
//                if (this.archAngel.mainGameScreen.gamestage1 == 1) {
                    // normal play not boss scene
                    // Use key_code 53 (NUM5 ~ fire) for clear key_code, action LEFT
                    this.key_code = 53; // Fix me This is tricky way to implement touch & hold
                    keyPressed();
                    this.game_action = 0;
//                }
//            }
        }

        if(isTouchedUp() || isTouchedDown()) {
//            if(this.archAngel.mainGameScreen.gamespeed <= 140 && this.archAngel.mainGameScreen.gamespeed >= 20) {
//                if (this.archAngel.mainGameScreen.gamestage1 == 1) {
                    // normal play not boss scene
                    // Use key_code 53 (NUM5 ~ fire) for clear key_code, action LEFT
                    this.key_code = 53; // Fix me This is tricky way to implement touch & hold
                    keyPressed();
                    this.game_action = 0;
//                }
//            }
        }

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

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = SCREEN_WIDTH/2;
        camera.position.y = SCREEN_HEIGHT/2;
        camera.update();

        batch.enableBlending();
        batch.begin();

        // run();

        // drawTouchPad();
        drawUI();
        batch.end();
    }

    @Override
    public void hide() {

    }

    public void create () {
        batch = new SpriteBatch();

        //Create camera
        float aspectRatio = (float) SCREEN_WIDTH / (float) SCREEN_HEIGHT;

        camera = new OrthographicCamera();
        // This seem take no effect on 16:9 multi-screen size 1280; 1920; or 2560px But on not 16:9, ie. 4:3 iPad this may take effect.
        int VP_WIDTH = 1080;
        int VP_HEIGHT = 1920;
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(VP_WIDTH, VP_HEIGHT, camera);
        viewport.apply();

        camera.position.x = SCREEN_WIDTH/2;
        camera.position.y = SCREEN_HEIGHT/2;
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        camera.update();

        Gdx.input.setInputProcessor(this); // TODO use an InputProcessor object

        loadTextures();
        // this.sbfme.startApp();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);
        Gdx.app.log("DEBUG", " on create ");
    }

    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    protected boolean isTouchedUp() {
        this.key_code = -1;
        return OverlapTester.pointInRectangle(upBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedDown() {
        this.key_code = -2;
        return OverlapTester.pointInRectangle(downBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedLeft() {
        this.key_code = -3;
        return OverlapTester.pointInRectangle(leftBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedRight() {
        this.key_code = -4;
        return OverlapTester.pointInRectangle(rightBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedOption() {
        return OverlapTester.pointInRectangle(optionBtnRect, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedOK() {
        this.key_code = KEY_OK;
        Rectangle textureBounds = new Rectangle(SCREEN_WIDTH-(fireBtnTexture.getWidth()+50)*SCALE, SCREEN_HEIGHT-(50+fireBtnTexture.getHeight())*SCALE, fireBtnTexture.getWidth()*SCALE,fireBtnTexture.getHeight()*SCALE);
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }
    protected boolean isTouchedNum3() {
        Rectangle textureBounds=new Rectangle(SCREEN_WIDTH-(fireBtnTexture.getWidth()+50+imgKeyNum3.getWidth()+(int)fireBtnTexture.getWidth()/2)*SCALE, SCREEN_HEIGHT-(40+imgSpeedUp.getHeight()+imgKeyNum3.getHeight())*SCALE, imgKeyNum3.getWidth()*SCALE,imgKeyNum3.getHeight()*SCALE);
        return textureBounds.contains(touchPoint.x, touchPoint.y);
    }
    protected boolean isTouchedMenuLeft() {
        Gdx.app.log("DEBUG Clip", "x: " + leftMenuBtn.x + " y " + leftMenuBtn.y + " w " + leftMenuBtn.getWidth() + " h " + leftMenuBtn.getHeight());
        this.key_code = KEY_LEFT_MENU;
        return OverlapTester.pointInRectangle(leftMenuBtn, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
    }
    protected boolean isTouchedMenuRight() {
        this.key_code = KEY_RIGHT_MENU;
        return OverlapTester.pointInRectangle(rightMenuBtn, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) );
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

    public void empty_func() {}

    public void fillRect(SpriteBatch paramGraphics, int x, int y, int width, int height, int color) {
        // Hard code default width x height of color img: 12x12 px
        float scaleY = (float) (height*MOBI_SCL / 12);
        float scaleX = (float) (width*MOBI_SCL / 12);
        // (Texture, float x, float destroy_n_e, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
        int pos_x = (int) (MOBI_SCL*x);
        int pos_y = (int) ((MOBI_H - y)*MOBI_SCL - imgColor[color].getHeight()*scaleY + BOTTOM_SPACE);

        paramGraphics.draw(imgColor[color], pos_x, pos_y, 0, 0, imgColor[color].getWidth(), imgColor[color].getHeight(), scaleX, scaleY, 0, 0, 0, (int)(imgColor[color].getWidth()*scaleX), (int)(imgColor[color].getHeight()*scaleY), false, false);
    }

    /**
     *
     * drawImage2(paramGraphics, this.img_arr_a[paramInt1], paramInt2, paramInt3, 20);
     *
     * @param paramGraphics
     * @param spriteIdx image sprite index
     * @param pos_x positon x (240x320 J2ME geometry)
     * @param pos_y positon y
     * 20 => anchor point; It seem this gap is reserved for phone top bar: signal strength, datetime ...
     */
    public void drawImage2(SpriteBatch paramGraphics, Texture image, int spriteIdx, int pos_x, int pos_y)
    {
        // 53 as BOTTOM_SPACE (~ 240 = 480/2) 240/4.5 ~= 53 (tile cell)
        int img_height = (int)(image.getHeight()*SCALE);
        int position_y = (int) ((MOBI_H - pos_y-20)*MOBI_SCL - img_height + BOTTOM_SPACE); // anchor 20

        // Fix me hard code position
        // if(spriteIdx == 3) {} // && (paramInt1 == 3) // this.msr_media.equals("font")
        paramGraphics.draw(image, (int)(pos_x*MOBI_SCL), position_y, image.getWidth()*SCALE, image.getHeight()*SCALE); // 20 anchor
    }

    public void drawImage(SpriteBatch paramGraphics)
    {
        // paramGraphics.setClip(0, 0, 240, 320);
        // this.drawImage2(paramGraphics,  3, 0, 300); // double check 3 is index
        // Can not use for now since now Array Of Images has been build.
    }

    public void drawRect(int x, int y, int width, int height, int color) {

    }

    public void drawRect(SpriteBatch paramGraphics, int x, int y, int width, int height, int color)
    {
    }

    public void drawString(SpriteBatch paramGraphics,  String paramString, int paramInt1, int paramInt2, int paramInt3, int color)
    {
        if(paramString == null) { // FIXME
            return;
        }

        int position_y = (int) ((MOBI_H-paramInt2-20)*MOBI_SCL + BOTTOM_SPACE); // anchor 20
        switch(paramInt3) {
            case 1:
                font.setColor(1, 0, 0, 1); // red
                break;
            default:
                font.setColor(1, 1, 1, 1);
                break;
        }
        font.draw(paramGraphics, paramString, (int)(paramInt1 * MOBI_SCL), position_y);

        for (int k = 0; k < paramString.length(); k++) // NAME: \n AZ 1 \n DAMAGE: 30MP
        {
            int j = paramString.charAt(k);
            if ((j >= 48) && (j <= 90))
            {
                int i = j - 48;
                // Clever way to draw ASCII from integer position and sprite-pack crop area
                // mySetClip(paramGraphics, paramInt1 + 6 * k, paramInt2, 5, 5); // font_00.png 215 x 5 ?
                // Gdx.app.log("DEBUG Clip", "int1: " + paramInt1 + " k " + k + " int 2 = "+paramInt2 + " index " + i + " Msg " + paramString);
                // TODO use GDX Title or text.
                // drawImageAnchor20(paramGraphics, 0, paramInt1 + 6 * k - i * 5, paramInt2);
                // ENERMY:null
                // myDrawClip(paramGraphics, i, paramInt2, 5, 5, 0, paramInt1 + 6 * k - i * 5, paramInt2, 20);
            }
        }
        // mySetClip(paramGraphics, 0, 0, 240, 320);
    }

    public void drawString(SpriteBatch paramGraphics, String paramString, int paramInt1, int paramInt2, int paramInt3)
    {
        if(paramString == null) { // FIXME
            return;
        }

        int position_y = (int) ((MOBI_H-paramInt2-20)*MOBI_SCL + BOTTOM_SPACE); // anchor 20
        switch(paramInt3) {
            case 1:
                font.setColor(1, 0, 0, 1); // red
                break;
            default:
                font.setColor(1, 1, 1, 1);
                break;
        }
        font.draw(paramGraphics, paramString, (int)(paramInt1 * MOBI_SCL), position_y);
    }

    /*
     * Simulate J2ME keyCode
     * https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/constant-values.html#javax.microedition.lcdui.Canvas.UP
     * */
    public int getGameAction(int keyCode) {
        return game_action;
    }

    public int getGameAction2(int pointer) {
        if(isTouchedUp()) {
            Gdx.input.vibrate(5);
            this.key_code = -1;
            return GAME_ACTION_UP;
        }
        if(isTouchedDown()) { // Careful with game state, ie. item_mode = 0
            Gdx.input.vibrate(5);
            this.key_code = -2;
            return GAME_ACTION_DOWN;
        }
        if(isTouchedLeft() && Gdx.input.isTouched()) {
            Gdx.input.vibrate(5);
            this.key_code = -3;
            return GAME_ACTION_LEFT;
        }
        if(isTouchedRight()) {
            this.key_code = -4;
            Gdx.input.vibrate(5);
            return GAME_ACTION_RIGHT;
        }

        if(isTouchedOK()) {
            this.key_code = KEY_OK; // -5
            Gdx.input.vibrate(5);
            return GAME_ACTION_OK;
        }

        return 0;
    }

    protected void drawUI() {
        // It seem single image can have it's own event Listener such as: touchDown/Up; See bellow
        // https://github.com/BrentAureli/ControllerDemo/blob/master/core/src/com/brentaureli/overlaydemo/Controller.java
        // TODO use custom IMAGE addEventListener for more UI refine. Can image used as Texture ?
        batch.draw(fireBtnTexture, SCREEN_WIDTH-(50+fireBtnTexture.getWidth())*SCALE, (int)(50*SCALE), fireBtnTexture.getWidth()*SCALE, fireBtnTexture.getHeight()*SCALE);
        batch.draw(imgKeyNum3, SCREEN_WIDTH-(50+fireBtnTexture.getWidth()+fireBtnTexture.getWidth()/2 + imgKeyNum3.getWidth())*SCALE, (40 + imgSpeedUp.getHeight())*SCALE, imgKeyNum3.getWidth()*SCALE, imgKeyNum3.getHeight()*SCALE);
        batch.draw(imgSpeedUp, SCREEN_WIDTH-(50+fireBtnTexture.getWidth()+fireBtnTexture.getWidth()/2 + imgSpeedUp.getWidth())*SCALE, 20*SCALE, imgSpeedUp.getWidth()*SCALE, imgSpeedUp.getHeight()*SCALE);
        batch.draw(imgSpeedDown, SCREEN_WIDTH-(50+fireBtnTexture.getWidth()+fireBtnTexture.getWidth()/2 + 2*imgSpeedDown.getWidth())*SCALE, 20*SCALE, imgSpeedDown.getWidth()*SCALE, imgSpeedDown.getHeight()*SCALE);
        batch.draw(touch_pad, 20*SCALE, 20*SCALE, touch_pad.getWidth()*SCALE, touch_pad.getHeight()*SCALE);
        batch.draw(touch_pad_knob, (20+touch_pad.getWidth()/2-touch_pad_knob.getWidth()/2)*SCALE, (20+touch_pad.getHeight()/2-touch_pad_knob.getHeight()/2)*SCALE, touch_pad_knob.getWidth()*SCALE, touch_pad_knob.getHeight()*SCALE);
    }

    private void loadTexturesOld() {
        fireBtnTexture = new Texture("data/samsung-white/fire.png");

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
        imgSpeedUp = new Texture("data/samsung-white/right_btn.png");
        imgSpeedDown = new Texture("data/samsung-white/left_btn.png");
        touch_pad = new Texture("data/gui/touchBackground.png");
        touch_pad_knob = new Texture("data/gui/touchKnob.png");
    }

    private void loadTextures() {
        fireBtnTexture = new Texture("data/samsung-white/fire.png");

        if(school <= 0) {
            school = 1;
        }
        imgBack = new Texture("data/samsung-white/back"+school+".png");
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

        if(tmp_stage <= 8) {
            tmp_stage = 1;
        }
        imgStage_num = new Texture("data/samsung-white/stage"+ tmp_stage + ".png"); // tmp_stage +
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

    // end Android GDX

    public void addScore(String paramString, int paramInt)
    {
        /*
        try
        {
            this.recordStore = RecordStore.openRecordStore(paramString, true);
            ByteArrayOutputStream localByteArrayOutputStream;
            DataOutputStream localDataOutputStream;
            if (this.recordStore.getNumRecords() == 0)
            {
                localByteArrayOutputStream = new ByteArrayOutputStream();
                localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
                try
                {
                    if (paramInt == 0)
                    {
                        int i = this.saved_gold * 10000 + this.last_stage * 100 + this.mana;
                        localDataOutputStream.writeInt(i);
                    }
                    else if (paramInt == 1)
                    {
                        localDataOutputStream.writeInt(this.speed);
                    }
                    byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
                    this.recordStore.addRecord(arrayOfByte1, 0, arrayOfByte1.length);
                }
                catch (Exception localException2) {}
            }
            else
            {
                localByteArrayOutputStream = new ByteArrayOutputStream();
                localDataOutputStream = new DataOutputStream(localByteArrayOutputStream);
                try
                {
                    if (paramInt == 0)
                    {
                        int j = this.saved_gold * 10000 + this.last_stage * 100 + this.mana;
                        localDataOutputStream.writeInt(j);
                    }
                    else if (paramInt == 1)
                    {
                        localDataOutputStream.writeInt(this.speed);
                    }
                    byte[] arrayOfByte2 = localByteArrayOutputStream.toByteArray();
                    this.recordStore.setRecord(1, arrayOfByte2, 0, arrayOfByte2.length);
                }
                catch (Exception localException3) {}
            }
        }
        catch (Exception localException1) {}finally
        {
            try
            {
                this.recordStore.closeRecordStore();
            }
            catch (Exception localException6) {}
        }
        */
    }

    public void printScore(String paramString, int paramInt)
    {
        /*
        try
        {
            this.recordStore = RecordStore.openRecordStore(paramString, true);
            if (this.recordStore.getNumRecords() == 0)
            {
                if (paramInt == 0)
                {
                    this.last_stage = 31; // orig.11
                    this.saved_gold = 100; // orig.0
                    this.mana = 0;
                }
                else if (paramInt == 1)
                {
                    this.speed = 1; // orig.4
                }
            }
            else {
                try
                {
                    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(this.recordStore.getRecord(1));
                    DataInputStream localDataInputStream = new DataInputStream(localByteArrayInputStream);
                    if (paramInt == 0)
                    {
                        int i = localDataInputStream.readInt();
                        this.last_stage = (i % 10000 / 100);
                        this.saved_gold = (i / 10000);
                        this.mana = (i % 100);
                    }
                    else if (paramInt == 1)
                    {
                        this.speed = localDataInputStream.readInt();
                    }
                }
                catch (Exception localException1) {}
            }
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
        }
        catch (Exception localException2) {}finally
        {
            try
            {
                this.recordStore.closeRecordStore();
            }
            catch (Exception localException5) {}
        }

        */
    }

    public void loadImage(int paramInt)
    {
        try
        {
            if (paramInt == 2)
            {
                this.imgMM = new Texture("data/samsung-white/mm.png");
                this.imgBk = new Texture("data/samsung-white/bk.png");
                this.imgSl = new Texture("data/samsung-white/sl.png");
                this.imgPl = new Texture("data/samsung-white/play.png");
                this.imgCh = new Texture("data/samsung-white/check.png");
            }
            else
            {
                int i;
                if (paramInt == 6)
                {
                    this.imgHero = new Texture[5];
                    this.imgEnemy = new Texture[4];
                    this.imgItem = new Texture[9];
                    this.imgItem_hyo = new Texture[2];
                    this.imgItem_hyo[0] = new Texture("data/samsung-white/hyo0.png");
                    this.imgItem_hyo[1] = new Texture("data/samsung-white/hyo1.png");
                    for (i = 0; i < 5; i++) {
                        this.imgHero[i] = new Texture("data/samsung-white/hero" + i + ".png");
                    }
                    if (get_random(2) == 0) {
                        for (int j = 0; j < 4; j++) {
                            this.imgEnemy[j] = new Texture("data/samsung-white/enemy0" + j + ".png");
                        }
                    } else {
                        for (int k = 0; k < 4; k++) {
                            this.imgEnemy[k] = new Texture("data/samsung-white/enemy1" + k + ".png");
                        }
                    }
                    for (int m = 0; m < 9; m++) {
                        this.imgItem[m] = new Texture("data/samsung-white/item" + m + ".png");
                    }
                    System.gc();
                    this.imgSnow_g = new Texture("data/samsung-white/snow_gauge.png");
                    this.imgPwd = new Texture("data/samsung-white/power.png");
                    this.imgShadow = new Texture("data/samsung-white/shadow0.png");
                    this.imgPok = new Texture("data/samsung-white/pok.png");
                    this.imgPPang = new Texture("data/samsung-white/bbang0.png");
                    this.imgPPang1 = new Texture("data/samsung-white/bbang1.png");
                    this.imgH_ppang = new Texture("data/samsung-white/h_bbang.png");
                    this.imgCh = new Texture("data/samsung-white/check.png");
                    this.imgAl = new Texture("data/samsung-white/al.png");
                    this.imgEffect = new Texture[2];
                    this.imgEffect[0] = new Texture("data/samsung-white/effect0.png");
                    this.imgEffect[1] = new Texture("data/samsung-white/effect1.png");
                }
                else if (paramInt == 100)
                {
                    this.imgBack = new Texture("data/samsung-white/back" + this.school + ".png");
                }
                else if (paramInt == 7)
                {
                    this.imgBoss = new Texture[4];
                    for (i = 0; i < 4; i++) {
                        this.imgBoss[i] = new Texture("data/samsung-white/boss" + this.e_boss + i + ".png");
                    }
                }
                else if (paramInt == -6)
                {
                    this.imgStage = new Texture[5];
                    for (i = 0; i < 5; i++) {
                        this.imgStage[i] = new Texture("data/samsung-white/word-" + i + ".png");
                    }
                    this.imgStage_num = new Texture("data/samsung-white/stage" + this.tmp_stage + ".png");
                }
                else if (paramInt == 8)
                {
                    this.imgSpecial = new Texture[3];
                    for (i = 0; i < 3; i++) {
                        this.imgSpecial[i] = new Texture("data/samsung-white/special" + i + ".png");
                    }
                    this.gameOn = true;
                }
                else if (paramInt == 9)
                {
                    this.imgSp = new Texture("data/samsung-white/sp" + this.special + ".png");
                }
                else if (paramInt == 3)
                {
                    this.imgVill = new Texture("data/samsung-white/village.png");
                    this.imgCh = new Texture("data/samsung-white/hero_icon.png");
                    this.imgSchool = new Texture("data/samsung-white/school.png");
                }
                else if (paramInt == 31)
                {
                    if (this.m_mode == 1) {
                        this.imgShop = new Texture("data/samsung-white/shop0.png");
                    }
                    if (this.m_mode == 0) {
                        this.imgShop = new Texture("data/samsung-white/shop1.png");
                    }
                }
                else if (paramInt == 200)
                {
                    this.imgVictory = new Texture("data/samsung-white/victory.png");
                    this.imgV = new Texture("data/samsung-white/v.png");
                    this.imgHero_v = new Texture("data/samsung-white/hero-vic.png");
                }
                else if (paramInt == 65336)
                {
                    this.imgLose = new Texture("data/samsung-white/lose.png");
                    this.imgHero_l = new Texture("data/samsung-white/hero-lose.png");
                }
                else if (paramInt == 1)
                {
                    this.imgNum = new Texture[10];
                    for (i = 0; i < 10; i++) {
                        this.imgNum[i] = new Texture("data/samsung-white/" + i + ".png");
                    }
                    this.imgLogo = new Texture("data/samsung-white/logo.png");
                }
            }
        }
        catch (Exception localException) {}
    }

    public void destroyImage(int paramInt)
    {
        if (paramInt == 1)
        {
            this.imgLogo = null;
        }
        else if (paramInt == 2)
        {
            this.imgMM = null;
            this.imgBk = null;
            this.imgPl = null;
            this.imgSl = null;
            this.imgCh = null;
        }
        else if (paramInt == 3)
        {
            this.imgVill = null;
            this.imgCh = null;
            this.imgSchool = null;
        }
        else if (paramInt == 31)
        {
            this.imgShop = null;
        }
        else if (paramInt == 6)
        {
            this.imgHero = null;
            this.imgEnemy = null;
            this.imgItem = null;
            this.imgSnow_g = null;
            this.imgPwd = null;
            this.imgShadow = null;
            this.imgPok = null;
            this.imgPPang = null;
            this.imgPPang1 = null;
            this.imgH_ppang = null;
            this.imgItem_hyo = null;
            this.imgCh = null;
            this.imgAl = null;
            this.imgEffect = null;
        }
        else if (paramInt == 100)
        {
            this.imgBack = null;
        }
        else if (paramInt == -6)
        {
            this.imgStage = null;
            this.imgStage_num = null;
        }
        else if (paramInt == 7)
        {
            this.imgBoss = null;
        }
        else if (paramInt == 8)
        {
            this.imgSpecial = null;
        }
        else if (paramInt == 9)
        {
            this.imgSp = null;
        }
        else if (paramInt == 200)
        {
            this.imgVictory = null;
            this.imgV = null;
            this.imgHero_v = null;
        }
        else if (paramInt == 65336)
        {
            this.imgLose = null;
            this.imgHero_l = null;
        }
        System.gc();
    }

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
        this.max_hp = 106;
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
        startThread();
        this.gameOn = true;
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
        if (paramInt < 0) {
            make_e_num(get_random(2) + 2, this.school);
        } else {
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

    public void paint(SpriteBatch paramGraphics)
    {
        int j;
        if (this.screen == 6)
        {
            helper.drawImage(paramGraphics, this.imgBack, 0, 0, 20);
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 25, 128, 84, 16777215);
            drawImage2(paramGraphics, this.imgHero[this.h_idx], this.h_x * 5, 83, 0x10 | 0x1);
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
                try
                {
                    drawImage2(paramGraphics, new Texture("/ui.png"), 0, 109, 20);
                }
                catch (Exception localException1) {}
                draw_item(paramGraphics);
                this.p_mode = 2;
                System.gc();
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
        }
        else if (this.screen == 2)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            // paramGraphics.setColor(16777164);
            drawString(paramGraphics, "1.Play", 13, 23, 20);
            drawString(paramGraphics, "2.Instructions", 13, 38, 20);
            drawString(paramGraphics, "3.Configuration", 13, 53, 20);
            drawString(paramGraphics, "4.Quit", 13, 68, 20);
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
            }
        }
        else if (this.screen == 31)
        {
            drawImage2(paramGraphics, this.imgShop, 24, 20, 20);
            // paramGraphics.setColor(16777062);
            drawRect(paramGraphics, 27, this.s_item * 13 + 30, 29, 10, 16777062);
            drawRect(paramGraphics, 28, this.s_item * 13 + 31, 27, 8, 16777062);
            // paramGraphics.setColor(13434777);
            drawRect(paramGraphics, this.b_item * 16 + 32, 70, 15, 15, 13434777);
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
            drawImage2(paramGraphics, this.imgCh, 3, this.m_mode * 14 + 18, 20);
            drawString(paramGraphics, "Resume", 15, 28, 20);
            drawString(paramGraphics, "MainMenu", 15, 42, 20);
            drawString(paramGraphics, "Sound", 15, 56, 20);
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
            // paramGraphics.setColor(0);
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
                drawString(paramGraphics, "ON /", 62, 23, 20); // TODO color required ?
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
            drawString(paramGraphics, "Vibration ", 12, 41, 20);
            if (this.v_mode == 1)
            {
                drawString(paramGraphics, "ON /", 62, 59, 20);
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
            drawString(paramGraphics, "Speed ", 14, 77, 20);
            drawString(paramGraphics, "[ " + String.valueOf(this.speed) + " ]", 68, 77, 20);
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
            // paramGraphics.setColor(16777164);
            try
            {
                if (this.m_mode == 1) {
                    drawImage2(paramGraphics, new Texture("/txt4.png"), 5, 25, 20);
                }
                if (this.m_mode == 2)
                {
                    fillRect(paramGraphics, 6, 23, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 45, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 61, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 84, 10, 10, 16777164);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item1.png"), 7, 24, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item2.png"), 7, 46, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item3.png"), 7, 62, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item4.png"), 7, 85, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/txt2.png"), 23, 25, 20);
                }
                if (this.m_mode == 3)
                {
                    fillRect(paramGraphics, 6, 23, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 38, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 53, 10, 10, 16777164);
                    fillRect(paramGraphics, 6, 67, 10, 10, 16777164);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item5.png"), 7, 24, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item6.png"), 7, 39, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item7.png"), 7, 54, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/item8.png"), 7, 68, 20);
                    drawImage2(paramGraphics, new Texture("data/samsung-white/txt1.png"), 23, 25, 20);
                }
            }
            catch (Exception localException2) {}
            System.gc();
        }
        else if (this.screen == 200)
        {
            if ((this.ani_step >= 13) && (this.ani_step < 27))
            {
                // paramGraphics.setColor(16777215);
                fillRect(paramGraphics, 0, 60, 128, 47, 16777215);
                drawImage2(paramGraphics, this.imgHero_v, this.h_x * 5, 83, 0x10 | 0x1);
            }
            else if ((this.ani_step >= 28) && (this.ani_step < 50))
            {
                drawImage2(paramGraphics, this.imgV, this.h_x * 5 + 8, 87, 0x10 | 0x1);
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
        else if (this.screen == -1)
        {
            loadImage(1);
            drawImage2(paramGraphics, this.imgLogo, 0, 0, 20);
            MPlay(0);
            destroyImage(1);
        }
        else if (this.screen == -2)
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 0, 128, 135, 16777215);
            // paramGraphics.setColor(25054);
            fillRect(paramGraphics, 0, 0, 128, 22, 25054);
            fillRect(paramGraphics, 0, 71, 128, 84, 25054);
            try
            {
                drawImage2(paramGraphics, new Texture("/present.png"), 64, 5, 0x10 | 0x1);
                drawImage2(paramGraphics, new Texture("/sam_logo.png"), 64, 28, 0x10 | 0x1);
                drawImage2(paramGraphics, new Texture("/http1.png"), 7, 77, 20);
                drawImage2(paramGraphics, new Texture("/http2.png"), 7, 103, 20);
            }
            catch (Exception localException3) {}
            System.gc();
        }
        else if (this.screen == 1000)
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 0, 25, 120, 85, 16777215);
            try
            {
                drawImage2(paramGraphics, new Texture("/allClear.png"), 64, 10, 0x10 | 0x1);
            }
            catch (Exception localException4) {}
        }
        else if (this.screen == -5)
        {
            // paramGraphics.setColor(16777215);
            fillRect(paramGraphics, 1, 20, 126, 90, 16777215);
            // paramGraphics.setColor(0);
            drawRect(paramGraphics, 0, 19, 127, 90, 0);
            drawRect(paramGraphics, 0, 21, 127, 86, 0);
            try
            {
                drawImage2(paramGraphics, new Texture("/txt4b.png"), 4, 30, 20);
            }
            catch (Exception localException5) {}
            System.gc();
        }
        else if (this.screen == 1)
        {
            drawImage2(paramGraphics, this.imgMM, 0, 0, 20);
            try
            {
                drawImage2(paramGraphics, new Texture("/title.png"), 64, 35, 0x10 | 0x1);
            }
            catch (Exception localException6) {}
            drawImage2(paramGraphics, this.imgPl, 68, 115, 20);
            drawImage2(paramGraphics, this.imgBk, 2, 115, 20);
            System.gc();
        }
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
        int i = this.message.length();
        // paramGraphics.setColor(44783);
        fillRect(paramGraphics, 0, 52, 128, 19, 44783);
        // paramGraphics.setColor(20361);
        drawRect(paramGraphics, 0, 52, 127, 19, 20361);
        // paramGraphics.setColor(0);
        drawString(paramGraphics, this.message, 64, 53, 0x10 | 0x1, 0);
        this.message = "";
    }

    public void draw_item(SpriteBatch paramGraphics)
    {
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
            fillRect(paramGraphics, this.del * 12 + 37, 111, 8, 8, 6974058);
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

    public void draw_int(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int paramInt3)
    {
        int i = 0;
        if (paramInt1 / 100 > 0) {
            i = 3;
        } else if (paramInt1 / 10 > 0) {
            i = 2;
        } else {
            i = 1;
        }
        int[] arrayOfInt = new int[i];
        if (i == 3)
        {
            arrayOfInt[2] = (paramInt1 / 100);
            arrayOfInt[1] = (paramInt1 / 10 % 10);
            arrayOfInt[0] = (paramInt1 % 10);
        }
        else if (i == 2)
        {
            arrayOfInt[1] = (paramInt1 / 10);
            arrayOfInt[0] = (paramInt1 % 10);
        }
        else if (i == 1)
        {
            arrayOfInt[0] = paramInt1;
        }
        for (int j = i; j > 0; j--) {
            if (paramInt1 < 10) {
                drawImage2(paramGraphics, this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2, paramInt3, 20);
            } else {
                drawImage2(paramGraphics, this.imgNum[arrayOfInt[(j - 1)]], (i - j) * 4 + paramInt2 - 2, paramInt3, 20);
            }
        }
    }

    public void run()
    {
        for (;;)
        {
            if (this.gameOn)
            {
                if (this.screen == 6)
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
                                System.out.println("E move dir = 0 AI: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
                                e_move_ai(i);
                            }
                            else if ((this.e_move_dir[i] < 100) && (this.e_move_dir[i] != 0) && (this.e_hp[i] > 0))
                            {
                                System.out.println("E move dir < 100: " + this.e_move_dir[0] + " " + this.e_move_dir[1]+ " --x-- " + this.e_x[0] + " "+ this.e_x[1]);
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
                            // repaint();
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
                            // repaint();
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
                            this.screen = 65336;
                            this.gold = 3;
                        }
                    }
                }
                else if (this.screen == 8)
                {
                    if ((this.ani_step < 50) && (this.ani_step > 0)) {
                        this.ani_step += 1;
                    }
                    // repaint();
                    // serviceRepaints();
                }
                else if (this.screen == 9)
                {
                    if ((this.ani_step < 46) && (this.ani_step >= 0)) {
                        this.ani_step += 1;
                    }
                    // repaint();
                    // serviceRepaints();
                }
                else if (this.screen == 200)
                {
                    if ((this.ani_step < 51) && (this.ani_step >= 0))
                    {
                        this.ani_step += 1;
                        // repaint();
                        // serviceRepaints();
                    }
                    else
                    {
                        this.gameOn = false;
                        destroyImage(200);
                        System.gc();
                        if (this.state != 10)
                        {
                            loadImage(2);
                            this.screen = 300;
                        }
                        else
                        {
                            this.screen = 1000;
                        }
                        // repaint();
                    }
                }
                else if (this.screen == 201)
                {
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
                        }
                        else
                        {
                            this.stage = 45;
                            this.state = 10;
                        }
                        this.last_stage = this.stage;
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
                else if (this.screen == 65335)
                {
                    if (this.ani_step <= 100)
                    {
                        this.ani_step += 1;
                        // repaint();
                        // serviceRepaints();
                    }
                    else
                    {
                        this.gameOn = false;
                        destroyImage(65336);
                        loadImage(2);
                        System.gc();
                        this.screen = 300;
                        // repaint();
                    }
                }
            }
            else {
                Gdx.app.log("DEBUG", "is there no one else?");
//                try
//                {
//                    Thread.sleep(100L);
//                }
//                catch (Exception localException2) {}
            }
        }
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
        MPlay(5);
        call_vib(3);
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
        loadImage(2);
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

    public void use_item(int paramInt)
    {
        if ((this.item_slot[paramInt] > 0) && (this.item_slot[paramInt] <= 4))
        {
            this.wp = this.item_slot[paramInt];
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
        else if (this.item_slot[paramInt] == 5)
        {
            delete_item(5);
            this.hp += this.max_hp / 3;
            if (this.hp > this.max_hp) {
                this.hp = this.max_hp;
            }
            this.h_timer_p = -4;
        }
        else if (this.item_slot[paramInt] == 6)
        {
            delete_item(6);
            this.mana += 10;
            if (this.mana > 36) {
                this.mana = 36;
            }
            this.d_gauge = 1;
        }
        else if (this.item_slot[paramInt] == 7)
        {
            delete_item(7);
            this.hp = this.max_hp;
            this.h_timer_p = -4;
        }
        else if (this.item_slot[paramInt] == 8)
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

    public void keyPressed() // int paramInt J2ME simulate from virtual Droid/iOS keyboard
    {
        int paramInt = this.key_code;
        // this.screen = 3; // init
        // paramInt = 35;

        // this.m_mode = 4; // init game

        this.screen = -1; // Init Logo screen

        int i;
        int j;
        if ((this.screen == 6) && (this.state == 1))
        {
            if ((getGameAction(paramInt) == 2) || (paramInt == 52))
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
                    // repaint();
                }
            }
            else if ((getGameAction(paramInt) == 5) || (paramInt == 54))
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
                    // repaint();
                }
            }
            else if ((getGameAction(paramInt) == 6) || (paramInt == 56))
            {
                if (this.mana >= 12) {
                    use_special();
                } else {
                    this.message = "Insufficient Mana";
                }
            }
            else if ((paramInt == -5) || (getGameAction(paramInt) == 1) || (paramInt == 50) || (paramInt == 53))
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
            else if (((paramInt == 35) || (paramInt == -7)) && (this.game_state == 0))
            {
                this.m_mode = 1;
                this.gameOn = false;
                this.screen = 100;
                // repaint();
            }
            else if ((paramInt == 51) && (this.game_state == 0))
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
                    // repaint();
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
            else if (getGameAction(paramInt) == 6)
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
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
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
                            // repaint();
                        }
                    }
                    else if (this.m_mode == 4)
                    {
                        this.screen = -5;
                    }
                }
            }
            // repaint();
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
                    return;
                }
            }
            // repaint();
        }
        else if (this.screen == 3)
        {
            Gdx.app.log("DEBUG", "kkk init");
            Gdx.app.log("DEBUG", " game action " + getGameAction(paramInt));
            if (getGameAction(paramInt) == 1)
            {
                j = this.h_y - 8;
                if (hero_move(this.h_x, j, 1) > 0) {
                    this.h_y = j;
                }
            }
            else if (getGameAction(paramInt) == 6)
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
            else if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7))
            {
                Gdx.app.log("DEBUG", "mmode " + this.m_mode);

                if ((this.m_mode == 0) || (this.m_mode == 1))
                {
                    loadImage(31);
                    this.screen = 31;
                    // repaint();
                }
                else if ((this.m_mode >= 2) && (this.m_mode <= 5))
                {
                    int k = -1;
                    if ((this.last_stage / 10 - this.school == 0) && (this.last_stage != 45)) {
                        k = this.last_stage;
                    }
                    destroyImage(3);
                    this.message = "Loading";
                    Gdx.app.log("DEBUG", "init game ...");
                    init_game(k);
                }
            }
            else if ((paramInt == 42) || (paramInt == -6))
            {
                destroyImage(3);
                loadImage(2);
                this.screen = 2;
                this.m_mode = 1;
            }
            // repaint();
        }
        else if (this.screen == 31)
        {
            if (getGameAction(paramInt) == 1) {
                this.s_item = 0;
            }
            if (getGameAction(paramInt) == 6) {
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
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == -7)) {
                if (this.s_item == 1)
                {
                    this.m_mode = -1;
                    destroyImage(31);
                    this.screen = 3;
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
            // repaint();
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
            if (getGameAction(paramInt) == 6) {
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
            // repaint();
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
            if (getGameAction(paramInt) == 6) {
                if (this.m_mode == 3) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
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
            // repaint();
        }
        else if (this.screen == -5)
        {
            this.screen = 100;
            // repaint();
        }
        else if (this.screen == -88)
        {
            if (getGameAction(paramInt) == 1) {
                if (this.m_mode <= 1) {
                    this.m_mode = 2;
                } else {
                    this.m_mode -= 1;
                }
            }
            if (getGameAction(paramInt) == 6) {
                if (this.m_mode >= 2) {
                    this.m_mode = 1;
                } else {
                    this.m_mode += 1;
                }
            }
            if ((paramInt == 35) || (getGameAction(paramInt) == 8) || (paramInt == 49) || (paramInt == 50) || (paramInt == -7))
            {
                if (paramInt > 48) {
                    this.m_mode = (paramInt - 48);
                }
                if (this.m_mode == 1)
                {
                    this.last_stage = 11;
                    this.stage = 11;
                    this.saved_gold = 0;
                    this.mana = 0;
                    addScore("hero", 0);
                }
                destroyImage(2);
                loadImage(3);
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
            // repaint();
        }
        else if (this.screen == -33)
        {
            if ((paramInt == 42) || (paramInt == -6))
            {
                loadImage(2);
                this.m_mode = 1;
                this.screen = 5;
                // repaint();
                System.gc();
            }
        }
        else if (this.screen == 300)
        {
            // MPlay(3);
            this.m_mode = -1;
            destroyImage(2);
            loadImage(3);
            this.h_x = 57;
            this.h_y = 46;
            this.saved_gold += this.gold;
            addScore("hero", 0);
            this.ani_step = 0;
            this.screen = 3;
            // repaint();
            System.gc();
        }
        else if (this.screen == -1) // TODO use constant // Logo start Screen LOGO_SCREEN
        {
            loadImage(-2);
            this.screen = -2;
            // repaint();
        }
        else if (this.screen == -2) // SAMSUNG_FUNCLUB_SCREEN
        {
            loadImage(2);
            this.screen = 1;
            // repaint();
        }
        else if (this.screen == 1)
        {
            if ((paramInt == 35) || (paramInt == -7))
            {
                this.screen = -88;
                this.m_mode = 1;
            }
            else if ((paramInt == 42) || (paramInt == -6))
            {
                this.screen = 2;
            }
            // repaint();
        }
        else if (this.screen == 1000)
        {
            loadImage(2);
            System.gc();
            this.screen = 300;
            // repaint();
        }

    }

    public void MPlay(int paramInt)
    {
        String str1 = null;
//    this.audioClip = null;
        if (this.s_play == 1)
        {
            String str2 = null;
            try
            {
                if (paramInt == 0) {
                    str2 = "/9.mmf";
                } else if (paramInt == 1) {
                    str2 = "/1.mmf";
                } else if (paramInt == 2) {
                    str2 = "/6.mmf";
                } else if (paramInt == 3) {
                    str2 = "/5.mmf";
                } else if (paramInt == 4) {
                    str2 = "/4.mmf";
                } else if (paramInt == 5) {
                    str2 = "/8.mmf";
                } else if (paramInt == 6) {
                    str2 = "/3.mmf";
                } else if (paramInt == 7) {
                    str2 = "/0.mmf";
                }
                str1 = new String(str2);
//        this.audioClip = new AudioClip(1, str1);
            }
            catch (Exception localException) {}
//      this.audioClip.play(1, 3);
        }
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

    private static class Const {
        public static final String BAR_VALUE = "BAR";
        public static final int LOGO_SCREEN = -1;
        public static final int SAMSUNG_FUNCLUB_SCREEN = -2;
    }
}
