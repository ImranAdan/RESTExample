package org.adani.example.external;


public interface RESTClient<I, V> {

    V post(V value);


    V get(I identifier);

}
