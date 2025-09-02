package app.gameengine.model.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;

import app.Settings;
import app.display.common.SpriteLocation;
import app.gameengine.Game;
import app.gameengine.Level;

/**
 * Represents the object controlled by the player.
 * <p>
 * Each game, by default, has one player object, which is often handled
 * separately from other objects. The keyboard and mouse controls usually apply
 * mostly to this character, handling movement and other actions.
 * 
 * @see DynamicGameObject
 * @see GameObject
 * @see Game
 * @see Level
 */
public class Player extends DynamicGameObject {

    private double iFrames = 0;

    /**
     * Constructs a player with the given location and max health.
     * 
     * @param x     the x location of the object
     * @param y     the y location of the object
     * @param maxHP the maximum HP of the object
     */
    public Player(double x, double y, int maxHP) {
        super(x, y, maxHP);
        this.getHitbox().setDimensions(0.8, 0.8);
        this.getHitbox().setOffset(0.1, 0.1);
        this.spriteSheetFilename = "MiniWorldSprites/Characters/Soldiers/Melee/CyanMelee/AxemanCyan.png";
    }

    public double getInvincibilityFrames() {
        return iFrames;
    }

    public void setInvincibilityFrames(double duration) {
        iFrames = duration;
    }

    public String getActiveItemID() {
        return "No item equipped";
    }

    public void cycleInventory() {
        
    }

    public void clearInventory() {
        
    }

    @Override
    public void initAnimations() {
        this.animations.put("walk_down", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 0),
                new SpriteLocation(1, 0),
                new SpriteLocation(2, 0),
                new SpriteLocation(3, 0),
                new SpriteLocation(4, 0))));
        this.animations.put("walk_up", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 1),
                new SpriteLocation(1, 1),
                new SpriteLocation(2, 1),
                new SpriteLocation(3, 1),
                new SpriteLocation(4, 1))));
        this.animations.put("walk_right", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 2),
                new SpriteLocation(1, 2),
                new SpriteLocation(2, 2),
                new SpriteLocation(3, 2),
                new SpriteLocation(4, 2))));
        this.animations.put("walk_left", new ArrayList<>(Arrays.asList(
                new SpriteLocation(0, 3),
                new SpriteLocation(1, 3),
                new SpriteLocation(2, 3),
                new SpriteLocation(3, 3),
                new SpriteLocation(4, 3))));
    }

    @Override
    public void takeDamage(int damage) {
        if (!Settings.godMode() && this.iFrames <= 0) {
            super.takeDamage(damage);
            this.setInvincibilityFrames(0.5);
        }
    }

    @Override
    public void destroy() {
        if (!Settings.godMode()) {
            super.destroy();
        }
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void reset() {
        super.reset();
        this.iFrames = 0;
        this.clearInventory();
    }

    @Override
    public void update(double dt, Level level) {
        super.update(dt, level);
        this.iFrames -= dt;
    }

}
