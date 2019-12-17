package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 三向切分快速排序
 *
 * @author HuSen
 * create on 2019/12/16 17:51
 */
public class Quick3Way extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        int v = a[lo];
        while (i <= gt) {
            int c = a[i];
            if (c < v) {
                each(a, lt++, i++);
            } else if (c > v) {
                each(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }
}
