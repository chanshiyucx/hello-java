package com.chanshiyu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author shiyu
 * @date 2019/9/4 15:14
 * @Description: 对于请求来讲，其实相当于【入站、入境】
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        // 获取 channel
        Channel channel = channelHandlerContext.channel();

        // 显示客户端的远程地址
        if (httpObject instanceof HttpRequest) {
            System.out.println(channel.remoteAddress());
        }

        // 定义发送的数据消息
        ByteBuf content = Unpooled.copiedBuffer("Hello netty", CharsetUtil.UTF_8);

        // 构建 http response
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        // 未响应增加数据类型和长度
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

        // 把响应刷到客户端
        channel.writeAndFlush(response);
    }

}
