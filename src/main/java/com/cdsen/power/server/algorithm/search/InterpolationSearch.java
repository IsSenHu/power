package com.cdsen.power.server.algorithm.search;

import com.cdsen.power.server.algorithm.RandomUtils;
import com.cdsen.power.server.algorithm.sort.impl.Shell;

import java.util.Scanner;

/**
 * 插值查找法是根据要查找的关键字key与查找表中最大最小记录的关键字比较后的查找方法，其核心就在于插值的计算公式
 *
 * @author HuSen
 * create on 2020/1/6 17:29
 */
public class InterpolationSearch {

    private static int find(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = low + ((target - arr[low]) / (arr[high] - arr[low])) * (high - low);
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
