package app.gameengine.model.gameobjects;
import app.gameengine.Game;
import app.gameengine.Level;

public abstract class Collectible extends StaticGameObject {

    private Game game;
    private String itemID;

    public Collectible(double x, double y, Game game, String itemID) {
        super(x, y);
        this.game = game;
        this.itemID = itemID;
    }

    public Game getGame() {
        return game;
    }

    public String getItemID() {
        return itemID;
    }

    /**
     * Abstract method to use this collectible item.
     *
     * @param level the current level
     */
    public abstract void use(Level level);

    /**
     * Called when this collectible is collected by a game object.
     * Adds the item to the player's inventory if the collector is a player.
     *
     * @param collector the game object that collected this item
     * @param level     the current level
     */
    public void onCollect(GameObject collector, Level level) {
        if (collector instanceof Player) {
            Player player = (Player) collector;
            player.addInventoryItem(this);
        }
    }

    /**
     * Called when a dynamic object collides with this collectible.
     * Adds the item to the player's inventory and destroys this collectible.
     *
     * @param otherObject the dynamic object that collided with this collectible
     */
    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        if (otherObject instanceof Player) {
            Player player = (Player) otherObject;
            player.addInventoryItem(this);
            this.destroy();
        }
    }

	@Override
	public boolean isSolid() {
		return false;
	}
}
