package de.swagner.sbf2;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import de.swagner.sbf.*;
import de.swagner.sbf.GameInstance;
import de.swagner.sbf.bomber.Bomber;
import de.swagner.sbf.factory.FactoryProduction;
import de.swagner.sbf.fighter.Fighter;
import de.swagner.sbf.frigate.Frigate;

/**
 * Created by nickfarow on 21/10/2016.
 */
public class Targeting {

    public static boolean onScreen(Vector2 position) {
        return onScreen(position, 0);
    }

    public static boolean onScreen(Vector2 position, float buffer) {
        return position.x >= Constants.screenLeft - buffer && position.x <= Constants.screenRight + buffer && position.y >= Constants.screenBottom - buffer && position.y <= Constants.screenTop + buffer;
    }

    /*
     * returns the closest target of the given type 0 = Fighter 1 = Bomber 2 = Frigate 3 = Factory
     */
    public static Ship getNearestOfType(Ship source, int shipType) {
        if (shipType == 0)
            return getNearestOfType(source, de.swagner.sbf.GameInstance.getInstance().fighters);
        else if (shipType == 3)
            return getFactoryWithHighestHealth(source, de.swagner.sbf.GameInstance.getInstance().factorys);
        else if (shipType == 1)
            return getNearestOfType(source, GameInstance.getInstance().bombers);
        else if (shipType == 2)
            return getNearestOfType(source, GameInstance.getInstance().frigates);
        else
            return null;
    }

    private static Ship getFactoryWithHighestHealth(Ship source, Array<Ship> ships) {
        // find the closest one!
        Ship closestShip = null;
        float highestHealth = Float.MIN_VALUE;

        for (int i = 0; i < ships.size; i++) {
            Ship ship = ships.get(i);
            float currentHealth = ship.hitPoints+ (((FactoryProduction)ship).harvestRate*500);

            if (ship.alive && source.id != ship.id && onScreen(ship.collisionCenter) && (currentHealth > highestHealth)) {
                closestShip = ship;
                highestHealth = currentHealth;
            }
        }

        return closestShip;
    }

    private static Ship getNearestOfType(Ship source, Array<Ship> ships) {
        // find the closest one!
        Ship closestShip = null;
        float closestDistanze = Float.MAX_VALUE;

        for (int i = 0; i < ships.size; i++) {
            Ship ship = ships.get(i);
            float currentDistance = source.collisionCenter.dst(ship.collisionCenter);

            if (ship.alive && source.id != ship.id && onScreen(ship.collisionCenter) && (currentDistance < closestDistanze)) {
                // skip if ship is not targeting source ship
                if(ship instanceof Fighter) {
                    if(((Fighter) ship).ai.target!=null && ((Fighter) ship).ai.target.id != source.id) {
                        continue;
                    }
                }
                if(ship instanceof Bomber) {
                    if(((Bomber) ship).ai.target!=null && ((Bomber) ship).ai.target.id != source.id) {
                        continue;
                    }
                }
                if(ship instanceof Frigate) {
                    if(((Frigate) ship).ai.target != null && ((Frigate) ship).ai.target.id != source.id) {
                        continue;
                    }
                }
                closestShip = ship;
                closestDistanze = currentDistance;
            }
        }

        return closestShip;
    }

    /*
 * return a random ship of the desired type that's in range
 * 0 = Fighter 1 = Bomber 2 = Frigate 3 = Factory
 */
    public static Ship getTypeInRange(Ship source, int shipType, float range) {
        if (shipType == 0)
            return getTypeInRange(source, de.swagner.sbf.GameInstance.getInstance().fighters, range);
        else if (shipType == 3)
            return getTypeInRange(source, de.swagner.sbf.GameInstance.getInstance().factorys, range);
        else if (shipType == 1)
            return getTypeInRange(source, de.swagner.sbf.GameInstance.getInstance().bombers, range);
        else if (shipType == 2)
            return getTypeInRange(source, de.swagner.sbf.GameInstance.getInstance().frigates, range);
        else
            return null;
    }

    /**
     * return a random ship of the desired type that's in range
     */
    private static Ship getTypeInRange(Ship source, Array<Ship> ships, float range) {
        Array<Ship> shipsInRange = new Array<Ship>();
        float range_squared = range * range;

        for (int i = 0; i < ships.size; i++) {
            Ship ship = ships.get(i);
            float currentDistance = source.collisionCenter.dst(ship.collisionCenter);

            if (ship.alive && source.id != ship.id && onScreen(ship.collisionCenter) && (currentDistance < range_squared)) {
                shipsInRange.add(ship);
            }
        }

        if (shipsInRange.size > 0) {
            return shipsInRange.get(MathUtils.random(0, shipsInRange.size - 1));
        } else {
            return null;
        }
    }
}
