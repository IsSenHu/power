package com.cdsen.power.server.email.model.ao;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author HuSen
 * create on 2019/11/4 14:21
 */
@Data
public class EmailUpdateAO {

    @NotNull
    private Long configId;
}
