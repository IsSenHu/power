package com.cdsen.power.server.algorithm.lineartable;

import com.cdsen.power.server.algorithm.lineartable.impl.LazyStackQueue;
import com.cdsen.power.server.algorithm.lineartable.impl.StackQueue;

/**
 * @author HuSen
 * create on 2019/12/12 16:40
 */
public class Main {

    public static void main(String[] args) {
        // 一般实现
        StackQueue<String> stackQueue = new StackQueue<>();
        stackQueue.push("胡森");
        stackQueue.push("唐青");
        stackQueue.push("魏婧");

        System.out.println(stackQueue.pop());
        System.out.println(stackQueue.pop());

        stackQueue.push("债主");

        System.out.println(stackQueue.pop());
        System.out.println(stackQueue.pop());

        // 变种 懒实现 需要的时候才倒栈
        LazyStackQueue<String> lazyStackQueue = new LazyStackQueue<>();
        lazyStackQueue.push("胡森");
        lazyStackQueue.push("唐青");
        lazyStackQueue.push("魏婧");

        System.out.println(lazyStackQueue.pop());
        System.out.println(lazyStackQueue.pop());

        lazyStackQueue.push("债主");

        System.out.println(lazyStackQueue.pop());
        System.out.println(lazyStackQueue.pop());
    }
}
