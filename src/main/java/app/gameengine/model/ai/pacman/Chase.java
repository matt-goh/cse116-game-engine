package app.gameengine.model.ai.pacman;

import java.util.ArrayList;

import app.gameengine.Level;
import app.gameengine.model.ai.Decision;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.PacmanUtils;
import app.games.pacman.Ghost;
import app.games.pacman.PacmanGame;
import app.games.pacman.PacmanLevel;

public class Chase extends Decision {

    private Ghost ghost;
    private PacmanGame game;
    private Vector2D lastTile;

    /**
     * Constructs a Chase decision for the given ghost.
     *
     * @param ghost the ghost
     * @param game  the pacman game
     * @param name  the decision name
     */
    public Chase(Ghost ghost, PacmanGame game, String name) {
        super(ghost, name);
        this.ghost = ghost;
        this.game = game;
        this.lastTile = null;
    }

    /**
     * Returns true if the ghost is in Chase state.
     *
     * @param dt    the time delta
     * @param level the current level
     * @return true if ghost state is "Chase"
     */
    @Override
    public boolean decide(double dt, Level level) {
        return ghost.getState().equals("Chase");
    }

    /**
     * Performs chase behavior using PacmanUtils methods.
     * Chases the player by finding best direction toward chase target.
     *
     * @param dt    the time delta
     * @param level the current level
     */
    @Override
    public void doAction(double dt, Level level) {
        if (PacmanUtils.canAct(ghost, dt, lastTile)) {
            PacmanLevel pacmanLevel = game.getCurrentLevel();

            Vector2D target = PacmanUtils.getChaseTarget(pacmanLevel, ghost);
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
