package com.cdsen.power.server.algorithm.tree;

import java.util.Arrays;

/**
 * @author HuSen
 * create on 2019/12/23 15:55
 */
public class Main {
    private static int next = 0;

    public static void main(String[] args) {
        String[] tree = new String[31];
        next(tree, new String[]{"A", "B", "#", "D", "#", "#", "C", "#", "#"}, 1);
        System.out.println(Arrays.toString(tree));
        preEach(tree, 1);
        System.out.println(Main.class.getSimpleName());
    }

    private static void next(String[] tree, String[] seq, int index) {
        String next = seq[Main.next++];
        if ("#".equals(next)) {
            tree[index - 1] = next;
            return;
        }
        tree[index - 1] = next;
        next(tree, seq, index * 2);
        next(tree, seq, index * 2 + 1);
    }

    private static void preEach(String[] tree, int index) {
        String current = tree[index - 1];
        if (current.equals("#")) {
            return;
        }
        System.out.println(current);
        preEach(tree, index * 2);
        preEach(tree, index * 2 + 1);
    }
}
