package app.gameengine.model.datastructures;

/**
 * Basic generic comparator class.
 * <p>
 * This class is intended to be extended to allow for more specific comparisons.
 */
public class Comparator<T> {

    /**
     * Returns whether the first object comes before the second object, according to
     * the ordering of the specific comparator.
     * 
     * @param a the first object
     * @param b the second object
     * @return {@code true} if the first object comes first, {@code false} otherwise
     */
    public boolean compare(T a, T b) {
        return false;
    }

}
