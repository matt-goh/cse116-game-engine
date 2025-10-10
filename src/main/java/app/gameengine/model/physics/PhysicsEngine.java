package app.gameengine.model.physics;

import java.util.ArrayList;

import app.gameengine.Level;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * Physics engine for handling collision detection and resolution within a game.
 * <p>
 * This class is meant to be the used when updating objects to apply physics and
 * object interactions. Whenever objects collide, that should be observed and
 * handled from here.
 * 
 * @see Collidable
 * @see DynamicGameObject
 * @see StaticGameObject
 * @see Level
 */
public class PhysicsEngine {

    /**
     * Update the physics for an entire level. This means updating each dynamic
     * object according to its velocity, and handling all collisions within the
     * level.
     * 
     * @param dt    the time elapsed since the last update, in seconds
     * @param level the level being updated
     */
    public void updateLevel(double dt, Level level) {
        for (DynamicGameObject gameObject : level.getDynamicObjects()) {
            updateObject(dt, gameObject);
        }
        processAllCollisions(level);
    }

    /**
     * Update a single dynamic object according to its velocity.
     * 
     * @param dt     the time elapsed since the last update, in seconds
     * @param object the object being updated
     */
    public void updateObject(double dt, DynamicGameObject object) {
        // Get the current location and velocity
        Vector2D currentLocation = object.getLocation();
        Vector2D velocity = object.getVelocity();

        // Calculate the change in position (delta = velocity * time)
        double deltaX = velocity.getX() * dt;
        double deltaY = velocity.getY() * dt;

        // Calculate the new location
        double newX = currentLocation.getX() + deltaX;
        double newY = currentLocation.getY() + deltaY;

        // Set the object's new location using the correct method signature
        object.setLocation(newX, newY);
    }

    /**
     * Detect whether a collision between two hitboxes has occurred. A collision is
     * defined as the two hitboxes having an overlapping distance of strictly
     * greater than 0.
     * 
     * @param hitbox1 the first hitbox
     * @param hitbox2 the second hitbox
     * @return {@code true} if a collision is occurring, {@code false} otherwise
     */
    public boolean detectCollision(Hitbox hitbox1, Hitbox hitbox2) {
        // Get the position and dimensions of the first hitbox
        double h1x = hitbox1.getLocation().getX();
        double h1y = hitbox1.getLocation().getY();
        double h1w = hitbox1.getDimensions().getX();
        double h1h = hitbox1.getDimensions().getY();

        // Get the position and dimensions of the second hitbox
        double h2x = hitbox2.getLocation().getX();
        double h2y = hitbox2.getLocation().getY();
        double h2w = hitbox2.getDimensions().getX();
        double h2h = hitbox2.getDimensions().getY();

        // Check for non-collision (AABB collision detection).
        // If any of these are true, they CANNOT be colliding.
        boolean noOverlap = (h1x + h1w <= h2x) || // h1 is completely to the left of h2
                (h1x >= h2x + h2w) || // h1 is completely to the right of h2
                (h1y + h1h <= h2y) || // h1 is completely above h2
                (h1y >= h2y + h2h);   // h1 is completely below h2

        // If there isn't a "no overlap" condition, they must be colliding.
        return !noOverlap;
    }

    /**
     * Returns the minimum overlapping distance of two hitboxes. If the hitboxes are
     * colliding, this distance should be greater than 0. Otherwise, they are not
     * colliding.
     * 
     * @param hitbox1 the first hitbox
     * @param hitbox2 the second hitbox
     * @return the minimum overlapping distance
     */
    public double getOverlap(Hitbox hitbox1, Hitbox hitbox2) {
        if (!detectCollision(hitbox1, hitbox2)) {
            return 0.0;
        }

        double h1Left = hitbox1.getLocation().getX();
        double h1Right = h1Left + hitbox1.getDimensions().getX();
        double h1Top = hitbox1.getLocation().getY();
        double h1Bottom = h1Top + hitbox1.getDimensions().getY();

        double h2Left = hitbox2.getLocation().getX();
        double h2Right = h2Left + hitbox2.getDimensions().getX();
        double h2Top = hitbox2.getLocation().getY();
        double h2Bottom = h2Top + hitbox2.getDimensions().getY();

        // Calculate minimum distance to separate in each direction
        double xSeparation = Math.min(h1Right - h2Left, h2Right - h1Left);
        double ySeparation = Math.min(h1Bottom - h2Top, h2Bottom - h1Top);

        return Math.min(xSeparation, ySeparation);
    }

    /**
     * Process all collisions within a level. This means that for each dynamic
     * object, detect which other dynamic or static objects it is colliding with,
     * and defer collision behavior to the respective objects.
     * 
     * @param level the level being updated
     */
    public void processAllCollisions(Level level) {
        ArrayList<DynamicGameObject> dynamicObjects = level.getDynamicObjects();
        ArrayList<StaticGameObject> staticObjects = level.getStaticObjects();

        for (int i = 0; i < dynamicObjects.size(); i++) {
            DynamicGameObject object1 = dynamicObjects.get(i);
            for (int j = i + 1; j < dynamicObjects.size(); j++) {
                DynamicGameObject object2 = dynamicObjects.get(j);
                if (detectCollision(object1.getHitbox(), object2.getHitbox())) {
                    object1.collideWithDynamicObject(object2);
                    object2.collideWithDynamicObject(object1);
                }
            }
            for (StaticGameObject staticObject : staticObjects) {
                if (detectCollision(object1.getHitbox(), staticObject.getHitbox())) {
                    staticObject.collideWithDynamicObject(object1);
                    object1.collideWithStaticObject(staticObject);
                }
            }
        }
    }

}
