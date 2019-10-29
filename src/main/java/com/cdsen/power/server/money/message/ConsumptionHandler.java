package com.cdsen.power.server.money.message;

import com.cdsen.power.server.money.service.ConsumptionService;
import com.cdsen.rabbit.model.ConsumptionCreateDTO;
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
 * create on 2019/10/21 14:53
 */
@Slf4j
@Component
public class ConsumptionHandler extends AbstractHandler<ConsumptionCreateDTO> {

    private final ConsumptionService consumptionService;

    public ConsumptionHandler(ConsumptionService consumptionService, StringRedisTemplate redisTemplate) {
        super(redisTemplate);
        this.consumptionService = consumptionService;
    }

    @RabbitListener(queues = "directQueueCreateConsumption")
    @RabbitHandler
    public void createConsumption(RabbitMessage<ConsumptionCreateDTO> sendMessage, Channel channel, Message message) {
        handle(sendMessage, channel, message);
    }

    @Override
    protected void realHandle(Long userId, ConsumptionCreateDTO data) {
        log.info("用户:{} 请求创建消费记录:{}", userId, data);
        consumptionService.create(userId, data);
    }
}
