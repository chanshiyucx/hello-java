package com.chanshiyu.netty.handler;

import com.chanshiyu.netty.handler.request.CreateRoomRequestHandler;
import com.chanshiyu.netty.handler.request.LoginRequestHandler;
import com.chanshiyu.netty.handler.request.MessageRequestHandler;
import com.chanshiyu.netty.protocol.Packet;
import com.chanshiyu.netty.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shiyu
 * @date 2019/9/12 11:15
 * @Description: 具体业务
 */
@Slf4j
@ChannelHandler.Sharable
public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Integer, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler() {
        handlerMap = new HashMap<>();
        // 登陆
        handlerMap.put(Command.LOGIN_REQUEST, LoginRequestHandler.INSTANCE);
        // 创建房间
        handlerMap.put(Command.CREATE_ROOM_REQUEST, CreateRoomRequestHandler.INSTANCE);
        // 发送消息
        handlerMap.put(Command.SEND_MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("packet.getCommand()-->{}", packet.getCommand());
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        log.info("服务器关闭IM");
    }

}
