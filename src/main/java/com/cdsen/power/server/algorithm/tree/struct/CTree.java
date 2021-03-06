package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author HuSen
 * create on 2019/12/27 11:45
 */
@Data
public class CTree<T> {

    /**
     * 结点数组
     */
    private ArrayList<CTBox<T>> nodes;

    /**
     * 根的位置
     */
    private int r;
}
