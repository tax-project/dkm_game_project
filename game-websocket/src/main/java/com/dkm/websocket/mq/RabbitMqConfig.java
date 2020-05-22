package com.dkm.websocket.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @author qf
 * @date 2020/3/31
 * @vesion 1.0
 **/
@Component
public class RabbitMqConfig {

   @Bean
   public Queue getQueue () {
      return new Queue ("game_msg_queue_");
   }

   @Bean
   public Queue getWebQueue () {
      return new Queue("game_msg_chat_queue");
   }

   @Bean
   public Queue getNotOnlineQueue () {
      return new Queue("game_msg_not_online_queue");
   }

   @Bean
   public FanoutExchange getFanoutExchange () {
      return new FanoutExchange("game_msg_fanoutExchange");
   }

   @Bean
   public Binding getBinding (Queue getQueue, FanoutExchange getFanoutExchange) {
      return BindingBuilder.bind(getQueue).to(getFanoutExchange);
   }
}
