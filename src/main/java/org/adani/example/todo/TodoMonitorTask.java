package org.adani.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * TODO: Investigate issues on implementing runnable
 * <p>
 * Caused by: java.lang.IllegalStateException: Cannot convert value of type [com.sun.proxy.$Proxy27 implementing java.lang.Runnable,java.lang.AutoCloseable,org.springframework.aop.SpringProxy,org.springframework.aop.framework.Advised] to required type [org.adani.example.todo.TodoMonitorTask] for property 'todoMonitorTask': no matching editors or conversion strategy found
 * at org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:302)
 * at org.springframework.beans.AbstractNestablePropertyAccessor.convertIfNecessary(AbstractNestablePropertyAccessor.java:576)
 * ... 43 common frames omitted18:12:40.155 [main] DEBUG org.springframework.test.context.support.AbstractDirtiesContextTestExecutionListener - After test class: context [DefaultTestContext@4ddced80 testClass = TodoDAOTest, testInstance = [null], testMethod = [null], testException = [null], mergedContextConfiguration = [MergedContextConfiguration@1534f01b testClass = TodoDAOTest, locations = '{classpath:application-context.xml}', classes = '{}', contextInitializerClasses = '[]', activeProfiles = '{}', propertySourceLocations = '{}', propertySourceProperties = '{}', contextLoader = 'org.springframework.test.context.support.DelegatingSmartContextLoader', parent = [null]]], class annotated with @DirtiesContext [false] with mode [null].
 */
public class TodoMonitorTask implements Runnable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMonitorTask.class);

    private static final long EXAMPLE_CACHE_REFRESH_RATE = 10000L;

    private volatile boolean monitoring;

    //TODO: Investigate Autowiring issue
    @Autowired
    private TodoCacheManager todoCacheManager;
    @Resource(name = "cacheRefreshRate")
    private Long cacheRefreshRate;


    public TodoMonitorTask(boolean shouldStart) {
        this.monitoring = shouldStart;
    }

    @Override
    public void run() {
        long lastRefresh = 0;
        while (monitoring) {
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
                e.printStackTrace();
            }
        }
    }

    public void setMonitoring(boolean monitoring) {
        this.monitoring = monitoring;
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Ending Monitoring....");
        setMonitoring(false);
    }
}
