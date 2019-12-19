package com.cdsen.power.server.algorithm.dc.interfaces;

/**
 * @author HuSen
 * create on 2019/12/19 14:19
 */
public interface Stack<T> {

    /**
     * 初始化操作，建立一个空栈
     */
    void init();

    /**
     * 销毁它
     */
    void destroy();

    /**
     * 将栈清空
     */
    void clear();

    /**
     * @return 若栈为空返回true，否则返回false
     */
    boolean isEmpty();

    /**
     * @return 若栈存在且非空，返回栈顶元素
     */
    T getTop();

    /**
     * 插入新元素e到栈并成为栈顶元素
     *
     * @param e 元素
     */
    void push(T e);

    /**
     * @return 删除栈顶的元素，并返回
     */
    T pop();

    /**
     * @return 返回栈的元素个数
     */
    int length();
}
