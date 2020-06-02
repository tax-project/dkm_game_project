package com.dkm.websocket.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/1
 * @vesion 1.0
 **/
@Component
public class RabbitMqFamilyConfig {

   @Bean
   public Queue getFamilyWithQueue() {
      return new Queue("game_family_queue_");
   }

   @Bean
   public FanoutExchange getFamilyFanoutExchange () {
      return new FanoutExchange("game_family_FanoutExchange");
   }

   @Bean
   public Binding getQueueWithExchange (Queue getFamilyWithQueue, FanoutExchange getFamilyFanoutExchange) {
      return BindingBuilder.bind(getFamilyWithQueue).to(getFamilyFanoutExchange);
   }
}
