package org.adani.example.domain;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TodoRestClient {

    private final RestTemplate restTemplate;
    private final String serviceUrl;

    public TodoRestClient(RestTemplate restTemplate, String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    public ResponseEntity<Todo> getById(long id) {
        ResponseEntity<Todo> todoById = getTodoById(id);
        return todoById;
    }

    private ResponseEntity<Todo> getTodoById(long id) {
        String url = serviceUrl + "/" + id;
        ResponseEntity<Todo> entity = restTemplate.getForEntity(url, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }
}
