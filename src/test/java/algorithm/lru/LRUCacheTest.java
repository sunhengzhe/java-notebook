package algorithm.lru;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class LRUCacheTest {

    @Test
    public void implement_by_hash_map_and_link_list() {
        LRUCacheImpByLinkList<Integer, Integer> cache = new LRUCacheImpByLinkList<>(2);

        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.get(1).intValue());

        cache.put(3, 3);    // evicts key 2
        assertNull(null, cache.get(2));

        cache.put(4, 4);    // evicts key 1
        assertNull(null, cache.get(1));
        assertEquals(3, cache.get(3).intValue());
        assertEquals(4, cache.get(4).intValue());
    }

    @Test
    public void implement_by_linked_hash_map() {
        LRUCacheImpByLinkedHashMap<Integer, Integer> cache = new LRUCacheImpByLinkedHashMap<>(2);

        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.get(1).intValue());

        cache.put(3, 3);    // evicts key 2
        assertNull(null, cache.get(2));

        cache.put(4, 4);    // evicts key 1
        assertNull(null, cache.get(1));
        assertEquals(3, cache.get(3).intValue());
        assertEquals(4, cache.get(4).intValue());
    }
}
