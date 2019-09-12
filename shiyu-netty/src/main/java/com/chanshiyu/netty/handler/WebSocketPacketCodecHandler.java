package com.chanshiyu.netty.handler;

import com.chanshiyu.netty.codec.PacketCodec;
import com.chanshiyu.netty.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;

/**
 * @author shiyu
 * @date 2019/9/12 10:42
 * @Description: 编解码器
 */
@ChannelHandler.Sharable
public class WebSocketPacketCodecHandler extends MessageToMessageCodec<TextWebSocketFrame, Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.encode(packet));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
        out.add(PacketCodec.INSTANCE.decode(msg));
    }

}