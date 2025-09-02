package app.display.common.effects;

import app.gameengine.model.gameobjects.GameObject;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.Node;

/**
 * Represents a visual effect that can be applied to any object in a game.
 * <p>
 * Effects may be temporary or permanent, and are responsible for updating
 * internal state, providing a visual element, and determining when they have
 * concluded, if ever. Typical uses include animations, particle effects, and
 * temporary overlays for certain items.
 * 
 * @see AnimatedEffect
 * @see StaticEffect
 * @see GameObject
 */
public interface Effect {

    /**
     * Determines whether the effect has finished and should be removed.
     * 
     * @return {@code true} if the effect is finished, {@code false otherwise}
     */
    boolean isFinished();

    /**
     * Updates the internal state of the effect.
     *
     * @param dt the time elapsed since the last update, in seconds
     */
    void update(double dt);

    /**
     * Returns the current visual representation of the effect as a JavaFX
     * {@link Node}.
     *
     * @param origin the central position in world space where the effect should be
     *               rendered
     * @return the JavaFX node representing the current frame of the effect
     */
    Node getFrame(Vector2D origin);

    /**
     * Resets this effect to its initial state so that it can be reused.
     */
    void reset();

}
