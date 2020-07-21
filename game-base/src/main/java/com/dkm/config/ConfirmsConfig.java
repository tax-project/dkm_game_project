package com.dkm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
public class ConfirmsConfig implements RabbitTemplate.ConfirmCallback {


   @Autowired
   private RabbitTemplate rabbitTemplate;


   @PostConstruct
   public void init () {
      rabbitTemplate.setConfirmCallback(this);
   }


   @Override
   public void confirm(CorrelationData correlationData, boolean ack, String cause) {
      log.info("confirm 确认结果:[{}]", ack);
      log.info("confirm 失败原因:[{}]",cause);
   }
}
