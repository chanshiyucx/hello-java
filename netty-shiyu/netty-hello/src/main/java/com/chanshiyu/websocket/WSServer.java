package com.chanshiyu.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author shiyu
 * @date 2019/9/4 18:40
 * @Description websocket 服务器
 */
public class WSServer {

    public static void main(String[] args) throws Exception {
        // 定义一对线程组
        // 主线程组，用于接收客户端连接，不做任何处理
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        // 从线程组，专门处理主线程组的任务
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            // netty 服务器的创建，ServerBootstrap 是一个启动类
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)                 // 设置主从线程组
                    .channel(NioServerSocketChannel.class)    // 设置 nio 的双向通道
                    .childHandler(new WSServerInitializer()); // 子处理器，用于处理 subGroup
            // 启动 Server，并且设置 8088 为启动端口号，同时启动方式为同步
            ChannelFuture channelFuture = server.bind(8088).sync();
            // 监听关闭的 channel，设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }

}
