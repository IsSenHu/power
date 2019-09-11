package com.cdsen.power.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/11 14:25
 */
@Getter
@Setter
public class PageResult<T> {

    private long total;
    private List<T> items;
}
