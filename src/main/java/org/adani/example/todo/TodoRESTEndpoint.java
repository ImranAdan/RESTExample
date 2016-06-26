package org.adani.example.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    @Path(value = "/todo")
    public Response create(HttpEntity<Todo> item) {
        ResponseEntity<Todo> todo = todoRestManager.create(item);
        return asResponse(todo);
    }

    @GET
    @Path("/todos/{id}")
    public Response getTodoById(@PathParam("id") long id) {
        final ResponseEntity<Todo> entity = todoRestManager.getById(id);
        return asResponse(entity);
    }

    @GET
    @Path("/todos")
    public Response getAll() {
        final ResponseEntity<Todo[]> entity = todoRestManager.getAll();
        return asResponse(entity);

    }

    private <T> Response asResponse(ResponseEntity<T> entity) {
        return Response.status(entity.getStatusCode().value()).entity(entity).build();
    }

    public TodoRESTManager getTodoRestManager() {
        return todoRestManager;
    }

    public void setTodoRestManager(TodoRESTManager todoRestManager) {
        this.todoRestManager = todoRestManager;
    }
}
