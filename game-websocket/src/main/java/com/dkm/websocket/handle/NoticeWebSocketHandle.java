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


   @Override
   protected void channelRead0(ChannelHandlerContext channelHandlerContext, MsgInfo msgInfo) throws Exception {

   }
}
