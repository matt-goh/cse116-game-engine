package app.games.mario;

import app.display.common.controller.KeyboardControls;
import app.display.common.controller.PlatformerControls;
import app.gameengine.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Keyboard controls for Mario.
 * <p>
 * An honest effort to implement accurate Mario controls. The various physics
 * constants below are based on the information in
 * data/resources/smb_playerphysics.png, also found at <a
 * href=https://web.archive.org/web/20130807122227/http://i276.photobucket.com/albums/kk21/jdaster64/smb_playerphysics.png>smb_playerphysics.png</a>.
 * <p>
 * The values are written as hexidecimal because this source did, and it was
 * easier to include the hex values directly. The names are also similar to
 * those in the document, to make them easier to find. Most of these constants
 * are self explanatory, but explanations have been added for some of the more
 * complicated ones.
 * 
 * @see PlatformerControls
 * @see KeyboardControls
 * @see MarioGame
 * @see MarioLevel
 */
public class MarioControls extends PlatformerControls {

    // ------------------------- Conversion constants -------------------------
    /**
     * Conversion factor from subsubsub-pixels/frame to units/second. The former is
     * the unit used in the referenced document for velocities, and the latter is
     * useable by the game engine.
     */
    private static final double VEL_CONVERT = 60 / 65536.0;
    /**
     * Conversion factor from subsubsub-pixels/frame^2 to units/second^2. The former
     * is the unit used in the referenced document for accelerations, and the latter
     * is useable by the game engine.
     */
    private static final double ACCEL_CONVERT = 3600 / 65536.0;

    // --------------------------- Ground constants ---------------------------
    /**
     * The minimum speed at which Mario will walk. If you begin moving left or
     * right, this speed will be set.
     */
    private static final double MIN_WALK_SPEED = 0x130 * VEL_CONVERT;
    private static final double MAX_WALK_SPEED = 0x1900 * VEL_CONVERT;
    private static final double MAX_RUN_SPEED = 0x2900 * VEL_CONVERT;
    private static final double WALK_ACCELERATION = 0x98 * ACCEL_CONVERT;
    private static final double RUN_ACCELERATION = 0xE4 * ACCEL_CONVERT;
    private static final double RELEASE_DECELERATION = 0xD0 * ACCEL_CONVERT;
    private static final double SKID_DECELERATION = 0x1A0 * ACCEL_CONVERT;
    /**
     * This one is weird. Basically, if Mario is moving left or right, and the
     * opposing directional key is pressed, he will begin to decelerate, and to move
     * in the other direction. {@code SKID_TURNAROUND_SPEED} is the speed at which
     * he will stop moving in the current direction and begin moving in the other
     * direction, essentially making it easier to turn around. This also means that
     * Mario will "skip over" a velocity of 0.
     */
    private static final double SKID_TURNAROUND_SPEED = 0x900 * VEL_CONVERT;

    // ----------------------------- Air constants ----------------------------
    private static final double AIR_FRICTION = 0.0; // No air friction, Mario maintains air momentum
    private static final double AIR_ACCELERATION_FULL = 0xE4 * ACCEL_CONVERT;
    private static final double AIR_ACCELERATION_SLOW = 0x98 * ACCEL_CONVERT;
    private static final double AIR_TURNAROUND_FULL = 0xE4 * ACCEL_CONVERT;
    private static final double AIR_TURNAROUND_MEDIUM = 0xD0 * ACCEL_CONVERT;
    private static final double AIR_TURNAROUND_SLOW = 0x98 * ACCEL_CONVERT;
    private static final double AIR_TURNAROUND_SPEED_THRESHOLD = 0x1900 * VEL_CONVERT;
    /**
     * This is another weird one. The game records the horizontal speed at the time
     * when Mario jumps. If player speed is below the
     * {@code AIR_TURNAROUND_SPEED_THRESHOLD} but the recorded jump speed is above
     * this the {@code AIR_TURNAROUND_JUMP_SPEED_THRESHOLD}, the medium turnaround
     * acceleration is used. Otherwise, the low turnaround acceleration is used.
     */
    private static final double AIR_TURNAROUND_JUMP_SPEED_THRESHOLD = 0x1D00 * VEL_CONVERT;

