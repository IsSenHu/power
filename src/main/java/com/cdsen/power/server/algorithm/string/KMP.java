package com.cdsen.power.server.algorithm.string;

import java.util.Arrays;

/**
 * @author HuSen
 * create on 2019/12/20 15:46
 */
public class KMP {

    /**
     * 通过计算返回子串t的next数组
     *
     * @param t 子串t
     * @return next数组
     */
    private static int[] getNext(String t) {
        char[] chars = t.toCharArray();
        int i = 1;
        int j = 0;
        int length = t.length();
        int[] next = new int[length + 1];
        next[0] = 0;
        while (i < length) {
            // i 后缀 j 前缀 ababa
            if (j == 0 || chars[i - 1] == chars[j - 1]) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    /**
     * 在字符串s中查找t
     *
     * @param s   字符串s
     * @param t   字符串t
     * @param pos 查找的起始位置
     * @return 匹配到的第一个字符的索引
     */
    private static int index(String s, String t, int pos) {
        char[] sc = s.toCharArray();
        char[] tc = t.toCharArray();
        int i = pos;
        int j = 1;
        int[] next = getNext(t);
        while (i < s.length() && j <= t.length()) {
            if (j == 0 || sc[i] == tc[j - 1]) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j > t.length()) {
            return i - t.length();
        } else {
            return -1;
        }
    }
}
