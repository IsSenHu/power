package com.cdsen.power;

import com.cdsen.power.core.JsonResult;
import com.cdsen.power.core.SpecificationFactory;
import com.cdsen.power.core.oss.OssClient;
import com.cdsen.power.core.util.JsonUtils;
import com.cdsen.power.server.money.dao.po.ConsumptionPO;
import com.cdsen.power.server.money.dao.repository.ConsumptionRepository;
import com.cdsen.power.server.money.model.ao.ConsumptionCreateAO;
import com.cdsen.power.server.money.model.ao.ConsumptionItemCreateAO;
import com.cdsen.power.server.money.model.cons.CurrencyType;
import com.cdsen.power.server.money.model.vo.ConsumptionVO;
import com.cdsen.power.server.money.service.ConsumptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private OssClient ossClient;

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

    @Test
    public void createConsumption() {
        for (int i = 0; i < 100; i++) {
            ConsumptionCreateAO ao = new ConsumptionCreateAO();
            ao.setCurrency(CurrencyType.REN_MIN_BI);
            ao.setTime(LocalDateTime.now());

            List<ConsumptionItemCreateAO> items = new ArrayList<>();
            ao.setItems(items);

            ConsumptionItemCreateAO item1 = new ConsumptionItemCreateAO();
            item1.setMoney(BigDecimal.valueOf(10));
            item1.setDescription("充值地铁卡");

            ConsumptionItemCreateAO item2 = new ConsumptionItemCreateAO();
            item2.setMoney(BigDecimal.valueOf(20));
            item2.setDescription("晚饭");

            items.add(item1);
            items.add(item2);

            consumptionService.create(ao);
        }
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void findConsumption() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ConsumptionPO> all = consumptionRepository.findAll(SpecificationFactory.produce((predicates, consumptionPORoot, criteriaBuilder) -> {

        }), pageable);
        System.out.println(all.getTotalElements());
        System.out.println(all.getContent());
    }

    @Test
    public void deleteConsumption() {
        JsonResult<ConsumptionVO> delete = consumptionService.delete(4L);
        System.out.println(delete);
    }

    @Test
    public void testOss() throws FileNotFoundException {
        ossClient.upload("测试", new FileInputStream(new File("D:\\TSBrowserDownloads\\timg.jpg")));
    }
}
