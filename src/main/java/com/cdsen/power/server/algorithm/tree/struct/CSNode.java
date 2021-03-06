package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/12/27 14:10
 */
@Data
public class CSNode<T> {
    /**
     * 数据
     */
    private T data;

    /**
     * 第一个孩子
     */
    private CSNode<T> firstChild;

    /**
     * 右兄弟
     */
    private CSNode<T> rightSib;
}
