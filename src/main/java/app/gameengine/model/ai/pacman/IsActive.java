package app.gameengine.model.ai.pacman;
import app.gameengine.Level;
import app.gameengine.model.ai.Decision;
import app.games.pacman.Ghost;

public class IsActive extends Decision {

    private Ghost ghost;

    /**
     * Constructs an IsActive decision for the given ghost.
     *
     * @param ghost the ghost
     * @param name  the decision name
     */
    public IsActive(Ghost ghost, String name) {
        super(ghost, name);
        this.ghost = ghost;
    }

    /**
     * Returns true if the ghost is in Chase or Scatter state.
     *
     * @param dt    the time delta
     * @param level the current level
     * @return true if ghost state is "Chase" or "Scatter"
     */
    @Override
    public boolean decide(double dt, Level level) {
        String state = ghost.getState();
        return state.equals("Chase") || state.equals("Scatter");
    }

    /**
     * Does nothing when executed.
     *
     * @param dt    the time delta
     * @param level the current level
     */
    @Override
    public void doAction(double dt, Level level) {
        // do nothing
    }
}
