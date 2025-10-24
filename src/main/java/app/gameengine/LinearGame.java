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

    private LinkedListNode<Level> levelList;

    public LinearGame(Player player) {
        super(player);
        this.levelList = null;
    }

    public LinkedListNode<Level> getLevelList() {
        return this.levelList;
    }

    public void setLevelList(LinkedListNode<Level> levelList) {
        this.levelList = levelList;
    }

    public void addLevel(Level level) {
        LinkedListNode<Level> newNode = new LinkedListNode<>(level, null);

        if (this.levelList == null) {
            this.levelList = newNode;
        } else {
            LinkedListNode<Level> current = this.levelList;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    public void advanceLevel() {
        if (this.getCurrentLevel() == null || this.levelList == null) {
            return;
        }
        LinkedListNode<Level> current = this.levelList;
        while (current != null) {
            if (current.getValue().getName().equals(this.getCurrentLevel().getName())) {
                if (current.getNext() != null) {
                    this.loadLevel(current.getNext().getValue());
                }
                return;
            }
            current = current.getNext();
        }
    }

    public void removeLevelByName(String name) {
        if (this.levelList == null) {
            return;
        }

        if (this.levelList.getValue().getName().equals(name)) {
            this.levelList = this.levelList.getNext();
            return;
        }

        LinkedListNode<Level> current = this.levelList;
        while (current.getNext() != null) {
            if (current.getNext().getValue().getName().equals(name)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }
}
