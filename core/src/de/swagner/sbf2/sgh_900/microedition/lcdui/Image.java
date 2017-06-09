package de.swagner.sbf2.sgh_900.microedition.lcdui;

/**
 * Created by Admin on 6/9/2017.
 */

public class Image {

    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) {
        return new Image();
    }

    public static Image createImage(Image source) {
        return source;
    }

    // TODO implement gdx/android InputStream
   /* public static Image createImage(InputStream stream) {

    }*/

    public static Image createImage(String name) {
        return new Image();
    }
}
