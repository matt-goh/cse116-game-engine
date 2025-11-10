package app.gameengine.model.ai.pacman;
import java.util.ArrayList;
import app.gameengine.Level;
import app.gameengine.model.ai.Decision;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.PacmanUtils;
import app.games.pacman.Ghost;
import app.games.pacman.PacmanGame;
import app.games.pacman.PacmanLevel;

public class Scatter extends Decision {

    private Ghost ghost;
    private PacmanGame game;
    private Vector2D lastTile;

    /**
     * Constructs a Scatter decision for the given ghost.
     *
     * @param ghost the ghost
     * @param game  the pacman game
     * @param name  the decision name
     */
    public Scatter(Ghost ghost, PacmanGame game, String name) {
        super(ghost, name);
        this.ghost = ghost;
        this.game = game;
        this.lastTile = null;
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
     * Performs scatter behavior using PacmanUtils methods.
     * Moves toward scatter target (corner) instead of chasing player.
     *
     * @param dt    the time delta
     * @param level the current level
     */
    @Override
    public void doAction(double dt, Level level) {
        if (PacmanUtils.canAct(ghost, dt, lastTile)) {
            PacmanLevel pacmanLevel = game.getCurrentLevel();

            Vector2D target = PacmanUtils.getScatterTarget(pacmanLevel, ghost);
            ArrayList<Vector2D> validDirs = PacmanUtils.getValidDirs(pacmanLevel, ghost);
            Vector2D bestDir = PacmanUtils.getBestDirection(validDirs, ghost.getLocation(), target);

            if (bestDir != null) {
                ghost.setOrientation(bestDir.getX(), bestDir.getY());
            }

            lastTile = Vector2D.round(ghost.getLocation());
        }

        ghost.followPath(dt);
    }
}
