package com.dkm.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkm.entity.websocket.MsgInfo;
import com.dkm.family.dao.FamilyDetailDao;
import com.dkm.family.entity.FamilyDetailEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qf
 * @date 2020/6/1
 * @vesion 1.0
 **/
@Slf4j
@Component
@RabbitListener(queues = "game_family_info_queue")
public class MqListener {

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Resource
   private FamilyDetailDao familyDetailDao;

   @RabbitHandler
   public void msg (@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, String msgInfo, Channel channel) {
      log.info("收到服务器传来的家族信息:" +msgInfo);

      //判断是不是家族消息
      MsgInfo info = null;
      try {
         info = JSONObject.parseObject(msgInfo, MsgInfo.class);
      } catch (Exception e) {
         log.info("rabbitMq接收业务平台的消息转换有误...");
         e.printStackTrace();
      }

      if (info.getType() == 5) {
         //家族消息
         List<Long> longList = new ArrayList<>();
         if (info.getManyChatId() != null) {
            List<FamilyDetailEntity> list = familyDetailDao.selectList(new QueryWrapper<FamilyDetailEntity>().lambda().eq(FamilyDetailEntity::getFamilyId,info.getManyChatId()));
            list.forEach(a->longList.add(a.getUserId()));
//            List<ManyChatInfo> list = manyChatInfoService.getManyChatInfoList(info.getManyChatId());
//            for (ManyChatInfo chatInfo : list) {
//               longList.add(chatInfo.getUserId());
//            }
         }
         info.setToIdList(longList);
         msgInfo = JSON.toJSONString(info);
      }

      //一些业务操作之后
      //将消息传给客户端
      //此处也要重新申明交换机，不然生产者找不到交换机
      //广播形式发给所有服务器的家族消息
      rabbitTemplate.convertAndSend("game_family_FanoutExchange","",msgInfo);

      //确认消息
      try {
         channel.basicAck(deliveryTag,true);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
