package app.gameengine.model.gameobjects;

import app.gameengine.Level;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.PathfindingUtils;

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
        if (this.path == null) {
            this.setVelocity(0, 0);
            return;
        }

        Vector2D targetTile = this.path.getValue();
        double distance = Vector2D.euclideanDistance(this.getLocation(), targetTile);

        if (distance < this.movementSpeed * dt) {
            this.setLocation(targetTile.getX(), targetTile.getY());
            this.setPath(this.path.getNext());
            this.setVelocity(0, 0);
        } else {
            Vector2D direction = Vector2D.sub(targetTile, this.getLocation());
            Vector2D normalizedDirection = Vector2D.normalize(direction);

            this.setOrientation(normalizedDirection.getX(), normalizedDirection.getY());
            this.setVelocity(normalizedDirection.getX() * this.movementSpeed, normalizedDirection.getY() * this.movementSpeed);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.path = null;
    }

}
