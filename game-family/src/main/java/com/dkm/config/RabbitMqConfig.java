package com.dkm.config;


import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 家族接收信息的mq
 * @author qf
 * @date 2020/6/1
 * @vesion 1.0
 **/
@Component
public class RabbitMqConfig {

   @Bean
   public FanoutExchange getFamilyFanoutExchange () {
      return new FanoutExchange("game_family_FanoutExchange");
   }

}
