package com.cdsen.power.server.algorithm;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author HuSen
 * create on 2020/1/6 16:59
 */
public class RandomUtils {

    private static final Random R = new SecureRandom();

    public static int[] rand(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = R.nextInt(size * 10);
        }
        return arr;
    }
}
