package org.adani.tutorial.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

@Component
@Path(value = "/example")
@Consumes({"application/json", "application/xml"})
@Produces({"application/json", "application/xml"})
public class ExampleTodoRESTTEndpoint {

    private ExampleTodoRESTManager exampleTodoRESTManager;


    @Transactional
    @POST @Path(value = "/todo")
    public ResponseEntity<Todo> create(HttpEntity<Todo> item){
        ResponseEntity<Todo> todo = exampleTodoRESTManager.create(item);
        return todo;
    }

    @GET
    public Response get(){
        return getAll();
    }

    @GET @Path("/todos")
    public Response getAll(){
        final ResponseEntity<Todo> responseEntity = exampleTodoRESTManager.getAll();
        return Response.status(responseEntity.getStatusCode().value()).entity(responseEntity).build();
    }

//    @GET @Path("/todos/{id}")
//    public Todo getById(@PathParam("id") long id) throws URISyntaxException {
//        Todo response = exampleTodoRESTManager.getById(id);
//        return response;
//    }

    public void setExampleTodoRESTManager(ExampleTodoRESTManager exampleTodoRESTManager) {
        this.exampleTodoRESTManager = exampleTodoRESTManager;
    }
}
