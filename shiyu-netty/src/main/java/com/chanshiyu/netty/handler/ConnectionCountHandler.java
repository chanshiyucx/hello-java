package com.chanshiyu.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shiyu
 * @date 2019/9/12 10:36
 * @Description: 链接检查
 */
@Slf4j
public class ConnectionCountHandler extends ChannelInboundHandlerAdapter {

    private AtomicInteger mConnectionCount = new AtomicInteger();

    public ConnectionCountHandler() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("connections: {}", mConnectionCount.get());
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        mConnectionCount.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        mConnectionCount.decrementAndGet();
    }

}