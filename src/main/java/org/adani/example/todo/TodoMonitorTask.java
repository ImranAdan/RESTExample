package org.adani.example.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;

public class TodoMonitorTask implements Runnable, AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoMonitorTask.class);

    @Resource(name = "serviceUrl")
    String serviceUrl;

    @Autowired
    private TodoDAO todoDAO;

    @Autowired
    private RestTemplate restTemplate;

    private volatile boolean polling;

    public ResponseEntity<Todo> create(Todo item) {
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }

    public ResponseEntity<Todo> getById(long id) {
        ResponseEntity<Todo> entity = restTemplate.getForEntity(serviceUrl + "/" + id, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }

    public ResponseEntity<Todo[]> getAll() {
        final ResponseEntity<Todo[]> responseEntity = restTemplate.getForEntity(serviceUrl, Todo[].class);
        return responseEntity;
    }


    @Override
    public void run() {
        while (polling) {
            try {
                Thread.sleep(120000L); // Sleep for 15 Minutes before polling for next result


            } catch (InterruptedException e) {
                LOGGER.error("ERROR", e);
                throw new RuntimeException(e);
            }
        }
    }

    public void setPolling(boolean polling) {
        this.polling = polling;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setTodoDAO(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Ending Monitoring thread....");
        this.polling = false;
    }
}
