package app.display.common.controller;

import app.Settings;
import app.gameengine.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Basic controls suitable for a top-down game.
 * <p>
 * Uses arrow keys or WASD to control directional movement. Allows for lateral
 * and diagonal movement.
 */
public class TopDownControls extends KeyboardControls {

    private static final double DEFAULT_SPEED = 7.0;
    private double runSpeed;
    private double walkSpeed;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean walkPressed;

    /**
     * Construct a new keyboard top down controller.
     * 
     * @param game the game associated with these controls.
     */
    public TopDownControls(Game game) {
        this(game, DEFAULT_SPEED);
    }

    /**
     * Construct a new keyboard platformer controller.
     * 
     * @param game     the game associated with these controls.
     * @param runspeed the maximum speed of the player
     */
    public TopDownControls(Game game, double runspeed) {
        super(game);
        this.runSpeed = runspeed;
        this.walkSpeed = this.runSpeed / 2;
        
    }

    @Override
    public void handle(KeyEvent event) {
        super.handle(event);

        if (Settings.paused()) {
            return;
        }

        this.upPressed = this.keyStates.getOrDefault(KeyCode.UP, false)
                || this.keyStates.getOrDefault(KeyCode.W, false);
        this.leftPressed = this.keyStates.getOrDefault(KeyCode.LEFT, false)
                || this.keyStates.getOrDefault(KeyCode.A, false);
        this.downPressed = this.keyStates.getOrDefault(KeyCode.DOWN, false)
                || this.keyStates.getOrDefault(KeyCode.S, false);
        this.rightPressed = this.keyStates.getOrDefault(KeyCode.RIGHT, false)
                || this.keyStates.getOrDefault(KeyCode.D, false);
        this.walkPressed = this.keyStates.getOrDefault(KeyCode.SHIFT, false);
    }

    @Override
    public void processInput(double dt) {
        double speed = this.walkPressed ? this.walkSpeed : this.runSpeed;

        if (upPressed && downPressed) {
            this.game.getPlayer().getVelocity().setY(0.0);
            this.game.getPlayer().getOrientation().setY(0.0);
            this.game.getPlayer().freezeAnimations();
        } else if (upPressed) {
            this.game.getPlayer().getVelocity().setY(-speed);
            this.game.getPlayer().setOrientation(0.0, -1.0);
            this.game.getPlayer().setAnimationState("walk_up");
        } else if (downPressed) {
            this.game.getPlayer().getVelocity().setY(speed);
            this.game.getPlayer().setOrientation(0.0, 1.0);
            this.game.getPlayer().setAnimationState("walk_down");
        } else {
            this.game.getPlayer().getVelocity().setY(0.0);
        }

        if (leftPressed && rightPressed) {
            this.game.getPlayer().getVelocity().setX(0.0);
            this.game.getPlayer().getOrientation().setX(0.0);
            this.game.getPlayer().freezeAnimations();
        } else if (leftPressed) {
            this.game.getPlayer().getVelocity().setX(-speed);
            this.game.getPlayer().setOrientation(-1.0, 0.0);
            this.game.getPlayer().setAnimationState("walk_left");
        } else if (rightPressed) {
            this.game.getPlayer().getVelocity().setX(speed);
            this.game.getPlayer().setOrientation(1.0, 0.0);
            this.game.getPlayer().setAnimationState("walk_right");
        } else {
            this.game.getPlayer().getVelocity().setX(0.0);
        }

        if (this.game.getPlayer().getVelocity().getX() != 0.0 && this.game.getPlayer().getVelocity().getY() != 0.0) {
            this.game.getPlayer().setVelocity(this.game.getPlayer().getVelocity().getX() * 0.707,
                    this.game.getPlayer().getVelocity().getY() * 0.707);
        }

        if (this.game.getPlayer().getVelocity().getX() == 0.0 && this.game.getPlayer().getVelocity().getY() == 0.0) {
            this.game.getPlayer().freezeAnimations();
        }
    }

}
