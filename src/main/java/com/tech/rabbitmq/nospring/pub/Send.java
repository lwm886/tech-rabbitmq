package com.tech.rabbitmq.nospring.pub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Send {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.50.134");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("tech");
        factory.setPassword("tech");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //绑定交换机
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String msg = "fanout交换机测试";
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.println("广播消息发送成功");
        }
    }
}
