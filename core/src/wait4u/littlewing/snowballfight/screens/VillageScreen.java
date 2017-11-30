package wait4u.littlewing.snowballfight.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import wait4u.littlewing.snowballfight.OverlapTester;

/**
 * Created by Admin on 11/29/2017.
 */

public class VillageScreen extends DefaultScreen {
    private SpriteBatch batch;
    Texture imgVill;
    Texture imgSchool;
    Texture samsung_blue;
    BitmapFont font;
    Vector3 touchPoint;

    public VillageScreen(Game game) {
        super(game);
        touchPoint = new Vector3();
    }
    @Override
    public void show() {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(6);

        imgVill = new Texture("data/samsung-white/village.png");
        imgSchool = new Texture("data/samsung-white/school.png");
        samsung_blue = new Texture("data/samsung-white/samsung_blue.png");
        batch = new SpriteBatch();
    }
    public void render(float delta) {
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(samsung_blue, 0, 0, SCREEN_HEIGHT, SCREEN_HEIGHT);
        batch.draw(imgVill, 0, 480);
        // font.draw(batch, "Item Shop", 110, 1300); // orig: 13 27 20-anchor
        // font.draw(batch, "Drugstore", 110, 1160); // orig: 13, 44, 20
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
            // Can not use font getBound(), so hard code position of text Bitmap.
            touchPoint.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            Rectangle selectButtonBound = new Rectangle(10, 480, SCREEN_WIDTH/2-32, 100);
            Rectangle optionsButtonBound = new Rectangle(SCREEN_WIDTH/2+12, 480, SCREEN_WIDTH/2-12, 100);
            if(OverlapTester.pointInRectangle(selectButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                game.setScreen(new GameScreen(game, 6));
            } else if(OverlapTester.pointInRectangle(optionsButtonBound, touchPoint.x, (SCREEN_HEIGHT-touchPoint.y) )) {
                game.setScreen(new GameScreen(game, 6));
            }
        }
    }

    @Override
    public void hide() {

    }
    /*paramGraphics.drawImage(this.imgVill, 0, 0, 20);
        paramGraphics.setColor(14994350);
        if (this.last_stage / 10 == 1)
        {
            paramGraphics.drawImage(this.imgSchool, 78, 87, 3);
            paramGraphics.drawImage(this.imgSchool, 49, 87, 3);
            paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
            paramGraphics.fillRect(76, 73, 6, 5);
            paramGraphics.fillRect(47, 73, 6, 5);
            paramGraphics.setColor(15132390);
            paramGraphics.fillRect(17, 44, 6, 5);
        }
        else if (this.last_stage / 10 == 2)
        {
            paramGraphics.drawImage(this.imgSchool, 49, 87, 3);
            paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
            paramGraphics.setColor(14994350);
            paramGraphics.fillRect(47, 73, 6, 5);
            paramGraphics.setColor(15132390);
            paramGraphics.fillRect(17, 44, 6, 5);
        }
        else if (this.last_stage / 10 == 3)
        {
            paramGraphics.drawImage(this.imgSchool, 19, 58, 3);
            paramGraphics.setColor(15132390);
            paramGraphics.fillRect(17, 44, 6, 5);
        }
        paramGraphics.drawImage(this.imgCh, this.h_x, this.h_y, 20);
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
        }*/
}
