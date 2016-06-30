package org.adani.example.domain;

import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TodoRestClient {

    private final RestTemplate restTemplate;
    private final String serviceUrl;

    public TodoRestClient(RestTemplate restTemplate, String serviceUrl) {
        this.restTemplate = restTemplate;
        this.serviceUrl = serviceUrl;
    }

    public Todo getById(long id) {
        Todo todoById = getTodoById(id);
        return todoById;
    }

    private Todo getTodoById(long id) {
        Todo entity = restTemplate.getForEntity(serviceUrl + "/" + id, Todo.class, Collections.singletonMap("id", id)).getBody();
        return entity;
    }
}
