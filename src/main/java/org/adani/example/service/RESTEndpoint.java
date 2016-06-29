package org.adani.example.service;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/rest")
@Consumes({"application/json"})
@Produces({"application/json"})
public interface RESTEndpoint<T> {

    @POST
    @Path("/post")
    Response post(T item);

    @GET
    @Path("/get")
    Response get(@Context UriInfo ui);
}
