package com.cdsen.power.server.email.service.impl;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.Processor;
import com.cdsen.power.server.email.model.cons.Components;
import com.cdsen.power.server.email.model.vo.SimpleMailAO;
import com.cdsen.power.server.email.service.MailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * @author HuSen
 * create on 2019/9/5 13:51
 */
@Service
public class MailServiceImpl implements MailService {

    private final MailProperties mailProperties;
    private final Processor<SimpleMailMessage> simpleMailMessageProcessor;

    public MailServiceImpl(MailProperties mailProperties, @Qualifier(Components.SEND_SIMPLE_MAIL_PROCESSOR) Processor<SimpleMailMessage> simpleMailMessageProcessor) {
        this.mailProperties = mailProperties;
        this.simpleMailMessageProcessor = simpleMailMessageProcessor;
    }

    @Override
    public JsonResult<SimpleMailAO> send(SimpleMailAO mail) {
        mail.setFrom(mailProperties.getUsername());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getFrom());
        message.setReplyTo(mail.getReplyTo());
        message.setTo(mail.getTo());
        message.setCc(mail.getCc());
        message.setBcc(mail.getBcc());
        message.setSentDate(mail.getSentDate());
        message.setSubject(mail.getSubject());
        message.setText(mail.getText());
        simpleMailMessageProcessor.handle(message);

        // TODO 邮件申请发送记录
        return JsonResult.of(mail);
    }
}
