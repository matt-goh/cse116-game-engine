package app.gameengine.model.physics;

import java.util.Objects;

import app.gameengine.model.gameobjects.GameObject;

/**
 * Represents a two-dimensional vector with X and Y components.
 * <p>
 * In addition to storing X and Y components, this class provides a variety of
 * vector operations, including arithmetic, normalization, rotation, and
 * distance calculations. This class is used primarily for positions,
 * velocities, and directions.
 * 
 * @see GameObject
 * @see Hitbox
 */
public class Vector2D {

    private double x;
    private double y;
    private static final double EPSILON = 1e-9;

    /**
     * Creates a vector with the given X and Y components.
     * 
     * @param x the X component
     * @param y the Y component
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X component of this vector.
     * 
     * @return the X component
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the X component of this vector.
     * 
     * @param x the new X component
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Returns the Y component of this vector.
     * 
     * @return the Y component
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the Y component of this vector.
     * 
     * @param y the new Y component
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Returns the angle, in degrees, of this vector from the positive X-axis. The
     * angle is in the range [0, 360).
     * <p>
     * Note that since the positive y direction is downward, a positive angle is
     * clockwise.
     * 
     * @return the rotation angle, in degrees
     */
    public double rotation() {
        double angle = Math.toDegrees(Math.atan2(this.y, this.x));
        return angle < 0 ? angle + 360 : angle;
    }

    /**
     * Returns the magnitude, or length, of this vector.
     * 
     * @return the magnitude of the vector
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns a copy of this vector. This copy is a new object with the same
     * components.
     * 
     * @return a new copy of the vector
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Negates both components of this vector in place.
     */
    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    /**
     * Normalizes this vector in place, making its magnitude 1 while preserving
     * direction.
     * <p>
     * If the magnitude of the {@code Vector2D} is 0.0, the resulting vector, it
     * will not be altered.
     */
    public void normalize() {
        double magnitude = this.magnitude();
        if (magnitude != 0) {
            this.x /= magnitude;
            this.y /= magnitude;
        }
    }

    /**
     * Rotates this vector in place by the specified angle, in degrees.
     * <p>
     * Note that since the positive y direction is downward, a positive angle is
     * clockwise.
     * 
     * @param angle the angle to rotate by, in degrees
     */
    public void rotateBy(double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        double x = this.x;
        double y = this.y;
        this.x = cos * x - sin * y;
        this.y = sin * x + cos * y;
    }

    /**
     * Rotates this vector in place to the specified angle, in degrees, preserving
     * its magnitude.
     * <p>
     * Note that since the positive y direction is downward, a positive angle is
     * clockwise.
     * 
     * @param angle the angle to rotate to, in degrees
     */
    public void rotateTo(double angle) {
        double magnitude = this.magnitude();
        this.x = Math.cos(Math.toRadians(angle)) * magnitude;
        this.y = Math.sin(Math.toRadians(angle)) * magnitude;
    }

    /**
     * Floors (rounds down) both components of this vector in place.
     */
    public void floor() {
        this.x = Math.floor(this.x);
        this.y = Math.floor(this.y);
    }

    /**
     * Ceils (rounds up) both components of this vector in place.
     */
    public void ceil() {
        this.x = Math.ceil(this.x);
        this.y = Math.ceil(this.y);
    }

    /**
     * Rounds (to nearest integer) both components of this vector in place.
     */
    public void round() {
        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
    }

