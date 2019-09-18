package com.cdsen.power.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author HuSen
 * create on 2019/9/11 15:30
 */
@Getter
@Setter
public class IPageRequest<T> {

    private Integer page = 1;
    private Integer number = 10;
    private String sort;
    private Boolean asc = false;

    private T customParams;

    public Pageable of() {
        return PageRequest.of(this.page - 1, this.number, asc == null || !asc ? Sort.by(sort).descending() : Sort.by(sort).ascending());
    }
}
