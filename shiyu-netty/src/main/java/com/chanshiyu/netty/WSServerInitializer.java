package com.chanshiyu.netty;

import com.chanshiyu.netty.handler.ConnectionCountHandler;
import com.chanshiyu.netty.handler.IMHandler;
import com.chanshiyu.netty.handler.IMIdleStateHandler;
import com.chanshiyu.netty.handler.WebSocketPacketCodecHandler;
import com.chanshiyu.netty.handler.request.HeartBeatRequestHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author shiyu
 * @date 2019/9/4 19:17
 * @Description: 初始化器，channel 注册后，会执行里面的初始化方法
 */
public class WSServerInitializer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        // 通过 SocketChannel 去获得对应的管道，通过管道添加 handler
        ChannelPipeline pipeline = nioSocketChannel.pipeline();

        /**
         * ==========================================================================
         *                             以下用于支持 http 协议
         * ==========================================================================
         */

        // HttpServerCodec 是由 netty 提供的助手类，可以理解为拦截器，当请求到服务端做解码，响应到客户端做编码
        // websocket 基于 http 协议，所以要有 http 编解码器
        pipeline.addLast(new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对 httpMessage 进行聚合，聚合成 FullHttpRequest 或 FullHttpResponse，几乎在 netty 中的编程，都会使用到此 handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        /**
         * ============================================================================
         *                            websocket 服务器处理协议
         * 处理握手动作：handshaking(close, ping, pong) ping + pong = 心跳
         * 对于 websokcet 来讲，都是以 frames 进行传输的，不同的数据类型对应不同的 frames 也不同
         * ============================================================================
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(ConnectionCountHandler.INSTANCE);      // 链接检查
        pipeline.addLast(IMIdleStateHandler.INSTANCE);          // 心跳包
        pipeline.addLast(WebSocketPacketCodecHandler.INSTANCE); // 编解码
        pipeline.addLast(HeartBeatRequestHandler.INSTANCE);     // 心跳
        pipeline.addLast(IMHandler.INSTANCE);                   // 具体业务
        // pipeline.addLast(new ChatHandler());                 // 消息测试
    }

}
