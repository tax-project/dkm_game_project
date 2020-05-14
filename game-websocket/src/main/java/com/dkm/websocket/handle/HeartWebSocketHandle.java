package com.dkm.websocket.handle;

import com.dkm.entity.websocket.MsgInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/4/2
 * @vesion 1.0
 **/
@Component
@Slf4j
@ChannelHandler.Sharable
public class HeartWebSocketHandle extends SimpleChannelInboundHandler<MsgInfo> {


   @Override
   protected void channelRead0(ChannelHandlerContext ctx, MsgInfo msgInfo) throws Exception {
      if (msgInfo.getType() == 2) {
         //当前是一个心跳信息
         log.info("收到心跳消息");
      } else {
         //非心跳消息
         ctx.fireChannelRead(msgInfo);
      }
   }
}
