package com.cdsen.power.server.algorithm.lineartable.impl;

import java.util.Stack;

/**
 * @author HuSen
 * create on 2019/12/12 16:35
 */
public class StackQueue<T> {

    private final Stack<T> s1 = new Stack<>();
    private final Stack<T> s2 = new Stack<>();

    public void push(T item) {
        s1.push(item);
    }

    public T pop() {
        // > 1 减少一次入栈操作
        while (!s1.isEmpty() && s1.size() > 1) {
            s2.push(s1.pop());
        }
        T pop = s1.pop();
        while (!s2.isEmpty()) {
            s1.push(s2.pop());
        }
        return pop;
    }
}
