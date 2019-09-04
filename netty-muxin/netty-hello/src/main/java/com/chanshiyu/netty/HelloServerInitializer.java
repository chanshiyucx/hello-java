package com.chanshiyu.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author shiyu
 * @date 2019/9/4 14:53
 * @Description: 初始化器，channel 注册后，会执行里面的初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // 通过 SocketChannel 去获得对应的管道
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 通过管道，添加 handler
        // HttpServerCodec 是由 netty 提供的助手类，可以理解为拦截器，当请求到服务端做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 添加自定义的助手类，返回 “Hello netty”
        pipeline.addLast("CustomHandler", new CustomHandler());
    }
}
