package app.games.roguelikeobjects;

import app.display.common.SpriteLocation;
import app.gameengine.Level;
import app.gameengine.model.physics.Vector2D;
import app.games.commonobjects.Wall;

/**
 * A wall which adapts its sprite based on its location within a level.
 * 
 * @see RoguelikeGame
 */
public class DirectionalWall extends Wall {

    public DirectionalWall(double x, double y, Level level) {
        super(x, y);
        this.spriteSheetFilename = "dungeon/environment/MainDungeon.png";
        this.setDirectionalSprite(level);
    }

    private void setDirectionalSprite(Level level) {
        this.defaultSpriteLocation = switch (getWallDirection(level)) {
            case "TopLeft":
                yield new SpriteLocation(0, 0);
            case "TopRight":
                yield new SpriteLocation(0, 0, 90);
            case "BottomRight":
                yield new SpriteLocation(0, 0, 180);
            case "BottomLeft":
                yield new SpriteLocation(0, 0, 270);
            case "Top":
                yield new SpriteLocation(1, 0);
            case "Right":
                yield new SpriteLocation(1, 0, 90);
            case "Bottom":
                yield new SpriteLocation(1, 0, 180);
            case "Left":
                yield new SpriteLocation(1, 0, 270);
            case "Middle":
            default:
                yield new SpriteLocation(0, 2);
        };
    }

    private String getWallDirection(Level level) {
        Vector2D location = this.getLocation();
        Vector2D levelSize = new Vector2D(level.getWidth() - 1, level.getHeight() - 1);
        if (location.equals(new Vector2D(0, 0))) {
            return "TopLeft";
        } else if (location.equals(new Vector2D(levelSize.getX(), 0))) {
            return "TopRight";
        } else if (location.equals(new Vector2D(0, levelSize.getY()))) {
            return "BottomLeft";
        } else if (location.equals(new Vector2D(levelSize.getX(), levelSize.getY()))) {
            return "BottomRight";
        } else if (location.getX() <= 1e-7) {
            return "Left";
        } else if (location.getY() <= 1e-7) {
            return "Top";
        } else if (location.getX() >= (levelSize.getX() - 1e-7)) {
            return "Right";
        } else if (location.getY() >= (levelSize.getY() - 1e-7)) {
            return "Bottom";
        } else {
            return "Middle";
        }
    }

}
