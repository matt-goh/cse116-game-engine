package app.tests;

import app.gameengine.model.gameobjects.Player;

import static org.junit.Assert.assertEquals;

public class TestUtils {
    // A small value to account for floating-point inaccuracies
    private static final double EPSILON = 0.001;

    /**
     * Asserts that two Player objects have the same state (location and velocity).
     * @param expected The expected Player object.
     * @param actual The actual Player object to check.
     */
    public static void comparePlayers(Player expected, Player actual) {
        // Assert that the X and Y components of the location are equal
        assertEquals(expected.getLocation().getX(), actual.getLocation().getX(), EPSILON);
        assertEquals(expected.getLocation().getY(), actual.getLocation().getY(), EPSILON);

        // Assert that the X and Y components of the velocity are equal
        assertEquals(expected.getVelocity().getX(), actual.getVelocity().getX(), EPSILON);
        assertEquals(expected.getVelocity().getY(), actual.getVelocity().getY(), EPSILON);
    }
}