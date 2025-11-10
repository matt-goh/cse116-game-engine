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
    private ArrayList<Collectible> inventory = new ArrayList<>();
    private int activeItemIndex = 0;

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

    /**
     * Adds a collectible item to the player's inventory.
     *
     * @param item the collectible to add
     */
    public void addInventoryItem(Collectible item) {
        inventory.add(item);
    }

    /**
     * Returns the number of items in the inventory.
     *
     * @return the inventory size
     */
    public int getInventorySize() {
        return inventory.size();
    }

    /**
     * Returns the currently active/equipped collectible item.
     *
     * @return the active item, or null if inventory is empty
     */
    public Collectible getActiveItem() {
        if (inventory.isEmpty()) {
            return null;
        }
        return inventory.get(activeItemIndex);
    }

    /**
     * Returns the ID of the currently active item.
     *
     * @return the active item ID, or "No item equipped" if none
     */
    public String getActiveItemID() {
        Collectible active = getActiveItem();
        if (active == null) {
            return "No item equipped";
        }
        return active.getItemID();
    }

    /**
     * Cycles to the next item in the inventory.
     * Wraps around to the beginning when reaching the end.
     */
    public void cycleInventory() {
        if (!inventory.isEmpty()) {
            activeItemIndex = (activeItemIndex + 1) % inventory.size();
        }
    }

    /**
     * Clears all items from the inventory.
     */
    public void clearInventory() {
        inventory.clear();
        activeItemIndex = 0;
    }

    /**
     * Uses the currently equipped item if available.
     *
     * @param level the current level
     */
    public void useActiveItem(Level level) {
        Collectible active = getActiveItem();
        if (active != null) {
            active.use(level);
        }
    }

    /**
     * Removes the currently active item from the inventory.
     * Adjusts the active item index accordingly.
     */
    public void removeActiveItem() {
        if (!inventory.isEmpty()) {
            inventory.remove(activeItemIndex);

            if (inventory.isEmpty()) {
                activeItemIndex = 0;
            }
            else if (activeItemIndex >= inventory.size()) {
                activeItemIndex = 0;
            }
        }
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
        
        for (Collectible item : inventory) {
            item.update(dt, level);
        }
    }

}
