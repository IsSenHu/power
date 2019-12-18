package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/12/18 17:11
 */
public class StaticLinkedList<T> implements List<T> {

    private Node[] nodes;

    private int length;

    public StaticLinkedList(int max) {
        //noinspection unchecked,ConstantConditions
        this.nodes = (Node[]) new Object[max];
        for (int i = 0; i < max; i++) {
            this.nodes[i].setCur(i);
        }
    }

    @Getter
    @Setter
    private class Node {
        private T data;
        private int cur;
    }

    @Override
    public boolean listEmpty() {
        return this.length == 0;
    }

    @Override
    public void clearList() {

    }

    @Override
    public T getElem(int i) {
        return null;
    }

    @Override
    public int locateElem(T e) {
        return 0;
    }

    @Override
    public void listInsert(int i, T e) {

    }

    @Override
    public T listDelete(int i) {
        return null;
    }

    @Override
    public int listLength() {
        return this.length;
    }
}
