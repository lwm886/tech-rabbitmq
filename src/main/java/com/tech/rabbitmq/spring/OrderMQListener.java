package com.tech.rabbitmq.spring;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lw
 * @since 2021/10/30
 */
@Slf4j
@Component
@RabbitListener(queues = "order_queue")
public class OrderMQListener {

    @RabbitHandler
    public void messageHandler(String body, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        System.out.println("msgTag="+deliveryTag);
//        System.out.println("message="+message.toString());
        log.info("body="+body);
        System.out.println(1/0);
        //进行手动确认
        //消息投递序号 是否批量
        channel.basicAck(deliveryTag,false);
//        if(body.contains("2")){
//            channel.basicAck(deliveryTag,false);
//        }

        //拒收消息
        //消息投递序号  是否批量  是否将消息回退到队列
//        channel.basicNack(deliveryTag,false,true);

        //拒收消息 （不支持批量拒收）
        //消息投递序号 是否将消息回退到队列
//        channel.basicReject(deliveryTag,true);
        System.out.println("*****************************");
    }
}
