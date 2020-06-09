package com.dkm.websocket.mq.config;

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

   /**
    *  接收通讯服务器的消息队列
    * @return
    */
   @Bean
   public Queue getQueue () {
      return new Queue ("game_msg_queue_",false);
   }

   /**
    *  交换机
    * @return
    */
   @Bean
   public FanoutExchange getFanoutExchange () {
      return new FanoutExchange("game_msg_fanoutExchange",false,false);
   }

   @Bean
   public Binding getBinding (Queue getQueue, FanoutExchange getFanoutExchange) {
      return BindingBuilder.bind(getQueue).to(getFanoutExchange);
   }

}
