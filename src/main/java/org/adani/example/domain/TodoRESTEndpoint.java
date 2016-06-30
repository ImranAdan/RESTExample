package org.adani.example.domain;

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

    public void initialiseService() {
        todoCacheManager.startMonitor();
    }

    @POST
    @Path("/todos")
    public Response postToDo(Todo item) {
        ResponseEntity<Todo> entity = todoCacheManager.create(item);
        Response response = asResponse(entity);
        return response;
    }

    @GET
    @Path("/todos/{id}")
    public Response getById(@PathParam("id") long id) {
        ResponseEntity<Todo> entity = todoCacheManager.getById(id);
        Response response = asResponse(entity);
        return response;
    }

    @GET
    @Path("/todos")
    public Response getAll() {
        final ResponseEntity<Todo[]> entity = todoCacheManager.getAll();
        Response response = asResponse(entity);
        return response;
    }

    private <T> Response asResponse(ResponseEntity<T> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity).build();
    }
}
