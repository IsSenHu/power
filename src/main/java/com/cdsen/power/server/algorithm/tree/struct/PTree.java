package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author HuSen
 * create on 2019/12/27 11:20
 */
@Data
public class PTree<T> {

    /**
     * 结点数组
     */
    private ArrayList<PTNode<T>> nodes;

    /**
     * 根的位置
     */
    private int r;
}
