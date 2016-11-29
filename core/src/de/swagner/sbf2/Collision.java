package de.swagner.sbf2;

import com.badlogic.gdx.math.Intersector;

public class Collision {

    private static Bullet bullet;
    private static Ship ship;

    public static void collisionCheck() {

        for (int i = 0; i< de.swagner.sbf.GameInstance.getInstance().bullets.size; i++) {
            bullet = de.swagner.sbf2.GameInstance.getInstance().bullets.get(i);
            if (bullet.alive) {
                for (int n = 0; n< de.swagner.sbf.GameInstance.getInstance().fighters.size; n++) {
                    ship =  de.swagner.sbf2.GameInstance.getInstance().fighters.get(n);
                    collisionCheck(bullet, ship);
                }
                for (int n = 0; n< de.swagner.sbf.GameInstance.getInstance().bombers.size; n++) {
                    ship =  de.swagner.sbf2.GameInstance.getInstance().bombers.get(n);
                    collisionCheck(bullet, ship);
                }
                for (int n = 0; n< de.swagner.sbf.GameInstance.getInstance().frigates.size; n++) {
                    ship =  de.swagner.sbf2.GameInstance.getInstance().frigates.get(n);
                    collisionCheck(bullet, ship);
                }
                for (int n = 0; n< de.swagner.sbf.GameInstance.getInstance().factorys.size; n++) {
                    ship =  de.swagner.sbf2.GameInstance.getInstance().factorys.get(n);
                    collisionCheck(bullet, ship);
                }
            }

        }
    }

    private static void collisionCheck(Bullet bullet, Ship ship) {
        if (bullet.id!=ship.id && ship.alive) {

            for(int i = 0; i<ship.collisionPoints.size;++i) {
                if(Intersector.isPointInPolygon(bullet.collisionPoints, ship.collisionPoints.get(i))) {
                    ship.damage(bullet.damage);
                    de.swagner.sbf2.GameInstance.getInstance().bulletHit(ship, bullet);
                    bullet.alive = false;
                    return;
                }
            }

            for(int i = 0; i<bullet.collisionPoints.size;++i) {
                if(Intersector.isPointInPolygon(ship.collisionPoints, bullet.collisionPoints.get(i))) {
                    ship.damage(bullet.damage);
                    de.swagner.sbf2.GameInstance.getInstance().bulletHit(ship, bullet);
                    bullet.alive = false;
                    return;
                }
            }
        }
    }

}
