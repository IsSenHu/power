package com.cdsen.power.server.money.model.query;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author HuSen
 * create on 2019/9/12 9:45
 */
@Getter
@Setter
public class InComeQuery {

    private LocalDate start;
    private LocalDate end;
}
