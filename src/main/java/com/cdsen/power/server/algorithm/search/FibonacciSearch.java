package com.cdsen.power.server.algorithm.search;

/**
 * 斐波那契查找
 *
 * @author HuSen
 * create on 2020/1/6 17:38
 */
public class FibonacciSearch {

    private static final int[] F = new int[47];

    static {
        // 初始化斐波那契数列
        for (int i = 0; i < F.length; i++) {
            if (i == 0) {
                F[i] = 0;
            } else if (i == 1) {
                F[i] = 1;
            } else {
                F[i] = F[i - 1] + F[i - 2];
            }
        }
    }

    private static int find(int[] arr, int target) {
        // 这个方法假设arr数组的长度是可变的
        int low = 0;
        int length = arr.length;
        int n = length - 1;
        int high = n;
        int k = 0;
        int mid;
        int i;
        // 计算n位于斐波那契数列的位置
        while (n > F[k] - 1) {
            k++;
        }
        for (i = n; i < F[k] - 1; i++) {
            arr[i] = arr[n];
        }
        while (low <= high) {
            mid = low + F[k - 1] - 1;
            if (target < arr[mid]) {
                high = mid - 1;
                k = k - 1;
            } else if (target > F[mid]) {
                low = mid + 1;
                k = k - 2;
            } else {
                if (mid <= n) {
                    return mid;
                } else {
                    return -1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {

    }
}
