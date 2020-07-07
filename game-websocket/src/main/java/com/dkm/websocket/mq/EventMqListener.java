package com.dkm.websocket.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.utils.StringUtils;
import com.dkm.websocket.entity.SenMsg;
import com.dkm.websocket.utils.GroupUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qf
 * @date 2020/7/7
 * @vesion 1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "game_event_notice")
public class EventMqListener {

   @Autowired
   private RedisTemplate redisTemplate;

   @Autowired
   private GroupUtils groupUtils;

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @RabbitHandler
   public void rabbitHandle (String msg) {

      MsgInfo msgInfo = null;
      try {
         msgInfo = JSONObject.parseObject(msg, MsgInfo.class);
      } catch (Exception e) {
         log.info("rabbitMq接收业务平台的消息转换有误...");
         e.printStackTrace();
      }

      if (msgInfo.getType() == 6) {
         //家族群聊消息

         //去redis中找设备id
         String cid = (String) redisTemplate.opsForValue().get(msgInfo.getToId());

         if (cid == null || "".equals(cid)) {
            log.error("redis中未找到对应的设备ID,有可能是对方未在线,将事件消息通过mq发送存入数据库");
            rabbitTemplate.convertAndSend("game_msg_not_online_queue",msg);
            return;
         }
         Channel channel = groupUtils.getChannel(cid);

         if (channel == null) {
            //对方未在线，应存入rabbitMQ消息队列中，等待客户端的连接再发送消息
            //存入一个新的队列中
            log.info("未找到对应的channel,有可能是对方未在线,将事件消息通过mq发送存入数据库");
            rabbitTemplate.convertAndSend("game_msg_not_online_queue",msg);
         }

         //将消息发送给客户端
         if (channel != null) {
            log.info("发送事件消息:" + msgInfo);
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msgInfo)));
         }

      }

   }
}
