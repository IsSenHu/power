package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 插入排序
 * 插入排序所需时间取决于输入中元素的初始位置
 * 平均N^2/4次比较和交换
 * 最坏N^2/2次比较和交换
 * 最好N-1次比较和0次交换
 *
 * @author HuSen
 * create on 2019/12/13 14:41
 */
public class Insertion extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        int length = a.length;
        for (int i = 1; i < length; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                each(a, j, j - 1);
            }
        }
    }
}
