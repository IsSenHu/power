package com.cdsen.power.server.algorithm.sort;

import com.cdsen.power.server.algorithm.sort.impl.Bubble;
import com.cdsen.power.server.algorithm.sort.impl.Select;

import java.util.Arrays;

/**
 * @author HuSen
 * create on 2019/12/12 18:20
 */
public class Main {

    public static void main(String[] args) {
        int[] arr = {2, 5, 12, 333, 12123, 112, 341, 122, 3, 4, 9};
        Bubble.sort(arr);
        System.out.println(Arrays.toString(arr));
        int[] arr2 = {2, 5, 12, 333, 12123, 112, 341, 122, 3, 4, 9};
        Select.sort(arr2);
        System.out.println(Arrays.toString(arr2));
    }
}
