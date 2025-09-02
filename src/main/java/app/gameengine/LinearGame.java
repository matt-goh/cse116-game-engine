package app.gameengine;

import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.gameobjects.Player;

/**
 * A {@code Game} subclass that organizes levels linearly, such that each level
 * has a single "next" level. This is implemented using the
 * {@link LinkedListNode} class.
 * <p>
 * This class implements the functionality to advance to the next level, as well
 * as additional functionality to replace or remove levels by name.
 * 
 * @see LinkedListNode
 * @see Game
 * @see Level
 */
public class LinearGame extends Game {

    public LinearGame() {
        super();
    }

    public LinearGame(Player player) {
        super(player);
    }

}
