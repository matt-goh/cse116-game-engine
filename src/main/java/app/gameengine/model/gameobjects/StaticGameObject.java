package app.gameengine.model.gameobjects;

import app.gameengine.Game;
import app.gameengine.Level;

/**
 * A {@code GameObject} not capable of movement.
 * <p>
 * One of the two main subsets of {@code GameObject}s, objects of this class are
 * not capable of movement, except through direct access and modification to the
 * internal location vector. This class should be used for objects like walls
 * and doors that do not move, but are still solid and prevent collision and
 * pathing.
 * 
 * @see GameObject
 * @see DynamicGameObject
 * @see Game
 * @see Level
 */
public abstract class StaticGameObject extends GameObject {

    /**
     * Constructs a new static object at the given location.
     * 
     * @param x the x location of the object
     * @param y the y location of the object
     */
    public StaticGameObject(double x, double y) {
        super(x, y);
    }

//    @Override
//    public void setLocation(double x, double y) {
//        // StaticGameObject cannot move
//    }

    @Override
    public boolean isSolid() {
        return true;
    }

}
