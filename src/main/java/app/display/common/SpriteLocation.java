package app.display.common;

import app.gameengine.model.gameobjects.GameObject;

/**
 * Represents the location and transformation of a sprite within a sprite sheet.
 * <p>
 * A SpriteLocation specifies the column and row of the sprite in the sheet,
 * as well as optional rotation and horizontal/vertical reflection. This allows
 * for flexible rendering of sprites with different orientations and
 * transformations.
 * <p>
 * Note that if both reflection and rotation are applied, reflection is handled
 * first.
 * Note that since the positive y direction is downward, a positive angle is
 * clockwise.
 * 
 * @see RenderableAsSprite
 * @see SpriteGraphics
 * @see GameObject
 */
public class SpriteLocation {

    private int row;
    private int column;
    private double rotation;
    private boolean reflectH;
    private boolean reflectV;

    /**
     * Constructs a SpriteLocation with the specified column and row, no rotation or
     * reflection.
     *
     * @param column the column index in the sprite sheet
     * @param row    the row index in the sprite sheet
     */
    public SpriteLocation(int column, int row) {
        this(column, row, 0, false, false);
    }

    /**
     * Constructs a SpriteLocation with the specified column, row, and reflection
     * flags.
     *
     * @param column            the column index in the sprite sheet
     * @param row               the row index in the sprite sheet
     * @param reflectHorizontal whether to reflect the sprite horizontally
     * @param reflectVertical   whether to reflect the sprite vertically
     */
    public SpriteLocation(int column, int row, boolean reflectHorizontal, boolean reflectVertical) {
        this(column, row, 0, reflectHorizontal, reflectVertical);
    }

    /**
     * Constructs a SpriteLocation with the specified column, row, and rotation.
     *
     * @param column   the column index in the sprite sheet
     * @param row      the row index in the sprite sheet
     * @param rotation the rotation angle in degrees
     */
    public SpriteLocation(int column, int row, double rotation) {
        this(column, row, rotation, false, false);
    }

    /**
     * Constructs a SpriteLocation with the specified column, row, rotation, and
     * reflection flags. Note that when both reflection and rotation are applied,
     * reflection is handled first. Note that since the positive y direction is
     * downward, a positive angle is clockwise.
     *
     * @param column            the column index in the sprite sheet
     * @param row               the row index in the sprite sheet
     * @param rotation          the rotation angle in degrees
     * @param reflectHorizontal whether to reflect the sprite horizontally
     * @param reflectVertical   whether to reflect the sprite vertically
     */
    public SpriteLocation(int column, int row, double rotation, boolean reflectHorizontal, boolean reflectVertical) {
        this.column = column;
        this.row = row;
        this.rotation = rotation % 360;
        this.reflectH = reflectHorizontal;
        this.reflectV = reflectVertical;
    }

    /**
     * Returns whether the sprite is reflected horizontally.
     *
     * @return {@code true} if reflected horizontally, {@code false} otherwise
     */
    public boolean isReflectedHorizontally() {
        return reflectH;
    }

    /**
     * Sets whether the sprite should be reflected horizontally.
     *
     * @param reflectH {@code true} to reflect horizontally, {@code false} otherwise
     */
    public void setReflectedHorizontally(boolean reflectH) {
        this.reflectH = reflectH;
    }

    /**
     * Returns whether the sprite is reflected vertically.
     *
     * @return {@code true} if reflected vertically, {@code false} otherwise
     */
    public boolean isReflectedVertically() {
        return reflectV;
    }

    /**
     * Sets whether the sprite should be reflected vertically.
     *
     * @param reflectY {@code true} to reflect vertically, {@code false} otherwise
     */
    public void setReflectedVertically(boolean reflectV) {
        this.reflectV = reflectV;
    }

    /**
     * Returns the row index of the sprite in the sprite sheet.
     *
     * @return the row index
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row index of the sprite in the sprite sheet.
     *
     * @param row the new row index
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column index of the sprite in the sprite sheet.
     *
     * @return the column index
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column index of the sprite in the sprite sheet.
     *
     * @param column the new column index
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Returns the rotation angle, in degrees, of the sprite.
     * <p>
     * Note that since the positive y direction is downward, a positive angle is
     * clockwise.
     *
     * @return the rotation angle, in degrees
     */
    public double getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation angle, in degrees, of the sprite.
     * <p>
     * Note that since the positive y direction is downward, a positive angle is
     * clockwise.
     *
     * @param rotation the new rotation angle, in degrees
     */
    public void setRotation(double rotation) {
        this.rotation = rotation % 360;
    }

    private static boolean doubleEquals(double d1, double d2) {
        return Math.abs(d1 - d2) < 1e-9;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        SpriteLocation other = (SpriteLocation) obj;
        return this.column == other.column && this.row == other.row && doubleEquals(this.rotation, other.rotation)
                && this.reflectH == other.reflectH && this.reflectV == other.reflectV;
    }

    @Override
    public String toString() {
        String base = String.format("[column=%d, row=%d, rotation=%2.0f]", this.column, this.row, this.rotation);
        if (this.reflectH) {
            base += " (Horizontal reflection)";
        }
        if (this.reflectV) {
            base += " (Vertical reflection)";
        }
        return base;
    }
}
