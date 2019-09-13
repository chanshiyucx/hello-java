package com.chanshiyu.netty.handler.request;

import com.chanshiyu.netty.protocol.request.MessageRequestPacket;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/12 18:44
 * @Description
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageRequestHandler extends DisruptorRequestHandler<MessageRequestPacket> {

    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {}

}
