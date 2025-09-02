package app.display.common.effects;

import java.util.ArrayList;
import java.util.Arrays;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

/**
 * An animated portal effect rendered as shrinking ellipses.
 * <p>
 * This effect displays a sequence of purple and blue ellipses to simulate a
 * portal opening or closing. It can be used for teleportation, such as when
 * entering a level.
 * 
 * @see AnimatedEffect
 */
public class PortalEffect extends AnimatedEffect {

    private Vector2D offset;
    private double maxRadius;

    /**
     * Constructs a portal effect with the given offset and maximum visual radius
     * 
     * @param offset    the offset from the owner's location
     * @param maxRadius the maximum visual radius
     */
    public PortalEffect(Vector2D offset, double maxRadius) {
        super(createFrames(maxRadius), false);
        this.offset = offset;
        this.maxRadius = maxRadius;
    }

    private static ArrayList<Node> createFrames(double maxRadius) {
        if (!JFXManager.isInitialized()) {
            return new ArrayList<>(Arrays.asList(new PlaceholderNode()));
        }
        ArrayList<Node> frames = new ArrayList<>();
        maxRadius *= Configuration.SCALE_FACTOR;
        double totalFrames = 25;
        for (int i = 1; i <= totalFrames; i++) {
            double progress = i / totalFrames;
            double xRad = (maxRadius / 2) * (1 - progress);
            double yRad = maxRadius * (1 - progress);

            Ellipse outer = new Ellipse(xRad, yRad);
            outer.setFill(Color.PURPLE.deriveColor(-45 * progress, 1, 1, 1 - progress));

            Ellipse inner = new Ellipse(xRad / 2, yRad / 2);
            inner.setFill(Color.BLUE.deriveColor(-45 * (1 - progress), 1, 1, 1 - progress));

            StackPane frame = new StackPane(outer, inner);
            frame.setPrefSize(maxRadius * 2, maxRadius * 2);
            frames.add(frame);
        }

        return frames;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        return super.getFrame(Vector2D.sub(Vector2D.add(origin, offset), this.maxRadius));
    }

}
