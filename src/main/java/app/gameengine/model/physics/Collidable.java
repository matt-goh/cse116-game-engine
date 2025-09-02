package app.gameengine.model.physics;

import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * Represents an object that can participate in collision detection and
 * resolution.
 * <p>
 * The {@code Collidable} interface defines how game objects can be involved in
 * physical interactions via hitboxes. It provides methods for configuring and
 * accessing hitbox properties, such as location offset and dimensions, and
 * methods for handling collisions with other objects.
 *
 * @see Hitbox
 * @see Vector2D
 * @see DynamicGameObject
 * @see StaticGameObject
 */

public interface Collidable {

    /**
     * Access the {@code Hitbox} of this object. This includes the physical
     * location and dimensions of this object.
     * 
     * @return the {@code Hitbox}
     */
    Hitbox getHitbox();

    /**
     * Returns whether this {@code Collidable} is solid or not. A solid object is
     * generally still subject to collision, but the specifics depend on the physics
     * engine being used and the objects interacting. However, certain properties,
     * like pathfinding or projectiles, may ignore non-solid objects.
     * 
     * @return whether the object is solid
     */
    boolean isSolid();

    /**
     * Defines how this {@code Collidable} responds when colliding with a
     * {@code StaticGameObject}.
     * 
     * @param otherObject the object this {@code Collidable} is interacting with
     */
    void collideWithStaticObject(StaticGameObject otherObject);

    /**
     * Defines how this {@code Collidable} responds when colliding with a
     * {@code DynamicGameObject}.
     * 
     * @param otherObject the object this {@code Collidable} is interacting with
     */
    void collideWithDynamicObject(DynamicGameObject otherObject);

}
