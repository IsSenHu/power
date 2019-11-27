package com.cdsen.power.server.algorithm.longdivision;

/**
 * 长整除
 *
 * @author HuSen
 * create on 2019/11/27 15:04
 */
public class LongDivision {

    public static void main(String[] args) {
        @SuppressWarnings("NumericOverflow")
        // 这个计算是完全以int来执行的，并且只有在运算完成之后，器结果才被提升为long。因此太迟，计算已经溢出。
        final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
        // 5
        System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);

        // 解决方案1
        final long MICROS_PER_DAY0 = 24L * 60 * 60 * 1000 * 1000;
        final long MILLIS_PER_DAY0 = 24L * 60 * 60 * 1000;
        // 1000
        System.out.println(MICROS_PER_DAY0 / MILLIS_PER_DAY0);
        // 解决方案2抛出溢出异常

        // 这两个解决方案都会造成性能的损失
    }
}
