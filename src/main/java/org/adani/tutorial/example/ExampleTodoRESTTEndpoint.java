package org.adani.tutorial.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;

@Component
@Path(value = "/example")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
public class ExampleTodoRESTTEndpoint {

    private ExampleTodoRESTManager exampleTodoRESTManager;

    @GET @Path("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello World");
    }

    @Transactional
    @POST @Path(value = "/todo")
    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> todo = exampleTodoRESTManager.create(item);
        return todo;
    }

    @GET @Path("/todo/{id}")
    public ResponseEntity<Todo> getById(@PathParam(value = "id") long id){
        ResponseEntity<Todo> response = exampleTodoRESTManager.getById(id);
        return response;
    }

    public void setExampleTodoRESTManager(ExampleTodoRESTManager exampleTodoRESTManager) {
        this.exampleTodoRESTManager = exampleTodoRESTManager;
    }
}
