package com.dkm.listener;

import com.alibaba.fastjson.JSONObject;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.friend.entity.vo.FriendNotOnlineVo;
import com.dkm.friend.service.IFriendNotOnlineService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qf
 * @date 2020/5/11
 * @vesion 1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "game_msg_not_online_queue")
public class MqNotOnlineListener {

   @Autowired
   private IFriendNotOnlineService friendNotOnlineService;


   @RabbitHandler
   public void getNotOnlineMsg (@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, String notOnlineMsg, Channel channel) {

      //业务操作

      //将未在线的信息存入数据库
      //当用户登录的时候取出来进行一一发送
      log.info("--接收到未在线的信息:" +notOnlineMsg);
      MsgInfo msg = null;
      try {
         msg = JSONObject.parseObject(notOnlineMsg, MsgInfo.class);
      } catch (Exception e) {
         e.printStackTrace();
      }

      //将数据加进数据库
      FriendNotOnlineVo vo = new FriendNotOnlineVo();
      if (msg != null) {
         vo.setFromId(msg.getFromId());
         vo.setToId(msg.getToId());
         vo.setContent(msg.getMsg());
         vo.setType(msg.getType());

         friendNotOnlineService.insertNotOnline(vo);

      }

      //确认消息
      try {
         channel.basicAck(deliveryTag,true);
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
