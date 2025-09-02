package app.display.common.effects;

import app.Settings;
import javafx.scene.paint.Color;

/**
 * Visual effect indicating god mode status.
 * <p>
 * This effect overlays a translucent cyan and blue tile to show that god mode
 * is active. It automatically disappears when god mode is disabled.
 * 
 * @see TileEffect
 * @see Settings
 */
public class GodEffect extends TileEffect {

    /**
     * Constructs a cyan tile effect that disappears when god mode is disabled.
     */
    public GodEffect() {
        super(Color.CYAN.deriveColor(0, 1, 1, 0.25), Color.BLUE.deriveColor(0, 1, 1, 0.25));
    }

    @Override
    public boolean isFinished() {
        return !(Settings.godMode());
    }

}
