package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("/external")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoCacheManager todoCacheManager;

    @GET
    @Path("/todos/{id}")
    public Response getById(@PathParam("id") long id) {
        Todo entity = todoCacheManager.get(id);
        Response response = buildResponse(entity);
        return response;
    }

    private <T> Response buildResponse(Todo item) {
        return Response.ok().entity(item).status(Response.Status.ACCEPTED).build();
    }
}
