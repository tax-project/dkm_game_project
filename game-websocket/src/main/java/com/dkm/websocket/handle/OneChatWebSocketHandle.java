package com.dkm.websocket.handle;

import com.alibaba.fastjson.JSON;
import com.dkm.entity.websocket.MsgInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/4/2
 * @vesion 1.0
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class OneChatWebSocketHandle extends SimpleChannelInboundHandler<MsgInfo> {

   @Autowired
   private RabbitTemplate rabbitTemplate;

   @Override
   protected void channelRead0(ChannelHandlerContext ctx, MsgInfo msgInfo) throws Exception {
      if (msgInfo.getType() == 3) {
         //此消息是一个单聊消息
         rabbitTemplate.convertAndSend("game_msg_chat_queue", JSON.toJSONString(msgInfo));
         log.info("收到一条单聊消息---" + msgInfo);
      } else {
         //不是单聊消息，继续透传
         ctx.fireChannelRead(msgInfo);
      }
   }
}
