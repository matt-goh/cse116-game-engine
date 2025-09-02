package app.display.common.effects;

import app.Configuration;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.Node;

/**
 * Represents a static, non-animated visual effect.
 * <p>
 * This effect displays a single JavaFX node at a given position and does not
 * change over time. It can be used for overlays, highlights, or persistent
 * indicators.
 * 
 * @see Effect
 * @see TimedEffect
 * @see AnimatedEffect
 */
public class StaticEffect implements Effect {

    protected Node node;

    /**
     * Constructs a static effect with the given node. Although intended primarily
     * for subclasses, it can be used directly if desired.
     * 
     * @param node the JavaFX display node
     */
    public StaticEffect(Node node) {
        this.node = node;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        this.node.setLayoutX(origin.getX() * Configuration.SCALE_FACTOR);
        this.node.setLayoutY(origin.getY() * Configuration.SCALE_FACTOR);
        return this.node;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void reset() {

    }

}
