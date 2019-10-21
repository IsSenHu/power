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

import java.io.IOException;

/**
 * @author HuSen
 * create on 2019/10/21 14:53
 */
@Slf4j
@Component
public class ConsumptionHandler {

    private final ConsumptionService consumptionService;
    private final StringRedisTemplate redisTemplate;

    public ConsumptionHandler(ConsumptionService consumptionService, StringRedisTemplate redisTemplate) {
        this.consumptionService = consumptionService;
        this.redisTemplate = redisTemplate;
    }

    @RabbitListener(queues = "directQueueCreateConsumption")
    @RabbitHandler
    public void createConsumption(RabbitMessage<ConsumptionCreateDTO> sendMessage, Channel channel, Message message) {
        // 注意消息的幂等性
        String id = sendMessage.getId();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(id, "");
        if (null != aBoolean && aBoolean) {
            try {
                channel.basicQos(1);
                consumptionService.create(sendMessage.getUserId(), sendMessage.getData());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                log.error("消息处理失败:", e);
                try {
                    redisTemplate.delete(id);
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                } catch (Exception ex) {
                    log.error("消息回退失败:", ex);
                }
            }
        }
    }
}
