package com.cdsen.power.server.email.service.impl;

import com.cdsen.email.EmailAuthToken;
import com.cdsen.email.EmailUtils;
import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.Processor;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.oss.OssClientManager;
import com.cdsen.power.core.security.util.SecurityUtils;
import com.cdsen.power.core.util.JsonUtils;
import com.cdsen.power.server.config.dao.repository.ConfigRepository;
import com.cdsen.power.server.email.dao.po.EmailPO;
import com.cdsen.power.server.email.dao.repository.EmailRepository;
import com.cdsen.power.server.email.model.ao.EmailUpdateAO;
import com.cdsen.power.server.email.model.cons.Components;
import com.cdsen.power.server.email.model.cons.EmailError;
import com.cdsen.power.server.email.model.vo.SimpleMailAO;
import com.cdsen.power.server.email.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/5 13:51
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    private final MailProperties mailProperties;
    private final Processor<SimpleMailMessage> simpleMailMessageProcessor;
    private final ConfigRepository configRepository;
    private final EmailRepository emailRepository;

    public MailServiceImpl(MailProperties mailProperties, @Qualifier(Components.SEND_SIMPLE_MAIL_PROCESSOR) Processor<SimpleMailMessage> simpleMailMessageProcessor, ConfigRepository configRepository, EmailRepository emailRepository) {
        this.mailProperties = mailProperties;
        this.simpleMailMessageProcessor = simpleMailMessageProcessor;
        this.configRepository = configRepository;
        this.emailRepository = emailRepository;
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

    @Override
    public JsonResult update(EmailUpdateAO ao) {
        return configRepository.findById(ao.getConfigId())
                .map(po -> {
                    JsonResult jsonResult = JsonResult.success();
                    String customInfo = po.getCustomInfo();
                    EmailAuthToken token = JsonUtils.parseObj(customInfo, EmailAuthToken.class);
                    if (token == null) {
                        jsonResult = JsonResult.of(EmailError.CONFIG_ERROR);
                    } else {
                        Long userId = SecurityUtils.currentUserDetails().getUserId();
                        Map<String, EmailPO> map = emailRepository.findAllByUserIdAndHost(userId, token.getHost())
                                .stream().collect(Collectors.toMap(EmailPO::getMessageId, e -> e));
                        OssClient ossClient = OssClientManager.getClient("EMAIL");
                        EmailUtils.readMessages(token, mimeMessages -> {
                            List<EmailPO> willUpdate = new ArrayList<>(map.size());
                            List<EmailPO> willAdd = new ArrayList<>(mimeMessages.size());

                            for (MimeMessage mimeMessage : mimeMessages) {
                                try {
                                    String messageId = EmailUtils.getMessageId(mimeMessage);
                                    boolean isNew = EmailUtils.isNew(mimeMessage);
                                    if (map.containsKey(messageId)) {
                                        EmailPO emailPO = map.get(messageId);
                                        emailPO.setIsNew(isNew);
                                        willUpdate.add(emailPO);
                                    } else {
                                        EmailPO emailPO = new EmailPO();
                                        emailPO.setUserId(userId);
                                        emailPO.setHost(token.getHost());
                                        emailPO.setProtocol(token.getProtocol());
                                        emailPO.setMessageId(messageId);
                                        emailPO.setIsNew(isNew);

                                        String subject = EmailUtils.getSubject(mimeMessage);
                                        emailPO.setSubject(subject);

                                        String from = EmailUtils.getFrom(mimeMessage);
                                        emailPO.setFrom(from);

                                        boolean replySign = EmailUtils.getReplySign(mimeMessage);
                                        emailPO.setReplySign(replySign);

                                        Date sentDate = EmailUtils.getSentDate(mimeMessage);
                                        emailPO.setSentDate(sentDate);

                                        String to = EmailUtils.getMailAddress(mimeMessage, Message.RecipientType.TO);
                                        String cc = EmailUtils.getMailAddress(mimeMessage, Message.RecipientType.CC);
                                        String bcc = EmailUtils.getMailAddress(mimeMessage, Message.RecipientType.BCC);
                                        emailPO.setTo(to);
                                        emailPO.setCc(cc);
                                        emailPO.setBcc(bcc);

                                        StringBuilder contentBuilder = new StringBuilder();
                                        EmailUtils.readMailContent(mimeMessage, contentBuilder);
                                        emailPO.setContent(contentBuilder.toString());

                                        StringBuilder attachmentsBuilder = new StringBuilder();
                                        if (EmailUtils.isContainAttach(mimeMessage)) {
                                            EmailUtils.saveAttachment(mimeMessage, (fileName, in) -> {
                                                ossClient.upload(messageId.concat("-").concat(fileName), in);
                                                attachmentsBuilder.append(",").append(fileName);
                                            });
                                        }
                                        emailPO.setAttachments(attachmentsBuilder.substring(1));
                                        willAdd.add(emailPO);
                                    }
                                } catch (Exception e) {
                                    log.error("邮件获取失败:", e);
                                }
                            }
                            save(willAdd, willUpdate);
                        });
                    }
                    return jsonResult;
                })
                .orElseGet(() -> JsonResult.of(EmailError.CONFIG_NOT_FOUNT));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<EmailPO> add, List<EmailPO> update) {
        emailRepository.saveAll(add);
        emailRepository.saveAll(update);
    }
}
