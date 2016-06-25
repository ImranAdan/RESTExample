package org.adani.tutorial.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


public class ExampleTodoRESTManager {

    private static final String serviceUrl = "http://jsonplaceholder.typicode.com/todo";

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> response = restTemplate.postForEntity(serviceUrl, item, Todo.class);
        return response;
    }

    public Todo getById(long id) throws URISyntaxException {
        String url = serviceUrl + "/" + id;
        final Todo response = restTemplate.getForObject(new URI(url), Todo.class);
        return response;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public ResponseEntity<Todo> getAll() {
        final String allToDos = "http://jsonplaceholder.typicode.com/todos";
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        final ResponseEntity<Todo> responseEntity = restTemplate.getForEntity(allToDos, Todo.class);
        return responseEntity;
    }
}
