package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Admin on 11/28/2017.
 */

public class LogoScreen extends DefaultScreen {
    // TODO do we need use InputProcessor or just texture ?
    TextureRegion logo2;
    Texture logo;
    SpriteBatch batch;
    float time = 0;

    public LogoScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        // TODO handle screen ratio
//        logo = new TextureRegion(new Texture(Gdx.files.internal("data/samsung-white/logo.png")), 0, 0, 1080, 1122);
        batch = new SpriteBatch();
//        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1080, 1122);
        logo = new Texture(Gdx.files.internal("data/samsung-white/logo.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();

        batch.begin();
        batch.draw(logo, 0, 400);
        batch.end();

//        time += delta;
//        if (time > 1) {}
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            game.setScreen(new SamsungFunclubScreen(game));
        }
    }

    @Override
    public void hide() {
        Gdx.app.debug("Snow Ball Fight", "dispose intro");
        batch.dispose();
        logo.dispose();
        /*loadImage(1);
        paramGraphics.drawImage(this.imgLogo, 0, 0, 20);
        MPlay(0);
        destroyImage(1);*/
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
}
