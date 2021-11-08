package com.tech.rabbitmq.nospring.simple;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.50.134");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("tech");
        factory.setPassword("tech");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //一般是固定的，可以作为会话的名称
                System.out.println("consumerTag="+consumerTag);
                //可以获取交换机、路由键等信息
                System.out.println("envelope="+envelope);
                System.out.println("properties="+properties);
                System.out.println("body="+new String("utf-8"));
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + message + "'");
//        };
//        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
