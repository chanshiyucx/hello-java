package com.chanshiyu.netty;

import com.chanshiyu.netty.WSServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author shiyu
 * @date 2019/9/4 18:40
 * @Description websocket 服务器
 */
@Component
public class WSServer {

    private EventLoopGroup mainGroup;

    private EventLoopGroup subGroup;

    private  ServerBootstrap server;

    private ChannelFuture channelFuture;

    private static class SingletionWSServer {
        static final WSServer instance = new WSServer();
    }

    public static WSServer getInstance() {
        return SingletionWSServer.instance;
    }

    public WSServer() {
        // 主线程组，用于接收客户端连接，不做任何处理
        mainGroup = new NioEventLoopGroup();
        // 从线程组，专门处理主线程组的任务
        subGroup = new NioEventLoopGroup();
        // netty 服务器
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)                 // 设置主从线程组
                .channel(NioServerSocketChannel.class)    // 设置 nio 的双向通道
                .childHandler(new WSServerInitializer()); // 子处理器，用于处理 subGroup
    }

    public void start() {
        this.channelFuture = server.bind(8088);
        System.out.println("netty websocket server 启动完毕...");
    }

}
