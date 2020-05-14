package com.dkm.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dkm.entity.websocket.MsgInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "msg_chat_queue")
public class MqListener {

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Autowired
   private RedisTemplate redisTemplate;

   @RabbitHandler
   public void msg (@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, String msgInfo, Channel channel) {
      log.info("通讯平台收到服务器的消息:" +msgInfo);

      //以下是群聊的测试
//      List<String> newList = new ArrayList<>();
//      newList.add("001");
//      newList.add("002");
//      redisTemplate.opsForList().rightPushAll(2+1L,newList);

      //一些业务操作之后
      //将消息传给客户端
      //此处也要重新申明交换机，不然生产者找不到交换机
      //广播形式发给所有服务器
      rabbitTemplate.convertAndSend("msg_fanoutExchange","",msgInfo);

      //确认消息
      try {
         channel.basicAck(deliveryTag,true);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
