public interface Queue<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    boolean enqueue(E e);
    E dequeue();
}