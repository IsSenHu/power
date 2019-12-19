package com.cdsen.power.server.algorithm.dc.interfaces;

/**
 * @author HuSen
 * create on 2019/12/19 16:40
 */
public interface Queue<T> {

    void init();

    void destroy();

    void clear();

    boolean isEmpty();

    T getHead();

    void enQueue(T e);

    T deQueue();

    int length();
}
