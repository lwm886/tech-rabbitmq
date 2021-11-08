package com.tech.rabbitmq.nospring.work.qos;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Recv1 {
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

        int fetchCount=1;
        //设置RabbitMQ给消费者发送fetchCount条消息后，需要等待有消息确认时，再继续给该消费者发送消息，
        // 发送的消息数量为消费者fetchCount-当前未消费者未ack的消息数
        //当fetchCount为1时，则RabbitMQ给消费者发送1条消息，等待这条消息ack后才会发下1条消息
        channel.basicQos(fetchCount);

        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    TimeUnit.SECONDS.sleep(1);
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
