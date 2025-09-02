package app.games.snake;

import java.util.ArrayList;

import app.display.common.Background;
import app.gameengine.Game;
import app.gameengine.Level;
import app.gameengine.model.physics.PhysicsEngine;
import app.gameengine.utils.Timer;

/**
 * A level within a game of snake.
 * <p>
 * This level manages logic including timing of player movement, spawning new
 * food, and keeping track of the snake positions.
 * 
 * @see Level
 * @see Snake
 * @see Food
 */
public class SnakeLevel extends Level {

    private Timer timer;
    private ArrayList<SnakeFood> food = new ArrayList<>();
    private ArrayList<SnakeBody> tail = new ArrayList<>();
    private int lengthIncrease;
    private int startingLength;
    private int numFood;

    public SnakeLevel(Game game, int size, Timer timer, String name) {
        this(game, size, timer, name, 1, 3, 1);
    }

    public SnakeLevel(Game game, int size, Timer timer, String name, int lengthIncrease, int startingLength,
            int numFood) {
        super(game, new PhysicsEngine(), size, size, name);
        this.timer = timer;
        this.lengthIncrease = lengthIncrease;
        this.startingLength = startingLength;
        this.numFood = numFood;
        this.keyboardControls = new SnakeControls(game);
        this.setBackground(new Background("snake/snakeColors.png", 3, 0));
    }

    /**
     * Returns the list of {@link SnakeFood} objects currently in the level.
     * 
     * @return the list of food
     */
    public ArrayList<SnakeFood> getFood() {
        return this.food;
    }

    /**
     * Returns the list of {@link SnakeBody} objects currently in the level. This
     * does not include the head of the snake.
     * 
     * @return the list of body segments
     */
    public ArrayList<SnakeBody> getTail() {
        return this.tail;
    }

    /**
     * Surrounds the level with {@link SnakeWall} objects, just out of bounds. This
     * is to prevent the snake from leaving the confines of the level.
     */
    public void wallOffBoundary() {

    }

    /**
     * Randomly choose a new location for food, that is not already occupied. If the
     * snake head and tail fill all available space, it will advance to the next
     * level. If there is no room to spawn food, it will do nothing.
     * <p>
     * Note that food is added to this class's list of food, as well as its list of
     * static objects.
     */
    public void spawnFood() {

    }

    /**
     * Increase the length of the snake by creating new body segments. The amount
     * which the length increases by is determined by the lengthIncrease passed into
     * the constructor.
     * <p>
     * Note that body segments are added to this class's list of body segments, as
     * well as its list of static objects.
     */
    public void lengthenSnake() {

    }

    /**
     * Create the snake at the center of the level. All body segments start in a
     * stack behind the head of the snake.
     */
    public void spawnSnake() {

    }

    /**
     * Update the entire level according to the amount of time that has elapsed
     * since the last frame. Updates specific to snake only occur according to a
     * central timer based on movement speed. If this update does occur, keyboard
     * input is processed, and tail and player locations are adjusted accordingly.
     */
    @Override
    public void update(double dt) {
        this.food.removeIf(SnakeFood::isDestroyed);
        this.tail.removeIf(SnakeBody::isDestroyed);
        // Move body
        if (this.timer.tick(dt)) {
            // Update snake head and tail locations
        }
        super.update(dt);
    }

    @Override
    public void load() {
        super.load();
        this.food.forEach(SnakeFood::destroy);
        this.tail.forEach(SnakeBody::destroy);
        this.food.clear();
        this.tail.clear();

        this.spawnSnake();
        // Spawn food
        for (int i = 0; i < this.numFood; i++) {
            this.spawnFood();
        }
        this.wallOffBoundary();
        this.game.pause();
    }

    @Override
    public void reset() {
        this.load();
    }

    @Override
    public String getUIString() {
        return "Score: " + this.getPlayer().getHP();
    }

}
