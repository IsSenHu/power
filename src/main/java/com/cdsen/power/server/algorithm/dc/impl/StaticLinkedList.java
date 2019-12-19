package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.List;

/**
 * @author HuSen
 * create on 2019/12/18 17:11
 */
public class StaticLinkedList<T> implements List<T> {

    private Node<T>[] nodes;

    private int next;

    private int length;

    public StaticLinkedList(Node<T>[] nodes) {
        this.nodes = nodes;
        int length = this.nodes.length;
        for (int i = 0; i < length; i++) {
            this.nodes[i] = new Node<>();
            this.nodes[i].setCur(i + 1);
        }
    }

    @Override
    public boolean listEmpty() {
        return this.length == 0;
    }

    @Override
    public void clearList() {
        for (int i = 0; i < this.length; i++) {
            Node<T> node = this.nodes[i];
            node.setData(null);
            node.setCur(i + 1);
        }
        this.length = 0;
        this.next = 0;
    }

    @Override
    public T getElem(int i) {
        return getNode(i).getData();
    }

    private Node<T> getNode(int i) {
        if (i < 0 || i > this.length - 1) {
            throw new IllegalArgumentException("value of i is error");
        }
        if (i == 0) {
            return this.nodes[i];
        }
        Node<T> node = this.nodes[0];
        for (int x = 1; x <= i; x++) {
            node = this.nodes[node.getCur()];
        }
        return node;
    }

    @Override
    public int locateElem(T e) {
        int cur = 0;
        for (int i = 0; i < length; i++) {
            Node<T> node = this.nodes[cur];
            if (node.getData().equals(e)) {
                return i;
            }
            cur = node.getCur();
        }
        return -1;
    }

    @Override
    public void listInsert(int i, T e) {
        if (i < 0 || i > this.length) {
            throw new IllegalArgumentException("value of i is error");
        }
        if (i == 0 && this.next == 0) {
            this.nodes[i].setData(e);
            this.next = this.nodes[this.next].getCur();
        } else if (i == 0) {
            int temp = this.nodes[this.next].getCur();
            swap(i, this.next);
            Node<T> head = this.nodes[i];
            head.setData(e);
            head.setCur(this.next);
            this.next = temp;
        } else if (i == this.length) {
            Node<T> last = getNode(i - 1);
            last.setCur(this.next);
            this.nodes[this.next].setData(e);
            this.next = this.nodes[this.next].getCur();
        } else {
            Node<T> left = getNode(i - 1);
            int right = left.getCur();
            left.setCur(this.next);
            Node<T> next = this.nodes[this.next];
            next.setData(e);
            next.setCur(right);
            this.next = this.nodes[this.next].getCur();
        }
        this.length++;
    }

    private void swap(int i, int j) {
        Node<T> t = this.nodes[i];
        this.nodes[i] = this.nodes[j];
        this.nodes[j] = t;
    }

    @Override
    public T listDelete(int i) {
        T ret;
        if (i == 0) {
            ret = this.nodes[0].getData();
            int cur = this.nodes[0].getCur();
            this.nodes[0] = this.nodes[cur];
            this.next = cur;
        } else if (i == length - 1) {
            ret = this.nodes[i].getData();
            this.nodes[i].setData(null);
            this.next = getNode(i - 1).getCur();
        } else {
            Node<T> left = getNode(i - 1);
            this.next = left.getCur();
            ret = this.nodes[left.getCur()].getData();
            this.nodes[left.getCur()].setData(null);
            left.setCur(this.nodes[left.getCur()].getCur());
        }
        this.length--;
        return ret;
    }

    @Override
    public int listLength() {
        return this.length;
    }
}
