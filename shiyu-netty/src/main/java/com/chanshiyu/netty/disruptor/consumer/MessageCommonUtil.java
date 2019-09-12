package com.chanshiyu.netty.disruptor.consumer;

import com.alibaba.fastjson.JSON;
import com.chanshiyu.netty.protocol.message.ChatMessage;
import com.chanshiyu.netty.protocol.response.MessageResponsePacket;
import com.chanshiyu.util.RedisOperator;
import com.chanshiyu.util.SpringUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author shiyu
 * @date 2019/9/12 16:45
 * @Description
 */
@Slf4j
public class MessageCommonUtil {

    public static RedisOperator getRedis() {
        return SpringUtil.getBean(RedisOperator.class);
    }

    /**
     * 转发消息,批量发送
     */
    public static void sendMsg(Channel channel, ChatMessage msg) {
        String json = JSON.toJSONString(msg);
        MessageResponsePacket packet = new MessageResponsePacket(0, null, json, false, new Date().getTime());
        channel.writeAndFlush(packet);
    }

    public static void sendMsg(ConcurrentLinkedQueue<Channel> channels, ChatMessage msg) {
        sendMsg(channels, msg, null,null);
    }

    public static void sendMsg(ConcurrentLinkedQueue<Channel> channels, ChatMessage msg, String sendUserId,String sendAvatar) {
        String json = JSON.toJSONString(msg);
        sendMsg(channels, json, sendUserId,sendAvatar);
    }

    public static void sendMsg(ConcurrentLinkedQueue<Channel> channels, String json, String sendUserId,String sendAvatar) {
        sendMsg(channels, json, sendUserId,sendAvatar, new Date().getTime());
    }

    public static void sendMsg(ConcurrentLinkedQueue<Channel> channels, String json, String sendUserId,String sendAvatar, long time) {
        MessageResponsePacket packet = new MessageResponsePacket(0, sendUserId, json, false, time);
        if (channels != null && channels.size() > 0) {
            channels.forEach(channel -> channel.writeAndFlush(packet));
        }
    }

}
