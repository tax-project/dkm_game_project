package com.dkm.confirms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author qf
 * @date 2020/4/9
 * @vesion 1.0
 **/
@Component
@Slf4j
public class ReturnConfig implements RabbitTemplate.ReturnCallback {

   @Autowired
   private RabbitTemplate rabbitTemplate;


   @PostConstruct
   public void init () {
      rabbitTemplate.setReturnCallback(this);
   }


   @Override
   public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
      log.info("ReturnConfig 消息:[{}]", message);
      log.info("ReturnConfig 交换机:[{}]",exchange);
      log.info("ReturnConfig 路由键:[{}]",routingKey);
   }
}
