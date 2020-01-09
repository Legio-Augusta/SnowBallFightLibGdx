package com.littlewing.sbf.game;


import com.badlogic.gdx.Gdx;
import java.io.InputStream;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Nick Farrow
 * Read .msr (media binary combined file). .msr is optional extension. It can be
 * extracted using some binary extract tool like photo_spliter.py by Greg Lavino.
 */
public class ReadMedia
{
    public Texture[] img_arr_a = new Texture[120];
    public Texture img_b = null;
    public InputStream inputStream;
    public int[] int_arr_d = null;
    public int int_bound_e = 0;
    public String msr_media;

    private static int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    // 120x160 or 128x128px from original J2ME resolution (in some game). This case screen_width is 240px
    private static float MOBI_SCL = (float)Gdx.graphics.getWidth()/240;
    private static int MOBI_H = 320;  // JavaME height = 320px
    float SCALE = (float)SCREEN_HEIGHT/1920;

    private static int BOTTOM_SPACE = (int)(SCREEN_HEIGHT/8 + 20*MOBI_SCL); // 20 as Java phone reserved top bar shift y
    BitmapFont font;

    // For clipped sprite manual draw (try to use sprite region on clip later)
    public Texture[] img_arr_clipped = new Texture[12];

    public void destroyImage(int paramInt)
    {
        this.img_arr_a[paramInt] = null;
        System.gc();
    }

    /**
     * https://docs.oracle.com/javame/config/cldc/ref-impl/midp2.0/jsr118/javax/microedition/lcdui/Graphics.html#setClip()
     * public void setClip(int x, int y, int width, int height)
     *     Sets the current clip to the rectangle specified by the given coordinates. Rendering operations have no effect outside of the clipping area.
     *     Parameters:
     *     x - the x coordinate of the new clip rectangle
     *     y - the y coordinate of the new clip rectangle
     *     width - the width of the new clip rectangle
     *     height - the height of the new clip rectangle
     *    TODO use GDX scissor, texture-packer, texture region ...
     */
    public void mySetClip(SpriteBatch paramGraphics, int x, int y, int width, int height)
    {
        // Depend on clip type font or sprites
    }

    /**
     * use width, height of crop area to identify what sprite-packer are used
     * char_idx => character idx in sprite-packer image
     * img_idx => index of texture image in array
     * anchor => original J2ME Graphics.drawImage() anchor
     */
    public void myDrawClip(SpriteBatch paramGraphics, int char_idx, int y, int width, int height, int img_idx, int pos_x, int pos_y, int anchor) {
        if(this.msr_media.equals("font")) {
            if(height == 5) { // Checkme
                this.img_arr_a[img_idx] = new Texture("archangel/font/font_0_" + char_idx + ".png");
            } else if(height == 13) {
                this.img_arr_a[img_idx] = new Texture("archangel/font/font_1_" + char_idx + ".png");
            }

            int img_height = (int)(this.img_arr_a[img_idx].getHeight()*SCALE);
            int position_y = (int) ((MOBI_H - pos_y+anchor)*MOBI_SCL - img_height + BOTTOM_SPACE);
            paramGraphics.draw(this.img_arr_a[img_idx], (int)(pos_x * MOBI_SCL), position_y, this.img_arr_a[img_idx].getWidth()*SCALE, this.img_arr_a[img_idx].getHeight()*SCALE);
        }
    }

    /**
     *
     * Handle draw clip region by simple cut original sprite image to single one then draw each of them.
     *
     * @param paramGraphics
     * @param spriteIdx image clipped sprite index
     * @param pos_x positon x (240x320 J2ME geometry)
     * @param pos_y positon y
     * TODO handle multiple sprite region draw
     */
    public void myDrawClipRegion(SpriteBatch paramGraphics, int spriteIdx, int pos_x, int pos_y)
    {
        int img_height = (int)(this.img_arr_clipped[spriteIdx].getHeight()*SCALE);
        int position_y = (int) ((MOBI_H - pos_y)*MOBI_SCL - img_height + BOTTOM_SPACE);
        // Condition for custom position for each sprite image
        paramGraphics.draw(this.img_arr_clipped[spriteIdx], (int)(pos_x*MOBI_SCL), position_y, this.img_arr_clipped[spriteIdx].getWidth()*SCALE, this.img_arr_clipped[spriteIdx].getHeight()*SCALE);
    }

    public void destroyImage53_115()
    {
        for (int i = 53; i <= 115; i++) {
            this.img_arr_a[i] = null;
        }
        System.gc();
    }

