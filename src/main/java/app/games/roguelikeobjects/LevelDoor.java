package app.games.roguelikeobjects;

import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.DynamicGameObject;
import app.gameengine.model.gameobjects.StaticGameObject;
import app.gameengine.model.physics.Vector2D;

/**
 * A {@link StaticGameObject} that simply calls changes to the next level when
 * collision with the player occurs.
 * 
 * @see DirectionalWall
 * @see RoguelikeGame
 */
public class LevelDoor extends DirectionalWall {

    private Game game;
    private String nextLevelName;
    private boolean active;
    private Vector2D doorDirection;

    public LevelDoor(double x, double y, Game game, Level level, String nextLevelName) {
        super(x, y, level);
        this.game = game;
        this.nextLevelName = nextLevelName;
        this.setActive(true);

        // Doors can only be on borders of the map.
        if (getLocation().getX() == 0) {
            doorDirection = new Vector2D(-1, 0);
        } else if (getLocation().getX() == level.getWidth() - 1) {
            doorDirection = new Vector2D(1, 0);
        } else if (getLocation().getY() == 0) {
            doorDirection = new Vector2D(0, -1);
        } else if (getLocation().getY() == level.getHeight() - 1) {
            doorDirection = new Vector2D(0, 1);
        }
    }

    @Override
    public void collideWithDynamicObject(DynamicGameObject otherObject) {
        if (otherObject.isPlayer()) {
            if (active) {
                this.game.markChangeLevel(this.nextLevelName);
            } else {
                super.collideWithDynamicObject(otherObject);
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;

        if (active) {
            this.getCurrentSpriteLocation().setColumn(0);
            this.getCurrentSpriteLocation().setRow(1);
        } else {
            this.getCurrentSpriteLocation().setColumn(1);
            this.getCurrentSpriteLocation().setRow(0);
        }
    }

    public boolean isActive() {
        return this.active;
    }

    /**
     * Sets the next level's name based on the position of the previous level.
     * This is useful when using a grid-based level system.
     * 
     * @param previousLevelPos the previous level's position.
     */
    public void setNextLevelName(Vector2D previousLevelPos) {
        this.nextLevelName = Vector2D.add(previousLevelPos, doorDirection).toString();
    }

    public void setNextLevelName(String nextLevelName) {
        this.nextLevelName = nextLevelName;
    }

    public Vector2D getDoorDirection() {
        return this.doorDirection;
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
