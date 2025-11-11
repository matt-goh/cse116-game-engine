package app.gameengine.model.datastructures;

public interface Comparator<T> {

    /**
     * Compares two objects of type T.
     *
     * @param a the first object
     * @param b the second object
     * @return true if a is considered "less than" b, false otherwise
     */
    public boolean compare(T a, T b);

}
