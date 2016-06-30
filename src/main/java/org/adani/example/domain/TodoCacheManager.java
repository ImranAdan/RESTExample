package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);

    private final Executor executor;
    private Map<Long, Todo> cache;

    public TodoCacheManager() {
        executor = Executors.newSingleThreadExecutor();
        cache = new ConcurrentHashMap<>();
    }

    public Todo put(Todo item) {
        final Todo cached = insert(item);
        return cached;
    }

    private Todo insert(Todo item) {
        Todo previous = cache.put(item.getId(), item);
        Todo current = cache.get(item.getId());
        LOGGER.info("REMOVED: " + previous);
        LOGGER.info("ADDED: " + current);
        return current;
    }

    private Todo get(long key) {
        Todo entry = cache.get(key);
        return entry;
    }

    private boolean expired(Todo todo) {
        return System.currentTimeMillis() - todo.getCreated().getTime() > CACHE_REFRESH_RATE;
    }

    public ResponseEntity<Todo[]> getAll() {
        ResponseEntity<Todo[]> responseEntity = restClient.getAll();
        return responseEntity;
    }

    public ResponseEntity<Todo> create(Todo item) {
        ResponseEntity<Todo> responseEntity = restClient.post(item);
        return responseEntity;
    }

    // Apply Cache Refresh
    public void invalidated() {
        refreshCache();
    }

    private void refreshCache() {
        cache.values().stream().filter(toRefresh -> expired(toRefresh)).forEach(toRefresh -> {
            Todo refreshedEntity = applyRefresh(toRefresh);
            LOGGER.info("APPLIED REFRESH ON:\n" + refreshedEntity.toString());
        });
    }

    public void startMonitor() {
        LOGGER.info("STARTING MONITORING THREAD ....");
        startMonitoring();
    }
}
