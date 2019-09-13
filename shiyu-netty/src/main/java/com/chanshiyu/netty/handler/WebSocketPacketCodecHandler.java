package com.chanshiyu.netty.handler;

import com.chanshiyu.netty.codec.PacketCodec;
import com.chanshiyu.netty.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/12 10:42
 * @Description: 编解码器
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketPacketCodecHandler extends MessageToMessageCodec<TextWebSocketFrame, Packet> {

    public static final WebSocketPacketCodecHandler INSTANCE = new WebSocketPacketCodecHandler();

    private WebSocketPacketCodecHandler() {}

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        log.info("encode -->{}", packet);
        out.add(PacketCodec.INSTANCE.encode(packet));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
        log.info("decode --> {}", msg.text());
        out.add(PacketCodec.INSTANCE.decode(msg));
    }

}