package com.cdsen.power.server.algorithm.dc;


import com.cdsen.power.server.algorithm.dc.impl.ArrayStack;
import com.cdsen.power.server.algorithm.dc.interfaces.Stack;

/**
 * @author HuSen
 * create on 2019/12/17 18:47
 */
public class Main {

    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>(10);
        stack.push(10);
        stack.push(9);
        System.out.println(stack.getTop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(8);
        stack.push(7);
        stack.push(6);
        stack.push(5);
        stack.push(4);
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.push(0);
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
