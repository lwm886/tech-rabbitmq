package com.tech.rabbitmq.spring.merchant;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lw
 * @since 2021/11/2
 */
@Component
//@RabbitListener(queues = RabbitMQConfig.LOCK_MERCHANT_DEAD_QUEUE)
public class MerchantMQListener {
//    @RabbitHandler
    public void messageHandler(String body, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("body="+body);
        channel.basicAck(deliveryTag,false);
        System.out.println("*****************************");
    }
}
