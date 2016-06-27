package org.adani.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;


public class TodoManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoManager.class);

    private final TodoMonitorTask todoMonitorTask;
    private Thread workerThread;

    private TodoDAO todoDAO;

    public TodoManager() {
        todoMonitorTask = new TodoMonitorTask();
    }

    public void monitor() {
        LOGGER.info("Starting Monitoring thread....");
        startMonitoring();
    }

    private void startMonitoring() {
        todoMonitorTask.setPolling(true);
        workerThread = new Thread(todoMonitorTask);
        workerThread.start();
    }

    //TODO: COMPLETE WORK ON WORKER THREAD!
    public ResponseEntity<Todo> getById(long id) {
        if (todoDAO.getCache().containsKey(id)) {
            // ResponseEntity<Todo> a = new ResponseEntity<Todo>() todoDAO.getCache().get(id);
        }
        return null;
    }

    public ResponseEntity<Todo> create(Todo item) {
        return null;
    }

    public ResponseEntity<Todo[]> getAll() {
        return null;
    }
}