    /**
     *
     * paramGraphics.drawImage(this.img_arr_a[paramInt1], paramInt2, paramInt3, 20);
     *
     * @param paramGraphics
     * @param spriteIdx image sprite index
     * @param pos_x positon x (240x320 J2ME geometry)
     * @param pos_y positon y
     * 20 => anchor point; It seem this gap is reserved for phone top bar: signal strength, datetime ...
     */
    public void drawImageAnchor20(SpriteBatch paramGraphics, int spriteIdx, int pos_x, int pos_y)
    {
        if (this.img_arr_a[spriteIdx] == null) {
            Gdx.app.log("ERROR", "load Media: " + this.msr_media + " idx= "+ spriteIdx);

            switch(spriteIdx) {
                // brief 17 -> 20 or may be these index are reset for reuse.
                default:
                    this.img_arr_a[spriteIdx] = new Texture("archangel/plasma_19.png");
                    break;
            }
        }

        // 53 as BOTTOM_SPACE (~ 240 = 480/2) 240/4.5 ~= 53 (tile cell)
        int img_height = (int)(this.img_arr_a[spriteIdx].getHeight()*SCALE);
        int position_y = (int) ((MOBI_H - pos_y-20)*MOBI_SCL - img_height + BOTTOM_SPACE); // anchor 20

        // Fix me hard code position
        // if(spriteIdx == 3) {} // && (paramInt1 == 3) // this.msr_media.equals("font")
        paramGraphics.draw(this.img_arr_a[spriteIdx], (int)(pos_x*MOBI_SCL), position_y, this.img_arr_a[spriteIdx].getWidth()*SCALE, this.img_arr_a[spriteIdx].getHeight()*SCALE); // 20 anchor
    }

    public ReadMedia()
    {
        readMediaStream("font");
        // reloadImageArr(0, 0);
        // reloadImageArr(1, 1);
        // for (int i = 3; i < 6; i++) {
        //     reloadImageArr(i, i);
        // }
        // closeInputStream();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(3);
        if(SCREEN_HEIGHT <= 1280) {
            font.getData().setScale(2);
        }

        this.loadClippedTexture();
    }

    // TODO prevent outofbound
    private void loadClippedTexture() {
        this.img_arr_clipped[0] = new Texture("archangel/clipped-sprite/aa_9_0" + ".png");
        this.img_arr_clipped[1] = new Texture("archangel/clipped-sprite/aa_9_1" + ".png");
        this.img_arr_clipped[2] = new Texture("archangel/clipped-sprite/aa_10_0" + ".png");
        this.img_arr_clipped[3] = new Texture("archangel/clipped-sprite/aa_10_1" + ".png");
        this.img_arr_clipped[4] = new Texture("archangel/clipped-sprite/aa_17_0" + ".png");
        this.img_arr_clipped[5] = new Texture("archangel/clipped-sprite/aa_17_1" + ".png");
        this.img_arr_clipped[6] = new Texture("archangel/clipped-sprite/aa_18_0" + ".png");
        this.img_arr_clipped[7] = new Texture("archangel/clipped-sprite/aa_18_1" + ".png");
    }

    public void destroyImage7_53()
    {
        for (int i = 7; i <= 53; i++) {
            this.img_arr_a[i] = null;
        }
        System.gc();
    }

    // Load image from binary file seem complex, in many case the img_arr (120 items) has many null value.
    // These null value lead to NullPointer exception
    public void drawImageAnchor36(SpriteBatch paramGraphics, int spriteIdx, int pos_x, int pos_y)
    {
        int img_height = this.img_arr_a[spriteIdx].getHeight();
        int position_y = (int) ((MOBI_H-pos_y+36)*MOBI_SCL-img_height/2 + BOTTOM_SPACE); // -36 fixme height/2

        paramGraphics.draw(this.img_arr_a[spriteIdx], (int)(pos_x*MOBI_SCL), position_y, this.img_arr_a[spriteIdx].getWidth()*SCALE, this.img_arr_a[spriteIdx].getHeight()*SCALE);
    }

