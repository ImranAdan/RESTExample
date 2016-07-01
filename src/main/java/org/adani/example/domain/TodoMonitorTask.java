package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class TodoMonitorTask implements Runnable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMonitorTask.class);
    private static final Random RAND = new Random();

    private final AtomicBoolean monitoring;
    private final long fetchIntervalRate;
    private final TodoRestClient restClient;
    private final TodoDAO todoDAO;
    private final TodoCacheManager todoCacheManager;

    public TodoMonitorTask(long fetchIntervalRate, TodoRestClient restClient, TodoDAO todoDAO, TodoCacheManager todoCacheManager) {
        this.restClient = restClient;
        this.todoDAO = todoDAO;
        this.todoCacheManager = todoCacheManager;
        monitoring = new AtomicBoolean(true);
        this.fetchIntervalRate = fetchIntervalRate;
    }

    private static int generateRandomBetween(int min, int max) {
        return RAND.nextInt(max - min + 1) + min;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("Monitoring Thread");
        long lastRefresh = 0;
        while (monitoring.get()) {
            LOGGER.info("MONITORING ....");

            if (fetchRequired(lastRefresh)) {
                long id = generateRandomBetween(1, 200);
                LOGGER.info("FETCHING NEXT RESULT = " + id);

                try {
                    ResponseEntity<Todo> externallyFetchedItem = restClient.getById(id);
                    LOGGER.info("FETCHED EXTERNAL RESOURCE -> " + externallyFetchedItem.toString());
                    final Todo record = todoDAO.create(externallyFetchedItem.getBody());
                    LOGGER.info("SAVED INTERNALLY AS -> " + record.toString());
                    todoCacheManager.put(externallyFetchedItem);
                } catch (Exception e) {
                    monitoring.set(false);
                    throw new RuntimeException("Error Getting External Value ...." + e.toString(), e);
                }
            }
        }
    }

    private boolean fetchRequired(long lastRefresh) {
        return System.currentTimeMillis() - lastRefresh > fetchIntervalRate;
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("ENDING MONITORING...", this);
        monitoring.set(false);
    }
}
