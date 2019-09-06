package com.cdsen.power.server.email.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author HuSen
 * create on 2019/9/5 14:51
 */
@Data
public class SimpleMailAO {

    private String from;
    private String replyTo;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private Date sentDate;
    private String subject;
    private String text;
}
