package com.cdsen.power.server.money.message;

import com.cdsen.power.server.money.service.IncomeService;
import com.cdsen.rabbit.model.InComeCreateDTO;
import com.cdsen.rabbit.model.RabbitMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author HuSen
 * create on 2019/10/29 10:41
 */
@Slf4j
@Component
public class IncomeHandler extends AbstractHandler<InComeCreateDTO> {

    private final IncomeService incomeService;

    public IncomeHandler(StringRedisTemplate redisTemplate, IncomeService incomeService) {
        super(redisTemplate);
        this.incomeService = incomeService;
    }

    @RabbitListener(queues = "directQueueCreateIncome")
    @RabbitHandler
    public void createIncome(RabbitMessage<InComeCreateDTO> sendMessage, Channel channel, Message message) {
        handle(sendMessage, channel, message);
    }

    @Override
    protected void realHandle(Long userId, InComeCreateDTO data) {
        log.info("用户:{} 请求创建收入记录:{}", userId, data);
        incomeService.create(userId, data);
    }
}
