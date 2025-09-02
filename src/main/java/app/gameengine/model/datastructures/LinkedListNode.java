package app.gameengine.model.datastructures;

/**
 * Represents a node in a linked list data structure.
 * <p>
 * Each node contains a value of some generic type and a reference to the next
 * node. This class provides methods to access and modify the value and next
 * node.
 * 
 * @param <T> the type of value stored in the node
 */
public class LinkedListNode<T> {

    private T value;
    private LinkedListNode<T> next;

    /**
     * Constructs a new LinkedListNode with the specified value and next node.
     * 
     * @param value the value to store in this node
     * @param next  the next node
     */
    public LinkedListNode(T value, LinkedListNode<T> next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Returns the value stored in this node.
     *
     * @return the value of this node
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Sets the value stored in this node.
     *
     * @param value the new value to store
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Returns the next node.
     *
     * @return the next node, or {@code null} if none
     */
    public LinkedListNode<T> getNext() {
        return this.next;
    }

    /**
     * Sets the next node.
     *
     * @param node the new next node
     */
    public void setNext(LinkedListNode<T> node) {
        this.next = node;
    }

}
