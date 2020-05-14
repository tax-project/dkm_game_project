package com.dkm.websocket.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.ChannelMatcher;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 此类为群聊的工具类
 * @author qf
 * @date 2020/4/3
 * @vesion 1.0
 **/
@Component
public class ChannelManyGroups {

   ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);

   public void addList(List<Channel> channelList) {

      for (Channel channel : channelList) {
         CHANNEL_GROUP.add(channel);
      }
   }

   public void add(Channel channel) {
      CHANNEL_GROUP.add(channel);
   }

   public ChannelGroupFuture broadcast(Object msg) {
      return CHANNEL_GROUP.writeAndFlush(msg);
   }

   public ChannelGroupFuture broadcast(Object msg, ChannelMatcher matcher) {
      return CHANNEL_GROUP.writeAndFlush(msg, matcher);
   }

   public ChannelGroup flush() {
      return CHANNEL_GROUP.flush();
   }

   public boolean discard(Channel channel) {
      return CHANNEL_GROUP.remove(channel);
   }

   public ChannelGroupFuture disconnect() {
      return CHANNEL_GROUP.disconnect();
   }

   public ChannelGroupFuture disconnect(ChannelMatcher matcher) {
      return CHANNEL_GROUP.disconnect(matcher);
   }

   public boolean contains(Channel channel) {
      return CHANNEL_GROUP.contains(channel);
   }

   public int size() {
      return CHANNEL_GROUP.size();
   }
}
