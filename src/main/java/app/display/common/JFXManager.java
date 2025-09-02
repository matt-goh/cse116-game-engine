package app.display.common;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Manages state related to function of JavaFX.
 * <p>
 * JavaFX can be very picky about when it wants to run. This static class
 * provides utilities for determining what is safe to do with JavaFX, and what
 * is not. Currently provides one utility, for determining whether the toolkit
 * is initialized, and thus whether JavaFX objects can safely be created and
 * used.
 * <p>
 * This is mostly important for running tests, as the toolkit will not be
 * initialized, but JavaFX objects may still be created, which will throw an
 * {@link IllegalStateException}. In cases where the toolkit is uninitialized,
 * the best solution is to use the dummy class {@link PlaceholderNode} in place
 * of the root of any UI elements or effects.
 * 
 * @see PlaceholderNode
 */
public class JFXManager {

    /**
     * Prevent instantiation, as this class is intended to be static.
     */
    private JFXManager() {
    }

    private static boolean initialized = false;

    /**
     * Returns whether the JavaFX toolkit is initialized, which determines whether
     * it is safe to create JavaFX elements like {@link Label}s and {@link Button}s.
     * <p>
     * If it is uninitialized, you should probably use the dummy class
     * {@link PlaceholderNode} in place of the root of any UI elements or effects.
     * 
     * @return {@code true} if the toolkit is initialized, {@code false} otherwise
     */
    public static boolean isInitialized() {
        if (initialized) {
            return true;
        }
        try {
            Platform.startup(() -> {
            });
            initialized = true;
            return true;
        } catch (IllegalStateException e) {
            initialized = true;
        } catch (Throwable t) {
            initialized = false;
        }
        return initialized;
    }

}
