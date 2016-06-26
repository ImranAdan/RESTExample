package org.adani.example.todo;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


public class TodoRESTManager {

    private static final String serviceUrl = "http://jsonplaceholder.typicode.com/todo";

    private final RestTemplate restTemplate;

    public TodoRESTManager(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }

    public ResponseEntity<Todo> getById(long id) {
        final String url = "http://jsonplaceholder.typicode.com/todos/" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ResponseEntity<Todo> entity = restTemplate.getForEntity(url, Todo.class, Collections.singletonMap("id", id));
        return entity;
    }

    public ResponseEntity<Todo[]> getAll() {
        final String allToDos = "http://jsonplaceholder.typicode.com/todos";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        final ResponseEntity<Todo[]> responseEntity = restTemplate.getForEntity(allToDos, Todo[].class);
        return responseEntity;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
