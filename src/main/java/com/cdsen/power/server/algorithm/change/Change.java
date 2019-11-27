package com.cdsen.power.server.algorithm.change;

/**
 * 找零
 *
 * Double.toString()，打印出来的小数，是足以将double类型的值与最靠近它的临近值区分出来的最短小数，它在小数点之前和之后都至少有一位。
 * 二进制浮点数对于货币计算是非常不合适的，因为它不可能将0.1或者10的任何次负幂，精确表示为一个有限长度的二进制小数
 * <方案1>
 *     解决该问题的一种方式就是使用某种类型整数类型，例如int或long，并且以分为单位来执行计算。
 * </方案1>
 * <方案2>
 *     另一种方式就是使用BigDecimal，一定要使用BigDecimal(String)构造器，而千万不要使用BigDecimal(double)。后一个构造器将用它的参数的精确值来创建一个实例。
 *     例如new BigDecimal(.1)，它将返回一个BigDecimal，0.10000000000000000055511.........
 *     但是BigDecimal的计算很有可能比那些原生类型的更慢。
 * </方案2>
 * @author HuSen
 * create on 2019/11/27 11:10
 */
public class Change {

    public static void main(String[] args) {
        // 0.8999999999999999 问题在于并不是所有的小数都可以用二进制浮点数精确表示 例如1.10
        System.out.println(2.00 - 1.10);
    }
}
