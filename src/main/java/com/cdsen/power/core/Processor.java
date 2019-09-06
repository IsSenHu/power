package com.cdsen.power.core;

/**
 * 处理器接口
 *
 * @author HuSen
 * create on 2019/9/5 14:06
 */
public interface Processor<T> {

    /**
     * 处理方法
     *
     * @param t 处理的数据
     */
    void handle(T t);
}
