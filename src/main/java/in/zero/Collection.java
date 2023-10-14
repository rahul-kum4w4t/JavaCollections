package in.zero;

public interface Collection<T> {

    Collection<T> add(T val);

    Collection<T> addAll(T... vals);

    boolean remove(T val);

    Boolean[] removeAll(T... vals);

    boolean search(T val);
}
