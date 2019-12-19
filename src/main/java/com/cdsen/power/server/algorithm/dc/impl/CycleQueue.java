package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.Queue;

/**
 * @author HuSen
 * create on 2019/12/19 16:49
 */
public class CycleQueue implements Queue<Integer> {

    private Integer[] items;

    private int queueSize;

    private int front;

    private int rear;

    public CycleQueue(int queueSize) {
        this.queueSize = queueSize + 1;
        this.init();
    }

    @Override
    public void init() {
        this.items = new Integer[this.queueSize];
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public void destroy() {
        this.items = null;
        this.front = 0;
        this.rear = 0;
    }

    @Override
    public void clear() {
        this.destroy();
    }

    @Override
    public boolean isEmpty() {
        return length() == 0;
    }

    @Override
    public Integer getHead() {
        return this.items[this.front];
    }

    @Override
    public void enQueue(Integer e) {
        if ((this.rear + 1) % this.queueSize == this.front) {
            throw new IllegalStateException("queue is full");
        }
        this.items[this.rear] = e;
        this.rear = (this.rear + 1) % this.queueSize;
    }

    @Override
    public Integer deQueue() {
        if (this.front == this.rear) {
            throw new IllegalStateException("queue is empty");
        }
        Integer ret = this.items[this.front];
        this.front = (this.front + 1) % this.queueSize;
        return ret;
    }

    @Override
    public int length() {
        return (rear - front + queueSize) % queueSize;
    }
}
