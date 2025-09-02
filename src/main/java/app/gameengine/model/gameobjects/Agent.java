package app.gameengine.model.gameobjects;

import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.physics.Vector2D;

/**
 * A {@link DynamicGameObject} capable of thinking.
 * <p>
 * This class adds onto its parent's abilities by defining methods that allow it
 * to choose and move along paths.
 * 
 * @see DynamicGameObject
 * @see LinkedListNode
 * @see Vector2D
 */
public abstract class Agent extends DynamicGameObject {

    protected double movementSpeed = 1.0;
    private LinkedListNode<Vector2D> path;

    /**
     * Constructs an agent with the given location and max HP
     * 
     * @param x     the x location of the object
     * @param y     the y location of the object
     * @param maxHP the maximum HP of the object
     */
    public Agent(double x, double y, int maxHP) {
        super(x, y, maxHP);
    }

    public LinkedListNode<Vector2D> getPath() {
        return path;
    }

    public void setPath(LinkedListNode<Vector2D> path) {
        this.path = path;
    }

    public double getMovementSpeed() {
        return this.movementSpeed;
    }

    public void setMovementSpeed(double speed) {
        this.movementSpeed = speed;
    }

    public void followPath(double dt) {
        
    }

    @Override
    public void reset() {
        super.reset();
        this.path = null;
    }

}
