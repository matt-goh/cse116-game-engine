package app.gameengine.model.ai.pacman;
import app.gameengine.Level;
import app.gameengine.model.ai.Decision;
import app.gameengine.model.physics.Vector2D;
import app.games.pacman.Ghost;

public class Idle extends Decision {

    private Ghost ghost;

    /**
     * Constructs an Idle decision for the given ghost.
     *
     * @param ghost the ghost
     * @param name  the decision name
     */
    public Idle(Ghost ghost, String name) {
        super(ghost, name);
        this.ghost = ghost;
    }

    /**
     * Always returns false (this is a leaf action node).
     *
     * @param dt    the time delta
     * @param level the current level
     * @return false
     */
    @Override
    public boolean decide(double dt, Level level) {
        return false;
    }

    /**
     * Makes the ghost bounce up and down at home location.
     * If velocity magnitude is 0, negates orientation and follows path.
     * If velocity magnitude is greater than 0, does nothing.
     *
     * @param dt    the time delta
     * @param level the current level
     */
    @Override
    public void doAction(double dt, Level level) {
        if (ghost.getVelocity().magnitude() > 0) {
            return;
        }

        Vector2D orientation = ghost.getOrientation();
        ghost.setOrientation(-orientation.getX(), -orientation.getY());
        ghost.followPath(dt);
    }
}
