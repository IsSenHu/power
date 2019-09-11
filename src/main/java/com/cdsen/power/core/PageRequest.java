package com.cdsen.power.core;

import lombok.Getter;
import lombok.Setter;

/**
 * @author HuSen
 * create on 2019/9/11 15:30
 */
@Getter
@Setter
public class PageRequest<T> {

    private Integer page;
    private Integer number;
    private String sort;

    private T customParams;
}