    // TODO debug trace J2ME read text data. May be some default data stored here.
    public void drawStringGraphic(SpriteBatch paramGraphics, int paramInt1, int paramInt2, String paramString, int paramInt3)
    {
        if(paramString == null) { // dungnv FIXME
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

    public void drawGraphicStr40_122(SpriteBatch paramGraphics, int paramInt1, int paramInt2, String paramString)
    {
        if(paramString == null) { // dungnv FIXME
            return;
        }
        int position_y = (int) ((MOBI_H-paramInt2)*MOBI_SCL + BOTTOM_SPACE);
        font.setColor(1, 1, 1, 1);
        font.draw(paramGraphics, paramString, (int)(paramInt1 * MOBI_SCL), position_y);

        for (int k = 0; k < paramString.length(); k++)
        {
            int j = paramString.charAt(k);
            if ((j >= 40) && (j <= 122))
            {
                int i = j - 40;
                // paramGraphics.setClip(paramInt1 + 9 * k, paramInt2, 9, 13); // JME 747 = 83 x 9px (83 character image)
                // drawImageAnchor20(paramGraphics, 1, paramInt1 + 9 * k - i * 9, paramInt2);
                // Press Any Key
                myDrawClip(paramGraphics, i, paramInt2, 9, 13, 1, paramInt1 + 9 * k - i * 9, paramInt2, 20);
            }
        }
        // mySetClip(paramGraphics, 0, 0, 240, 320);
    }

    public void drawImageSwitch(SpriteBatch paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
        // paramGraphics.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
        // Gdx.app.log("DEBUG", "drawImageSwitch width= " + paramInt3  + " height = " + paramInt4 + " msr "+this.msr_media);
        switch (paramInt6)
        {
            case 0:
                // drawImageAnchor20(paramGraphics, 26, paramInt1, paramInt2 - paramInt4 * paramInt5);
                myDrawClip(paramGraphics, paramInt4, paramInt2, paramInt3, paramInt4, 26, paramInt1, paramInt2 - paramInt4 * paramInt5, 20);
                break;
            case 1:
                // drawImageAnchor20(paramGraphics, 27, paramInt1, paramInt2 - paramInt4 * paramInt5);
                myDrawClip(paramGraphics, paramInt4, paramInt2, paramInt3, paramInt4, 27, paramInt1, paramInt2 - paramInt4 * paramInt5, 20);
                break;
            case 2:
                // drawImageAnchor20(paramGraphics, 28, paramInt1, paramInt2 - paramInt4 * paramInt5);
                myDrawClip(paramGraphics, paramInt4, paramInt2, paramInt3, paramInt4, 28, paramInt1, paramInt2 - paramInt4 * paramInt5, 20);
                break;
        }
        // mySetClip(paramGraphics, 0, 0, 240, 320);
    }

    // TODO debug to figure out how to load image to array[120] and clean
    // The order of load is important because of index in array depend on how many item has been loaded already.
    // For example plasma has 24 sprite image, enermy has 9 ...
    public void reloadImageArr(int paramInt1, int paramInt2)
    {
        this.img_arr_a[paramInt2] = null;
        this.img_arr_a[paramInt2] = loadImage(paramInt1);
    }

    public Texture loadImage(int paramInt)
    {
        /*
        byte[] arrayOfByte = new byte[this.int_arr_d[(paramInt + 1)] - this.int_arr_d[paramInt]];
        try
        {
            if (this.int_bound_e <= this.int_arr_d[paramInt])
            {
                this.inputStream.skip(this.int_arr_d[paramInt] - this.int_bound_e);
            }
            else
            {
                this.inputStream.close();
                this.inputStream = getClass().getResourceAsStream(this.msr_media);
                this.inputStream.skip(this.int_arr_d[paramInt]);
            }
            this.inputStream.read(arrayOfByte);
            this.int_bound_e = this.int_arr_d[(paramInt + 1)];
        }
        catch (Exception localException) {}
        */
        // return Image.createImage(arrayOfByte, 0, arrayOfByte.length);
        // TODO calculate and return Texture image instead of byte stream as original J2ME
        if ( this.msr_media.equals("font") ) { // 0->5
            return new Texture("archangel/font_" + paramInt + ".png");
        } else if( this.msr_media.equals("aa") ) { // 0-24 9-33 10-34
            // 24 -> 34; 13 to 16 is Hecman transform
            return new Texture("archangel/aa_" + paramInt +".png");
        } else if( this.msr_media.equals(new String("background0")) ) { // 7 -> 17
            return new Texture("archangel/background0_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("background1")) ) {
            return new Texture("archangel/background1_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("background2")) ) {
            return new Texture("archangel/background2_" + paramInt + ".png");
        } else if(this.msr_media.equals("boss0")) {
            // this.img_arr_a[21] = new Texture("archangel/boss0_8.png");
            return new Texture("archangel/boss0_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss1")) ) {
            return new Texture("archangel/boss1_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss2")) ) {
            return new Texture("archangel/boss2_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss3")) ) {
            return new Texture("archangel/boss3_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss4")) ) {
            return new Texture("archangel/boss4_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss5")) ) {
            return new Texture("archangel/boss5_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss6")) ) {
            return new Texture("archangel/boss6_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("boss7")) ) {
            return new Texture("archangel/boss7_" + paramInt + ".png");
        } else if(this.msr_media.equals("brief")) { // 17->20
            return new Texture("archangel/brief_" + paramInt + ".png");
        } else if(this.msr_media.equals("effect")) { // 71 -> 83; effect_12 is aim; 10, 11 is target lock
            return new Texture("archangel/effect_" + paramInt + ".png");
        } else if(this.msr_media.equals("end")) {
        } else if(this.msr_media.equals("enermy0")) { // 53 -> 60
            return new Texture("archangel/enermy0_" + paramInt + ".png");
            // this.img_arr_a[22] = new Texture("archangel/enermy0_8.png");
        } else if(this.msr_media.equals(new String("enermy1")) ) { // 53 -> 60
            return new Texture("archangel/enermy1_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("enermy2")) ) {
            return new Texture("archangel/enermy2_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("enermy3")) ) {
            return new Texture("archangel/enermy3_" + paramInt + ".png");
        } else if(this.msr_media.equals("etc")) { // 3_114 0_111
            return new Texture("archangel/etc_"+ paramInt + ".png");
        } else if(this.msr_media.equals(new String("fence0")) ) { // 44 -> 51
            return new Texture("archangel/fence0_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("fence1")) ) {
            return new Texture("archangel/fence1_" + paramInt + ".png");
        } else if(this.msr_media.equals(new String("fence2")) ) {
            return new Texture("archangel/fence2_" + paramInt + ".png");
        } else if(this.msr_media.equals("intro")) { // 30
            return new Texture("archangel/intro_0.png");
        } else if(this.msr_media.equals("logo")) { // 3 img
        } else if(this.msr_media.equals("menu")) { // 13 -> 16
            return new Texture("archangel/menu_" + paramInt + ".png");
        } else if(this.msr_media.equals("open")) { // 29
            return new Texture("archangel/open_0.png");
        } else if(this.msr_media.equals("plasma")) { // 102 -> 107
            return new Texture("archangel/plasma_" + paramInt + ".png");
        } else if(this.msr_media.equals("result")) { // 6->12
            return new Texture("archangel/result_" + (paramInt) + ".png");
        } else if(this.msr_media.equals("select")) {
            return new Texture("archangel/select_" + paramInt + ".png"); // It seem index is flexible, not constant
        } else if(this.msr_media.equals("shop")) {
        } else if(this.msr_media.equals("shot")) {
        } else if(this.msr_media.equals("ui")) { // 21-23
            return new Texture("archangel/ui_" + paramInt + ".png");
        }

        return new Texture(Gdx.files.internal("archangel/fence2_6.png")); // DEBUG
    }

    public void drawImageInArr(SpriteBatch paramGraphics, int spriteIdx, int pos_x, int pos_y)
    {
        if (this.img_arr_a[spriteIdx] == null) {
            Gdx.app.log("ERROR", "load Media Arr: " + this.msr_media + " idx "+ spriteIdx);
            this.img_arr_a[spriteIdx] = new Texture("archangel/plasma_15.png");
        }
        int img_height = (int)(this.img_arr_a[spriteIdx].getHeight()*SCALE);
        int position_y = (int) ((MOBI_H-pos_y+3)*MOBI_SCL-img_height + BOTTOM_SPACE);
        // Gdx.app.log("DEBUG INARR", "IMG: " + this.msr_media + "_" + paramInt1 + " JME destroy_n_e= "+paramInt3 + " position destroy_n_e="+position_y + " height="+img_height + " top bound="+(position_y+img_height));
        paramGraphics.draw(this.img_arr_a[spriteIdx], (int)(pos_x*MOBI_SCL), position_y, this.img_arr_a[spriteIdx].getWidth()*SCALE, this.img_arr_a[spriteIdx].getHeight()*SCALE); // 3
    }

    public int readBinaryData()
            throws Exception
    {
        int i = 0;
        byte[] arrayOfByte = new byte[4];
        this.inputStream.read(arrayOfByte);
        i = arrayOfByte[0] & 0xFF;
        i += ((arrayOfByte[1] & 0xFF) << 8);
        i += ((arrayOfByte[2] & 0xFF) << 16);
        i += ((arrayOfByte[3] & 0xFF) << 24);
        return i;
    }

    public void drawStringImage(String paramString, int paramInt1, SpriteBatch paramGraphics, int paramInt2, int paramInt3)
    {
        readMediaStream(paramString);
        // paramGraphics.draw(loadImage(paramInt1), paramInt2, paramInt3); //20
        int img_height = (int)(this.img_arr_a[paramInt1].getHeight()*SCALE);
        int position_y = (int) ((MOBI_H - paramInt3 +20)*MOBI_SCL-img_height + BOTTOM_SPACE); // anchor 20
        paramGraphics.draw(this.img_arr_a[paramInt1], (int)(paramInt2*MOBI_SCL), position_y, this.img_arr_a[paramInt1].getWidth()*SCALE, this.img_arr_a[paramInt1].getHeight()*SCALE); //20
    }

    public void drawLoadImage(int paramInt1, SpriteBatch paramGraphics, int paramInt2, int paramInt3)
    {
        Texture temp = loadImage(paramInt1);
        int position_y = (int) ((MOBI_H - paramInt3 + 20)*MOBI_SCL-temp.getHeight()*SCALE + BOTTOM_SPACE); // anchor 20
        // Gdx.app.log("DEBUG Load IMG", "IMG: " + this.msr_media + "_" + paramInt1 + " JME destroy_n_e= "+paramInt3 + " position destroy_n_e="+position_y + " height="+temp.getHeight() + " top bound="+(position_y+temp.getHeight()));
        paramGraphics.draw(temp, (int)(paramInt2*MOBI_SCL), position_y, temp.getWidth()*SCALE, temp.getHeight()*SCALE); // 20
    }

    public void closeInputStream()
    {
        this.int_arr_d = null;
        try
        {
//            this.inputStream.close();
        }
        catch (Exception localException) {}
        System.gc();
    }

    /**
     * @param paramString
     *  aa.msr => 00 - 18           background0.msr  10
    boss0.msr 8                 brief.msr 0-3
    effect.msr 12               end.msr 01
    enermy0.msr 8               etc.msr 3
    fence0.msr 8                font.msr 5
    intro.msr 00                logo.msr 2
    menu.msr 3                  open.msr 00
    plasma.msr 23               result.msr 2
    select.msr 6                shop.msr 2
    shot.msr 8                  ui.msr 2
     */
    public void readMediaStream(String paramString)
    {
        this.msr_media = paramString;
        /*
        paramString = "android/assets/msr/" + paramString + ".msr";
            int i = readBinaryData() + 1;
            this.int_arr_d = new int[i];
            for (int fighter_hp = 0; fighter_hp < i; fighter_hp++) {
                this.int_arr_d[fighter_hp] = readBinaryData();
            }
        this.int_bound_e = this.int_arr_d[0];
        */
        // TODO try read file from path

        if (paramString.equals("font")) { // 0->5
            for (int i = 0; i < 6; i++) {
                this.img_arr_a[i] = new Texture("archangel/font_" + i + ".png");
            }
        } else if(paramString.equals("aa")) { // 0-24 9-33 10-34
            // 24 -> 34
            for (int i = 24; i <= 42; i++) {
                this.img_arr_a[i] = new Texture("archangel/aa_" + (i-24) +".png");
            }
        } else if( paramString.equals(new String("background0")) ) { // 7 -> 17
            for (int i = 7; i <= 17; i++) {
                this.img_arr_a[i] = new Texture("archangel/background0_" + (i-7) + ".png");
            }
        } else if( paramString.equals(new String("background1")) ) {
            for (int i = 7; i <= 17; i++) {
                this.img_arr_a[i] = new Texture("archangel/background1_" + (i-7) + ".png");
            }
        } else if( paramString.equals(new String("background2")) ) {
            for (int i = 7; i <= 17; i++) {
                this.img_arr_a[i] = new Texture("archangel/background2_" + (i-7) + ".png");
            }
        } else if( paramString.equals(new String("boss0")) ) {
            this.img_arr_a[21] = new Texture("archangel/boss0_8.png");
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss0_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss1")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss1_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss2")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss2_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss3")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss3_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss4")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss4_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss5")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss5_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss6")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss6_" + (i-62) + ".png");
            }
        } else if( paramString.equals(new String("boss7")) ) {
            for (int i = 62; i <= 68; i++) {
                this.img_arr_a[i] = new Texture("archangel/boss7_" + (i-62) + ".png");
            }
        } else if(paramString.equals("brief")) { // 17->20
            for (int i = 17; i <= 20; i++) {
                this.img_arr_a[i] = new Texture("archangel/brief_" + (i-17) + ".png");
            }
        } else if(paramString.equals("effect")) { // 71 -> 83
            for (int i = 71; i < 83; i++) {
                this.img_arr_a[i] = new Texture("archangel/effect_" + (i-71) + ".png");
            }
        } else if(paramString.equals("end")) {
        } else if( paramString.equals(new String("enermy0")) ) { // 53 -> 60
            for (int i = 53; i <= 60; i++) {
                this.img_arr_a[i] = new Texture("archangel/enermy0_" + (i-53) + ".png");
            }
            this.img_arr_a[22] = new Texture("archangel/enermy0_8.png");
        } else if( paramString.equals(new String("enermy1")) ) { // 53 -> 60
            // this.img_arr_a[22] = new Texture("archangel/enermy1_8.png"); // This override < right arrow turn guidance
            for (int i = 53; i <= 60; i++) {
                this.img_arr_a[i] = new Texture("archangel/enermy1_" + (i-53) + ".png");
            }
        } else if( paramString.equals(new String("enermy2")) ) {
            for (int i = 53; i <= 60; i++) {
                this.img_arr_a[i] = new Texture("archangel/enermy2_" + (i-53) + ".png");
            }
        } else if( paramString.equals(new String("enermy3")) ) {
            for (int i = 53; i <= 60; i++) {
                this.img_arr_a[i] = new Texture("archangel/enermy3_" + (i-53) + ".png");
            }
        } else if(paramString.equals("etc")) { // 3_114 0_111
            this.img_arr_a[114] = new Texture("archangel/etc_3.png");
            this.img_arr_a[111] = new Texture("archangel/etc_0.png");
            this.img_arr_a[112] = new Texture("archangel/etc_1.png");
        } else if( paramString.equals(new String("fence0")) ) { // 44 -> 51
            for (int i = 44; i <= 51; i++) {
                this.img_arr_a[i] = new Texture("archangel/fence0_" + (i-44) + ".png");
            }
        } else if( paramString.equals(new String("fence1")) ) {
            for (int i = 44; i <= 51; i++) {
                this.img_arr_a[i] = new Texture("archangel/fence1_" + (i-44) + ".png");
            }
        } else if( paramString.equals(new String("fence2")) ) {
            for (int i = 44; i <= 51; i++) {
                this.img_arr_a[i] = new Texture("archangel/fence2_" + (i-44) + ".png");
            }
        } else if(paramString.equals("intro")) { // 30
            this.img_arr_a[30] = new Texture("archangel/intro_0.png");
        } else if(paramString.equals("logo")) {
        } else if(paramString.equals("menu")) { // 13 -> 16
            for (int i = 13; i <= 16; i++) {
                this.img_arr_a[i] = new Texture("archangel/menu_" + (i-13) + ".png");
            }
        } else if(paramString.equals("open")) { // 29
            this.img_arr_a[29] = new Texture("archangel/open_0.png");
        } else if(paramString.equals("plasma")) { // 102 -> 107
            for (int i = 102; i <= 107; i++) {
                this.img_arr_a[i] = new Texture("archangel/plasma_" + (i-102) + ".png");
            }
            for (int i = 84; i <= 101; i++) {
                this.img_arr_a[i] = new Texture("archangel/plasma_" + (i-84) + ".png");
            }
        } else if(paramString.equals("result")) { // 6->12
            // this.img_arr_a[i] = new Texture("archangel/result_" + (i-6) + ".png");
        } else if(paramString.equals("select")) {
            // This seem conflict with 7->17 background; TODO debug J2ME to inspect reload images array logic.
            for (int i = 6; i <= 12; i++) {
                this.img_arr_a[i] = new Texture("archangel/select_" + (i-6) + ".png");
            }
        } else if(paramString.equals("shop")) {
        } else if(paramString.equals("shot")) {
        } else if(paramString.equals("ui")) { // 21-23
            for (int i = 21; i <= 23; i++) {
                this.img_arr_a[i] = new Texture("archangel/ui_" + (i-21) + ".png");
            }
        } else {
            return;
        }
    }
}
