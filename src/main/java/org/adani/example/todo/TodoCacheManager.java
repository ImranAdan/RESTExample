package org.adani.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TodoCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoCacheManager.class);
    private final Executor executor;
    private final TodoMonitorTask todoMonitorTask;
    @Autowired
    private TodoRestClient restClient;
    @Autowired
    private TodoDAO todoDAO;

    TodoCacheManager() {
        todoMonitorTask = new TodoMonitorTask();
        executor = Executors.newSingleThreadExecutor();
    }

    public void monitor() {
        LOGGER.info("TodoCacheManager Starting Monitoring thread....");
        startMonitoring();
    }

    private void startMonitoring() {
        todoMonitorTask.setMonitoring(true);
        executor.execute(todoMonitorTask);
    }

    public ResponseEntity<Todo> getById(long id) {
        ResponseEntity<Todo> responseEntity = restClient.getById(id);
        return responseEntity;
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
        todoDAO.refreshCache();
    }

    public void setRestClient(TodoRestClient restClient) {
        this.restClient = restClient;
    }

    public void setTodoDAO(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }
}
