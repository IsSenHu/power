package com.cdsen.power.server.algorithm.sort;

import com.cdsen.power.server.algorithm.sort.impl.*;
import org.springframework.util.Assert;

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
        Assert.isTrue(Selection.isSorted(rand), "");

        int[] rand6 = rand(10);
        new Bubble().sort(rand6);
        Bubble.show(rand6);
        Assert.isTrue(Bubble.isSorted(rand6), "");

        int[] rand1 = rand(1000);
        new Insertion().sort(rand1);
        Insertion.show(rand1);
        Assert.isTrue(Insertion.isSorted(rand1), "");

        int[] rand2 = rand(1000);
        new Shell().sort(rand2);
        Shell.show(rand2);
        Assert.isTrue(Shell.isSorted(rand2), "");

        int[] rand3 = rand(1000);
        new Merge().sort(rand3);
        Merge.show(rand3);
        Assert.isTrue(Merge.isSorted(rand3), "");

        int[] rand4 = rand(1000);
        new MergeBu().sort(rand4);
        MergeBu.show(rand4);
        Assert.isTrue(MergeBu.isSorted(rand4), "");

        int[] rand5 = rand(1000);
        new Quick().sort(rand5);
        Quick.show(rand5);
        Assert.isTrue(Quick.isSorted(rand5), "");

        int[] rand7 = rand(1000);
        new Quick3Way().sort(rand7);
        Quick3Way.show(rand7);
        Assert.isTrue(Quick3Way.isSorted(rand7), "");
    }
}
