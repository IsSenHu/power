package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/12/18 17:53
 */
public class CycleLinkedList<T> implements List<T> {

    private Node head;

    private Node tail;

    private int length;

    @Override
    public boolean listEmpty() {
        return this.length == 0;
    }

    @Override
    public void clearList() {
        this.head = null;
        this.tail = null;
        this.length = 0;
    }

    @Override
    public T getElem(int i) {
        return getNode(i).getData();
    }

    @Setter
    @Getter
    private class Node {
        private T data;
        private Node next;
    }

    private Node getNode(int i) {
        if (i == 0) {
            return this.head;
        }
        Node node = this.head;
        for (int x = 1; x <= i; x++) {
            node = node.getNext();
        }
        return node;
    }

    @Override
    public int locateElem(T e) {
        Node node = this.head;
        for (int i = 0; i < this.length; i++) {
            if (node.getData().equals(e)) {
                return i;
            }
            node = node.getNext();
        }
        return -1;
    }

    @Override
    public void listInsert(int i, T e) {
        Node node = new Node();
        node.setData(e);
        if (i == length) {
            this.tail = node;
        }
        if (i == 0) {
            node.setNext(this.head);
            this.head = node;
            this.tail.setNext(node);
        } else {
            Node left = getNode(i - 1);
            node.setNext(left.getNext());
            left.setNext(node.getNext());
        }
        this.length++;
    }

    @Override
    public T listDelete(int i) {
        Node node = getNode(i - 1);
        Node current = node.getNext();
        node.setNext(current.getNext());
        this.length--;
        return current.getData();
    }

    @Override
    public int listLength() {
        return this.length;
    }
}
