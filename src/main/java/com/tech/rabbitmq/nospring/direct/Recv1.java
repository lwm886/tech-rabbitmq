package com.tech.rabbitmq.nospring.direct;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author lw
 * @since 2021/10/25
 */
public class Recv1 {
    private final static String EXCHANGE_NAME = "exchange_direct";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.50.134");
        factory.setPort(5672);
        factory.setVirtualHost("/dev");
        factory.setUsername("tech");
        factory.setPassword("tech");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机和队列,指定routingKey
        channel.queueBind(queueName, EXCHANGE_NAME, "debugRoutingKey");
        channel.queueBind(queueName, EXCHANGE_NAME, "infoRoutingKey");
        channel.queueBind(queueName, EXCHANGE_NAME, "errorRoutingKey");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body=" + new String(body, "utf-8"));
                //手动确认消息，不是多条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //false 关闭自动确认
        channel.basicConsume(queueName, false, consumer);
    }
}
