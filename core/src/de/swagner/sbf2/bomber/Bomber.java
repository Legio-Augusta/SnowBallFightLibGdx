package de.swagner.sbf2.bomber;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import de.swagner.sbf2.GameInstance;

/**
 * Created by nickfarow on 21/10/2016.
 */
public class Bomber extends de.swagner.sbf2.Ship {
    public BomberAI ai = new BomberAI(this);

    public Bomber(int id, Vector2 position, Vector2 facing) {
        super(id, position, facing);

        turnSpeed = 45f;
        accel = 45.0f;
        hitPoints = 440;

        switch (id) {
            case 1:
                this.set(de.swagner.sbf2.Resources.getInstance().bomberP1);
                break;
            case 2:
                this.set(de.swagner.sbf2.Resources.getInstance().bomberP2);
                break;
            case 3:
                this.set(de.swagner.sbf2.Resources.getInstance().bomberP3);
                break;
            default:
                this.set(de.swagner.sbf2.Resources.getInstance().bomberP4);
                break;
        }
        this.setOrigin(this.getWidth()/2, this.getHeight()/2);
    }

    @Override
    public void draw(Batch batch) {
        ai.update();

        super.draw(batch);
    }

    public void shoot(int approach) {
        Vector2 bombFacing = new Vector2().set(facing).rotate(90*approach);
        GameInstance.getInstance().bullets.add(new Bomb(id, collisionCenter, bombFacing));

    }
}