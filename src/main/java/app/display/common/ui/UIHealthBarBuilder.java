package app.display.common.ui;

import app.Configuration;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/**
 * Builder class for creating {@link UIHealthBar} instances with custom
 * properties.
 * <p>
 * Allows configuration of size, colors, alignment, and offset for health bars.
 * 
 * @see UIHealthBar
 * @see UIElement
 */
public class UIHealthBarBuilder {

    private double width = Configuration.ZOOM * 50;
    private double height = Configuration.ZOOM * 5;
    private double xOffset = Configuration.ZOOM * 5;
    private double yOffset = Configuration.ZOOM * 5;
    private Color backgroundColor = Color.DARKRED;
    private Color foregroundColor = Color.LIMEGREEN;
    private Pos alignment = Pos.TOP_LEFT;

    /**
     * Set the size of the health bar to the given width and height, in pixels.
     * 
     * @param width  the width, in pixels
     * @param height the height, in pixels
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the x and y offset of the health bar within its container.
     * 
     * @param offset the x and y offset, in pixels
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder offset(double offset) {
        return offset(offset, offset);
    }

    /**
     * Sets the x and y offset of the health bar within its container.
     * 
     * @param xOffset the x offset, in pixels
     * @param yOffset the y offset, in pixels
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder offset(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        return this;
    }

    /**
     * Sets the background color of the health bar. This is the portion that is a
     * fixed width, and is underneath the foreground color.
     * 
     * @param color the color to set
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder backgroundColor(Color color) {
        this.backgroundColor = color;
        return this;
    }

    /**
     * Sets the foreground color of the health bar. This is the portion whose width
     * changes with the health of the object.
     * 
     * @param color the color to set
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder foregroundColor(Color color) {
        this.foregroundColor = color;
        return this;
    }

    /**
     * Sets the alignment of the health bar within its container. This may be
     * overridden by the {@link UICollection} that posesses this health bar.
     * 
     * @param alignment the alignment to set
     * @return the same {@code UIHealthBarBuilder} instance for method chaining
     */
    public UIHealthBarBuilder alignment(Pos alignment) {
        this.alignment = alignment;
        return this;
    }

    /**
     * Creates a {@link UIHealthBar} with the properties previously set on this
     * builder.
     * 
     * @return a new health bar object
     */
    public UIHealthBar build() {
        return new UIHealthBar(width, height, xOffset, yOffset, backgroundColor, foregroundColor, alignment);
    }
}
