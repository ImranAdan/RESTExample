package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class TodoMonitorTask implements Runnable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMonitorTask.class);
    private static final Random RAND = new Random();

    @Autowired
    AtomicBoolean monitoring;

    @Autowired
    TodoCacheManager todoCacheManager;

    @Autowired
    Long fetchIntervalRate;

    @Autowired
    TodoRestClient restClient;

    @Autowired
    TodoDAO todoDAO;

    public static int generateRandomBetween(int min, int max) {
        return RAND.nextInt(max - min + 1) + min;
    }

    @Override
    public void run() {
        long lastRefresh = 0;
        while (monitoring.get()) {
            if (fetchRequired(lastRefresh)) {

                Todo externallyFetchedItem = restClient.getById(generateRandomBetween(1, 200));
                LOGGER.info("FETCHED EXTERNAL RESOURCE -> " + externallyFetchedItem.toString());

                final Todo record = todoDAO.create(externallyFetchedItem);
                LOGGER.info("SAVED INTERNALLY AS -> " + record.toString());

                todoCacheManager.put(record);
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
