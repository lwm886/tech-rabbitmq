package com.tech.rabbitmq.nospring.direct;

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

    private final static String EXCHANGE_NAME = "exchange_direct";

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
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String debugMsg = "这是订单服务的debug日志";
            String infoMsg = "这是订单服务的info日志";
            String errorMsg = "这是订单服务的error日志";
            channel.basicPublish(EXCHANGE_NAME, "debugRoutingKey", null, debugMsg.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "infoRoutingKey", null, infoMsg.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_NAME, "errorRoutingKey", null, errorMsg.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");
        }
    }
}
