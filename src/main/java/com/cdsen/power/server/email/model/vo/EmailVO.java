package com.cdsen.power.server.email.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author HuSen
 * create on 2019/11/5 14:10
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmailVO extends SimpleEmailVO {

    private String messageId;

    private String content;

    private String to;

    private String cc;

    private String bcc;

    private Boolean isHtml;

    List<Map<String, String>> attachments;
}
