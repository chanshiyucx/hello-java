package com.chanshiyu.netty.disruptor.producer;

import com.chanshiyu.netty.protocol.Packet;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/12 15:04
 * @Description
 */
@Slf4j
public class MessageProducer {

    /**
     * 发布事件
     * @param packet 应用包
     * @param ctx    上下文
     */
    public void publish(Packet packet, ChannelHandlerContext ctx) {
        log.info("生成消息 -> {}",packet.getCommand());
    }

}
