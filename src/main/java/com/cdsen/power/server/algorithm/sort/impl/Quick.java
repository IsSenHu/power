package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 快排 重点在于切分
 *
 * @author HuSen
 * create on 2019/12/16 16:41
 */
public class Quick extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        sort(a, 0, a.length - 1);
    }

    private static void sort(int[] a, int lo, int hi) {
        // 优化 要随机打乱数组
        if (lo >= hi) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    private static int partition(int[] a, int lo, int hi) {
        int v = a[lo];
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(v, a[--j])) {
                // 这个条件可去掉
                if (j == lo) {
                    break;
                }
            }
            // 相等无需交换 i > j 上一次 j - i = 1 已经交换过了 无需交换
            if (i >= j) {
                break;
            }
            // 把找到的大于v和小于v的元素互相交换
            each(a, i, j);
        }
        // 最后将v与j指针所值元素交换 也就是扫描结束后放在上一个j指针之前
        each(a, lo, j);
        return j;
    }
}
