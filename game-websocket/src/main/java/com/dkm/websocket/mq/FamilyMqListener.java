package com.dkm.websocket.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.utils.StringUtils;
import com.dkm.websocket.entity.SenMsg;
import com.dkm.websocket.utils.ChannelManyGroups;
import com.dkm.websocket.utils.GroupUtils;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qf
 * @date 2020/6/5
 * @vesion 1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "game_family_queue_")
public class FamilyMqListener {


   @Autowired
   private RedisTemplate redisTemplate;

   @Autowired
   private GroupUtils groupUtils;

   @Autowired
   private ChannelManyGroups channelManyGroups;

   @RabbitHandler
   public void rabbitHandle (String msg, com.rabbitmq.client.Channel mqChannel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {

      MsgInfo msgInfo = null;
      try {
         msgInfo = JSONObject.parseObject(msg, MsgInfo.class);
      } catch (Exception e) {
         log.info("rabbitMq接收业务平台的消息转换有误...");
         e.printStackTrace();
      }

      if (msgInfo.getType() == 5) {
         //家族群聊消息
         //去redis中取出所有设备ID，找到channel通道的集合
         List<Channel> channels = new ArrayList<>();
         //去redis中找设备id
         for (Long id : msgInfo.getToIdList()) {
            String cid = null;
            if (id != null) {
               cid = (String) redisTemplate.opsForValue().get(id);
            }
            //将除了自己以外的所有群聊人员都发消息
            if (StringUtils.isNotBlank(cid)) {
               Channel channel = groupUtils.getChannel(cid);
               if (channel != null) {
                  channels.add(channel);
               }
            }
         }

         //将建立群聊的channel管理起来
         channelManyGroups.addList(channels);

         SenMsg senMsg = new SenMsg();
         BeanUtils.copyProperties(msgInfo, senMsg);

         //将消息群发
         log.info("家族消息群发:" +senMsg);
         channelManyGroups.broadcast(new TextWebSocketFrame(JSON.toJSONString(senMsg)));

         try {
            mqChannel.basicAck(deliveryTag,true);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

   }
}
