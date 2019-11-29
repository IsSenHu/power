package com.cdsen.power.server.algorithm.animalfarm;

/**
 * 动物庄园
 *
 * 1.2个字符串对象用==比较而不是两个字符串常量用==比较 所以为pig != dog
 * 2.+号操作符的运算级别高于==号，所以最终的结果只有false
 * @author HuSen
 * create on 2019/11/29 14:54
 */
public class AnimalFarm {

    public static void main(String[] args) {
        final String pig = "length: 10";
        final String dog = "length: " + pig.length();
        // false 而不是Animal are equal: false
        System.out.println("Animal are equal: " + pig == dog );
    }
}
