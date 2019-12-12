package com.cdsen.power.server.algorithm.sort.impl;

/**
 * 选择排序
 *
 * @author HuSen
 * create on 2019/12/12 18:22
 */
public class Select {

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }
}
