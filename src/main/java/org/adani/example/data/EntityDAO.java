package org.adani.example.data;

public interface EntityDAO<K, V> {

    V fetch(K key);


    V create(V value);


    V update(V value);


    void delete(K key);

}
