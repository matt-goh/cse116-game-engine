package app.gameengine.statistics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import app.gameengine.model.datastructures.BinaryTreeNode;
import app.gameengine.model.datastructures.Comparator;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.datastructures.ScoreComparator;

public class Scoreboard {
    private static String STATS_DIRECTORY = "data/stats/";
    private static String STATS_POSTFIX = "_stats.csv";
    private String statsPath;
    private Comparator<GameStat> comparator;
    private BinaryTreeNode<GameStat> scoreTree;
    private boolean statsLoaded = false;

    public Scoreboard(String gameName) {
        this(gameName, new ScoreComparator());
    }

    public Scoreboard(String gameName, Comparator<GameStat> comparator) {
        this.statsPath = STATS_DIRECTORY + gameName.toLowerCase() + STATS_POSTFIX;
        this.comparator = comparator;
        this.scoreTree = null;
    }

    /**
     * Gets the comparator for this scoreboard.
     *
     * @return the comparator
     */
    public Comparator<GameStat> getComparator() {
        return comparator;
    }

    /**
     * Sets the comparator for this scoreboard.
     *
     * @param comparator the new comparator
     */
    public void setComparator(Comparator<GameStat> comparator) {
        this.comparator = comparator;
    }

    /**
     * Gets the score tree for this scoreboard.
     *
     * @return the root of the score tree
     */
    public BinaryTreeNode<GameStat> getScoreTree() {
        return scoreTree;
    }

    /**
     * Sets the score tree for this scoreboard.
     *
     * @param scoreTree the new score tree root
     */
    public void setScoreTree(BinaryTreeNode<GameStat> scoreTree) {
        this.scoreTree = scoreTree;
    }

    /**
     * Adds a score to the scoreboard tree.
     *
     * @param score the GameStat to add
     */
    public void addScore(GameStat score) {
        scoreTree = addScoreRecursive(scoreTree, score);
    }

    /**
     * Recursive helper method to add a score to the BST.
     *
     * @param node  the current node
     * @param score the GameStat to add
     * @return the updated node
     */
    private BinaryTreeNode<GameStat> addScoreRecursive(BinaryTreeNode<GameStat> node, GameStat score) {
        if (node == null) {
            return new BinaryTreeNode<>(score, null, null);
        }

        if (comparator.compare(score, node.getValue())) {
            node.setLeft(addScoreRecursive(node.getLeft(), score));
        } else {
            node.setRight(addScoreRecursive(node.getRight(), score));
        }

        return node;
    }

    /**
     * Loads statistics from the CSV file and populates the BST.
     * Only executes once per scoreboard instance.
     */
    public void loadStats() {
        if (statsLoaded) {
            return;
        }
        statsLoaded = true;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(this.statsPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double playtime = Double.parseDouble(parts[1]);
                    double score = Double.parseDouble(parts[2]);
                    GameStat stat = new GameStat(name, playtime, score);
                    addScore(stat);
                }
            }
        } catch (IOException e) {
        } catch (NumberFormatException e) {
            System.out.println("Error parsing stats file: " + e.getMessage());
        }
    }

    /**
     * Returns a linked list of GameStat objects from the score tree.
     * The list is built by traversing the BST in-order.
     *
     * @return the head of the linked list, or null if tree is empty
     */
    public LinkedListNode<GameStat> getScoreList() {
        return getScoreList(scoreTree);
    }

    /**
     * Returns a linked list of GameStat objects from the given tree.
     * The list is built by traversing the BST in-order.
     *
     * @param tree the root of the BST to convert
     * @return the head of the linked list, or null if tree is empty
     */
    public LinkedListNode<GameStat> getScoreList(BinaryTreeNode<GameStat> tree) {
        if (tree == null) {
            return null;
        }

        LinkedListNode<GameStat> leftList = getScoreList(tree.getLeft());
        LinkedListNode<GameStat> rightList = getScoreList(tree.getRight());

        LinkedListNode<GameStat> current = new LinkedListNode<>(tree.getValue(), rightList);

        if (leftList == null) {
            return current;
        }

        LinkedListNode<GameStat> temp = leftList;
        while (temp.getNext() != null) {
            temp = temp.getNext();
        }
        temp.setNext(current);

        return leftList;
    }

    public void saveStats() {
        this.loadStats();
        if (this.scoreTree == null) {
            return;
        }
        System.out.println("** Saving statistics to " + this.statsPath + " **");
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(this.statsPath))) {
            for (LinkedListNode<GameStat> stats = this.getScoreList(); stats != null; stats = stats.getNext()) {
                writer.write(stats.getValue().toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("** Could not save statistics to " + this.statsPath + " **");
            e.printStackTrace();
        }
    }

}
