package com.tech.rabbitmq.nospring.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.50.134");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("tech");
        factory.setPassword("tech");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //队列名称、是否持久化、是否独占（只能有一个消费者）、是否自动删除（在没有消费者时自动删除）、其他参数
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            //交换机名称（不写为默认交换机，路由键名称与队列名称一致才能被路由）
            //路由键名称
            //配置信息
            //字节数组
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
