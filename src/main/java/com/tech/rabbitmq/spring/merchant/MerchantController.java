package com.tech.rabbitmq.spring.merchant;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lw
 * @since 2021/11/2
 */
//@RestController
public class MerchantController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("check")
    Object check(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.NEW_MERCHANT_EXCHANGE,
                RabbitMQConfig.NEW_MERCHANT_ROUTING_KEY,"商家账号审核通过");
        Map<String,Object> res=new HashMap<>();
        res.put("code",200);
        res.put("msg","商家账号审核通过，请10s内上传一个商品");
        return res;
    }
}
