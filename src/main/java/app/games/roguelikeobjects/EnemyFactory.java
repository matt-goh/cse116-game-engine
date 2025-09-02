package app.games.roguelikeobjects;

import app.gameengine.model.physics.Vector2D;
import app.gameengine.utils.Randomizer;
import app.games.topdownobjects.Archer;
import app.games.topdownobjects.Demon;
import app.games.topdownobjects.Enemy;
import app.games.topdownobjects.Minotaur;
import app.games.topdownobjects.Sorcerer;

/**
 * Factory class for creating enemies within a {@link RoguelikeGame}.
 * 
 * @see RoguelikeGame
 */
public class EnemyFactory {
    private static String[] enemyNames = { "Demon", "Minotaur", "Sorcerer", "Archer" };

    public static Enemy getEnemy(Vector2D spawnLocation, String name) {
        Enemy enemy = null;
        double x = spawnLocation.getX();
        double y = spawnLocation.getY();

        switch (name) {
            case "Demon":
                enemy = new Demon(x, y);
                break;
            case "Minotaur":
                enemy = new Minotaur(x, y);
                break;
            case "Archer":
                enemy = new Archer(x, y);
                break;
            case "Sorcerer":
                enemy = new Sorcerer(x, y);
                break;
        }

        if (enemy == null) {
            return null;
        }
        return enemy;
    }

    public static Enemy getRandomEnemy(Vector2D spawnLocation) {
        return getEnemy(spawnLocation, Randomizer.randomSelect(enemyNames));
    }
}
