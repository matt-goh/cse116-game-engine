package app.display.common.controller;

import app.Settings;
import app.gameengine.Game;
import app.gameengine.model.physics.Vector2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Mouse controls with default behavior suitable for many games.
 * <p>
 * This class is intended to be the default mouse controls, with basic click
 * position tracking, which sends primary (left) and secondary (right) clicks to
 * the game to be handled, along with their location within the game.
 * 
 * @see MouseControls
 * @see Game
 */
public class BasicMouseControls extends MouseControls {

    /**
     * Construct a new mouse controller. This controller handles basic mouse
     * controls, forwarding left and right clicks to the current level.
     * 
     * @param game the game associated with these controls.
     */
    public BasicMouseControls(Game game) {
        super(game);
    }

    @Override
    public void handle(MouseEvent event) {
        super.handle(event);
        if (event.getEventType() != MouseEvent.MOUSE_PRESSED || Settings.paused()) {
            return;
        }

        Vector2D gameLoc = this.game.screenSpaceToWorldSpace(this.mouseStates.get(event.getButton()).getValue());
        if (event.getButton() == MouseButton.PRIMARY) {
            this.game.getCurrentLevel().handleLeftClick(gameLoc);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            this.game.getCurrentLevel().handleRightClick(gameLoc);
        }
    }

}
