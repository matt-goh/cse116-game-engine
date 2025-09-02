package app.gameengine.model.physics;

import app.gameengine.model.gameobjects.GameObject;

/**
 * An object with location and dimension, specifically for detecting and
 * resolving collisions.
 * 
 * @see GameObject
 * @see Collidable
 * @see Vector2D
 */
public class Hitbox {

    private Vector2D baseLocation;
    private Vector2D offset;
    private Vector2D dimensions;

    /**
     * Creates a new hitbox with the specified location and dimension.
     * 
     * @param location   the location of the hitbox
     * @param dimensions the dimensions of the hitbox
     */
    public Hitbox(Vector2D location, Vector2D dimensions) {
        this(location, dimensions, new Vector2D(0, 0));
    }

    /**
     * Creates a new hitbox with the specified location, dimension, and offset. The
     * offset specifies how far in each direction the actual location is from the
     * base direction. This is useful when the vector passed in for the
     * {@code baseLocation} is internal to some {@code GameObject}.
     * 
     * @param baseLocation the base location of the hitbox
     * @param dimensions   the dimensions of the hitbox
     * @param offset       the location offset of the hitbox
     */
    public Hitbox(Vector2D baseLocation, Vector2D dimensions, Vector2D offset) {
        this.baseLocation = baseLocation;
        this.dimensions = dimensions;
        this.offset = offset;
    }

    /**
     * Returns the location of the hitbox, including its offset. Note that this is
     * likely not the same reference as that of the location of the object, if any,
     * that possesses the hitbox, and modification of one will not impact the other.
     * 
     * @return the location of the hitbox
     */
    public Vector2D getLocation() {
        return Vector2D.add(this.baseLocation, this.offset);
    }

    /**
     * Returns the dimensions of the hitbox.
     * 
     * @return the dimensions of the hitbox
     */
    public Vector2D getDimensions() {
        return this.dimensions;
    }

    /**
     * Sets the dimensions of the hitbox.
     * 
     * @param x the width of the hitbox
     * @param y the height of the hitbox
     */
    public void setDimensions(double x, double y) {
        this.dimensions.setX(x);
        this.dimensions.setY(y);
    }

    /**
     * Returns the offset from the base location of this hitbox.
     * 
     * @return the location offset
     */
    public Vector2D getOffset() {
        return this.offset;
    }

    /**
     * Sets the offset from the base location of this hitbox.
     * 
     * @param x the x offset
     * @param y the y offset
     */
    public void setOffset(double x, double y) {
        this.offset.setX(x);
        this.offset.setY(y);
    }

}
