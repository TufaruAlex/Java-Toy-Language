package Model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V> {
    Map<K, V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<K,V>();
    }

    @Override
    public String toString() {
        StringBuilder stackString = new StringBuilder();
        for (K key : dictionary.keySet()){
            stackString.append(key.toString()).append(" --> ").append(dictionary.get(key).toString()).append("\n");
        }
        return stackString.toString();
    }

    @Override
    public void add(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V remove(K key) {
        return dictionary.remove(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    @Override
    public V lookup(K key) {
        return dictionary.get(key);
    }

    @Override
    public void update(K key, V newValue) {
        dictionary.put(key, newValue);
    }

    @Override
    public Map<K, V> getContent() {
        return dictionary;
    }

    @Override
    public MyIDictionary<K, V> copy() {
        MyIDictionary<K, V> copy = new MyDictionary<>();
        for (K key: dictionary.keySet()){
            copy.add(key, lookup(key));
        }
        return copy;
    }
}
