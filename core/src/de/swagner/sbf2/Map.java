package de.swagner.sbf2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

public class Map {
    static int EMPTY = 0;
    static int TILE = 0xffffff;
    static int START = 0xff0000;
    static int END = 0xff00ff;
    static int DISPENSER = 0xff0100;
    static int SPIKES = 0x00ff00;
    static int ROCKET = 0x0000ff;
    static int MOVING_SPIKES = 0xffff00;
    static int LASER = 0x00ffff;

    int[][] tiles;
    public Hero bob;
    Cube cube;

    public Map () {
        loadBinary();
    }

    private void loadBinary () {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("data/cubocy/levels.png"));
        tiles = new int[pixmap.getWidth()][pixmap.getHeight()];

    }

    boolean match (int src, int dst) {
        return src == dst;
    }

    public void update (float deltaTime) {

    }

    public boolean isDeadly (int tileId) {
        return tileId == SPIKES;
    }
}
