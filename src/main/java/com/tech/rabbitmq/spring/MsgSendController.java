package com.tech.rabbitmq.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lw
 * @since 2021/10/30
 */
@Slf4j
@RestController
public class MsgSendController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private int c=0;

    @GetMapping("send")
    String send() {
//        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.new", "新订单 "+(++c));
//        }
        return "ok";
    }

    /**
     * 发送确认机制
     * @return
     */
    @GetMapping("confirm")
    String confirm() {

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 发送确认接口
             * @param correlationData 配置
             * @param ack 发送成功为true 失败为false
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("correlationData={}", correlationData);
                log.info("ack={}", ack);
                log.info("cause={}", cause);
                if (ack) {
                    log.info("消息发送成功");
                } else {
                    log.info("消息发送失败");
                }
            }
        });

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.new", "新订单");
        //模拟发送失败，使用一个不存在的交换机
        //rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME+"A","order.new","新订单");
        log.info("ok");
        return "ok";
    }

    @GetMapping("return")
    String ret(){
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.info("code={}",returned.getReplyCode());
                log.info("returned={}",returned);
            }
        });
//        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.new", "新订单 ReturnsCallback");
//        模拟交换机转发消息到队列失败
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "A"+"order.new", "新订单 ReturnsCallback");
        log.info("ok");
        return "ok";
    }
}
