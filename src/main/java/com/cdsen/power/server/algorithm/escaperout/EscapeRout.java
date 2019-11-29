package com.cdsen.power.server.algorithm.escaperout;

/**
 * 转义字符的溃败
 *
 * @author HuSen
 * create on 2019/11/29 15:01
 */
public class EscapeRout {

    public static void main(String[] args) {
        // 2
        System.out.println("a\u0022.length() +\u0022b".length());
        // 实际上是这个
        System.out.println("a".length() + "b".length());
    }
}
