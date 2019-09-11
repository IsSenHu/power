package com.cdsen.power.core;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author HuSen
 * create on 2019/9/4 14:12
 */
public class Cache<K, V> {

    private final ConcurrentMap<K, V> cache = new ConcurrentHashMap<>();

    public void save(K k, V v) {
        cache.putIfAbsent(k, v);
    }

    public void refresh(K k, V v) {
        if (cache.containsKey(k)) {
            cache.replace(k, v);
        } else {
            save(k, v);
        }
    }

    public void delete(K k) {
        cache.remove(k);
    }

    public V get(K k) {
        return cache.get(k);
    }

    public Set<Map.Entry<K,V>> all() {
        return cache.entrySet();
    }
}
