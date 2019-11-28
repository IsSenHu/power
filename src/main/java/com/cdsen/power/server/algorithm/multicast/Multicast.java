package com.cdsen.power.server.algorithm.multicast;

/**
 * 多重转型
 * 如果你通过观察不能确定程序要做什么，那么它做的事就很有可能不是你想要的
 *
 * byte 有符号类型
 * char 无符号类型
 *
 * <规则>
 *     如果最初的类型是有符号的，那么就执行符号扩展；如果它是char，那么不管它将要被转换成什么类型，都执行零扩展。
 *     byte -1 => char：作为结果的char数值的16位都被置位了，因此它等于2^16 - 1 = 65535
 * </规则>
 * <有符号扩展>
 *
 * </有符号扩展>
 * <无符号扩展>
 *
 * </无符号扩展>
 * @author HuSen
 * create on 2019/11/28 11:49
 */
public class Multicast {

    public static void main(String[] args) {
        // 65535
        System.out.println((int) (char) (byte) -1);

        char c = (char) -1;
//        int i = c & 0xfff;
        System.out.println(c);
    }
}
