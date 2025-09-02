package app.display.common.effects;

import app.Configuration;
import app.Settings;
import app.gameengine.model.gameobjects.GameObject;
import app.gameengine.model.physics.Hitbox;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.paint.Color;

/**
 * Visual effect for outlining the hitbox of a game object.
 * <p>
 * This effect draws a colored outline around the object's hitbox, useful for
 * debugging or highlighting. It automatically disappears when hitbox display is
 * disabled or the object is destroyed.
 * <p>
 * If no color is specified, the outline color depends on the result of
 * {@link GameObject#isSolid()}, being red if it returns {@code true}, and blue
 * otherwise.
 * 
 * @see TileEffect
 * @see GameObject
 * @see Hitbox
 * @see Settings
 */
public class HitboxOutline extends TileEffect {

    private GameObject object;

    /**
     * Constructs an outline surrounding the hitbox of the given object. The outline
     * is either red or blue depending on if the object is solid or not solid,
     * respectively.
     * 
     * @param object the object possessing the outline
     */
    public HitboxOutline(GameObject object) {
        this(object, object.isSolid() ? Color.RED : Color.BLUE);
    }

    /**
     * Constructs an outline surrounding the hitbox of the given object, with the
     * given color.
     * 
     * @param object the object possessing the outline
     * @param color  the color of the outline
     */
    public HitboxOutline(GameObject object, Color color) {
        this(object, color, object.getHitbox().getDimensions(), object.getHitbox().getOffset());
    }

    /**
     * Constructs an outline surrounding the object, with the given dimensions,
     * offset from the owner's location, and color.
     * 
     * @param object     the object possessing the outline
     * @param color      the color of the outline
     * @param dimensions the dimensions of the outline
     * @param offset     the x and y offset from the owner's location
     */
    public HitboxOutline(GameObject object, Color color, Vector2D dimensions, Vector2D offset) {
        super(Color.TRANSPARENT, color, dimensions, offset);
        this.object = object;
    }

    @Override
    public boolean isFinished() {
        return !Settings.showHitboxes() || object.isDestroyed();
    }

    @Override
    public void update(double dt) {
        this.rect.setWidth(this.object.getHitbox().getDimensions().getX() * Configuration.SCALE_FACTOR);
        this.rect.setHeight(this.object.getHitbox().getDimensions().getY() * Configuration.SCALE_FACTOR);
    }

}
