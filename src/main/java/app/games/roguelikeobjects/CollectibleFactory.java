package app.games.roguelikeobjects;

import app.gameengine.Game;
import app.gameengine.model.gameobjects.Collectible;
import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.Randomizer;
import app.games.commonobjects.AxePickup;
import app.games.commonobjects.MagicPickup;
import app.games.commonobjects.PotionPickup;

/**
 * Factory class for creating collectibles within a {@link RoguelikeGame}.
 * 
 * @see RoguelikeGame
 */
public class CollectibleFactory {
    private static String[] lootNames = { "MagicPickup", "AxePickup", "PotionPickup" };

    public static Collectible getLoot(Vector2D spawnLocation, Game game, String name) {
        Collectible loot = null;
        double x = spawnLocation.getX();
        double y = spawnLocation.getY();

        switch (name) {
            case "MagicPickup":
                loot = new MagicPickup(x, y, game);
                break;
            case "AxePickup":
                loot = new AxePickup(x, y, game);
                break;
            case "PotionPickup":
                loot = new PotionPickup(x, y, 30, game);
                break;
        }

        if (loot == null) {
            return null;
        }
        return loot;
    }

    public static Collectible getRandomLoot(Vector2D spawnLocation, Game game) {
        return getLoot(spawnLocation, game, Randomizer.randomSelect(lootNames));
    }
}
