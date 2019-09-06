package com.cdsen.power.server.email.processor;

import com.cdsen.power.core.AbstractThreadPoolProcessor;
import com.cdsen.power.server.email.model.cons.Components;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author HuSen
 * create on 2019/9/5 14:05
 */
@Slf4j
@Component(Components.SEND_SIMPLE_MAIL_PROCESSOR)
public class SendSimpleMailProcessor extends AbstractThreadPoolProcessor<SimpleMailMessage> {

    private final JavaMailSender sender;

    public SendSimpleMailProcessor(JavaMailSender sender) {
        super(5, Duration.ofSeconds(30), 10, 1000, Components.SEND_SIMPLE_MAIL_PROCESSOR);
        this.sender = sender;
    }

    @Override
    protected Runnable runnable(SimpleMailMessage simpleMailMessage) {
        return () -> {
            try {
                if (sender != null) {
                    sender.send(simpleMailMessage);
                }
            } catch (Exception e) {
                log.error("邮件发送失败:", e);
            }
        };
    }
}
