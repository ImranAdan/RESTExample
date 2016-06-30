package org.adani.example.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;


public class TodoMonitorTask implements Runnable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMonitorTask.class);
    private final long fetchIntervalRate;
    private final AtomicBoolean monitoring;
    @Autowired
    TodoRestClient restClient;
    @Autowired
    TodoDAO todoDAO;

    public TodoMonitorTask(long fetchIntervalRate, boolean isMonitoring) {
        this.fetchIntervalRate = fetchIntervalRate;
        this.monitoring = new AtomicBoolean(isMonitoring);
    }

    @Override
    public void run() {
        long lastRefresh = 0;
        while (monitoring.get()) {
            /**
             * Put application logic here for monitoring!
             *
             * TODO: There is autowiring dependency issue fix required; see todoCacheManager!!
             */
            try {
                Thread.sleep(2000L);
                System.out.println("Background  monitoring task ......");
                if (System.currentTimeMillis() - lastRefresh > EXAMPLE_CACHE_REFRESH_RATE) {
                    System.out.println("Schedule Triggered!!!!, Applying Cache Refresh!...");
                    /**
                     * TODO: Apply cache invalidation logic here
                     */
                    lastRefresh = System.currentTimeMillis();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Monitoring Failure!");
            }
        }
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("ENDING MONITORING...", this);
        monitoring.set(false);
    }
}
