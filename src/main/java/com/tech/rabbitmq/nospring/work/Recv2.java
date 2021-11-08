package com.tech.rabbitmq.nospring.work;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Recv2 {
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
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //一般是固定的，可以作为会话的名称
//                System.out.println("consumerTag="+consumerTag);
                //可以获取交换机、路由键等信息
//                System.out.println("envelope="+envelope);
//                System.out.println("properties="+properties);
                System.out.println("body="+new String(body,"utf-8"));
                //手动确认消息
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //false 关闭自动确认
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
