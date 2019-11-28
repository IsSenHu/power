package com.cdsen.power.server.algorithm.dosequis;

/**
 * Dos Equis
 *
 * <规则>
 *     1.如果第二个和第三个操作数具有相同的类型，那么它就是表达式的类型
 *     2.如果一个操作数的类型是T，T表示byte、short、char，而另一个操作数是一个int类型的常量表达式，它的值可以用T表示，那么条件表达式的类型就是T
 *     3.否则，将对操作数类型进行二进制数字提升，而条件表达式的类型就是第二个和第三个操作数被提升之后的类型
 *
 *     复合赋值表达式自动地将所执行计算的结果转型为其左侧变量的类型
 * </规则>
 * @author HuSen
 * create on 2019/11/28 14:57
 */
public class DosEquis {

    public static void main(String[] args) {
        char x = 'X';
        int i = 0;
        // x88
        System.out.print(true ? x : 0);
        System.out.println(false ? i : x);
    }
}
