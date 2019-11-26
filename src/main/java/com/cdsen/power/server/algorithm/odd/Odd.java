package com.cdsen.power.server.algorithm.odd;

/**
 * 是否为奇数
 *
 * @author HuSen
 * create on 2019/11/26 10:48
 */
public class Odd {

    public static boolean is(int number) {
        // 负整数不成立
        // return number % 2 == 1;
        return (number & 1) != 0;
    }

    public static boolean not(int number) {
        return (number & 1) == 0;
    }

}
