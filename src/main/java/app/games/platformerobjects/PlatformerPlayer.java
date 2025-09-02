package app.games.platformerobjects;

import app.gameengine.Level;
import app.gameengine.model.gameobjects.Player;
import app.gameengine.model.physics.Vector2D;

/**
 * Special kind of player for use in platformers.
 * <p>
 * Includes additional checks for being on the ground, and allows for coyote
 * time.
 */
public class PlatformerPlayer extends Player {

    protected double maxCoyoteTime = 0.07;
    private double timeSinceGround;
    protected Collider collider;

    public PlatformerPlayer(double x, double y, int maxHP) {
        super(x, y, maxHP);
        this.getHitbox().setDimensions(0.6, 0.8);
        this.getHitbox().setOffset(0.2, 0.1);
        this.collider = new Collider(this.getLocation(), new Vector2D(0.6, 0.2), new Vector2D(0.2, 0.71));
        this.spriteSheetFilename = "MiniWorldSprites/Characters/Soldiers/Melee/CyanMelee/AssasinCyan.png";
    }

    public Collider getCollider() {
        return this.collider;
    }

    public void jump() {
        this.timeSinceGround = maxCoyoteTime;
    }

    @Override
    public void reset() {
        this.setOnGround(false);
        super.reset();
    }

    @Override
    public void revive() {
        this.setOnGround(false);
        super.revive();
    }

    @Override
    public void update(double dt, Level level) {
        super.update(dt, level);
        this.timeSinceGround += dt;
        // Check for ground collision, accounting for coyote time
        if (this.collider.checkCollision(level.getPhysicsEngine(), level) && this.getVelocity().getY() >= 0) {
            this.setOnGround(true);
            this.timeSinceGround = 0;
        } else if (this.timeSinceGround < this.maxCoyoteTime) {
            this.setOnGround(true);
        } else {
            this.setOnGround(false);
        }
    }

}
