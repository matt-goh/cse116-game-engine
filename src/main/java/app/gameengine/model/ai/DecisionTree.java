package app.gameengine.model.ai;
import app.gameengine.Level;
import app.gameengine.model.datastructures.BinaryTreeNode;

public class DecisionTree {

    private BinaryTreeNode<Decision> tree;

    /**
     * Constructs a DecisionTree with the specified root node.
     *
     * @param tree the root of the decision tree
     */
    public DecisionTree(BinaryTreeNode<Decision> tree) {
        this.tree = tree;
    }

    /**
     * Returns the root of the decision tree.
     *
     * @return the tree root
     */
    public BinaryTreeNode<Decision> getTree() {
        return tree;
    }

    /**
     * Sets the root of the decision tree.
     *
     * @param tree the new tree root
     */
    public void setTree(BinaryTreeNode<Decision> tree) {
        this.tree = tree;
    }

    /**
     * Traverses the decision tree starting from the given node.
     *
     * @param node  the current node in the traversal
     * @param dt    the time delta since last update
     * @param level the current level
     * @return the final Decision reached after traversal
     */
    public Decision traverse(BinaryTreeNode<Decision> node, double dt, Level level) {
        if (node == null) {
            return null;
        }

        Decision decision = node.getValue();
        boolean result = decision.decide(dt, level);

        // If result is false, go left; if true, go right
        BinaryTreeNode<Decision> nextNode = result ? node.getRight() : node.getLeft();

        if (nextNode == null) {
            return decision;
        }

        return traverse(nextNode, dt, level);
    }

    /**
     * Initiates the traversal of the decision tree and executes the final decision's action.
     *
     * @param dt    the time delta since last update
     * @param level the current level
     */
    public void traverse(double dt, Level level) {
        Decision finalDecision = traverse(tree, dt, level);
        if (finalDecision != null) {
            finalDecision.doAction(dt, level);
        }
    }
}
