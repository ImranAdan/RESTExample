package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoTaskManager todoTaskManager;

    @GET
    @Path("/todos/{id}")
    public Response getById(@PathParam("id") long id) {
        Todo entity = todoTaskManager.get(id);
        Response response = buildResponse(entity);
        return response;
    }

    private Response buildResponse(Todo item) {
        return Response.ok().entity(item).status(Response.Status.OK).build();
    }

}
