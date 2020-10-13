package com.dkm.websocket.handle;

import com.alibaba.fastjson.JSON;
import com.dkm.entity.websocket.MsgInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * 家族处理handle
 * @author qf
 * @date 2020/6/1
 * @vesion 1.0
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class FamilyWebSocketHandle extends SimpleChannelInboundHandler<MsgInfo> {

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Override
   protected void channelRead0(ChannelHandlerContext ctx, MsgInfo msgInfo) {
         if (msgInfo.getType() == 5) {
            //该消息是家族的信息
            rabbitTemplate.convertAndSend("game_family_info_queue", JSON.toJSONString(msgInfo));
            log.info("收到一条家族消息:" +msgInfo);
         } else {
            //不是家族消息，继续透传
            ctx.fireChannelRead(msgInfo);
         }
   }
}
