package com.cdsen.power.server.algorithm.sort.impl;

import com.cdsen.power.server.algorithm.sort.AbstractSortExample;

/**
 * 堆排序
 * 不适合待排序序列个数较少的情况
 *
 * @author HuSen
 * create on 2020/1/4 16:53
 */
public class Heap extends AbstractSortExample {

    @Override
    public void sort(int[] a) {
        int i;
        int length = a.length;
        for (i = length / 2 - 1; i >= 0; i--) {
            heapAdjust(a, i, length - 1);
        }
        for (i = length - 1; i > 0;) {
            each(a, 0, i);
            heapAdjust(a, 0 , --i);
        }
    }

    private void heapAdjust(int[] a, int s, int m) {
        int temp, j;
        temp = a[s];
        for (j = 2 * s + 1; j <= m; j = j * 2 + 1) {
            // 找出左右子叶值最大的用j来标识
            if (j < m && less(a[j], a[j + 1])) {
                j++;
            }
            // 判断顶点与子叶的大小 如果顶点大于最大的值的叶子结点 则无需交换
            if (temp >= a[j]) {
                break;
            }
            // 否则交换
            a[s] = a[j];
            // 让 s=j 是为了修复上一次交换所打乱的子结点作为顶点的次序
            s = j;
        }
        // 将temp赋值到正确的位置
        a[s] = temp;
    }
}
