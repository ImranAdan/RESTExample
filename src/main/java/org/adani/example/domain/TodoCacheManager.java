package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);

    private final Executor executor;
    private Map<Long, Todo> cache;

    private TodoCacheManager() {
        executor = Executors.newSingleThreadExecutor();
        cache = new ConcurrentHashMap<>();
    }

    public synchronized Todo put(Todo item) {
        final Todo cached = insert(item);
        LOGGER.info("CACHED: " + cached.toString());
        return cached;
    }

    public synchronized Todo get(long key) {
        Todo entry = cache.get(key);
        return entry;
    }

    private Todo insert(Todo item) {
        Todo previous = cache.put(item.getId(), item);
        Todo current = cache.get(item.getId());
        LOGGER.info("REMOVED: " + previous);
        LOGGER.info("ADDED: " + current);
        return current;
    }


    public void execute() {
        executor.execute(TodoMonitorTask.getInstance());
    }
}
