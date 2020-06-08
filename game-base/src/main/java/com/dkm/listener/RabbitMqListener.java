package com.dkm.listener;

import com.alibaba.fastjson.JSONObject;
import com.dkm.entity.websocket.MsgInfo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author qf
 * @date 2020/6/8
 * @vesion 1.0
 **/
@Slf4j
public class RabbitMqListener {


//   @RabbitHandler
//   public void getMsg (@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, String msgInfo, Channel channel) {
//      log.info("通讯平台收到服务器的消息:" +msgInfo);
//
//      //判断是不是群聊消息
//      MsgInfo info = null;
//      try {
//         info = JSONObject.parseObject(msgInfo, MsgInfo.class);
//      } catch (Exception e) {
//         log.info("rabbitMq接收业务平台的消息转换有误...");
//         e.printStackTrace();
//      }
//
//
//
//      //确认消息
//      try {
//         channel.basicAck(deliveryTag,true);
//      } catch (IOException e) {
//         e.printStackTrace();
//      }
//   }
}
