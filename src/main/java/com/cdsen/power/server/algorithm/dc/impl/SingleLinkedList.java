package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.List;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/12/18 16:04
 */
public class SingleLinkedList<T> implements List<T> {

    /**
     * 第一个节点
     */
    private Node head;

    /**
     * 长度
     */
    private int length;

    @Setter
    @Getter
    public class Node {
        private T data;
        private Node next;
    }

    @Override
    public boolean listEmpty() {
        return this.head == null;
    }

    @Override
    public void clearList() {
        this.head = null;
        this.length = 0;
    }

    @Override
    public T getElem(int i) {
        return getNode(i).getData();
    }

    private Node getNode(int i) {
        if (i < 0 || i > this.length - 1) {
            throw new IllegalArgumentException("value of i is error");
        }
        Node v = this.head;
        for (int j = 0; j < i; j++) {
            v = v.getNext();
        }
        return v;
    }

    @Override
    public int locateElem(T e) {
        int i = 0;
        Node node = this.head;
        while (i < length) {
            if (e.equals(node.getData())) {
                break;
            }
            node = node.getNext();
            i++;
        }
        return i;
    }

    @Override
    public void listInsert(int i, T e) {
        if (i < 0 || i > this.length) {
            throw new IllegalArgumentException("value of i is error");
        }
        Node insert = new Node();
        insert.setData(e);
        if (i == 0) {
            insert.setNext(this.head);
            this.head = insert;
        } else if (i == length) {
            Node last = getNode(length - 1);
            last.setNext(insert);
        } else {
            Node left = getNode(i - 1);
            Node right = left.getNext();
            left.setNext(insert);
            insert.setNext(right);
        }
        this.length++;
    }

    @Override
    public T listDelete(int i) {
        Node delete;
        if (i == 0) {
            delete = this.head;
            this.head = this.head.getNext();
        } else {
            Node left = getNode(i - 1);
            delete = left.getNext();
            Node right = delete.getNext();
            left.setNext(right);
        }
        this.length--;
        return delete.getData();
    }

    @Override
    public int listLength() {
        return this.length;
    }
}
