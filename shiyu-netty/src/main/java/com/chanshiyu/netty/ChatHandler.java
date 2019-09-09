package com.chanshiyu.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @author shiyu
 * @date 2019/9/4 19:47
 * @Description: 处理消息的 handler
 * TextWebSocketFrame: 在 netty 中，是用于为 websocket 专门处理文本的对象，frame 是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用于记录和管理所有客户端的 channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        // 获取客户端传输过来的消息
        String content = textWebSocketFrame.text();
        System.out.println("接受数据消息：" + content);

        // for (Channel client : clients) {
        //    client.writeAndFlush(new TextWebSocketFrame("[接收消息" + LocalDateTime.now() + "]" + content));
        // }

        // 此方法给全部 channel 刷入消息，和上面循环一致
        clients.writeAndFlush(new TextWebSocketFrame("[接收消息" + LocalDateTime.now() + "] " + content));
    }

    /**
     * 当客户端连接服务器之后（打开连接）
     * 获取客户端的 channel，并且放到 ChannelGroup 中去管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发 handlerRemoved 时，ChannelGroup 会自动移除对应客户端的 channel，无需手动移除
        // clients.remove(ctx.channel());
        System.out.println("客户端断开连接，channel 对应的长 id 为：" + ctx.channel().id().asLongText());
    }
}
