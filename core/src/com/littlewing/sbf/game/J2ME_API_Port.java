package com.littlewing.sbf.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class J2ME_API_Port {
    private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    // 120x160 or 128x128px from original J2ME resolution (in some game). This case screen_width is 240px
    private static float MOBI_SCL = (float)Gdx.graphics.getWidth()/240; // FIXME 4.5 is not integer
    private static int MOBI_H = 320;  // JavaME height = 320px

    private static int VIEW_PORT_HEIGHT = (int)SCREEN_HEIGHT*3/4;
    private static int BOTTOM_SPACE = (int)(SCREEN_HEIGHT/8 + 20*MOBI_SCL); // 20 as Java phone reserved top bar shift y


    // private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    float SCALE = (float)SCREEN_HEIGHT/1920;

    private Texture[] imgColor; // For fillRect with color; TODO color constant and add remain color png

    public J2ME_API_Port() {
        imgColor = new Texture[6];
        for (int i = 0; i < 6; i++) {
            imgColor[i] = new Texture("data/sprites/color-" + i + ".png");
        }
    }

    public void loadBrief1(SpriteBatch paramGraphics, int o, int p, SnowBallFightME archAngel)
    {

    }

    public void simple_arm_helper(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int o, int p, SnowBallFightME archAngel)
    {
        o = paramInt2;
        p = paramInt1;
        machineShopLogic(paramGraphics, paramInt1, paramInt2, archAngel);
    }

    public void machineShopLogic(SpriteBatch paramGraphics, int paramInt1, int paramInt2, SnowBallFightME archAngel)
    {

    }

    public void displayGameOver(SpriteBatch paramGraphics, SnowBallFightME archAngel)
    {

    }

    public void drawGameMenu(SpriteBatch paramGraphics, int l, SnowBallFightME archAngel)
    {

    }

    public void briefOpen(SpriteBatch paramGraphics, int o, int p, SnowBallFightME archAngel)
    {

    }

    public void briefAbout(SpriteBatch paramGraphics, int o, int p, int l, SnowBallFightME archAngel)
    {

    }

    public void simple_helper2(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int o, int p, SnowBallFightME archAngel)
    {
        o = paramInt2;
        p = paramInt1;
        draw_str_helper(paramGraphics, paramInt1, paramInt2, archAngel);
    }

    public void draw_str_helper(SpriteBatch paramGraphics, int paramInt1, int paramInt2, SnowBallFightME archAngel)
    {
//        int i1 = paramInt2;
//        int i2 = 5;
//        archAngel.readText.processTxt(paramInt1);
//        String str;
//        while ((str = archAngel.readText.buildString()) != null)
//        {
//            //return; // Avoid draw string error outOfBound
//            // paramGraphics.setColor(16777215);
//            if (archAngel.screen == 4) {
//                // paramGraphics.drawString(str, 120, i1, 17);
//                drawString(paramGraphics, 120, i1, str, 17);
//            }
//            i1 += 17;
//        }
    }

    public void draw_system_setin(SpriteBatch paramGraphics, int l, int o, int p, int q, int t, int x, int y, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    public void simple_helper(SpriteBatch paramGraphics, SnowBallFightME archAngel)
    {

    }

    public void setup2(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int o, int p, int q, int t, int x, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    public void draw_arm_shop_menu(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int q, int t, int x, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    /**
     * Data embeded in text file, like inline/custom Database.
     * @param paramGraphics
     * @param paramInt1
     * @param paramInt2
     */
    public void drawDataInTxt(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int t, int x, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    public void b(SpriteBatch paramGraphics, int paramInt, int x, int t, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    public void draw_ammunation_buy(SpriteBatch paramGraphics, int paramInt1, boolean paramBoolean, int paramInt2, int x, int t, String[][] str_arr_w, SnowBallFightME archAngel, ReadText readText)
    {

    }

    public void fillRect(SpriteBatch batch, int x, int y, int width, int height, int color) {
        // Hard code default width x height of color img: 12x12 px
        float scaleY = (float) (height*MOBI_SCL / 12);
        float scaleX = (float) (width*MOBI_SCL / 12);
        // (Texture, float x, float destroy_n_e, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY)
        int pos_x = (int) (MOBI_SCL*x);
        int pos_y = (int) ((MOBI_H - y)*MOBI_SCL - imgColor[color].getHeight()*scaleY + BOTTOM_SPACE);

        batch.draw(imgColor[color], pos_x, pos_y, 0, 0, imgColor[color].getWidth(), imgColor[color].getHeight(), scaleX, scaleY, 0, 0, 0, (int)(imgColor[color].getWidth()*scaleX), (int)(imgColor[color].getHeight()*scaleY), false, false);
    }

    public void drawImage(SpriteBatch batch, Texture image, int x, int y, int anchor_x) {

        int img_height = (int)(image.getHeight()*SCALE);
        int position_y = (int) ((MOBI_H - y -20)*MOBI_SCL - img_height + BOTTOM_SPACE); // anchor 20

        // Fix me hard code position
        // if(spriteIdx == 3) {} // && (paramInt1 == 3) // this.msr_media.equals("font")
        batch.draw(image, (int)(x*MOBI_SCL), position_y, image.getWidth()*SCALE, image.getHeight()*SCALE); // 20 anchor
    }

}