    // ------------------------ Jump/gravity constants ------------------------
    private static final double JUMP_SPEED_FULL = 0x5000 * VEL_CONVERT;
    private static final double JUMP_SPEED_SLOW = 0x4000 * VEL_CONVERT;
    private static final double GRAVITY_JUMP_FAST = 0x0280 * VEL_CONVERT;
    private static final double GRAVITY_JUMP_MEDIUM = 0x1e0 * VEL_CONVERT;
    private static final double GRAVITY_JUMP_SLOW = 0x200 * VEL_CONVERT;
    private static final double GRAVITY_FALL_FAST = 0x900 * VEL_CONVERT;
    private static final double GRAVITY_FALL_MEDIUM = 0x600 * VEL_CONVERT;
    private static final double GRAVITY_FALL_SLOW = 0x700 * VEL_CONVERT;
    private static final double GRAVITY_THRESHOLD_FAST = 0x2500 * VEL_CONVERT;
    private static final double GRAVITY_THRESHOLD_SLOW = 0x1000 * VEL_CONVERT;
    private static final double MAX_FALL_SPEED = 0x4800 * VEL_CONVERT;

    // --------------------------- Other constants ----------------------------
    /**
     * I kinda eyeballed this one. Supposedly it should be exactly 16 frames, or
     * ~0.267 seconds, but it didn't feel right.
     */
    private static final double MAX_JUMP_TIME = 0.28;
    /**
     * All of the gravity constants were way too small. I don't know if I'm applying
     * the gravity differently than the actual game or something, but I used this
     * constant to adjust all types of gravity, and tweaked it until it felt
     * right.
     */
    private static final double GRAVITY_FACTOR = 30;
    /**
     * Similar to the {@code GRAVITY_FACTOR}, and probably stemming from the same
     * issue, the jump speed was just off. All jump speeds are adjusted by this
     * factor, which I just tweaked until it felt right.
     */
    private static final double JUMP_SPEED_FACTOR = 0.7;

    // ------------------------- Animation constants --------------------------
    private static final double ANIMATION_TIME_FAST = 2 / 60.0;
    private static final double ANIMATION_TIME_MEDIUM = 4 / 60.0;
    private static final double ANIMATION_TIME_SLOW = 10 / 60.0;
    /**
     * I eyeballed this one too. It seems to switch to fast animations as soon as
     * Mario starts sprinting, so this is slightly faster than max walking speed.
     */
    private static final double ANIMATION_THRESHOLD_FAST = 0x1A00 * VEL_CONVERT;
    /**
     * I just made this one up, idk
     */
    private static final double ANIMATION_THRESHOLD_SLOW = 0xF00 * VEL_CONVERT;

    private boolean isRunning = false;
    private double gravity;

    public MarioControls(Game game) {
        super(game);
        this.groundTurnaroundAcceleration = SKID_DECELERATION;
        this.groundFriction = RELEASE_DECELERATION;
        this.airFriction = AIR_FRICTION;
        super.gravity = 0; // Gravity is weird in Mario, so it'll be handled entirely from this class
        this.gravity = 0;
        this.maxJumpTime = MAX_JUMP_TIME;
    }

    @Override
    public void handle(KeyEvent event) {
        super.handle(event);
        this.isRunning = this.keyStates.getOrDefault(KeyCode.CONTROL, false)
                || this.keyStates.getOrDefault(KeyCode.SHIFT, false);
    }

