package com.cdsen.power.server.algorithm;

/**
 * 欧几里得算法 求最大公约数
 * <一个算法的5个重要特征>
 *     <a>有限性:一个算法在有限步骤之后必然要终止</a>
 *     <b>确定性:一个算法的每个步骤都必须精确地定义，要执行的动作每一步都必须严格地和无歧义地描述清楚</b>
 *     <c>输入:一个算法有零个或多个输入</c>
 *     <d>输出:一个算法有一个或多个输出</d>
 *     <e>能行性:一个算法一般可以认为是能行的(简称有效的)，其含义是指它的所有运算必须是充分基本的，因而原则上人们用笔和纸都可在有限时间内精确地完成它们</e>
 * </一个算法的5个重要特征>
 *
 * @author HuSen
 * create on 2019/12/2 15:14
 */
public class EuclideanAlgorithm {

    public static int compute(int m, int n) {
        if (n > m) {
            int temp = m;
            m = n;
            n = temp;
        }
        int r = m % n;
        if (r == 0) {
            return n;
        }
        return compute(n, r);
    }

    public static void main(String[] args) {
        System.out.println(compute(119, 544));
    }
}
