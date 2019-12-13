package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 希尔排序
 * 最坏N^(3/2)
 * 有经验的程序员有时会选择希尔排序，因为对于中等大小的数组它的运行时间是可以接受的。
 * 它的代码量很小，且不需要使用额外的内存空间。
 *
 * @author HuSen
 * create on 2019/12/13 15:35
 */
public class Shell extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        int length = a.length;
        int h = 1;
        while (h < length / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    each(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }
}
