package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 优先队列
 *
 * <定义>
 *     当一棵二叉树的每个节点都大于等于它的子节点时，它被称为堆有序
 *     二叉堆是一组能够用堆有序的完全二叉树排序的元素，并在数组中按照层级储存（不使用数组的第一个位置）
 *     一棵大小为N的完全二叉树的高度为lgN，当N达到2的幂时高度会加1
 * </定义>
 *
 * @author HuSen
 * create on 2019/12/17 10:46
 */
public class MaxPQ extends AbstractSortExample {

    private int[] pq;

    public MaxPQ() {
        pq = new int[1000];
    }

    public MaxPQ(int max) {
        pq = new int[max];
    }

    public MaxPQ(int[] a) {
        pq = a;
    }

    public void insert(int v) {

    }

    public int max() {
        return 0;
    }

    public int delMax() {
        return 0;
    }

    public boolean isEmpty() {
        return pq.length == 0;
    }

    public int size() {
        return pq.length;
    }

    @Override
    public void sort(int[] a) {

    }
}
