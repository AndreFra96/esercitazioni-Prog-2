public interface Set<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    E choose();
    boolean add(E e);
    boolean remove(Object o);
}