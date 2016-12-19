package de.swagner.sbf2.screens;

import de.swagner.sbf2.Assets.HeroAsset;
import de.swagner.sbf2.Bob;
import de.swagner.sbf2.SnowBallFight;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by nickfarow on 13/10/2016.
 */

public class Screen1 implements Screen{

    SnowBallFight game;
    OrthographicCamera camera;
    SpriteBatch batch;
    Texture ttrSplash;
    Texture hero;

    private Sprite bobSprite;

    int farmerX;
    float heroSpeed = 10.0f; // 10 pixels per second.
    float heroX;
    float heroY = 660; // TODO use ratio screen

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis

    private Bob bob;
    private Bob huey;
    private Bob dewey;
    private Bob boss;

    private Texture bobTexture;

    private static final int        FRAME_COLS = 3;
    private static final int        FRAME_ROWS = 2;
    Animation walkAnimation;
    Texture                         walkSheet;
    TextureRegion[]                 walkFrames;
    TextureRegion                   currentFrame;

    float stateTime;


    // Enermy state

    // Game state


    // Hero state


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

    public Screen1(SnowBallFight game1){
        this.game = game1;

        camera = newOrthographicCamera(1920, 1080);

        initSpriteBatchAndHeroTexture();

        farmerX = 480-85;
        HeroAsset.load();

//        loadTextures();  // From demo animation Bob
        setEnermyTexture();
        setHeroTexture();
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

        bobSprite = new Sprite(bobTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth()/2-blockSprite.getWidth()/2, Gdx.graphics.getHeight()/2-blockSprite.getHeight()/2);
//        bobSprite.setPosition(Gdx.graphics.getWidth()/2-bobTexture.getWidth()/2, Gdx.graphics.getHeight()/4-bobTexture.getHeight()/2);

        blockSpeed = 5;
    }

