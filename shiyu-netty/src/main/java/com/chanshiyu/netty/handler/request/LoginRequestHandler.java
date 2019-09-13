package com.chanshiyu.netty.handler.request;

import com.chanshiyu.netty.protocol.request.LoginRequestPacket;
import com.chanshiyu.netty.session.Session;
import com.chanshiyu.netty.session.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author shiyu
 * @date 2019/9/13 14:41
 * @Description
 */
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestHandler extends DisruptorRequestHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Session session = SessionUtil.getSession(ctx.channel());
        if (session != null) {
            log.info("解绑IM信息 -> {}", session);
            SessionUtil.unBindSession(ctx.channel());
        }
    }

}
