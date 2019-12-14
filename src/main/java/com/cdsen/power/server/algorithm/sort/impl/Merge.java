package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 归并排序
 * 运行时间与NlgN成正比
 * 缺点是辅助数组使用的额外空间和N成正比
 *
 * @author HuSen
 * create on 2019/12/13 17:14
 */
public class Merge extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        setAux(new int[a.length]);
        sort(a, 0, a.length - 1);
    }

    private void sort(int[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }
}
