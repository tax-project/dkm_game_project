package com.dkm.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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
      return new FanoutExchange("game_msg_fanoutExchange",false,false);
   }


   /**
    *  接收客户端单聊群聊的消息队列
    * @return
    */
   @Bean
   public Queue getWebQueue () {
      return new Queue("game_msg_chat_queue",false);
   }

   /**
    *  传输用户不在线的消息队列
    * @return
    */
   @Bean
   public Queue getNotOnlineQueue () {
      return new Queue("game_msg_not_online_queue",false);
   }
}
