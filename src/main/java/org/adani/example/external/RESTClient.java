package org.adani.example.external;

/**
 * Basic RESTful client
 *
 * @param <I> Identifier og GET Request for a specfic resource.
 * @param <V> Value of Request
 */
public interface RESTClient<I, V> {

    V post(V value);


    V get(I identifier);

}
