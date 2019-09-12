package com.cdsen.power;

import com.cdsen.power.core.util.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void contextLoads() {

    }

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testQuery() {
        // native sql 确实可以实现 既然要用native sql 为什么不选 mybatis ?

        Query nativeQuery = entityManager.createNativeQuery("SELECT c.id, ct.money FROM tb_consumption c LEFT JOIN tb_consumption_item ct ON c.id = ct.consumption_id");
        List resultList = nativeQuery.getResultList();
        System.out.println(JsonUtils.toJsonString(resultList));
    }

    @Test
    public void testSendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1178515826@qq.com");
        message.setTo("husen@archly.cc");
        message.setSubject("主题：简单测试邮件");
        message.setText("测试邮件内容");
        // 邮件显示的时间
        message.setSentDate(new Date(System.currentTimeMillis() - 60 * 1000 * 60));
        // 密送
        message.setBcc();
        // 抄送
        message.setCc();
        // 这封邮件回复给谁
        message.setReplyTo("2662368115@qq.com");
        javaMailSender.send(message);
    }
}
