package org.adani.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoManager todoManager;


    @POST
    @Path("/todos")
    public Response create(Todo item) {
        ResponseEntity<Todo> todo = todoManager.create(item);
        return asResponse(todo);
    }


    @GET
    @Path("/todos/{id}")
    public Response getTodoByKey(@PathParam("id") long id) {
        final ResponseEntity<Todo> entity = todoManager.getById(id);
        return asResponse(entity);
    }

    @GET
    @Path("/todos")
    public Response getAll() {
        final ResponseEntity<Todo[]> entity = todoManager.getAll();
        return asResponse(entity);
    }

    private <T> Response asResponse(ResponseEntity<T> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity).build();
    }

    public void initialiseService() {
        todoManager.monitor();
    }

    public void setTodoManager(TodoManager todoManager) {
        this.todoManager = todoManager;
    }

}
