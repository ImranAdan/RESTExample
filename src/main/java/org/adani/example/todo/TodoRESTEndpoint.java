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
    TodoCacheManager todoCacheManager;

    @GET
    @Path("/todos/{id}")
    public Response getTodoByKey(@PathParam("id") long id) {
        final ResponseEntity<Todo> entity = todoCacheManager.getById(id);
        return asResponse(entity);
    }

    @GET
    @Path("/todos")
    public Response getAll() {
        final ResponseEntity<Todo[]> entity = todoCacheManager.getAll();
        return asResponse(entity);
    }

    private <T> Response asResponse(ResponseEntity<T> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity).build();
    }

    public void initialiseService() {
        todoCacheManager.monitor();
    }

    public void setTodoCacheManager(TodoCacheManager todoCacheManager) {
        this.todoCacheManager = todoCacheManager;
    }

    @POST
    @Path("/todos") //POST EXAMPLE
    public Response create(Todo item) {
        ResponseEntity<Todo> todo = todoCacheManager.create(item);
        return asResponse(todo);
    }
}
