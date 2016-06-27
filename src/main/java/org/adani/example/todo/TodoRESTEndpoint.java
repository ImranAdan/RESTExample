package org.adani.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoRESTManager todoRestManager;

    @Transactional
    @POST
    @Path(value = "/todos")
    public Response create(Todo item) {
        ResponseEntity<Todo> todo = todoRestManager.create(item);
        return asResponse(todo);
    }

    @GET
    @Path("/todos")
    public Response getAll() {
        final ResponseEntity<Todo[]> entity = todoRestManager.getAll();
        return asResponse(entity);
    }

    @GET
    @Path("/todos/{id}")
    public Response getTodoByKey(@PathParam("id") long id) {
        final ResponseEntity<Todo> entity = todoRestManager.getById(id);
        return asResponse(entity);
    }

    private <T> Response asResponse(ResponseEntity<T> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity).build();
    }

    public void setTodoRestManager(TodoRESTManager todoRestManager) {
        this.todoRestManager = todoRestManager;
    }
}
