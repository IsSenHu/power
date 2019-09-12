package com.cdsen.power.core.fuction;

/**
 * @author HuSen
 * create on 2019/9/12 10:04
 */
@FunctionalInterface
public interface ThreeConsumer<K, I, V> {

    void accept(K k, I i, V v);
}
