package com.cdsen.power.server.algorithm.odd;

/**
 * 是否为奇数
 *
 * % 该操作符被定义为所有的int数值a和所有的非零int数值b，都满足下面的恒等式：
 * (a / b) * b + (a % b) == a
 * 意味着：当取余操作返回一个非零的结果时，它与左操作数具有相同的正负符号。
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
