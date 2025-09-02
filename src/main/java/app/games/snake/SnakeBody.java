package app.games.snake;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * A body segment for a game of Snake. Really just a colored rectangle.
 * 
 * @see SnakeGame
 * @see SnakeLevel
 */
public class SnakeBody extends StaticGameObject {

    public SnakeBody(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "snake/snakeColors.png";
        this.defaultSpriteLocation = new SpriteLocation(1, 0);
    }

}
