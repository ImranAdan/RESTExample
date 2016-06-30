package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);

    private final Map<Long, Todo> cache;

    public TodoCacheManager() {
        cache = new ConcurrentHashMap<>();
    }


    public synchronized Todo put(Todo item) {
        final Todo cached = cacheEntry(item);
        LOGGER.info("CACHED: " + cached.toString());
        return cached;
    }

    public synchronized Todo get(long key) {
        Todo entry = cache.get(key);
        return entry;
    }

    private Todo cacheEntry(Todo item) {
        Todo previous = cache.put(item.getId(), item);
        Todo current = cache.get(item.getId());
        LOGGER.info("REMOVED: " + previous);
        LOGGER.info("ADDED: " + current);
        return current;
    }
}
