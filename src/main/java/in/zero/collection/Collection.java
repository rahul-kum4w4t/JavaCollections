package in.zero.collection;

/**
 * Interface which every collection class needs to implement
 * @param <T>
 */
public interface Collection<T> {

    Collection<T> add(T val);

    Collection<T> addAll(T... vals);

    T remove(T val);

    T[] removeAll(T... vals);

    boolean search(T val);
}
