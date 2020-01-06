package com.cdsen.power.server.algorithm.search;

import com.cdsen.power.server.algorithm.RandomUtils;
import com.cdsen.power.server.algorithm.sort.impl.Shell;

import java.util.Scanner;

/**
 * 二分查找
 *
 * @author HuSen
 * create on 2020/1/6 17:10
 */
public class BinarySearch {

    private static int find(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (target < arr[mid]) {
                high = mid - 1;
            } else if (target > arr[mid]) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] rand = RandomUtils.rand(10);
        new Shell().sort(rand);
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();
        System.out.println(find(rand, target));
    }
}
