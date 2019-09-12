package com.cdsen.power.core;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/11 14:25
 */
@Getter
@Setter
public class PageResult<T> {

    private long total;
    private List<T> items;

    public static <T, PO> PageResult<T> of(long total, Function<PO, T> function, List<PO> data) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(total);
        result.setItems(data.stream().map(function).collect(Collectors.toList()));
        return result;
    }
}
