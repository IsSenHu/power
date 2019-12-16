package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 自底向上归并排序
 *
 * @author HuSen
 * create on 2019/12/14
 */
public class MergeBu extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        int length = a.length;
        int[] aux = new int[length];
        for (int size = 1; size < length; size+=size) {
            for (int lo = 0; lo < length - size; lo += (size + size)) {
                merge(aux, a, lo, lo + size - 1, Math.min(lo + size - 1 + size, length - 1));
            }
        }
    }
}
