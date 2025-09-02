package app.display.common.effects;

import java.util.ArrayList;
import java.util.Arrays;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.model.physics.Vector2D;
import app.games.commonobjects.Projectile;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * An animated explosion effect rendered as expanding colored circles.
 * <p>
 * This effect displays a sequence of expanding orange and yellow circles to
 * simulate an explosion. It is used for visual feedback when projectiles or
 * objects are destroyed.
 * 
 * @see AnimatedEffect
 * @see Projectile
 */
public class ExplosionEffect extends AnimatedEffect {

    private Vector2D offset;

    /**
     * Constructs an explosion effect with the given offset and maximum radius.
     * 
     * @param offset    the x and y offset from the owner's location
     * @param maxRadius the maximum visual radius of the explosion
     */
    public ExplosionEffect(Vector2D offset, double maxRadius) {
        super(createFrames(maxRadius), false);
        this.offset = offset;
    }

    private static ArrayList<Node> createFrames(double maxRadius) {
        if (!JFXManager.isInitialized()) {
            return new ArrayList<>(Arrays.asList(new PlaceholderNode()));
        }
        ArrayList<Node> frames = new ArrayList<>();
        maxRadius *= Configuration.SCALE_FACTOR;
        double totalFrames = 5;
        for (int i = 1; i <= totalFrames; i++) {
            double progress = i / totalFrames;
            Circle circle = new Circle(progress * maxRadius);
            circle.setFill(Color.ORANGE.deriveColor(-45 * progress, 1, 1, 1 - progress));
            circle.setStroke(Color.YELLOW);
            frames.add(circle);
        }

        return frames;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        return super.getFrame(Vector2D.add(origin, this.offset));
    }
}
