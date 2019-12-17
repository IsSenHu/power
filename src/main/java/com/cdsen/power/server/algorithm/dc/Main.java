package com.cdsen.power.server.algorithm.dc;

import com.cdsen.power.server.algorithm.dc.impl.ArrayList;
import com.cdsen.power.server.algorithm.dc.interfaces.List;

/**
 * @author HuSen
 * create on 2019/12/17 18:47
 */
public class Main {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(10);

        list.listInsert(0, 1);
        list.listInsert(0, 2);
        list.listInsert(0, 3);
        list.listInsert(0, 4);
        list.listInsert(0, 5);
        list.listInsert(0, 6);
        list.listInsert(0, 7);
        list.listInsert(0, 8);
        list.listInsert(0, 9);

        System.out.println(list.listLength());

        System.out.println(list.listDelete(2));

        System.out.println(list.listLength());

        System.out.println(list.getElem(5));
    }
}
