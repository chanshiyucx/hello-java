package com.chanshiyu.netty.disruptor.consumer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.chanshiyu.netty.protocol.command.Command;
import com.chanshiyu.netty.protocol.request.MessageRequestPacket;
import com.chanshiyu.netty.protocol.response.MessageResponsePacket;
import com.chanshiyu.netty.protocol.response.MessageSuccessResponsePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
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
        log.info("onEvent-->{}", command);
        switch (command) {
            case Command.MESSAGE_REQUEST:
                // 消息处理
                msg(ctx, (MessageRequestPacket) event.getPacket());
                break;
            default:
                log.error("command -> {}, 该消息未被处理", command);
                break;
        }
    }

    /**
     * 消息处理
     * @param ctx
     * @param packet
     */
    private void msg(ChannelHandlerContext ctx, MessageRequestPacket packet) {
        log.info("收到一条消息");
        Channel channel = ctx.channel();
        int msgIndex = packet.getMsgIndex();
        sendMsgSuccess(channel, msgIndex, 10, new Date().getTime());
//        Session session = SessionUtil.getSession(ctx.channel());
//        String sendImUserId = session.getImUserId();
//        String sendAvatar = MessageCommonUtil.getImUserAvatar(session.getNodeId(), session.getThirdPartyId());
//        String acceptUserId = getClientAcceptUserId(packet, session);
//        // 不选择问题，直接发言
//        speakDirectly(channel, session);
//        // 消息处理
//        int msgId;
//        Date sysTimer = new Date();
//        msgId = messageProcessing(channel, packet, session, sendImUserId,sendAvatar, acceptUserId, sysTimer);
//        // 最后一次聊天时间
//        session.setLastSendMsgDate(new Date());
//        // 消息发送成功
//        MessageCommonUtil.sendMsgSuccess(ctx.channel(), msgIndex, msgId, sysTimer.getTime());
//        // 记录最后的的发送时间
//        String time = ChatDateFormat.format(new Date());
//        RedisOperator operator = SpringUtil.getBean(RedisOperator.class);
//        operator.set(String.format(IM_LAST_CHAT_TIMER, sendImUserId), time);

    }

    /**
     * 消息发送成功
     */
    public static void sendMsgSuccess(Channel channel, int msgIndex, int msgId, long date) {
        channel.writeAndFlush(new MessageSuccessResponsePacket(msgId, msgIndex, date));
    }


}
