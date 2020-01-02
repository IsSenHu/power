package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/12/27 11:39
 */
@Data
public class CTNode {

    /**
     * 在数组中的位置
     */
    private int child;

    /**
     * 下一个孩子
     */
    private CTNode next;
}
