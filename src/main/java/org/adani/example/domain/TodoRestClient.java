package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TodoRestClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    String serviceUrl;

    public ResponseEntity<Todo> post(Todo item) {
        ResponseEntity<Todo> createdEntity = postTodoItem(item);
        return createdEntity;
    }

    public ResponseEntity<Todo> getById(long id) {
        ResponseEntity<Todo> todoById = getTodoById(id);
        return todoById;
    }

    public ResponseEntity<Todo[]> getAll() {
        ResponseEntity<Todo[]> allTodos = all();
        return allTodos;
    }

    private ResponseEntity<Todo> getTodoById(long id) {
        ResponseEntity<Todo> entity = restTemplate.getForEntity(serviceUrl + "/" + id, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }

    private ResponseEntity<Todo[]> all() {
        ResponseEntity<Todo[]> responseEntity = restTemplate.getForEntity(serviceUrl, Todo[].class);
        return responseEntity;
    }

    private ResponseEntity<Todo> postTodoItem(Todo item) {
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }
}
