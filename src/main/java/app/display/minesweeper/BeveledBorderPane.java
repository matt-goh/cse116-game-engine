package app.display.minesweeper;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * StackPane with beveled borders.
 * <p>
 * Intended for use as part of the Minesweeper UI, this class can be used to
 * house other UI elements and components, surrounding them in beveled borders.
 * The bevels are considered part of the border of the pane, meaning that all
 * content is laid out inside of the bevels. A builder class is provided for
 * convenience.
 * 
 * @see BeveledBorderPaneBuilder
 * @see MinesweeperMenu
 * @see javafx.scene.layout.StackPane
 */
public class BeveledBorderPane extends StackPane {

    private double bevelThickness;
    private Node content;

    private Rectangle background = new Rectangle();
    private Polygon topBevel = new Polygon();
    private Polygon leftBevel = new Polygon();
    private Polygon bottomBevel = new Polygon();
    private Polygon rightBevel = new Polygon();

    /**
     * Constructs pane around the given content, with the given properties.
     * 
     * @param content          the content of the pane
     * @param bevelThickness   the thickness of the borders of the pane
     * @param backgroundColor  the background color of the pane
     * @param topLeftColor     the color of the top and left edges of the pane
     * @param bottomRightColor the color of the bottom and right edges of the pane
     */
    public BeveledBorderPane(Node content, double bevelThickness, Color backgroundColor, Color topLeftColor,
            Color bottomRightColor) {
        this.bevelThickness = bevelThickness;
        this.content = content;

        background.setFill(backgroundColor);
        topBevel.setFill(topLeftColor);
        leftBevel.setFill(topLeftColor);
        bottomBevel.setFill(bottomRightColor);
        rightBevel.setFill(bottomRightColor);

        this.getChildren().addAll(background, content, topBevel, leftBevel, bottomBevel, rightBevel);
        StackPane.setAlignment(content, Pos.CENTER);
        StackPane.setAlignment(topBevel, Pos.TOP_CENTER);
        StackPane.setAlignment(leftBevel, Pos.CENTER_LEFT);
        StackPane.setAlignment(bottomBevel, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(rightBevel, Pos.CENTER_RIGHT);
    }

    public void setBackgroundColor(Color color) {
        background.setFill(color);
    }

    public void setBevelColors(Color topLeft, Color bottomRight) {
        topBevel.setFill(topLeft);
        leftBevel.setFill(topLeft);
        bottomBevel.setFill(bottomRight);
        rightBevel.setFill(bottomRight);
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        double t = bevelThickness;

        background.setWidth(w);
        background.setHeight(h);

        topBevel.getPoints().setAll(
                0.0, 0.0,
                w, 0.0,
                w - t, t,
                t, t);

        leftBevel.getPoints().setAll(
                0.0, 0.0,
                t, t,
                t, h - t,
                0.0, h);

        bottomBevel.getPoints().setAll(
                0.0, h,
                t, h - t,
                w - t, h - t,
                w, h);

        rightBevel.getPoints().setAll(
                w, 0.0,
                w, h,
                w - t, h - t,
                w - t, t);

        layoutInArea(content, t, t, w - 2 * t, h - 2 * t, 0, null, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return content.prefWidth(height) + 2 * bevelThickness;
    }

    @Override
    protected double computePrefHeight(double width) {
        return content.prefHeight(width) + 2 * bevelThickness;
    }

    @Override
    protected double computeMinWidth(double height) {
        return computePrefWidth(height);
    }

    @Override
    protected double computeMinHeight(double width) {
        return computePrefHeight(width);
    }

    @Override
    protected double computeMaxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    protected double computeMaxHeight(double width) {
        return Double.MAX_VALUE;
    }
}
