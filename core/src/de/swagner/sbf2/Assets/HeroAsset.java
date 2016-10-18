package de.swagner.sbf2.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by nickfarow on 13/10/2016.
 */
public class HeroAsset {

    public static Texture texture_sheet;
    public static Texture texture_sheet2;
    public static Texture texture_sheet3;
    public static Texture texture_sheet4;
    public static Sprite hero1;
    public static Sprite hero2;
    public static Sprite hero3;
    public static Sprite hero4;
    public static Sprite hero5;
    public static Sprite hero6;
    public static Sprite hero7;
    public static Sprite hero8;

    private static final String TAG = "Testing: ";

    public static void load(){
        texture_sheet = new Texture("data/samsung-white/hero3_1.png");
        texture_sheet2 = new Texture("data/samsung-white/hero3_2.png");
        texture_sheet3 = new Texture("data/samsung-white/hero3_3.png");
        texture_sheet4 = new Texture("data/samsung-white/hero3_4.png");
        hero1 = new Sprite (texture_sheet, 1, 0, 55, 60);
        hero2 = new Sprite (texture_sheet2, -1, 0, 170, 60);
        hero3 = new Sprite (texture_sheet3, 0, 0, 170, 60);
        hero4 = new Sprite (texture_sheet4, 0, 0, 30, 60);
        hero5 = new Sprite (texture_sheet, 0, 0, 70, 60);
        hero6 = new Sprite (texture_sheet2, 0, -1, 60, 60);
        hero7= new Sprite (texture_sheet3, 0, 0, 77, 60);
        hero8= new Sprite (texture_sheet4, 0, 10, 70, 60);

        hero1.setPosition(540, 60);
        hero2.setPosition(300, 60);
        hero3.setPosition(400, 70);
        hero4.setPosition(600, 80);
        hero5.setPosition(700, 20);
        hero6.setPosition(850, 60);
        hero7.setPosition(900, 60);
        hero8.setPosition(850, 60);

        hero1.setScale(2, 2);

//        hero1.flip(false, true);
//        hero2.flip(false, true);
//        hero3.flip(false, true);
//        hero4.flip(false, true);
//        hero5.flip(false, true);
//        hero6.flip(false, true);
//        hero7.flip(false, true);
//        hero8.flip(false, true);
    }

}