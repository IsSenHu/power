package com.cdsen.power.server.algorithm.dc;


import com.cdsen.power.server.algorithm.dc.impl.CycleQueue;
import com.cdsen.power.server.algorithm.dc.interfaces.Queue;

/**
 * @author HuSen
 * create on 2019/12/17 18:47
 */
public class Main {

    public static void main(String[] args) {
        Queue<Integer> queue = new CycleQueue(10);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.enQueue(4);
        queue.enQueue(5);
        queue.enQueue(6);
        queue.enQueue(7);
        queue.enQueue(8);
        queue.enQueue(9);
        queue.enQueue(10);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }

        queue.enQueue(11);
        System.out.println(queue.getHead());
        System.out.println(queue.deQueue());

        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        queue.enQueue(4);

        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());

        queue.enQueue(3);
        queue.enQueue(4);

        while (!queue.isEmpty()) {
            System.out.println(queue.deQueue());
        }
    }
}
