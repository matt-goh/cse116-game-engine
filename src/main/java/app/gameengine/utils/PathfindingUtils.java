package app.gameengine.utils;

import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.physics.Vector2D;

public class PathfindingUtils {

    /**
     * Finds a shortest valid path from a start vector to an end vector.
     *
     * @param start The starting vector.
     * @param end   The ending vector.
     * @return The head of a linked list representing the path.
     */
    public static LinkedListNode<Vector2D> findPath(Vector2D start, Vector2D end) {
        Vector2D startTile = Vector2D.floor(start);
        Vector2D endTile = Vector2D.floor(end);

        if (startTile.equals(endTile)) {
            return new LinkedListNode<>(startTile, null);
        }

        LinkedListNode<Vector2D> head = new LinkedListNode<>(startTile, null);
        LinkedListNode<Vector2D> current = head;
        Vector2D currentPos = startTile.copy();

        // Move horizontally
        while (currentPos.getX() != endTile.getX()) {
            if (currentPos.getX() < endTile.getX()) {
                currentPos.setX(currentPos.getX() + 1);
            } else {
                currentPos.setX(currentPos.getX() - 1);
            }
            LinkedListNode<Vector2D> nextNode = new LinkedListNode<>(currentPos.copy(), null);
            current.setNext(nextNode);
            current = nextNode;
        }

        // Move vertically
        while (currentPos.getY() != endTile.getY()) {
            if (currentPos.getY() < endTile.getY()) {
                currentPos.setY(currentPos.getY() + 1);
            } else {
                currentPos.setY(currentPos.getY() - 1);
            }
            LinkedListNode<Vector2D> nextNode = new LinkedListNode<>(currentPos.copy(), null);
            current.setNext(nextNode);
            current = nextNode;
        }

        return head;
    }
}