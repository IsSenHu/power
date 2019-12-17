package com.cdsen.power.server.algorithm.dc.impl;

import com.cdsen.power.server.algorithm.dc.interfaces.List;

/**
 * @author HuSen
 * create on 2019/12/17 17:53
 */
public class ArrayList<T> implements List<T> {

    /**
     * 数组存储数据元素
     */
    private T[] data;

    /**
     * 线性表当前长度
     */
    private int length;

    public ArrayList(int max) {
        //noinspection unchecked
        this.data = (T[]) new Object[max];
    }

    @Override
    public boolean listEmpty() {
        return length == 0;
    }

    @Override
    public void clearList() {
        for (int i = 0; i < length; i++) {
            data[i] = null;
        }
        this.length = 0;
    }

    @Override
    public T getElem(int i) {
        if (length == 0 || i < 0 || i > length - 1) {
            throw new IllegalArgumentException("value of i is error");
        }
        return data[i];
    }

    @Override
    public int locateElem(T e) {
        return 0;
    }

    @Override
    public void listInsert(int i, T e) {
        if (length == data.length) {
            throw new IllegalStateException("Array list is full");
        }
        if (i < 0 || i > length) {
            throw new IllegalArgumentException("value of i is error");
        }
        // 如果插入的数据不在表尾
        if (i < length) {
            System.arraycopy(data, i, data, i + 1, length - i);
        }
        data[i] = e;
        length++;
    }

    @Override
    public T listDelete(int i) {
        if (length == 0) {
            throw new IllegalStateException("Array list is empty");
        }
        if (i < 0 || i > length - 1) {
            throw new IllegalArgumentException("value of i is error");
        }
        T t = data[i];
        System.arraycopy(data, i + 1, data, i, length - 1 - i);
        // 把表尾置为空
        data[length - 1] = null;
        length--;
        return t;
    }

    @Override
    public int listLength() {
        return this.length;
    }
}
