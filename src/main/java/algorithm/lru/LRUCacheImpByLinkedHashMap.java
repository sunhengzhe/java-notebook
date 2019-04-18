package algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpByLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    public LRUCacheImpByLinkedHashMap(int initialCapacity) {
        super(initialCapacity, 0.75f, true);
        capacity = initialCapacity;
    }

    @Override
    public V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public V get(Object key) {
        return super.get(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }
}
