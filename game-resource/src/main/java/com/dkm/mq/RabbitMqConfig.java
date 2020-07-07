package com.dkm.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/7/7
 * @vesion 1.0
 **/
@Slf4j
@Component
public class RabbitMqConfig {

   @Bean
   public Queue getRabbitQueue () {
      return new Queue("game_event_notice", false);
   }
}
