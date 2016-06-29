package org.adani.example.caching;


public interface Cache<K, V> {

    V get(K key);

    V put(K key, V value);
}
