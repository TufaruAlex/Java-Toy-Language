package Model.ADTs;

import java.util.HashMap;
import java.util.Map;

public class Heap<K, V>{
    HashMap<Integer, V> heap;
    int freeLocation;

    @Override
    public String toString() {
        StringBuilder stackString = new StringBuilder();
        for (Integer key : heap.keySet()){
            stackString.append(key.toString()).append(" --> ").append(heap.get(key).toString()).append("\n");
        }
        return stackString.toString();
    }

    public int getFreeLocation() {
        return freeLocation;
    }

    public void setContent(Map<K, V> newHeap) {
        heap.clear();
        for (K i: newHeap.keySet()){
            heap.put((Integer) i, newHeap.get(i));
        }
    }

    public HashMap<Integer, V> getContent() {
        return heap;
    }

    public Heap() {
        heap = (HashMap<Integer, V>) new HashMap<K, V>();
        freeLocation = 1;
    }

    public void add(V value) {
        heap.put(freeLocation, value);
        freeLocation = freeLocation + 1;
    }

    public V remove(K key) {
        return heap.remove(key);
    }

    public boolean isDefined(K key) {
        return heap.containsKey(key);
    }

    public V lookup(K key) {
        return heap.get(key);
    }

    public void update(K key, V value) {
        heap.put((Integer) key, value);
    }
}
