package app.display.common.ui;

import app.Configuration;
import app.display.common.JFXManager;
import app.display.common.PlaceholderNode;
import app.gameengine.Level;
import app.gameengine.model.gameobjects.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A UI element that displays a health bar for the player.
 * <p>
 * The health bar visually represents the player's current health and updates as
 * the player's health changes.
 * 
 * @see UIHealthBarBuilder
 * @see UIElement
 * @see UICollection
 * @see Player
 */
public class UIHealthBar extends UIElement {

    private Node root = new StackPane();
    private Rectangle background;
    private Rectangle foreground;

    private double fullWidth;

    /**
     * Constructs a new health bar with the default properties.
     */
    public UIHealthBar() {
        this(Configuration.ZOOM * 50, Configuration.ZOOM * 5, Configuration.ZOOM * 5, Configuration.ZOOM * 5,
                Color.DARKRED, Color.LIMEGREEN, Pos.TOP_LEFT);
    }

    /**
     * Constructs a new health bar with the given properties. A builder,
     * {@link UIHealthBarBuilder}, is provided for convenience.
     * 
     * @param width           the width, in pixels, of the health bar
     * @param height          the height, in pixels, of the health bar
     * @param xOffset         the x offset, in pixels, of this health bar within its
     *                        container
     * @param yOffset         the y offset, in pixels, of this health bar within its
     *                        container
     * @param backgroundColor the color of the background of the health bar
     * @param foregroundColor the color of the foreground of the health bar
     * @param alignment       the alignment of the health bar within its container
     */
    public UIHealthBar(double width, double height, double xOffset, double yOffset, Color backgroundColor,
            Color foregroundColor, Pos alignment) {
        if (!JFXManager.isInitialized()) {
            this.root = new PlaceholderNode();
            return;
        }
        this.fullWidth = width;
        this.background = new Rectangle(width, height);
        this.background.setFill(backgroundColor);
        this.foreground = new Rectangle(width, height);
        this.foreground.setFill(foregroundColor);

        StackPane healthBar = new StackPane();
        this.root = healthBar;
        healthBar.getChildren().addAll(this.background, this.foreground);
        StackPane.setAlignment(this.background, alignment);
        StackPane.setAlignment(this.foreground, alignment);
        Insets offset = this.computeOffset(alignment, xOffset, yOffset);
        StackPane.setMargin(this.root, offset);
    }

    @Override
    public Node getRenderable() {
        return this.root;
    }

    @Override
    public void update(double dt, Level level) {
        if (this.foreground != null) {
            this.foreground.setWidth(fullWidth * level.getPlayer().getHP() / level.getPlayer().getMaxHP());
        }
    }

}
