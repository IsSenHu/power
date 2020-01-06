package com.cdsen.power.server.algorithm.search;

import com.cdsen.power.server.algorithm.RandomUtils;

import java.util.Scanner;

/**
 * 顺序表查找算法
 *
 * @author HuSen
 * create on 2020/1/6 16:55
 */
public class SequentialSearch {

    private static int find(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] rand = RandomUtils.rand(1000);
        Scanner scanner = new Scanner(System.in);
        String target = scanner.nextLine();
        System.out.println(find(rand, Integer.parseInt(target)));
    }
}