    @Override
    public void processInput(double dt) {
        double vx = this.game.getPlayer().getVelocity().getX();
        double vy = this.game.getPlayer().getVelocity().getY();

        // If moving below min speed, set to min speed
        if (Math.abs(vx) < MIN_WALK_SPEED && (this.leftPressed ^ this.rightPressed)) {
            this.game.getPlayer().getVelocity().setX(MIN_WALK_SPEED * (this.leftPressed ? -1 : 1));
        }
        // Skid turnaround
        boolean turnaround = (this.leftPressed ^ this.rightPressed) && Math.signum(vx) == (this.rightPressed ? -1 : 1);
        if (turnaround && Math.abs(vx) < SKID_TURNAROUND_SPEED) {
            this.game.getPlayer().getVelocity().setX(0);
        }
        // Walk vs run speeds/accelerations
        if (this.isRunning) {
            this.groundAcceleration = RUN_ACCELERATION;
            this.maxMoveSpeed = MAX_RUN_SPEED;
        } else {
            this.groundAcceleration = WALK_ACCELERATION;
            this.maxMoveSpeed = MAX_WALK_SPEED;
        }
        // Air acceleration based on speed
        if (Math.abs(vx) >= AIR_TURNAROUND_SPEED_THRESHOLD) {
            this.airAcceleration = AIR_ACCELERATION_FULL;
            this.airTurnaroundAcceleration = AIR_TURNAROUND_FULL;
        } else if (this.horizontalJumpSpeed >= AIR_TURNAROUND_JUMP_SPEED_THRESHOLD) {
            this.airAcceleration = AIR_ACCELERATION_SLOW;
            this.airTurnaroundAcceleration = AIR_TURNAROUND_MEDIUM;
        } else {
            this.airAcceleration = AIR_ACCELERATION_SLOW;
            this.airTurnaroundAcceleration = AIR_TURNAROUND_SLOW;
        }
        // Adjust jump height and gravity based on horizontal speed
        if (this.horizontalJumpSpeed >= GRAVITY_THRESHOLD_FAST) {
            this.jumpSpeed = JUMP_SPEED_FULL;
            this.gravity = this.jumpPressed && vy < 0 ? GRAVITY_JUMP_FAST : GRAVITY_FALL_FAST;
        } else if (this.horizontalJumpSpeed >= GRAVITY_THRESHOLD_SLOW) {
            this.jumpSpeed = JUMP_SPEED_SLOW;
            this.gravity = this.jumpPressed && vy < 0 ? GRAVITY_JUMP_MEDIUM : GRAVITY_FALL_MEDIUM;
        } else {
            this.jumpSpeed = JUMP_SPEED_SLOW;
            this.gravity = this.jumpPressed && vy < 0 ? GRAVITY_JUMP_SLOW : GRAVITY_FALL_SLOW;
        }
        // Apply gravity
        this.jumpSpeed *= JUMP_SPEED_FACTOR;
        this.game.getPlayer().getVelocity().setY(Math.min(vy + this.gravity * dt * GRAVITY_FACTOR, MAX_FALL_SPEED));
        // Animation speed
        if (Math.abs(vx) >= ANIMATION_THRESHOLD_FAST) {
            this.game.getPlayer().setAnimationDuration(ANIMATION_TIME_FAST);
        } else if (Math.abs(vx) >= ANIMATION_THRESHOLD_SLOW) {
            this.game.getPlayer().setAnimationDuration(ANIMATION_TIME_MEDIUM);
        } else {
            this.game.getPlayer().setAnimationDuration(ANIMATION_TIME_SLOW);
        }
        // Animation state
        String direction = this.game.getPlayer().getOrientation().getX() < 0 ? "left" : "right";
        if (!this.game.getPlayer().isOnGround()) {
            this.game.getPlayer().setAnimationState("jump_" + direction);
        } else if (turnaround) {
            this.game.getPlayer().setAnimationState("skid_" + direction);
        } else if (Math.abs(vx) < 1e-5) {
            this.game.getPlayer().freezeAnimations();
        } else {
            this.game.getPlayer().setAnimationState("walk_" + direction);
        }

        super.processInput(dt);
    }

}
