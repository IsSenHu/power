package com.cdsen.power.server.algorithm.tree.struct;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 二叉链表
 *
 * @author HuSen
 * create on 2019/12/27 15:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BiTNode<T> {
    /**
     * 节点数据
     */
    private T data;
    /**
     * 左孩子
     */
    private BiTNode<T> lChild;
    /**
     * 右孩子
     */
    private BiTNode<T> rChild;
}
