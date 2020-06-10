package com.dkm.websocket.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/1
 * @vesion 1.0
 **/
@Component
public class RabbitMqQueueConfig {

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

   /**
    *  传输家族的消息队列
    * @return
    */
   @Bean
   public Queue getFamilyQueue () {
      return new Queue("game_family_info_queue",false);
   }
}
