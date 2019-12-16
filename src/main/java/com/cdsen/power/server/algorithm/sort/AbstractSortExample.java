package com.cdsen.power.server.algorithm.sort;

import java.util.Arrays;

/**
 * @author HuSen
 * create on 2019/12/13 14:19
 */
public abstract class AbstractSortExample {

    protected static boolean less(int v, int w) {
        return v < w;
    }

    protected static void each(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    protected static void merge(int[] aux, int[] a, int lo, int mid, int hi) {
        int i = lo;
        int j = mid + 1;
        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void show(int[] a) {
        System.out.println(Arrays.toString(a));
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public abstract void sort(int[] a);
}