    public void render(float delta) {
//        renderTouchPad();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        generalUpdate();
//        batch.setProjectionMatrix(camera.combined);


        /**/
        drawSplashBatch();

        drawTouchPad();
        batch.enableBlending();
        batch.begin();
//        HeroAsset.hero1.draw(batch);

        handleKeyMoveHero();
        // renderManualTextureDraw()
        testDrawBob();

        testDrawInitPositionEnermy();
//        drawBob();
        batch.end();
/**/
    }

    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
    }

    private void loadTextures() {
        walkSheet = new  Texture("data/samsung-white/animation_sheet.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() /
                FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.025f, walkFrames);

        stateTime = 0f;
    }

    private void drawBob() {
//        Bob bob = new Bob(new Vector2(7, 2));    // Bob related to map not re-create when draw ?
        int facex=1;
        if(bob.facingLeft){
            facex=-1;
        }

        Gdx.app.debug(TAG, bob.position.x + " x ");
        if (bob.state==bob.state.WALKING){
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            stateTime += Gdx.graphics.getDeltaTime();
//            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, bob.position.x * ppuX, bob.position.y * ppuY, facex*Bob.SIZE * ppuX, Bob.SIZE * ppuY);

        }
        else if(bob.state==bob.state.IDLE){
            batch.draw(bobTexture, bob.position.x * ppuX, bob.position.y * ppuY, facex* Bob.SIZE * ppuX, Bob.SIZE * ppuY);
        }
    }

    public void generalUpdate() {
        if(Gdx.input.isKeyPressed(Keys.A)||Gdx.input.isKeyPressed(Keys.LEFT)){
            handleHeroMoveLeft();
        }
        else if(Gdx.input.isKeyPressed(Keys.D)||Gdx.input.isKeyPressed(Keys.RIGHT)){
            handleHeroMoveRight();
        }

        testEnermyMoving();
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
        bob = new Bob(new Vector2(480, 320));
        huey = new Bob(new Vector2(240, 1380));
        dewey = new Bob(new Vector2(480, 1380));
        boss = new Bob(new Vector2(320, 1300));
        huey.setBobTexture("data/samsung-white/enemy0_0.png");
        dewey.setBobTexture("data/samsung-white/enemy1_0.png");
        boss.setBobTexture("data/samsung-white/boss2_0.png");
    }

    protected void setHeroTexture() {
        bobTexture = new  Texture("data/samsung-white/hero3_1.png");
    }

    protected void initSpriteBatchAndHeroTexture () {
        batch = new SpriteBatch();
        ttrSplash = new Texture("data/samsung-white/menu_bg.png");
        hero = new Texture("data/samsung-white/hero3_1.png");
    }

    protected void drawSplashBatch() {
        batch.disableBlending();
        batch.begin();
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    protected void handleKeyMoveHero() {
        if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT))
            heroX -= Gdx.graphics.getDeltaTime() * heroSpeed;
        if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT))
            heroX += Gdx.graphics.getDeltaTime() * heroSpeed;
        if(Gdx.input.isKeyPressed(Keys.DPAD_UP))
            heroY += Gdx.graphics.getDeltaTime() * heroSpeed;
        if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN))
            heroY -= Gdx.graphics.getDeltaTime() * heroSpeed;
    }

    protected void testDrawBob() {
        batch.draw(bobTexture, (int)bob.position.x, (int)bob.position.y, 0, 0, 45, 60, 2, 2, 0, 0, 0, 45, 60, bob.facingLeft, false);
    }

    protected void testDrawInitPositionEnermy() {
        batch.draw(huey.getBobTexture(), (int)huey.position.x, (int)huey.position.y, 0, 0, 40, 40, 2, 2, 0, 0, 0, 40, 40, huey.facingLeft, false);
        batch.draw(dewey.getBobTexture(), (int)dewey.position.x, (int)dewey.position.y, 0, 0, 35, 40, 2, 2, 0, 0, 0, 35, 40, dewey.facingLeft, false);
        batch.draw(boss.getBobTexture(), (int)boss.position.x, (int)boss.position.y, 0, 0, 40, 55, 2, 2, 0, 0, 0, 40, 55, boss.facingLeft, false);
    }

    protected void displayManualTextureDraw() {
        //        batch.draw(hero, (int)heroX, (int)heroY, 0, 0, 45, 60, 2, 2, 0, 0, 0, 45, 60, false, false);
        //      (Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
    }

    protected void handleHeroMoveLeft() {
        // TODO add bounce to sprite margin
        if(heroX > 5) {
            heroX -= 5;
        }

        bob.state = Bob.State.WALKING;
        bob.facingLeft = true;

        if(bob.position.x > 5) {
            bob.position.x -= 8;
        }
    }

    protected void handleHeroMoveRight() {
        // TODO def constant
        if(heroX < 1000) {
            heroX += 5;
        }
        bob.state = Bob.State.WALKING;
        bob.facingLeft = false;

        if(bob.position.x < 1000) {
            bob.position.x += 8;
        }
    }

    protected void testEnermyMoving() {
        int dir1 = (int)(Math.random() * 12);
        dir1 = (dir1 > 6) ? 1 : -1;

        int dir2 = (int)(Math.random() * 6);
        dir2 = (dir2 > 3) ? 1 : -1;

        int dir3 = (int)(Math.random() * 9);
        dir3 = (dir3 > 4) ? 1 : -1;

        if(huey.position.x > 1000) {
            dir1 = -1;
        } else {
            dir1 = 1;
        }
        if(dewey.position.x > 1000) {
            dir2 = -1;
        } else {
            dir2 = 1;
        }
        if(huey.position.x < 40) {
            dir1 = 1;
        }

        huey.position.x  += (2 + (int)(Math.random() * 12)) * dir1;
        dewey.position.x += (2 + (int)(Math.random() * 6)) * dir2;
        boss.position.x  += (2 + (int)(Math.random() * 9)) * dir3;
    }

    protected void attackHeroTowardEnermy() {

        // game mode on
        // Hero fire shell
        // snow gap ? ()
        // handle key press,
        // disable another shell til this shell drop or hit enermy
        // power up snow ?
        // handle colidate enermy
        // handle health enermy
        // update state of hero

        // TODO handle onTouch, gdx way

    }

    protected void drawHeroHp() {

    }

    protected void drawTouchPad() {
//        Gdx.gl.glClearColor(0.294f, 0.294f, 0.294f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        //Move blockSprite with TouchPad. TODO remove test code
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY()*blockSpeed);
        bobSprite.setX(bobSprite.getX() + touchpad.getKnobPercentX()*blockSpeed);

        // TODO use object instead of global var
        bob.position.x -= (blockSprite.getX() + touchpad.getKnobPercentX()*heroSpeed);

        //Draw
        batch.enableBlending();
        batch.begin();
        blockSprite.draw(batch);
        bobSprite.draw(batch);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    protected void heroHitTarget() {

    }

    protected void enermyHitTarget() {

    }



}