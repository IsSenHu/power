package com.cdsen.power.server.email.model.vo;

import com.cdsen.power.core.cons.TimeCons;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author HuSen
 * create on 2019/11/5 10:25
 */
@Data
public class SimpleEmailVO {

    private Long id;

    private String subject;

    private String from;

    @JsonFormat(pattern = TimeCons.F1, timezone = TimeCons.GMT8)
    private Date sentDate;

    private Boolean replySign;

    private Boolean isNew;

    private Boolean containAttachment;
}
