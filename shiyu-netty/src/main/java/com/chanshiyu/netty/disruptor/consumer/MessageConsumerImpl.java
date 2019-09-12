package com.chanshiyu.netty.disruptor.consumer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.chanshiyu.netty.protocol.command.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author shiyu
 * @date 2019/9/12 15:18
 * @Description
 */
@Slf4j
public class MessageConsumerImpl extends MessageConsumer {

    // 时间
    static SimpleDateFormat ChatDateFormat;

    static {
        ChatDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        ChatDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    @Override
    public void onEvent(TranslatorDataWrapper event) {
        super.onEvent(event);
        // 上下文
        ChannelHandlerContext ctx = event.getCtx();
        Channel channel = ctx.channel();
        // 命令字
        Integer command = event.getPacket().getCommand();
    }

}
