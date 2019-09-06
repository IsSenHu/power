package com.cdsen.power.server.email.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.email.model.vo.SimpleMailAO;

/**
 * @author HuSen
 * create on 2019/9/5 13:51
 */
public interface MailService {

    /**
     * 发送邮件
     *
     * @param mail 邮件
     * @return 发送结果
     */
    JsonResult<SimpleMailAO> send(SimpleMailAO mail);
}
