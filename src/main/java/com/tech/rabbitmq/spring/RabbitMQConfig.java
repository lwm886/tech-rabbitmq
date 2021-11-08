package com.tech.rabbitmq.spring;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lw
 * @since 2021/10/30
 */
@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME="exchange_order";
    public static final String QUEUE="order_queue";

    /**
     * topic 交换机
     * @return
     */
    @Bean
    public Exchange orderExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 队列
     * @return
     */
    @Bean
    public Queue orderQueue(){
        return QueueBuilder.durable(QUEUE).build();
    }

    /**
     * 绑定队列和交换机
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding orderBinding(Queue queue,Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
    }
}
