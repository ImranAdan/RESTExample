package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class TodoTaskManager {

    private final Executor executor;
    private final Runnable task;

    @Autowired
    TodoCacheManager todoCacheManager;


    public TodoTaskManager(Runnable task) {
        this.task = task;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void execute() {
        executor.execute(task);
    }

    public ResponseEntity<Todo> get(long id) {
        ResponseEntity<Todo> cachedEntry = todoCacheManager.get(id);
        return cachedEntry;
    }
}
