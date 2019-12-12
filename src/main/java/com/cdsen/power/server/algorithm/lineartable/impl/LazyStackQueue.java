package com.cdsen.power.server.algorithm.lineartable.impl;

import java.util.Stack;

/**
 * @author HuSen
 * create on 2019/12/12 16:50
 */
public class LazyStackQueue<T> {

    private final Stack<T> s1 = new Stack<>();
    private final Stack<T> s2 = new Stack<>();

    public void push(T item) {
        if (!s1.isEmpty()) {
            s1.push(item);
            return;
        }
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        s1.push(item);
    }

    public T pop() {
        if (!s2.isEmpty()) {
            return s2.pop();
        }
        while (!s1.isEmpty() && s1.size() > 1) {
            s2.push(s1.pop());
        }
        return s1.pop();
    }
}
