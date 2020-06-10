package com.dkm.listener;

<<<<<<< HEAD
=======

import lombok.extern.slf4j.Slf4j;
>>>>>>> 4a164cc7ad3ee5f58ef0281ef7cc0c6e20572302

import lombok.extern.slf4j.Slf4j;

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
