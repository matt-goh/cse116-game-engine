package app.tests;

import app.gameengine.Level;
import app.gameengine.model.datastructures.LinkedListNode;
import app.gameengine.model.gameobjects.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestUtils {
    // A small value to account for floating-point inaccuracies
    private static final double EPSILON = 0.001;

    /**
     * Asserts that two Player objects have the same state (location and velocity).
     *
     * @param expected The expected Player object.
     * @param actual   The actual Player object to check.
     */
    public static void comparePlayers(Player expected, Player actual) {
        // Assert that the X and Y components of the location are equal
        assertEquals(expected.getLocation().getX(), actual.getLocation().getX(), EPSILON);
        assertEquals(expected.getLocation().getY(), actual.getLocation().getY(), EPSILON);

        // Assert that the X and Y components of the velocity are equal
        assertEquals(expected.getVelocity().getX(), actual.getVelocity().getX(), EPSILON);
        assertEquals(expected.getVelocity().getY(), actual.getVelocity().getY(), EPSILON);
    }
    
    public static void compareListsOfLevels(LinkedListNode<Level> list1, LinkedListNode<Level> list2) {
        // Keep traversing while both lists have nodes
        while (list1 != null && list2 != null) {
            // Check if the names of the levels at the current position are the same
            assertEquals(list1.getValue().getName(), list2.getValue().getName());

            // Move to the next node in each list
            list1 = list1.getNext();
            list2 = list2.getNext();
        }

        // After the loop, if the lists were the same length, both should be null.
        // This fails the test if one list is longer than the other.
        assertNull("List 1 was longer than List 2", list1);
        assertNull("List 2 was longer than List 1", list2);
    }
}