package app.display.common.ui;

import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.Game;
import app.gameengine.Level;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;

/**
 * Abstract base class for all UI elements in the game.
 * <p>
 * UI elements provide a renderable JavaFX node and update logic for integration
 * with the game level.
 * 
 * @see UICollection
 * @see Level
 * @see Game
 * @see JFXManager
 * @see PlaceholderNode
 */
public abstract class UIElement {

    /**
     * Returns the JavaFX node that visually represents this UI element. It is
     * expected that this is the root node of the element, and while the content may
     * freely change, that root node should not.
     * <p>
     * If the JavaFX node toolkit is not initialized, this node is responsible for
     * reacting safely. The easiest way to check is by using the
     * {@link JFXManager#isInitialized()} method, and the easiest resolution in the
     * cast that it is uninitialized is by replacing the root node with a
     * {@link PlaceholderNode}. More details on the issues and solutions can be
     * found in those classes.
     *
     * @return the renderable JavaFX node
     */
    public abstract Node getRenderable();

    /**
     * Updates the UI element's state based on the elapsed time and current level.
     *
     * @param dt    the time elapsed since the last update, in seconds
     * @param level the current game level
     */
    public abstract void update(double dt, Level level);

    /**
     * Computes the offset insets for positioning the UI element based on alignment
     * and offset values.
     *
     * @param alignment the alignment position
     * @param xOffset   the horizontal offset
     * @param yOffset   the vertical offset
     * @return the computed {@code Insets} for positioning
     */
    protected Insets computeOffset(Pos alignment, double xOffset, double yOffset) {
        double top = 0, right = 0, bottom = 0, left = 0;

        switch (alignment) {
            case TOP_LEFT:
                top = yOffset;
                left = xOffset;
                break;
            case TOP_CENTER:
                top = yOffset;
                break;
            case TOP_RIGHT:
                top = yOffset;
                right = xOffset;
                break;
            case CENTER_LEFT:
                left = xOffset;
                break;
            case CENTER:
                break;
            case CENTER_RIGHT:
                right = xOffset;
                break;
            case BOTTOM_LEFT:
                bottom = yOffset;
                left = xOffset;
                break;
            case BOTTOM_CENTER:
                bottom = yOffset;
                break;
            case BOTTOM_RIGHT:
                bottom = yOffset;
                right = xOffset;
                break;
            case BASELINE_LEFT:
                left = xOffset;
                break;
            case BASELINE_CENTER:
                break;
            case BASELINE_RIGHT:
                right = xOffset;
                break;
        }

        return new Insets(top, right, bottom, left);
    }

    /**
     * Resets the UI element to its initial state.
     * <p>
     * Subclasses may override this method to implement custom reset behavior if
     * necessary.
     */
    public void reset() {

    }

}
