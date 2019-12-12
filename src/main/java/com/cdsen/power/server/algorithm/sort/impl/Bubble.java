package com.cdsen.power.server.algorithm.sort.impl;

/**
 * 冒泡排序
 *
 * @author HuSen
 * create on 2019/12/12 18:16
 */
public class Bubble {

    public static void sort(int[] arr) {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j < length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
