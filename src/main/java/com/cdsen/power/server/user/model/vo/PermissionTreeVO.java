package com.cdsen.power.server.user.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author HuSen
 * create on 2019/9/19 11:36
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionTreeVO {

    private Integer id;
    private String name;
    private List<PermissionTreeVO> children;
}
