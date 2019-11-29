package com.cdsen.power.server.algorithm.rhymes;

import java.util.Random;

/**
 * 不劳而获
 *
 * @author HuSen
 * create on 2019/11/29 17:37
 */
public class Rhymes {

    private static Random random = new Random();

    public static void main(String[] args) {
        StringBuffer word = null;
        switch (random.nextInt(2)) {
            case 1: word = new StringBuffer('P');
            case 2: word = new StringBuffer('G');
            default: word = new StringBuffer('M');
        }
        word.append('a');
        word.append('i');
        word.append('n');
        // ain
        System.out.println(word);
    }
}
