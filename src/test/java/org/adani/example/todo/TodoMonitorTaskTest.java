package org.adani.example.todo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-context.xml")
public class TodoMonitorTaskTest {


    @Test // Basic test To see the thread starting and running
    public void testMonitoringThread() throws Exception {
        Executor e = Executors.newSingleThreadExecutor();
        e.execute(new TodoMonitorTask(true));
    }

}