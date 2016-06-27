package org.adani.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collections;

public class TodoRestClient {

    @Resource(name = "externalServiceUrl")
    String serviceUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<Todo> getById(long id) {
        ResponseEntity<Todo> todoById = getTodoById(id);
        return todoById;
    }

    // Apply paging for large resultset
    public ResponseEntity<Todo[]> getAll() {
        ResponseEntity<Todo[]> allTodos = all();
        return allTodos;
    }

    public ResponseEntity<Todo> post(Todo item) {
        ResponseEntity<Todo> createdEntity = create(item);
        return createdEntity;
    }

    private ResponseEntity<Todo> getTodoById(long id) {
        ResponseEntity<Todo> entity = restTemplate.getForEntity(serviceUrl + "/" + id, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }

    private ResponseEntity<Todo[]> all() {
        ResponseEntity<Todo[]> responseEntity = restTemplate.getForEntity(serviceUrl, Todo[].class);
        return responseEntity;
    }

    private ResponseEntity<Todo> create(Todo item) {
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}