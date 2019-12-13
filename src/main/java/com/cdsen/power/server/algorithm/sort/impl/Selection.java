package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 选择排序
 * 交换N次 比较N^2/2次 并且与输入无关
 *
 * @author HuSen
 * create on 2019/12/12 18:22
 */
public class Selection extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        int length = a.length;
        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            each(a, i, min);
        }
    }
}
