package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

/**
 * 双亲表示法的结点结构定义代码
 *
 * @author HuSen
 * create on 2019/12/27 11:15
 */
@Data
public class PTNode<T> {

    /**
     * 结点数据
     */
    private T data;

    /**
     * 双亲位置
     */
    private int parent;
}