    private static boolean doubleEquals(double d1, double d2) {
        return Math.abs(d1 - d2) < EPSILON;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Vector2D other = (Vector2D) obj;
        return doubleEquals(this.x, other.x) && (doubleEquals(this.y, other.y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(x / EPSILON), Math.round(y / EPSILON));
    }

    @Override
    public String toString() {
        return String.format("<%.2f, %.2f>", x, y);
    }

    /**
     * Calculates the Manhattan distance between the input {@code Vector2D}s,
     * considering them as points. The Manhattan distance is defined as the sum of
     * the differences between the x and y coordinates of two points.
     * 
     * @param p1 the first point
     * @param p2 the second point
     * @return the Manhattan distance between the two input points
     */
    public static double manhattanDistance(Vector2D p1, Vector2D p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

    /**
     * Calculates the Euclidean distance between the input {@code Vector2D}s,
     * considering them as points. The Euclidean distance is defined as the square
     * root of the sum of the squares of the differences between the x and y
     * coordinates of two points. It is better thought of as the straight line
     * distance between two points.
     * 
     * @param p1 the first point
     * @param p2 the second point
     * @return the Euclidean distance between the two input points
     */
    public static double euclideanDistance(Vector2D p1, Vector2D p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    /**
     * Calculates the smallest angle between two {@code Vector2D}s, in degrees. The
     * angle will always be between 0 and 180 degrees, and does not account for the
     * direction of that angle.
     * <p>
     * If either vector has a magnitude of zero, the resulting value will be
     * {@code NaN}.
     * 
     * @param v1 the first vector
     * @param v2 the first vector
     * @return the smallest angle between the two input vectors.
     */
    public static double angleBetween(Vector2D v1, Vector2D v2) {
        return Math.toDegrees(Math.acos(Math.min(dot(v1, v2) / (v1.magnitude() * v2.magnitude()), 1.0)));
    }

    /**
     * Calculates the oriented angle from {@code v1} to {@code v2}. The angle will
     * always be between -180 and 180 degrees, accounting for the direction.
     * <p>
     * If either vector has a magnitude of zero, the resulting value will be 0.0.
     * 
     * @param v1 the first vector
     * @param v2 the first vector
     * @return the signed angle between the two input vectors
     */
    public static double signedAngleBetween(Vector2D v1, Vector2D v2) {
        return Math.toDegrees(Math.atan2(v1.getX() * v2.getY() - v1.getY() * v2.getX(),
                v1.getX() * v2.getX() + v1.getY() * v2.getY()));
    }

    /**
     * Calculates the dot product of two {@code Vector2D}s, which is defined as the
     * sum of the product of the x and y components of two vectors. This
     * value represents how closely two vectors are aligned in the same
     * direction, and can be used for other calculations, including the angle
     * between vectors.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the dot product of the two input vectors
     */
    public static double dot(Vector2D v1, Vector2D v2) {
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    /**
     * Calculates the element-by-element sum of two {@code Vector2D}s.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the sum of the two input vectors
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    /**
     * Calculates the scalar sum of a {@code Vector2D} and a value. That is, a
     * vector whose components are each the result of that original component plus
     * the input value.
     * 
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar sum
     */
    public static Vector2D add(Vector2D v1, double v) {
        return new Vector2D(v1.getX() + v, v1.getY() + v);
    }

    /**
     * Calculates the element-by-element difference of two {@code Vector2D}s.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the difference of the two input vectors
     */
    public static Vector2D sub(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    /**
     * Calculates the scalar difference of a {@code Vector2D} and a value. That is,
     * a vector whose components are each the result of that original component
     * minus the input value.
     * 
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar difference
     */
    public static Vector2D sub(Vector2D v1, double v) {
        return new Vector2D(v1.getX() - v, v1.getY() - v);
    }

    /**
     * Calculates the element-by-element product of two {@code Vector2D}s.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the product of the two input vectors
     */
    public static Vector2D mul(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() * v2.getX(), v1.getY() * v2.getY());
    }

    /**
     * Calculates the scalar product of a {@code Vector2D} and a value. That is, a
     * vector whose components are each the result of that original component times
     * the input value.
     * 
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar product
     */
    public static Vector2D mul(Vector2D v1, double v) {
        return new Vector2D(v1.getX() * v, v1.getY() * v);
    }

    /**
     * Calculates the element-by-element quotient of two {@code Vector2D}s.
     * 
     * @param v1 the first vector
     * @param v2 the second vector
     * @return the quotient of the two input vectors
     */
    public static Vector2D div(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.getX() / v2.getX(), v1.getY() / v2.getY());
    }

    /**
     * Calculates the scalar quotient of a {@code Vector2D} and a value. That is, a
     * vector who's components are each the result of that original component
     * divided by the input value.
     * 
     * @param v1 the first vector
     * @param v  the scalar value
     * @return the scalar quotient
     */
    public static Vector2D div(Vector2D v1, double v) {
        return new Vector2D(v1.getX() / v, v1.getY() / v);
    }

    /**
     * Returns a new {@code Vector2D} that is the negation of the input vector. That
     * is, both the components are the negation of the input.
     * 
     * @param v the input vector
     * @return the negation of the input vector
     */
    public static Vector2D negate(Vector2D v) {
        return new Vector2D(-v.getX(), -v.getY());
    }

    /**
     * Returns a new {@code Vector2D} that is the norm of the input vector. That is,
     * a vector with the same rotation and a magnitude of 1.
     * <p>
     * If the magnitude of the input {@code Vector2D} is 0.0, the resulting vector
     * will also have a magnitude of 0.0.
     * 
     * @param v the input vector
     * @return the norm of the input vector
     */
    public static Vector2D normalize(Vector2D v) {
        double magnitude = v.magnitude();
        if (magnitude != 0) {
            return new Vector2D(v.getX() / magnitude, v.getY() / magnitude);
        }
        return new Vector2D(0, 0);
    }

    /**
     * Returns a new {@code Vector2D} with the same magnitude as the input vector,
     * but rotated by {@code angle} degrees.
     * 
     * @param v     the input vector
     * @param angle the angle to be rotated by
     * @return the rotated equivalent of the input vector
     */
    public static Vector2D rotateBy(Vector2D v, double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sin = Math.sin(Math.toRadians(angle));
        return new Vector2D(cos * v.getX() - sin * v.getY(), sin * v.getX() + cos * v.getY());
    }

    /**
     * Returns a new {@code Vector2D} with the same magnitude as the input vector,
     * but rotated to {@code angle} degrees. Differs from
     * {@link #rotateBy(Vector2D, double)} in that the resulting angle will be
     * exactly {@code angle}.
     * 
     * @param v   the input vector
     * @param the the resulting angle
     * @return the rotated equivalent of the input vector
     */
    public static Vector2D rotateTo(Vector2D v, double angle) {
        double magnitude = v.magnitude();
        return new Vector2D(Math.cos(Math.toRadians(angle)) * magnitude, Math.sin(Math.toRadians(angle)) * magnitude);
    }

    /**
     * Returns a new {@code Vector2D} whose components are the floor (round down)
     * of the components of the input.
     * 
     * @param v the input vector
     */
    public static Vector2D floor(Vector2D v) {
        return new Vector2D(Math.floor(v.getX()), Math.floor(v.getY()));
    }

    /**
     * Returns a new {@code Vector2D} whose components are the ceil (round up) of
     * the components of the input.
     * 
     * @param v the input vector
     */
    public static Vector2D ceil(Vector2D v) {
        return new Vector2D(Math.ceil(v.getX()), Math.ceil(v.getY()));
    }

    /**
     * Returns a new {@code Vector2D} whose components are rounded to the nearest
     * integer of the components of the input.
     * 
     * @param v the input vector
     */
    public static Vector2D round(Vector2D v) {
        return new Vector2D(Math.round(v.getX()), Math.round(v.getY()));
    }

}
