package com.cdsen.power.server.algorithm.sort;

import com.cdsen.power.server.algorithm.sort.impl.Insertion;
import com.cdsen.power.server.algorithm.sort.impl.Merge;
import com.cdsen.power.server.algorithm.sort.impl.Selection;
import com.cdsen.power.server.algorithm.sort.impl.Shell;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author HuSen
 * create on 2019/12/12 18:20
 */
public class Main {

    private static final Random R = new SecureRandom();

    private static int[] rand(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = R.nextInt(size * 10);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] rand = rand(10);
        new Selection().sort(rand);
        Selection.show(rand);
        assert Selection.isSorted(rand);

        int[] rand1 = rand(10);
        new Insertion().sort(rand1);
        Insertion.show(rand1);
        assert Insertion.isSorted(rand1);

        int[] rand2 = rand(100);
        new Shell().sort(rand2);
        Shell.show(rand2);
        assert Shell.isSorted(rand2);

        int[] rand3 = rand(1000);
        new Merge().sort(rand3);
        Merge.show(rand3);
        assert Merge.isSorted(rand3);
    }
}
