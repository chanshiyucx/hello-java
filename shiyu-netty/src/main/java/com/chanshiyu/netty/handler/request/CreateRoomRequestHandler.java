package com.chanshiyu.netty.handler.request;

import com.chanshiyu.netty.protocol.request.CreateRoomRequestPacket;
import io.netty.channel.ChannelHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/13 18:43
 * @Description
 */
@Slf4j
@ChannelHandler.Sharable
public class CreateRoomRequestHandler extends DisruptorRequestHandler<CreateRoomRequestPacket> {

    public static final CreateRoomRequestHandler INSTANCE = new CreateRoomRequestHandler();

    private CreateRoomRequestHandler() {}

}
