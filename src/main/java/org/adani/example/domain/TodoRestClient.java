package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class TodoRestClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    String serviceUrl;

    public Todo getById(long id) {
        Todo todoById = getTodoById(id);
        return todoById;
    }

    private Todo getTodoById(long id) {
        Todo entity = restTemplate.getForObject(serviceUrl + "/" + id, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }
}
