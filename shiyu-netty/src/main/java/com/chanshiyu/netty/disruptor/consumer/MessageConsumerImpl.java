package com.chanshiyu.netty.disruptor.consumer;

import com.chanshiyu.netty.disruptor.wapper.TranslatorDataWrapper;
import com.chanshiyu.netty.protocol.command.Command;
import com.chanshiyu.netty.protocol.request.CreateRoomRequestPacket;
import com.chanshiyu.netty.protocol.request.LoginRequestPacket;
import com.chanshiyu.netty.protocol.request.MessageRequestPacket;
import com.chanshiyu.netty.protocol.response.CreateRoomResponsePacket;
import com.chanshiyu.netty.protocol.response.LoginResponsePacket;
import com.chanshiyu.netty.protocol.response.MessageSuccessResponsePacket;
import com.chanshiyu.netty.session.Session;
import com.chanshiyu.netty.session.SessionUtil;
import com.chanshiyu.pojo.Room;
import com.chanshiyu.pojo.Users;
import com.chanshiyu.service.RoomService;
import com.chanshiyu.service.UserService;
import com.chanshiyu.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
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
            case Command.LOGIN_REQUEST:
                // 登陆处理
                login(ctx, (LoginRequestPacket) event.getPacket());
                break;
            case Command.CREATE_ROOM_REQUEST:
                createRoom(ctx, (CreateRoomRequestPacket) event.getPacket());
                break;
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
     * login处理
     */
    private void login(ChannelHandlerContext ctx, LoginRequestPacket packet) {
        Session session = new Session(packet.getUserId(), packet.getUsername(), packet.getNickname());
        SessionUtil.bindSession(session, ctx.channel());
        LoginResponsePacket response = new LoginResponsePacket(packet.getUserId(), packet.getUsername(), packet.getNickname(),true, "登录成功");
        ctx.writeAndFlush(response);
    }

    /**
     * 创建房间
     */
    private void createRoom(ChannelHandlerContext ctx, CreateRoomRequestPacket packet)  {
        RoomService roomService = SpringUtil.getBean(RoomService.class);
        UserService userService = SpringUtil.getBean(UserService.class);
        String users = packet.getUsers();
        // 获取或创建房间
        Room room = roomService.queryRoomByUsers(users);
        if (room != null) {
            // 返回已存在房间
            CreateRoomResponsePacket response = new CreateRoomResponsePacket(room.getId(), room.getName(), room.getUsers(), room.getCreateUser(), room.getCreateTime(), room.getIcon());
            ctx.writeAndFlush(response);
        } else {
            try {
                // 创建新房间
                Room newRoom = new Room();
                newRoom.setName(packet.getName());
                newRoom.setUsers(packet.getUsers());
                newRoom.setCreateUser(packet.getCreateUserId());
                // 取房间第一个用户头像为图标
                String[] usersStr = packet.getUsers().split(",");
                String userId = usersStr[0];
                Users users1 = userService.queryUserById(userId);
                if (users1 != null) {
                    newRoom.setIcon(users1.getAvatar());
                }
                Room result = roomService.createRoom(newRoom);
                CreateRoomResponsePacket response = new CreateRoomResponsePacket(result.getId(), result.getName(), result.getUsers(), result.getCreateUser(), result.getCreateTime(), result.getIcon());
                ctx.writeAndFlush(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 消息处理
     */
    private void msg(ChannelHandlerContext ctx, MessageRequestPacket packet) {
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
