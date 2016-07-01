package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);
    private final Map<Long, ResponseEntity<Todo>> cache;

    public TodoCacheManager() {
        cache = new ConcurrentHashMap<>();
    }

    public synchronized ResponseEntity<Todo> put(ResponseEntity<Todo> item) {
        final ResponseEntity<Todo> cached = cacheEntry(item);
        LOGGER.info("CACHED: " + cached.toString());
        return cached;
    }

    public synchronized ResponseEntity<Todo> get(long key) {
        ResponseEntity<Todo> entry = cache.get(key);
        return entry;
    }

    private ResponseEntity<Todo> cacheEntry(ResponseEntity<Todo> item) {
        long key = item.getBody().getId();
        ResponseEntity<Todo> previousResponse = cache.put(key, item);
        ResponseEntity<Todo> currentResponse = cache.get(key);
        LOGGER.info("REMOVED: " + previousResponse.toString());
        LOGGER.info("ADDED: " + currentResponse.toString());
        return currentResponse;
    }
}
