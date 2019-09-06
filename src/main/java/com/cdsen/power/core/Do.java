package com.cdsen.power.core;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author HuSen
 * create on 2019/8/28 18:07
 */
public interface Do<T> {

    /**
     * 如果表达式为true，则执行
     *
     * @param consumer   执行的函数
     * @param t          表达式因子
     */
    default void doIt(T t, Consumer<T> consumer) {
        if (expression().test(t)) {
            consumer.accept(t);
        }
    }

    /**
     * 获取表达式
     *
     * @return 表达式
     */
    Predicate<T> expression();
}
