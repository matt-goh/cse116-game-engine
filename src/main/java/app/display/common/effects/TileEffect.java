package app.display.common.effects;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents a static visual effect rendered as a colored tile or rectangle.
 * <p>
 * This effect can be customized with fill and outline colors, dimensions, and
 * an offset. It is typically used for highlighting tiles, overlays, or simple
 * visual indicators.
 * 
 * @see Effect
 * @see StaticEffect
 */
public class TileEffect extends StaticEffect {

    protected Rectangle rect;
    protected Vector2D offset;

    /**
     * Constructs a square tile effect of size 1x1, in game units, with the given
     * outline and fill colors.
     * 
     * @param fillColor    the color of the inside of the tile
     * @param outlineColor the color of the outline of the tile
     */
    public TileEffect(Color fillColor, Color outlineColor) {
        this(fillColor, outlineColor, new Vector2D(1, 1), new Vector2D(0, 0));
    }

    /**
     * Constructs a tile with the given colors, dimensions, and offset from the
     * owner's location.
     * 
     * @param fillColor    the color of the inside of the tile
     * @param outlineColor the color of the outline of the tile
     * @param dimensions   the width and height of the tile
     * @param offset       the x and y offset from the owner's location
     */
    public TileEffect(Color fillColor, Color outlineColor, Vector2D dimensions, Vector2D offset) {
        super(null);
        if (!JFXManager.isInitialized()) {
            this.node = new PlaceholderNode();
            return;
        }
        this.rect = createFrame(dimensions.getX(), dimensions.getY(), fillColor, outlineColor);
        this.node = this.rect;
        this.offset = offset;
    }

    private static Rectangle createFrame(double width, double height, Color fillColor, Color outlineColor) {
        double scaleFactor = Configuration.SCALE_FACTOR;
        Rectangle frame = new Rectangle(width * scaleFactor, height * scaleFactor);
        frame.setFill(fillColor);
        frame.setStroke(outlineColor);
        return frame;
    }

    @Override
    public Node getFrame(Vector2D origin) {
        return super.getFrame(Vector2D.add(origin, this.offset));
    }

}
