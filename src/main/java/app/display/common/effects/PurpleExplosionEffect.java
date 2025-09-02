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
 * An animated explosion effect with purple coloring.
 * <p>
 * This effect displays a sequence of expanding violet circles to simulate a
 * purple explosion. It is used for visual feedback when projectiles or objects
 * are destroyed.
 * 
 * @see ExplosionEffect
 * @see AnimatedEffect
 * @see Projectile
 */
public class PurpleExplosionEffect extends ExplosionEffect {

    /**
     * Constructs an explosion effect, but purple.
     * 
     * @param offset    the x and y offset from the owner's location
     * @param maxRadius the maximum visual radius of the explosion
     */
    public PurpleExplosionEffect(Vector2D offset, double maxRadius) {
        super(offset, maxRadius);
        this.frames = createFrames(maxRadius);
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
            circle.setFill(Color.VIOLET.deriveColor(-45 * progress, 1, 1, 1 - progress));
            circle.setStroke(Color.BLUE);
            frames.add(circle);
        }

        return frames;
    }

}
