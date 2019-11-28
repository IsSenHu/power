package com.cdsen.power.server.algorithm.joyofhex;

/**
 * 十六进制的趣事
 *
 * 通常最好是避免混合类型的计算
 * @author HuSen
 * create on 2019/11/28 11:17
 */
public class JoyOfHex {

    public static void main(String[] args) {
        // cafebabe
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabe));
        // 1cafebabe
        System.out.println(Long.toHexString(0x100000000L + 0xcafebabeL));
    }
}
