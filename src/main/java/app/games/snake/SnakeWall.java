package app.games.snake;

import app.display.common.SpriteLocation;
import app.gameengine.model.gameobjects.StaticGameObject;

/**
 * Simple static object for surrounding a level of snake.
 */
public class SnakeWall extends StaticGameObject {

    public SnakeWall(double x, double y) {
        super(x, y);
        this.spriteSheetFilename = "snake/snakeColors.png";
        this.defaultSpriteLocation = new SpriteLocation(4, 0);
    }

}
