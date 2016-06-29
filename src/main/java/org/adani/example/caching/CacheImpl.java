package org.adani.example.caching;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: No reason to build own cache use a provided
 * ConcurrentCache implementation * e.g. see spring/apache projects
 */
public class CacheImpl<K, V> implements Cache<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheImpl.class);


    public V get(K key) {
        throw new NotImplementedException("No implementation available");
    }


    public V put(K key, V value) {
        throw new NotImplementedException("No implementation available");
    }
}
