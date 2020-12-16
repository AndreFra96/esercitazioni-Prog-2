public interface Map<K, V> extends Iterable<java.util.Map.Entry<K, V>> {
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    V get(Object key);
    V put(K key, V value);
    V remove(Object key);
    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }
}