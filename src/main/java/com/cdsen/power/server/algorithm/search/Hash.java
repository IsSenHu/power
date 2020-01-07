package com.cdsen.power.server.algorithm.search;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 散列函数
 * 1.计算简单
 * 2.散列地址分布均匀
 *
 * @author HuSen
 * create on 2020/1/7 16:30
 */
public class Hash {

    // 直接定址法：f(key) = a * key + b
    // 数字分析法
    // 平方取中法
    // 折叠法
    // 除留余数法
    // 随机数法

    // 散列冲突
    // 开放地址法，线性探测法
    // 再散列函数法
    // 链地址法
    // 公共溢出区法

    public static void main(String[] args) {
        Random random = new SecureRandom("2".getBytes());
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(100));
        }
    }
}
