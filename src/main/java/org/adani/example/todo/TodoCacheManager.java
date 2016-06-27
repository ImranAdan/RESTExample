package org.adani.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);
    private static final long CACHE_REFRESH_RATE = 600000L; // For Cache Invalidation
    private final Executor executor;
    private final TodoMonitorTask todoMonitorTask;
    private volatile Map<Long, Todo> cache;
    @Autowired
    private TodoRestClient restClient;

    @Autowired
    private TodoDAO todoDAO;

    public TodoCacheManager() {
        todoMonitorTask = new TodoMonitorTask(true);
        executor = Executors.newSingleThreadExecutor();
        cache = new HashMap<>();
    }

    public ResponseEntity<Todo> getById(long id) {

        if ((cache.containsKey(id) && cache.get(id) != null) && !expired(cache.get(id))) {
            return ResponseEntity.ok(cache.get(id));
        }

        ResponseEntity<Todo> responseEntity = restClient.getById(id);

        Todo cachedEntry = applyCaching(responseEntity.getBody());
        return ResponseEntity.ok(cachedEntry);
    }

    private Todo applyCaching(Todo item) {
        final Todo todo = todoDAO.create(item);
        return cache(todo);
    }

    private Todo applyRefresh(Todo updatedRecord) {
        final Todo updatedTodo = todoDAO.update(updatedRecord);
        return cache(updatedTodo);
    }

    private Todo cache(Todo todo) {
        Todo record = todoDAO.fetchWithToDoId(todo.getId());
        Todo previous = cache.put(record.getId(), record);
        Todo current = cache.get(record.getId());
        LOGGER.info("REMOVED: " + previous);
        LOGGER.info("ADDED: " + current);
        return current;
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

    public void stopMonitoring() {
        LOGGER.info("STOPPING MONITORING THREAD ....");
        todoMonitorTask.setMonitoring(false);
    }

    private void startMonitoring() {
        todoMonitorTask.setMonitoring(true);
        executor.execute(todoMonitorTask);
    }

    public void setRestClient(TodoRestClient restClient) {
        this.restClient = restClient;
    }

    public void setTodoDAO(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

}
