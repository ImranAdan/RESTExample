package org.adani.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Service denoted by the path: localhost:9000/example
 */
@Path("/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoCacheManager todoCacheManager;

    public void initialiseService() {
        todoCacheManager.startMonitor();
    }

    @GET
    @Path("/todos/{id}")
    public Response getById(@PathParam("id") long id) {
        final ResponseEntity<Todo> entity = todoCacheManager.getById(id);
        return asResponse(entity);
    }

    @POST
    @Path("/todos")
    public Response postToDo(Todo item) {
        ResponseEntity<Todo> todo = todoCacheManager.create(item);
        return asResponse(todo);
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

    public void setTodoCacheManager(TodoCacheManager todoCacheManager) {
        this.todoCacheManager = todoCacheManager;
    }

    public void tearDownService() {
        todoCacheManager.stopMonitoring();
    }
}
