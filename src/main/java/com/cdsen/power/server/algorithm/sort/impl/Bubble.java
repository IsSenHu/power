package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 冒泡排序
 *
 * @author HuSen
 * create on 2019/12/12 18:16
 */
public class Bubble extends AbstractSortExample {

    @Override
    public void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (less(arr[j], arr[i])) {
                    each(arr, i, j);
                }
            }
        }
    }
}
