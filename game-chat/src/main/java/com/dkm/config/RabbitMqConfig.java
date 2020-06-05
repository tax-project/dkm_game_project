package com.dkm.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Configuration
public class RabbitMqConfig {

   @Bean
   public FanoutExchange fanoutExchange () {
      return new FanoutExchange("game_msg_fanoutExchange");
   }
}
