package org.adani.example.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class RESTEndpointImpl<T> implements RESTEndpoint<T> {

    @Override
    public Response post(T item) {
        return null;
    }

    @Override
    public Response get(@Context UriInfo ui) {
        return null;
    }
}
