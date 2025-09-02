package app.gameengine.utils;

import java.util.ArrayList;

import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.physics.Vector2D;

/**
 * Static class containing general game utilities.
 * 
 * @see Game
 * @see Level
 * @see Vector2D
 */
public class GameUtils {

    /**
     * Prevent instantiation, as this class is intended to be static.
     */
    private GameUtils() {
    }

    /**
     * Returns whether a given vector is within the bounds of a level, inclusive of
     * the edge tiles.
     * <p>
     * For example, if the level has a width and height of 5, valid vector
     * components range from 0-4.
     * 
     * @param level the level
     * @param v     the vector
     * @return {@code true} if the vector is within the level, {@code false}
     *         otherwise
     */
    public static boolean isInBounds(Level level, Vector2D v) {
        return v.getX() >= 0 && v.getX() < level.getWidth() && v.getY() >= 0 && v.getY() < level.getHeight();
    }

    /**
     * Returns whether a given vector is within the bounds of another vector.
     * <p>
     * For example, if the bounds are <5, 5>, the valid vector components range from
     * 0-4.
     * 
     * @param v      the vector to check
     * @param bounds the X and Y bounds (exclusive)
     * @return {@code true} if the vector is within the bounds, {@code false}
     *         otherwise
     */
    public static boolean isInBounds(Vector2D v, Vector2D bounds) {
        return v.getX() >= 0 && v.getX() < bounds.getX() && v.getY() >= 0 && v.getY() < bounds.getY();
    }

    /**
     * Deal splash damage to all enemies in the level within a certain radius.
     * Enemies further away from the explosion take less damage, and enemies at a
     * distance of {@code radius} take one point of damage.
     * 
     * @param level     the level in which the splash damage is being dealt
     * @param maxDamage the amount of damage to be dealt
     * @param radius    how wide the splash damage reaches
     * @param origin    the point from which the splash damage originates
     */
    public static void dealSplashDamage(Level level, int maxDamage, double radius, Vector2D origin) {
        dealSplashDamage(level, maxDamage, radius, origin, new ArrayList<>());
    }

    /**
     * Deal splash damage to all enemies in the level within a certain radius.
     * Enemies further away from the explosion take less damage, and enemies at a
     * distance of {@code radius} take 1 point of damage. The exceptions can be used
     * to prevent damage being dealt to specific objects, such as the one who fired
     * the projectile.
     * 
     * @param level      the level in which the splash damage is being dealt
     * @param maxDamage  the amount of damage to be dealt
     * @param radius     how wide the splash damage reaches
     * @param origin     the point from which the splash damage originates
     * @param exceptions a list of objects immune to damage from the splash damage
     */
    public static void dealSplashDamage(Level level, int maxDamage, double radius, Vector2D origin,
            ArrayList<DynamicGameObject> exceptions) {
        for (DynamicGameObject other : level.getDynamicObjects()) {
            double distance = Vector2D.euclideanDistance(origin, other.getLocation());
            if (distance < radius && !exceptions.contains(other)) {
                other.takeDamage(Math.clamp((int) (maxDamage - (maxDamage * distance / radius)), 1, maxDamage));
            }
        }
    }

}
