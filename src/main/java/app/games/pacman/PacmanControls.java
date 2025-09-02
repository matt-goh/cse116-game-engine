package app.games.pacman;

import java.util.HashMap;
import java.util.Map;

import app.Settings;
import app.display.common.controller.KeyboardControls;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Keyboard controls for Pacman.
 * <p>
 * These are relatively simple controls that maintain velocity in the most
 * recently pressed direction. Upon pressing a new direction, it is queued, and
 * as soon as that is a valid direction of movement it is performed.
 * 
 * @see KeyboardControls
 * @see PacmanGame
 * @see PacmanLevel
 */
public class PacmanControls extends KeyboardControls {

    private static HashMap<String, Vector2D> directionToVector = new HashMap<>(Map.of(
            "up", new Vector2D(0, -1),
            "down", new Vector2D(0, 1),
            "left", new Vector2D(-1, 0),
            "right", new Vector2D(1, 0)));

    private String direction = "left";
    private Vector2D intendedDirection = directionToVector.get(direction);
    private PacmanGame game;
    private double speed = 10.0;

    public PacmanControls(PacmanGame game) {
        super(game);
        this.game = game;
    }

    @Override
    public void handle(KeyEvent event) {
        super.handle(event);

        if (event.getEventType() != KeyEvent.KEY_PRESSED) {
            return;
        }

        boolean upPressed = event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W;
        boolean downPressed = event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S;
        boolean leftPressed = event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A;
        boolean rightPressed = event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D;

        if (Settings.paused() && (upPressed || downPressed || leftPressed || rightPressed)) {
            this.game.unpause();
        }

        if (upPressed) {
            this.direction = "up";
            this.intendedDirection = directionToVector.get(this.direction);
        } else if (downPressed) {
            this.direction = "down";
            this.intendedDirection = directionToVector.get(this.direction);
        } else if (leftPressed) {
            this.direction = "left";
            this.intendedDirection = directionToVector.get(this.direction);
        } else if (rightPressed) {
            this.direction = "right";
            this.intendedDirection = directionToVector.get(this.direction);
        }
    }

    @Override
    public void processInput(double dt) {
        Vector2D location = game.getPlayer().getLocation();
        Vector2D roundedLoc = Vector2D.round(location);

        if ((Vector2D.euclideanDistance(location, roundedLoc) < this.speed * dt
                || this.game.getPlayer().getVelocity().magnitude() < 1e-5) && intendedDirection != null) {
            Vector2D intendedLocation = Vector2D.add(roundedLoc, intendedDirection);
            if (!this.game.getCurrentLevel().getWalls().containsKey(intendedLocation)) {
                this.game.getPlayer().setOrientation(intendedDirection.getX(), intendedDirection.getY());
                Vector2D velocity = Vector2D.mul(this.intendedDirection, this.speed);
                this.game.getPlayer().setVelocity(velocity.getX(), velocity.getY());
                this.game.getPlayer().getLocation().round();
                intendedDirection = null;
                this.game.getPlayer().setAnimationState("move_" + this.direction);
            }
        } else if (this.game.getPlayer().getVelocity().magnitude() < 1e-5) {
            this.game.getPlayer().setAnimationState("default_" + this.direction);
        }
    }

}
