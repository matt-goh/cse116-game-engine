package app.display.common.effects;

import java.util.ArrayList;
import java.util.Arrays;

import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.display.common.RenderableAsSprite;
import app.display.common.SpriteGraphics;
import app.gameengine.model.gameobjects.GameObject;
import javafx.scene.Node;

/**
 * An animated effect for representing the death of a game object.
 * <p>
 * This effect displays a sequence of fading and rotating sprite graphics to
 * visually indicate destruction.
 * 
 * @see AnimatedEffect
 * @see GameObject
 * @see RenderableAsSprite
 */
public class DeathEffect extends AnimatedEffect {

    /**
     * Constructs a death effect for the given object.
     * 
     * @param renderable the object possessing the effect
     */
    public DeathEffect(RenderableAsSprite renderable) {
        super(getFrames(renderable), false);
    }

    private static ArrayList<Node> getFrames(RenderableAsSprite renderable) {
        if (!JFXManager.isInitialized()) {
            return new ArrayList<>(Arrays.asList(new PlaceholderNode()));
        }
        ArrayList<Node> frames = new ArrayList<>();
        double totalFrames = 10;

        for (int i = 1; i <= totalFrames; i++) {
            double progress = i / totalFrames;
            SpriteGraphics graphic = new SpriteGraphics(renderable);
            graphic.setOpacity(1 - progress);
            graphic.setRotate(90 * progress);
            double scale = 1 - progress / 2;
            graphic.setScaleX(scale);
            graphic.setScaleY(scale);
            frames.add(graphic);
        }

        return frames;
    }

}
