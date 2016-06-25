package org.adani.tutorial.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class ExampleTodoRESTManager {

    private static final String serviceUrl = "http://jsonplaceholder.typicode.com/todo";

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }

    public ResponseEntity<Todo> getById(long id){
        ResponseEntity<Todo> response = restTemplate.getForEntity(serviceUrl + "/" + id, Todo.class);
        return response;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
