package de.swagner.sbf2.screens;

import de.swagner.sbf2.Bob;
import de.swagner.sbf2.Boss;
import de.swagner.sbf2.Item;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

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

    private Bob bob;
    // TODO loop create sprite
    private Bob huey;
    private Bob dewey;
    private Boss boss;

    private Texture bobTexture;

    float stateTime;

    // enemy move dir

    // Enermy state

    // Game state

    // Hero state

    private Random rnd = new Random();

    // Test touchpad
    private Stage stage;
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
    public static float sgh_120_1080_screen_ratio = (5*9); // (5*9/2)

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
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("data/gui/block.png"));
        blockSprite = new Sprite(blockTexture);

        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);
        blockSpeed = 5;
    }

    public void update() {

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
            e_move_ai();
        }
        else if ((huey.e_move_dir < 100) && (huey.e_move_dir != 0) && (!huey.isDead()))
        {
            Gdx.app.log("INFO", "Huey move dir = 0 AI :"+ huey.e_move_dir + " --x--" + huey.position.x + " y " + huey.position.y);
            e_move();
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
        if(bob.facingLeft){
        }
        if (bob.state==bob.state.WALKING){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        else if(bob.state==bob.state.IDLE){
            batch.draw(heroTexture, bob.position.x * ppuX, bob.position.y * ppuY, facex* Bob.SIZE * ppuX, Bob.SIZE * ppuY);
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
//        huey = new Bob(new Vector2(240, enemyPosY-enemyStepY));
        // Try J2ME original: 5px is old cell in map (120px = 24 * 5) In 1080 = 120 * 9. But because of sprite scale we try 4.5 instead of 9.
        huey = new Bob(new Vector2(240/sgh_120_1080_screen_ratio, (enemyPosY-enemyStepY)/sgh_120_1080_screen_ratio ));
        dewey = new Bob(new Vector2(480/sgh_120_1080_screen_ratio, enemyPosY/sgh_120_1080_screen_ratio));
        boss = new Boss(new Vector2(320/sgh_120_1080_screen_ratio, (enemyPosY-(3*enemyStepY))/sgh_120_1080_screen_ratio ));
        huey.setBobTexture("data/samsung-white/enemy0_0_60x.png");
        dewey.setBobTexture("data/samsung-white/enemy1_1_60x.png");
        boss.setBobTexture("data/samsung-white/boss2_0_60x.png");

        huey.setBound(new Rectangle(huey.position.x*sgh_120_1080_screen_ratio, huey.position.y*sgh_120_1080_screen_ratio, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight()));
        dewey.setBound(new Rectangle(dewey.position.x*sgh_120_1080_screen_ratio, dewey.position.y*sgh_120_1080_screen_ratio, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight()));
        boss.setBound(new Rectangle(boss.position.x*sgh_120_1080_screen_ratio, boss.position.y*sgh_120_1080_screen_ratio, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight()));
    }

    protected void initBobItem() {
        Vector2 facing = new Vector2(bob.position.x, Gdx.graphics.getHeight()*4/5);
        Item item = new Item(0, bob.position, facing);
        item.setTexture(new Texture("data/samsung-white/item3_0_36x.png"));
        bob.setItem(item);
        bob.getItem().setBound(new Rectangle(item.getX(), item.getY(), item.getWidth(), item.getHeight()) );
    }

    protected void initSpriteBatchAndHeroTexture () {
        batch = new SpriteBatch();
        ttrSplash = new Texture("data/samsung-white/menu_bg.png");
        heroTexture = new Texture("data/samsung-white/hero3_1_60x80.png");
        bob = new Bob(new Sprite());
        bob.position.x = Gdx.graphics.getWidth()/2;
        bob.setBobTexture(new Texture("data/samsung-white/hero3_1_60x80.png"));
        bob.position.y = Gdx.graphics.getHeight()/5-bob.getBobTexture().getHeight();

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
        batch.draw(bob.getBobTexture(), (int)bob.position.x, (int)bob.position.y, 0, 0, 45, 60, 5/2, 5/2, 0, 0, 0, 45, 60, bob.facingLeft, false);
    }

    protected void oldTestDrawInitPositionEnermy() {
        if(!huey.isDead()) {
            batch.draw(huey.getBobTexture(), (int)huey.position.x, (int)huey.position.y, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 2, 2, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), huey.facingLeft, false);
            huey.facingLeft = !huey.facingLeft;
        }
        if(!dewey.isDead()) {
            batch.draw(dewey.getBobTexture(), (int)dewey.position.x, (int)dewey.position.y, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), 2, 2, 0, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), dewey.facingLeft, false);
            dewey.facingLeft = !dewey.facingLeft;
        }
        if(!boss.isDead()) {
            batch.draw(boss.getBobTexture(), (int)boss.position.x, (int)boss.position.y, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 2, 2, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), boss.facingLeft, false);
            boss.facingLeft = !boss.facingLeft;
        }
    }

    /*
    * Try use old J2ME logic to render position of enemy
    * */
    protected void testDrawInitPositionEnermy() {
        if(!huey.isDead()) {
            batch.draw(huey.getBobTexture(), (int)huey.position.x*sgh_120_1080_screen_ratio, (int)huey.position.y*sgh_120_1080_screen_ratio, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), 2, 2, 0, 0, 0, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight(), huey.facingLeft, false);
            huey.facingLeft = !huey.facingLeft;
        }
        if(!dewey.isDead()) {
            batch.draw(dewey.getBobTexture(), (int)dewey.position.x*sgh_120_1080_screen_ratio, (int)dewey.position.y*sgh_120_1080_screen_ratio, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), 2, 2, 0, 0, 0, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight(), dewey.facingLeft, false);
            dewey.facingLeft = !dewey.facingLeft;
        }
        if(!boss.isDead()) {
            batch.draw(boss.getBobTexture(), (int)boss.position.x*sgh_120_1080_screen_ratio, (int)boss.position.y*sgh_120_1080_screen_ratio, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), 2, 2, 0, 0, 0, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight(), boss.facingLeft, false);
            boss.facingLeft = !boss.facingLeft;
        }
    }

    /*
    * J2ME original
    * */
    public void draw_enemy()
    {

    }

    protected void displayManualTextureDraw() {
        //        batch.draw(hero, (int)heroX, (int)heroY, 0, 0, 45, 60, 2, 2, 0, 0, 0, 45, 60, false, false);
        //      (Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
    }

    protected void handleHeroMoveLeft() {
    }

    protected void handleHeroMoveRight() {
    }

    /*
    * Try ony huey instead of array
    * */
    protected void e_move() {
        int i = huey.e_move_dir;
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
            if ((huey.position.x != 2) && (i % 3 == 0)) {
                huey.position.x -= 1;
            }
            if (i == 31) {
                i = 100;
            }
        }
        else if ((i > -31) && (i <= -21))
        {
            i--;
            if ((huey.position.x != 22) && (i % 3 == 0)) {
                huey.position.x += 1;
            }
            if (i == -31) {
                i = 100;
            }
        }
        else if ((i >= 11) && (i < 14))
        {
            i++;
            if ((huey.position.y != 1) && (i % 2 == 0)) {
                huey.position.y -= 1;
            }
            if (i == 14) {
                i = 100;
            }
        }
        else if ((i > -14) && (i <= -11))
        {
            i--;
            if ((huey.position.y != 7) && (i % 2 == 0)) {
                huey.position.y += 1;
            }
            if (i == -14) {
                i = 100;
            }
        }
        huey.e_move_dir = i;

    }

    protected void e_move_ai() {
        int i;
        if (((int)huey.position.x == 2))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                huey.e_move_dir = -21;
            } else if (i == 2) {
                huey.e_move_dir = -11;
            } else if (i == 3) {
                huey.e_move_dir = 11;
            }
        }
        else if (((int)huey.position.x == 22) || (((int)huey.position.x >= 14)))
        {
            i = get_random(4);
            if ((i == 0) || (i == 1)) {
                huey.e_move_dir = 21;
            } else if (i == 2) {
                huey.e_move_dir = -11;
            } else if (i == 3) {
                huey.e_move_dir = 11;
            }
        }
        else if (((int)huey.position.y == 6) || ((int)huey.position.y == 7))
        {
            i = get_random(4);
            if ((i == 1) || (i == 2)) {
                huey.e_move_dir = 11;
            } else if (i == 0) {
                huey.e_move_dir = 21;
            } else if (i == 1) {
                huey.e_move_dir = -21;
            }
        }
        else
        {
            i = get_random(8);
            if ((i == 0) || (i == 1)) {
                huey.e_move_dir = 21;
            } else if ((i == 2) || (i == 3)) {
                huey.e_move_dir = -21;
            } else if (i == 4) {
                huey.e_move_dir = 11;
            } else if (i == 5) {
                huey.e_move_dir = -11;
            } else {
                huey.e_move_dir = 1;
            }
        }
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

    /*
    * Update 8-Nov-2017.
    * Try use original e_move() from J2ME.
    * */
    protected void testEnermyMoving() {

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
            huey.position.x = (r.nextInt(180) + bob.position.x)/sgh_120_1080_screen_ratio;
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

    protected void oldTestEnermyMoving() {
        float leftBound = 0;
        float rightBoundHuey = Gdx.graphics.getWidth() - huey.getBobTexture().getWidth();
        float rightBoundDewey = Gdx.graphics.getWidth() - dewey.getBobTexture().getWidth();
        float rightBoundBoss = Gdx.graphics.getWidth() - boss.getBobTexture().getWidth();

        Random r = new Random();
        // TODO random position when hit rightBound, Horizontal front line enemy move by step up/down
    }

    protected void heroAttack() {

        // disable another shell til this shell drop or hit enermy
        // power up snow ?
        // handle colidate enermy
        // handle health enermy
        // update state of hero

        // TODO handle onTouch, gdx way
    }

    protected void drawHeroHp() {

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

        batch.draw(bob.getBobTexture(), bob.position.x, bob.position.y, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), 2, 2, 0, 0, 0, heroTexture.getWidth(), heroTexture.getHeight(), bob.facingLeft, false);
        bob.facingLeft = !bob.facingLeft;

        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    protected void handleFireTouch() {
        if(Gdx.input.isTouched())
        {
            Vector3 touchPos=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            // TODO find out why unproject make position wrong. Unproject deal with zoom or similar variables in touch screen
            // When zoom in/out ... the extract position may be difference. So the pointer pos after unproject may be reset to 0
//            camera.unproject(touchPos);
            Rectangle textureBounds=new Rectangle(Gdx.graphics.getWidth()-fireBtnTexture.getWidth()-50, Gdx.graphics.getHeight()-50-fireBtnTexture.getHeight(), fireBtnTexture.getWidth(),fireBtnTexture.getHeight());

            Item bobItem = bob.getItem();
            if(textureBounds.contains(touchPos.x, touchPos.y) && (heroFireState == false))
            {
//                Gdx.app.log("INFO", "I like it when I feel your touch");
                if(bobItem.position.y <= Gdx.graphics.getHeight()*4/5) {
                    heroFireState = true;
                } else {
                    bobItem.position.y = bob.position.y;
                }
            }
            if(heroFireState) {
                bobItem.setVelocity(new Vector2(0, 12));
                bobItem.velocity.scl(2);
//                Gdx.app.log("INFO", "snow x "+ bobItem.position.toString() + " delta "+ bobItem.getDelta() + " state " + heroFireState + " screen "+ Gdx.graphics.getHeight()*4/5);
                if(bobItem.position.y <= Gdx.graphics.getHeight()*4/5) {
                    bobItem.position.add(bobItem.velocity.x * bobItem.getDelta(), bobItem.velocity.y * 2);
                    // Update snow bound. TODO handle messing between hero.x and snow.x
                    bobItem.setBound(new Rectangle(bob.position.x, bobItem.position.y, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight()));
                    heroFire();
                    // collision
                    checkCollisionHeroToEnemy();
                } else {
                    heroFireState = false;
                    bobItem.setPosition(bob.position.x, bob.position.y);
                    // TODO handle smooth (fire one, impact one) instead of steped fire.
                    // Snow do not fire direct from player to enemy, it fly a distance then wait until next event like touch. So it is a bug.
                    Gdx.app.log("INFO", "Over rooftop " + bobItem.position.toString());
                }
            }
            Gdx.app.log("INFO", "Snow "+ bobItem.getBound().toString()+ "huey "+ huey.getBound().toString() + " dewey "+ dewey.getBound().toString() + " boss " + boss.getHp() + " screen ");
            if(bobItem.position.y > Gdx.graphics.getHeight()*4/5) {
                heroFireState = false;
                bobItem.setPosition(bob.position.x, bob.position.y);
            }
        }
    }

    protected void heroFire() {
        Item bobItem = bob.getItem();
//        bobItem.acting();
        // 10 space between player and snow
//        batch.draw(bobItem.getItemTexture(), bobItem.position.x+(bob.getBobTexture().getWidth()*2-10), bobItem.position.y+bob.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
        batch.draw(bobItem.getItemTexture(), bob.position.x+(bob.getBobTexture().getWidth()*2-10), bobItem.position.y+bob.getBobTexture().getHeight()/2, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), (float)1.3, (float)1.3, 0, 0, 0, bobItem.getItemTexture().getWidth(), bobItem.getItemTexture().getHeight(), false, false);
    }

    // TODO firePos, itemType, power, direction, angle (gap)
    protected void fireItem() {

    }

    protected void heroHitTarget() {

    }

    protected void enermyHitTarget() {

    }

    protected void handleHeroMoveBound() {
        bobTexture = bob.getBobTexture();
        float leftBound = 0;
        float rightBound = Gdx.graphics.getWidth() - bobTexture.getWidth();
        // TODO use object instead of global var, handle screen size
        if(bob.position.x < leftBound) {
//            bob.position.x = leftBound;
            bob.position.x = Gdx.graphics.getWidth()/2;
        }
        if(bob.position.x > rightBound) {
            bob.position.x = rightBound;
        }
        if((bob.position.x >= leftBound) && (bob.position.x <= rightBound) ) {
            bob.position.x = (bob.position.x + touchpad.getKnobPercentX() * heroSpeed);
        }
//        if(touchpad.getKnobPercentX() < 0) {
//            bob.facingLeft = true;
//        } else {
//            bob.facingLeft = false;
//        }

//        Gdx.app.log("INFO", "Left "+ leftBound + " right " + rightBound + " bob: " + bob.position.x + " y "+ bob.position.y);
    }

    protected void checkCollisionHeroToEnemy() {
        Rectangle heroItem = bob.getItem().getBound();
        if(heroItem.overlaps(huey.getBound())) {
            huey.loseHp(bob.getItem().getDamage());
        }

        if(heroItem.overlaps(dewey.getBound())) {
            dewey.loseHp(bob.getItem().getDamage());
        }

        if(heroItem.overlaps(boss.getBound())) {
            boss.loseHp(bob.getItem().getDamage());
        }
    }

    protected void checkCollisionEnemyToHero() {

    }

    protected int getGameState() {
        if(bob.isDead()) {
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
        bob.setBound(new Rectangle(bob.position.x, bob.position.y, bob.getBobTexture().getWidth(), bob.getBobTexture().getHeight()));
    }
    protected void updateEnemyBound() {
        huey.setBound(new Rectangle(huey.position.x, huey.position.y, huey.getBobTexture().getWidth(), huey.getBobTexture().getHeight()));
        dewey.setBound(new Rectangle(dewey.position.x, dewey.position.y, dewey.getBobTexture().getWidth(), dewey.getBobTexture().getHeight()));
        boss.setBound(new Rectangle(boss.position.x, boss.position.y, boss.getBobTexture().getWidth(), boss.getBobTexture().getHeight()));
    }
}