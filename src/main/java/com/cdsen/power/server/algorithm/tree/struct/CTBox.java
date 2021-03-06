package com.cdsen.power.server.algorithm.tree.struct;

import lombok.Data;

/**
 * @author HuSen
 * create on 2019/12/27 11:43
 */
@Data
public class CTBox<T> {
    /**
     * 数据
     */
    private T data;
    /**
     * 第一个孩子
     */
    private CTNode firstChild;
}
