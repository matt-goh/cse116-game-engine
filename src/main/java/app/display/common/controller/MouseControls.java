package app.display.common.controller;

import java.util.HashMap;

import app.gameengine.Game;
import app.gameengine.model.physics.Vector2D;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

/**
 * Base controls class for handling mouse input in a game.
 * <p>
 * This class manages mouse button state tracking, but does not perform any
 * default behaviors for any mouse button. It is intended to be extended by more
 * specific control classes for different game types.
 * 
 * @see Game
 * @see KeyboardControls
 */
public class MouseControls implements EventHandler<MouseEvent> {

    protected Game game;
    protected HashMap<MouseButton, Pair<EventType<? extends MouseEvent>, Vector2D>> mouseStates = new HashMap<>();

    /**
     * Constructs a new {@code MouseControls} instance for the specified
     * {@code Game}.
     * 
     * @param game the {@code Game} instance to be controlled
     */
    public MouseControls(Game game) {
        this.game = game;
    }

    /**
     * Reset the controls to their initial state. Namely, this means clearing the
     * buttons currently being pressed.
     */
    public void reset() {
        mouseStates.clear();
    }

    @Override
    public void handle(MouseEvent event) {
        this.mouseStates.put(event.getButton(),
                new Pair<>(event.getEventType(), new Vector2D(event.getSceneX(), event.getSceneY())));
    }

}
