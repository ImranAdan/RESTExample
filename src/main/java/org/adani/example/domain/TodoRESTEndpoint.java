package org.adani.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Path("/example")
@Consumes({"application/json"})
@Produces({"application/json"})
public class TodoRESTEndpoint {

    @Autowired
    TodoTaskManager todoTaskManager;

    @GET
    @Path("/todos/{id}")
    public Response getById(@PathParam("id") long id) {
        ResponseEntity<Todo> entity = todoTaskManager.get(id);
        Response response = buildResponse(entity);
        return response;
    }

    private Response buildResponse(ResponseEntity<Todo> item) {
        final HttpStatus statusCode = item.getStatusCode();
        final Todo body = item.getBody();
        final Response.ResponseBuilder responseBuilder = Response.status(statusCode.value()).entity(body);

        final HttpHeaders headers = item.getHeaders();
        final Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            String s = entry.getKey();
            Object o = entry.getValue();
            responseBuilder.header(s, o);
        }

        return Response.ok().entity(item).status(Response.Status.OK).build();
    }

}
