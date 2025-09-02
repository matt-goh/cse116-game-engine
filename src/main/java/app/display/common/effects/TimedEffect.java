package app.display.common.effects;

import javafx.scene.Node;

/**
 * Represents a static effect that persists for a set duration.
 * <p>
 * This effect displays a single JavaFX node at a given position, until a
 * specific duration has elapsed. Although it does not by default, it can change
 * over the course of its lifetime. It is similar to the {@link AnimatedEffect},
 * but uses a fixed duration instead of a fixed set of frames.
 * 
 * @see StaticEffect
 * @see AnimatedEffect
 * @see Effect
 */
public class TimedEffect extends StaticEffect {

    protected double elapsed;
    protected double duration;

    /**
     * Constructs a timed effect with the given duration and JavaFX node. Although
     * intended primarily for subclasses, it can be used directly if desired.
     * 
     * @param node     the JavaFX display node
     * @param duration the duration, in seconds, to display
     */
    public TimedEffect(Node node, double duration) {
        super(node);
        this.duration = duration;
    }

    /**
     * Reset the time to prolong the duration of the effect
     */
    public void resetTime() {
        this.elapsed = 0;
    }

    @Override
    public boolean isFinished() {
        return this.elapsed > this.duration;
    }

    @Override
    public void update(double dt) {
        this.elapsed += dt;
    }

    @Override
    public void reset() {
        this.elapsed = 0;
    }

}
