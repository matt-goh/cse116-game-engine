package app.display.common.controller;

import java.util.HashMap;

import app.Configuration;
import app.Settings;
import app.display.common.effects.GodEffect;
import app.gameengine.Game;
import app.games.commonobjects.Projectile;
import app.games.topdownobjects.Enemy;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Base controls class for handling keyboard input in a game.
 * <p>
 * This class manages key state tracking and handles standard game controls,
 * including pausing, inventory controls, and debug controls. It is intended to
 * be extended by more specific control classes for different game types.
 * 
 * @see Game
 * @see MouseControls
 */
public class KeyboardControls implements EventHandler<KeyEvent> {

    protected Game game;
    protected HashMap<KeyCode, Boolean> keyStates = new HashMap<>();

    /**
     * Constructs a new {@code KeyboardControls} instance for the specified
     * {@code Game}.
     * 
     * @param game the {@code Game} instance to be controlled
     */
    public KeyboardControls(Game game) {
        this.game = game;
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            this.keyStates.put(event.getCode(), true);
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            this.keyStates.put(event.getCode(), false);
        }

        if (event.getEventType() != KeyEvent.KEY_PRESSED) {
            return;
        }
        // Normal controls
        if (event.getCode() == KeyCode.F9) {
            Platform.exit();
        } else if (event.getCode() == KeyCode.ESCAPE) {
            Settings.togglePaused();
            if (Settings.paused()) {
                this.game.pause();
            } else {
                this.game.unpause();
            }
        }
        // Pausable controls
        if (!Settings.paused()) {
            if (event.getCode() == KeyCode.SPACE) {
                this.game.getCurrentLevel().actionButtonPressed();
            } else if (event.getCode() == KeyCode.TAB) {
                this.game.getPlayer().cycleInventory();
            }
        }
        // Debug controls
        if (event.getEventType() == KeyEvent.KEY_PRESSED && Configuration.DEBUG_MODE) {
            if (event.getCode() == KeyCode.F1) {
                this.game.resetCurrentLevel();
            }
            if (event.getCode() == KeyCode.F2) {
                this.game.resetGame();
            }
            if (event.getCode() == KeyCode.F3) {
                this.game.advanceLevel();
            }
            if (event.getCode() == KeyCode.F4) {
                Settings.toggleShowHitboxes();
            }
            if (event.getCode() == KeyCode.F5) {
                this.game.getCurrentLevel().getDynamicObjects().forEach(obj -> {
                    if (obj instanceof Enemy || obj instanceof Projectile)
                        obj.destroy();
                });
            }
            if (event.getCode() == KeyCode.F6) {
                Settings.toggleGodMode();
                this.game.getPlayer().getEffects().add(new GodEffect());
            }
        }
    }

    /**
     * Process any inputs that have been sent since the last frame, and update
     * game/player state accordingly.
     * <p>
     * This method is not guaranteed to do anything, as it's possible to perform all
     * necessary operations immediately when the key is pressed. However, this
     * method is often necessary to sync operations with dt.
     * 
     * @param dt the time elapsed since the last frame, in seconds
     */
    public void processInput(double dt) {

    }

    /**
     * Reset the controls to their initial state. Namely, this means clearing the
     * keys currently being pressed.
     */
    public void reset() {
        keyStates.clear();
    }

}
