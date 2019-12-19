package com.cdsen.power.server.algorithm.dc.impl;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/12/19 11:51
 */
@Getter
@Setter
public class Node<T> {
    private T data;
    private int cur;
}
