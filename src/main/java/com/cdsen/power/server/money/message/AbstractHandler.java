package com.cdsen.power.server.money.message;

import com.cdsen.rabbit.model.RabbitMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author HuSen
 * create on 2019/10/29 10:45
 */
@Slf4j
public abstract class AbstractHandler<T> {

    private final StringRedisTemplate redisTemplate;

    AbstractHandler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    void handle(RabbitMessage<T> rabbitMessage, Channel channel, Message message) {
        // 注意消息的幂等性
        String id = rabbitMessage.getId();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(id, "");
        if (null != aBoolean && aBoolean) {
            try {
                channel.basicQos(1);
                realHandle(rabbitMessage.getUserId(), rabbitMessage.getData());
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

    /**
     * 真正的业务逻辑
     *
     * @param userId 用户ID
     * @param data   业务数据
     */
    protected abstract void realHandle(Long userId, T data);
}
