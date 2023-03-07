package Model.ADTs;

import java.util.Map;

public interface MyIDictionary<K, V> {
    void add(K key, V value);

    V remove(K key);

    boolean isDefined(K key);

    V lookup(K key);

    void update(K key, V value);

    Map<K, V> getContent();

    MyIDictionary<K, V> copy();
}
