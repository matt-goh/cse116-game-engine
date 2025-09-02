package app.gameengine.model.datastructures;

/**
 * Represents a node in a binary tree data structure.
 * <p>
 * Each node contains a value of some generic type and references to the left
 * and right child nodes. This class provides methods to access and modify the
 * value and child nodes.
 * 
 * @param <A> the type of value stored in the node
 */
public class BinaryTreeNode<A> {
    private A value;
    private BinaryTreeNode<A> left;
    private BinaryTreeNode<A> right;

    /**
     * Constructs a new BinaryTreeNode with the specified value and child nodes.
     *
     * @param value the value to store in this node
     * @param left  the left child node
     * @param right the right child node
     */
    public BinaryTreeNode(A value, BinaryTreeNode<A> left, BinaryTreeNode<A> right) {
        this.value = value;
        this.right = right;
        this.left = left;
    }

    /**
     * Returns the value stored in this node.
     *
     * @return the value of this node
     */
    public A getValue() {
        return value;
    }

    /**
     * Sets the value stored in this node.
     *
     * @param value the new value to store
     */
    public void setValue(A value) {
        this.value = value;
    }

    /**
     * Returns the left child node.
     *
     * @return the left child node, or {@code null} if none
     */
    public BinaryTreeNode<A> getLeft() {
        return left;
    }

    /**
     * Sets the left child node.
     *
     * @param left the new left child node
     */
    public void setLeft(BinaryTreeNode<A> left) {
        this.left = left;
    }

    /**
     * Returns the right child node.
     *
     * @return the right child node, or {@code null} if none
     */
    public BinaryTreeNode<A> getRight() {
        return right;
    }

    /**
     * Sets the right child node.
     *
     * @param right the new right child node
     */
    public void setRight(BinaryTreeNode<A> right) {
        this.right = right;
    }
}
