package lab9;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int buckets_index = hash(key);
        return buckets[buckets_index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        size += 1;
        if (loadFactor()>=MAX_LF) {
            resize();
        }
        int buckets_index = hash(key);
        buckets[buckets_index].put(key, value);
    }

    public void resize() {
        Set<K> bucket_set;
        K[] bucket_array;
        K key_temp;
        V value_temp;
        int numBuckets_new;
        int buckets_index_new;
        ArrayMap<K, V>[] buckets_new = new ArrayMap[buckets.length * 2];
        // initialize the bin
        for (int i = 0; i < buckets_new.length; i += 1) {
            buckets_new[i] = new ArrayMap<>();
        }
        for (int i = 0; i < buckets.length; i++) {
            bucket_set = buckets[i].keySet();
            bucket_array = (K[]) bucket_set.toArray();
            for (int j = 0; j < bucket_array.length; j++) {
                key_temp = bucket_array[j];
                value_temp = buckets[i].get(key_temp);
                numBuckets_new = buckets_new.length;
                Math.floorMod(key_temp.hashCode(), numBuckets_new);
                buckets_index_new = hash(key_temp);
                buckets_new[buckets_index_new].put(key_temp, value_temp);
            }
        }
        buckets = buckets_new;
    }


    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        int buckets_size = 0;
        for (int i = 0; i < buckets.length; i++) {
            buckets_size += buckets[i].size;
        }
        return buckets_size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
