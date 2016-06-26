package org.adani.tutorial.example;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Component
@Path(value = "/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class ExampleTodoRESTTEndpoint {

    private ExampleTodoRESTManager exampleTodoRESTManager;

    public ExampleTodoRESTTEndpoint() {
        exampleTodoRESTManager = new ExampleTodoRESTManager();
    }

    @Transactional
    @POST @Path(value = "/todo")
    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> todo = exampleTodoRESTManager.create(item);
        return todo;
    }

    @GET
    public Response get(){
        Response all = getAll();
        return all;
    }

    @GET @Path("/todos")
    public Response getAll(){
        final ResponseEntity<Todo[]> responseEntity = exampleTodoRESTManager.getAll();
        Response response = Response.status(responseEntity.getStatusCode().value()).entity(responseEntity).build();
        return response;
    }

    public void setExampleTodoRESTManager(ExampleTodoRESTManager exampleTodoRESTManager) {
        this.exampleTodoRESTManager = exampleTodoRESTManager;
    }
}
