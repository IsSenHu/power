package com.cdsen.power.server.email.service;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.server.email.dao.po.EmailPO;
import com.cdsen.power.server.email.model.ao.EmailUpdateAO;
import com.cdsen.power.server.email.model.vo.SimpleMailAO;

import java.util.List;

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

    /**
     * 更新邮件
     *
     * @param ao 更新邮件数据模型
     * @return 更新结果
     */
    JsonResult update(EmailUpdateAO ao);

    /**
     * 保存邮件
     *
     * @param add    新增的邮件
     * @param update 更新的邮件
     */
    void save(List<EmailPO> add, List<EmailPO> update);
}
