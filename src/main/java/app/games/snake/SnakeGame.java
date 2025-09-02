package app.games.snake;

import app.Configuration;
import app.display.common.FontManager;
import app.display.common.ui.UILabelBuilder;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.utils.Timer;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * A classic game of Snake.
 * <p>
 * Mostly manages the UI and resetting the level. Also increases size and speed
 * upon success. Starting parameters can be changed by modifying the instance
 * variables below.
 * 
 * @see SnakeLevel
 * @see Game
 * @see Snake
 */
public class SnakeGame extends Game {

    private int size = 16;
    private double speed = 10; // tiles/second
    private int lengthIncrease = 3; // length increase for each food
    private int startingLength = 3;
    private int numFood = 3;

    public SnakeGame() {
        this.setPlayer(new Snake(0, 0, size * size));
    }

    private Level getNewLevel() {
        Level level = new SnakeLevel(this, size, new Timer(1 / speed), "level " + size / 10, this.lengthIncrease,
                this.startingLength, this.numFood);
        level.setPlayerStartLocation(size / 2, size / 2);
        return level;
    }

    public void setGameParameters(int startingLength, int lengthIncrease, int numFood) {
        this.startingLength = startingLength;
        this.lengthIncrease = lengthIncrease;
        this.numFood = numFood;
    }

    @Override
    public String getName() {
        return "Snake";
    }

    @Override
    public void init() {
        super.init();
        this.getUICollection().clearElements();
        this.getUICollection().addElement("score",
                new UILabelBuilder().font(FontManager.getFont("Minecraft.ttf", Configuration.ZOOM * 20))
                        .alignment(Pos.TOP_CENTER).backgroundColor(Color.WHITE.deriveColor(0, 1, 1, 0.5))
                        .text("Score: 0").build());
        this.loadLevel(getNewLevel());
    }

    @Override
    public void advanceLevel() {
        this.size += 10;
        this.speed *= 2;
        this.resetCurrentLevel();
    }

    @Override
    public void resetCurrentLevel() {
        this.setPlayer(new Snake(0, 0, size * size));
        this.loadLevel(getNewLevel());
    }

    @Override
    public void resetGame() {
        this.resetCurrentLevel();
    }

}
