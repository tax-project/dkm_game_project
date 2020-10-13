package com.dkm.websocket.handle;

import com.dkm.entity.websocket.MsgInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author qf
 * @date 2020/6/6
 * @vesion 1.0
 **/
@Slf4j
@Component
@ChannelHandler.Sharable
public class NoticeWebSocketHandle extends SimpleChannelInboundHandler<MsgInfo> {

   /**
    *  起始通知值
    */
   private Integer startType = 6;

   /**
    * 结束通知值
    */
   private Integer endType = 10;


   @Override
   protected void channelRead0(ChannelHandlerContext ctx, MsgInfo msgInfo) {

      if (msgInfo.getType() >= startType && msgInfo.getType() <= endType) {
         log.info("收到即使通知:" + msgInfo);

         if (msgInfo.getType().equals(startType)) {
            //----即使通知消息
         }

         if (msgInfo.getType() == 7) {
            //----即使通知消息
         }
      } else {
         //不是即使通知消息，继续透传
         ctx.fireChannelRead(msgInfo);
      }
   }
}
