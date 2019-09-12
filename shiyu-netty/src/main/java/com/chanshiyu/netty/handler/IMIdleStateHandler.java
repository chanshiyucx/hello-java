package com.chanshiyu.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author shiyu
 * @date 2019/9/12 10:26
 * @Description: 心跳包
 */
@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 180;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("新增链接 ip -> {}", ipSocket != null ? ipSocket.getAddress().getHostAddress() : "0.0.0.0");
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        ctx.channel().close();
        log.info(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
    }

}
