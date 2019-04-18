package algorithm.lru;

import java.util.HashMap;
import java.util.LinkedList;

public class LRUCacheImpByLinkList<K, V> {
    private int capacity;
    private HashMap<K, V> map;
    private LinkedList<K> list;

    public LRUCacheImpByLinkList(int capacity) {
        this.capacity = capacity;
        this.list = new LinkedList<>();
        this.map = new HashMap<>();
    }

    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }

        list.remove(key);
        list.addFirst(key);

        return map.get(key);
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            list.remove(key);
            list.addFirst(key);
            return;
        }

        if (list.size() == capacity) {
            K expiredKey = list.removeLast();
            map.remove(expiredKey);
        }

        map.put(key, value);
        list.addFirst(key);
    }
}
