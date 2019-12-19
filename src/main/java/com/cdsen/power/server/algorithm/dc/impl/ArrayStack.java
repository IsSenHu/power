package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.Stack;

/**
 * @author HuSen
 * create on 2019/12/19 14:31
 */
public class ArrayStack<T> implements Stack<T> {

    private T[] items;

    private int length;

    private int max;

    public ArrayStack(int max) {
        this.max = max;
        this.init();
    }

    @Override
    public void init() {
        //noinspection unchecked
        items = (T[]) new Object[this.max];
    }

    @Override
    public void destroy() {
        this.clear();
    }

    @Override
    public void clear() {
        this.items = null;
        this.max = 0;
        this.length = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public T getTop() {
        return this.items[this.length - 1];
    }

    @Override
    public void push(T e) {
        this.items[length] = e;
        this.length++;
    }

    @Override
    public T pop() {
        T item = this.items[length - 1];
        this.items[length - 1] = null;
        this.length--;
        return item;
    }

    @Override
    public int length() {
        return this.length;
    }
}
